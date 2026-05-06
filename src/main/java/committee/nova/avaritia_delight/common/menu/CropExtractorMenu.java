package committee.nova.avaritia_delight.common.menu;

import committee.nova.avaritia_delight.common.block.entity.CropExtractorBlockEntity;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import committee.nova.avaritia_delight.init.registry.ADMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

import java.util.Objects;

public class CropExtractorMenu extends AbstractContainerMenu {

    public static final int INPUT_SLOT = 0;
    public static final int OUTPUT_SLOT_1 = 1;
    public static final int OUTPUT_SLOT_2 = 2;
    public static final int OUTPUT_SLOT_3 = 3;
    public static final int OUTPUT_SLOT_4 = 4;

    private final CropExtractorBlockEntity blockEntity;
    private final ItemStackHandler inventory;
    private final ContainerLevelAccess canInteractWithCallable;
    protected final Level level;

    public CropExtractorMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getBlockEntity(playerInventory, data));
    }

    public CropExtractorMenu(final int windowId, final Inventory playerInventory, final CropExtractorBlockEntity blockEntity) {
        super(ADMenus.crop_extractor.get(), windowId);
        this.blockEntity = blockEntity;
        this.inventory = blockEntity.getInventory();
        this.level = playerInventory.player.level();
        this.canInteractWithCallable = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        int startX = 62;
        int startY = 51;
        int borderSlotSize = 18;

        this.addSlot(new SlotItemHandler(inventory, INPUT_SLOT, 44, 35));

        for (int row = 0; row < 2; ++row) {
            for (int column = 0; column < 2; ++column) {
                this.addSlot(new OutputSlotItemHandler(inventory, OUTPUT_SLOT_1 + (row * 2) + column,
                        (startX + (column * borderSlotSize))+45,
                        (startY + (row * borderSlotSize))-25));
            }
        }

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column,
                        startX + ((column * borderSlotSize)-54),
                        startY + (row * borderSlotSize) + 33));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column,
                    startX + ((column * borderSlotSize)-54),
                    startY + 91));
        }

        this.addDataSlots(blockEntity.getDataAccess());
    }

    private static CropExtractorBlockEntity getBlockEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (blockEntity instanceof CropExtractorBlockEntity extractor) {
            return extractor;
        }
        throw new IllegalStateException("Block entity is not correct! " + blockEntity);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, ADBlocks.crop_extractor.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        ItemStack slotStackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            slotStackCopy = slotStack.copy();

            if (index < 5) {
                if (!this.moveItemStackTo(slotStack, 5, 41, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(slotStack, 0, 5, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == slotStackCopy.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, slotStack);
        }
        return slotStackCopy;
    }

    public int getExtractionProgressScaled() {
        int progress = this.blockEntity.getExtractionProgress();
        int total = this.blockEntity.getExtractionTimeTotal();
        return total != 0 && progress != 0 ? progress * 25 / total : 0;
    }

    private static class OutputSlotItemHandler extends SlotItemHandler {
        public OutputSlotItemHandler(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack stack) {
            return false;
        }
    }
}
