package committee.nova.avaritia_delight.common.block.entity;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.crafting.recipe.CropExtractorRecipe;
import committee.nova.avaritia_delight.common.menu.CropExtractorMenu;
import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

import java.util.Optional;

@EventBusSubscriber(modid = AvaritiaDelight.MOD_ID)
public class CropExtractorBlockEntity extends BlockEntity implements MenuProvider {

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT_1 = 1;
    public static final int OUTPUT_SLOT_2 = 2;
    public static final int OUTPUT_SLOT_3 = 3;
    public static final int OUTPUT_SLOT_4 = 4;
    public static final int INVENTORY_SIZE = 5;

    private final ItemStackHandler inventory = createHandler();
    private final IItemHandler inputHandler = new InputItemHandler(inventory);
    private final IItemHandler outputHandler = new OutputItemHandler(inventory);

    private int extractionTime;
    private int extractionTimeTotal;

    protected final ContainerData dataAccess = createDataAccess();
    private final RecipeManager.CachedCheck<RecipeWrapper, CropExtractorRecipe> quickCheck;

    public CropExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ADBlockEntities.CROP_EXTRACTOR_BE.get(), pos, state);
        this.quickCheck = RecipeManager.createCheck(ADRecipeTypes.CROP_EXTRACTOR.get());
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ADBlockEntities.CROP_EXTRACTOR_BE.get(),
                (be, context) -> {
                    if (context == Direction.UP) {
                        return be.inputHandler;
                    } else if (context == Direction.DOWN) {
                        return be.outputHandler;
                    }
                    return be.inventory;
                }
        );
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        extractionTime = compound.getInt("ExtractionTime");
        extractionTimeTotal = compound.getInt("ExtractionTimeTotal");
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.putInt("ExtractionTime", extractionTime);
        compound.putInt("ExtractionTimeTotal", extractionTimeTotal);
        compound.put("Inventory", inventory.serializeNBT(registries));
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CropExtractorBlockEntity entity) {
        boolean didInventoryChange = false;

        if (entity.hasInput()) {
            Optional<RecipeHolder<CropExtractorRecipe>> recipe = entity.getMatchingRecipe();
            if (recipe.isPresent() && entity.canExtract(recipe.get())) {
                didInventoryChange = entity.processExtraction(recipe.get());
            } else {
                entity.extractionTime = Math.max(entity.extractionTime - 2, 0);
            }
        } else {
            entity.extractionTime = Math.max(entity.extractionTime - 2, 0);
        }

        if (didInventoryChange) {
            entity.setChanged();
        }
    }

    private Optional<RecipeHolder<CropExtractorRecipe>> getMatchingRecipe() {
        if (level == null || !hasInput()) {
            return Optional.empty();
        }
        return quickCheck.getRecipeFor(new RecipeWrapper(inventory), level);
    }

    private boolean hasInput() {
        return !inventory.getStackInSlot(INPUT_SLOT).isEmpty();
    }

    private boolean canExtract(RecipeHolder<CropExtractorRecipe> recipe) {
        if (level == null) return false;

        CropExtractorRecipe extractorRecipe = recipe.value();
        ItemStack result1 = extractorRecipe.getOutput1();
        ItemStack result2 = extractorRecipe.getOutput2();
        ItemStack result3 = extractorRecipe.getOutput3();
        ItemStack result4 = extractorRecipe.getOutput4();

        ItemStack output1Stack = inventory.getStackInSlot(OUTPUT_SLOT_1);
        ItemStack output2Stack = inventory.getStackInSlot(OUTPUT_SLOT_2);
        ItemStack output3Stack = inventory.getStackInSlot(OUTPUT_SLOT_3);
        ItemStack output4Stack = inventory.getStackInSlot(OUTPUT_SLOT_4);

        boolean slot1Valid = output1Stack.isEmpty() ||
                (ItemStack.isSameItem(output1Stack, result1) &&
                        output1Stack.getCount() + result1.getCount() <= Math.min(output1Stack.getMaxStackSize(), 64));

        boolean slot2Valid = output2Stack.isEmpty() ||
                (ItemStack.isSameItem(output2Stack, result2) &&
                        output2Stack.getCount() + result2.getCount() <= Math.min(output2Stack.getMaxStackSize(), 64));

        boolean slot3Valid = output3Stack.isEmpty() ||
                (ItemStack.isSameItem(output3Stack, result3) &&
                        output3Stack.getCount() + result3.getCount() <= Math.min(output3Stack.getMaxStackSize(), 64));

        boolean slot4Valid = output4Stack.isEmpty() ||
                (ItemStack.isSameItem(output4Stack, result4) &&
                        output4Stack.getCount() + result4.getCount() <= Math.min(output4Stack.getMaxStackSize(), 64));

        return slot1Valid && slot2Valid && slot3Valid && slot4Valid;
    }


    private boolean processExtraction(RecipeHolder<CropExtractorRecipe> recipe) {
        if (level == null) return false;

        ++extractionTime;
        extractionTimeTotal = recipe.value().getExtractionTime();

        if (extractionTime < extractionTimeTotal) {
            return false;
        }

        extractionTime = 0;

        CropExtractorRecipe extractorRecipe = recipe.value();
        ItemStack result1 = extractorRecipe.getOutput1();
        ItemStack result2 = extractorRecipe.getOutput2();
        ItemStack result3 = extractorRecipe.getOutput3();
        ItemStack result4 = extractorRecipe.getOutput4();

        ItemStack output1Stack = inventory.getStackInSlot(OUTPUT_SLOT_1);
        ItemStack output2Stack = inventory.getStackInSlot(OUTPUT_SLOT_2);
        ItemStack output3Stack = inventory.getStackInSlot(OUTPUT_SLOT_3);
        ItemStack output4Stack = inventory.getStackInSlot(OUTPUT_SLOT_4);

        if (!result1.isEmpty()) {
            if (output1Stack.isEmpty()) {
                inventory.setStackInSlot(OUTPUT_SLOT_1, result1.copy());
            } else {
                output1Stack.grow(result1.getCount());
            }
        }

        if (!result2.isEmpty()) {
            if (output2Stack.isEmpty()) {
                inventory.setStackInSlot(OUTPUT_SLOT_2, result2.copy());
            } else {
                output2Stack.grow(result2.getCount());
            }
        }

        if (!result3.isEmpty()) {
            if (output3Stack.isEmpty()) {
                inventory.setStackInSlot(OUTPUT_SLOT_3, result3.copy());
            } else {
                output3Stack.grow(result3.getCount());
            }
        }

        if (!result4.isEmpty()) {
            if (output4Stack.isEmpty()) {
                inventory.setStackInSlot(OUTPUT_SLOT_4, result4.copy());
            } else {
                output4Stack.grow(result4.getCount());
            }
        }

        inventory.getStackInSlot(INPUT_SLOT).shrink(1);

        return true;
    }

    public void dropContents() {
        if (level != null) {
            Containers.dropContents(level, worldPosition, (Container) inventory);
        }
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public ContainerData getDataAccess() {
        return dataAccess;
    }

    public int getExtractionProgress() {
        return extractionTime;
    }

    public int getExtractionTimeTotal() {
        return extractionTimeTotal;
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(INVENTORY_SIZE) {
            @Override
            protected int getStackLimit(int slot, ItemStack stack) {
                return Math.min(super.getStackLimit(slot, stack), 64);
            }

            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
                if (!level.isClientSide()) {
                    level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
                }
            }

            @Override
            public boolean isItemValid(int slot, ItemStack stack) {
                return slot == INPUT_SLOT;
            }
        };
    }

    private ContainerData createDataAccess() {
        return new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> CropExtractorBlockEntity.this.extractionTime;
                    case 1 -> CropExtractorBlockEntity.this.extractionTimeTotal;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> CropExtractorBlockEntity.this.extractionTime = value;
                    case 1 -> CropExtractorBlockEntity.this.extractionTimeTotal = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new CropExtractorMenu(containerId, playerInventory, this);
    }

    @Override
    public Component getDisplayName() {
        return ADBlocks.crop_extractor.get().getName();
    }

    private static class InputItemHandler extends ItemStackHandler {
        public InputItemHandler(ItemStackHandler handler) {
            super(handler.getSlots());
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return slot == INPUT_SLOT && super.isItemValid(slot, stack);
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            if (slot != INPUT_SLOT) return stack;
            return super.insertItem(slot, stack, simulate);
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            return ItemStack.EMPTY;
        }
    }

    private static class OutputItemHandler extends ItemStackHandler {
        public OutputItemHandler(ItemStackHandler handler) {
            super(handler.getSlots());
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return false;
        }

        @Override
        public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
            return stack;
        }

        @Override
        public ItemStack extractItem(int slot, int amount, boolean simulate) {
            if (slot == INPUT_SLOT) return ItemStack.EMPTY;
            return super.extractItem(slot, amount, simulate);
        }
    }
}
