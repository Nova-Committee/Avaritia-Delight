package committee.nova.avaritia_delight.common.block.entity;

import committee.nova.avaritia_delight.common.block.ExtremeSkilletBlock;
import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.avaritia_delight.init.registry.ADItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Clearable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import vectorwing.farmersdelight.common.block.SkilletBlock;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.utility.ItemUtils;
import vectorwing.farmersdelight.common.utility.TextUtils;

import java.util.Objects;
import java.util.Optional;

public class ExtremeSkilletBlockEntity extends SyncedBlockEntity implements HeatableBlockEntity, Clearable
{
    private final ItemStackHandler inventory = createHandler();
    private int cookingTime;
    private int cookingTimeTotal;
    private int fireAspectLevel;

    private ItemStack skilletStack;

    private final RecipeManager.CachedCheck<SingleRecipeInput, CampfireCookingRecipe> quickCheck;

    public ExtremeSkilletBlockEntity(BlockPos pos, BlockState state) {
        super(ADBlockEntities.EXTREME_SKILLET_BE.get(), pos, state);
        skilletStack = new ItemStack(ADItems.extreme_skillet.get());
        quickCheck = RecipeManager.createCheck(RecipeType.CAMPFIRE_COOKING);
    }

    public static void cookingTick(Level level, BlockPos pos, BlockState state, ExtremeSkilletBlockEntity skillet) {
        boolean isHeated = skillet.isHeated(level, pos);

        if (state.getValue(ExtremeSkilletBlock.WATERLOGGED)) {
            if (ItemUtils.doesInventoryHaveItems(skillet.inventory)) {
                ItemUtils.dropItems(level, pos, skillet.inventory);
                skillet.inventoryChanged();
            }
        } else if (isHeated) {
            ItemStack cookingStack = skillet.getStoredStack();
            if (cookingStack.isEmpty()) {
                skillet.cookingTime = 0;
            } else {
                skillet.cookAndOutputItems(cookingStack, level);
            }
        } else if (skillet.cookingTime > 0) {
            skillet.cookingTime = Mth.clamp(skillet.cookingTime - 2, 0, skillet.cookingTimeTotal);
        }
    }

    public static void animationTick(Level level, BlockPos pos, BlockState state, ExtremeSkilletBlockEntity skillet) {
        if (skillet.isHeated(level, pos) && skillet.hasStoredStack()) {
            RandomSource random = level.random;
            if (random.nextFloat() < 0.2F) {
                double x = (double) pos.getX() + 0.5D + (random.nextDouble() * 0.4D - 0.2D);
                double y = (double) pos.getY() + 0.1D;
                double z = (double) pos.getZ() + 0.5D + (random.nextDouble() * 0.4D - 0.2D);
                double motionY = random.nextBoolean() ? 0.015D : 0.005D;
                level.addParticle(ModParticleTypes.STEAM.get(), x, y, z, 0.0D, motionY, 0.0D);
            }
            if (skillet.fireAspectLevel > 0 && random.nextFloat() < skillet.fireAspectLevel * 0.05F) {
                double x = (double) pos.getX() + 0.5D + (random.nextDouble() * 0.4D - 0.2D);
                double y = (double) pos.getY() + 0.1D;
                double z = (double) pos.getZ() + 0.5D + (random.nextDouble() * 0.4D - 0.2D);
                double motionX = level.random.nextFloat() - 0.5F;
                double motionY = level.random.nextFloat() * 0.5F + 0.2f;
                double motionZ = level.random.nextFloat() - 0.5F;
                level.addParticle(ParticleTypes.ENCHANTED_HIT, x, y, z, motionX, motionY, motionZ);
            }
        }

    }

    private void cookAndOutputItems(ItemStack cookingStack, Level level) {
        ++cookingTime;
        if (cookingTime >= cookingTimeTotal) {
            Optional<RecipeHolder<CampfireCookingRecipe>> recipe = getMatchingRecipe(cookingStack);
            if (recipe.isPresent()) {
                ItemStack resultStack = recipe.get().value().assemble(new SingleRecipeInput(cookingStack), level.registryAccess());
                Direction direction = getBlockState().getValue(ExtremeSkilletBlock.FACING).getClockWise();
                ItemUtils.spawnItemEntity(level, resultStack.copy(),
                        worldPosition.getX() + 0.5, worldPosition.getY() + 0.3, worldPosition.getZ() + 0.5,
                        direction.getStepX() * 0.08F, 0.25F, direction.getStepZ() * 0.08F);

                cookingTime = 0;
                inventory.extractItem(0, 1, false);
            }
        }
    }

    public boolean isCooking() {
        return isHeated() && hasStoredStack();
    }

    public boolean isHeated() {
        if (level != null) {
            return isHeated(level, worldPosition);
        }
        return false;
    }

    private Optional<RecipeHolder<CampfireCookingRecipe>> getMatchingRecipe(ItemStack stack) {
        if (level == null) return Optional.empty();
        return this.quickCheck.getRecipeFor(new SingleRecipeInput(stack), this.level);
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.loadAdditional(compound, registries);
        inventory.deserializeNBT(registries, compound.getCompound("Inventory"));
        cookingTime = compound.getInt("CookTime");
        cookingTimeTotal = compound.getInt("CookTimeTotal");
        skilletStack = ItemStack.parseOptional(registries, compound.getCompound("Skillet"));
        this.fireAspectLevel = EnchantmentHelper.getTagEnchantmentLevel((Holder)registries.holder(Enchantments.FIRE_ASPECT).get(), this.skilletStack);
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {
        super.saveAdditional(compound, registries);
        compound.put("Inventory", inventory.serializeNBT(registries));
        compound.putInt("CookTime", cookingTime);
        compound.putInt("CookTimeTotal", cookingTimeTotal);
        if (!skilletStack.isEmpty()) {
            compound.put("Skillet", skilletStack.save(registries));
        }
    }

    public ItemStack getSkilletAsItem() {
        return skilletStack;
    }

    public void setSkilletItem(ItemStack stack) {
        this.skilletStack = stack.copy();
        if (this.level != null) {
            Optional<Holder.Reference<Enchantment>> fireAspect = this.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(Enchantments.FIRE_ASPECT);
            Objects.requireNonNull(stack);
            this.fireAspectLevel = (Integer)fireAspect.map(stack::getEnchantmentLevel).orElse(0);
        } else {
            this.fireAspectLevel = 0;
        }

        this.inventoryChanged();
    }

    public ItemStack addItemToCook(ItemStack addedStack, Player player) {
        Optional<RecipeHolder<CampfireCookingRecipe>> recipe = this.getMatchingRecipe(addedStack);
        if (recipe.isPresent() && this.getStoredStack().isEmpty()) {
            if ((Boolean)this.getBlockState().getValue(SkilletBlock.WATERLOGGED)) {
                player.displayClientMessage(TextUtils.block("skillet.underwater", new Object[0]), true);
                return addedStack;
            }

            boolean wasEmpty = this.getStoredStack().isEmpty();
            ItemStack remainderStack = this.inventory.insertItem(0, addedStack.copy(), false);
            if (!ItemStack.matches(remainderStack, addedStack)) {
                this.cookingTimeTotal = SkilletBlock.getSkilletCookingTime(((CampfireCookingRecipe)((RecipeHolder)recipe.get()).value()).getCookingTime(), this.fireAspectLevel);
                this.cookingTime = 0;
                if (wasEmpty && this.level != null && this.isHeated(this.level, this.worldPosition)) {
                    this.level.playSound((Player)null, (double)((float)this.worldPosition.getX() + 0.5F), (double)((float)this.worldPosition.getY() + 0.5F), (double)((float)this.worldPosition.getZ() + 0.5F), (SoundEvent)ModSounds.BLOCK_SKILLET_ADD_FOOD.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
                }

                return remainderStack;
            }
        } else {
            player.displayClientMessage(TextUtils.block("skillet.invalid_item", new Object[0]), true);
        }

        return addedStack;
    }

    public ItemStack removeItem() {
        return inventory.extractItem(0, getStoredStack().getMaxStackSize(), false);
    }

    public IItemHandler getInventory() {
        return inventory;
    }

    public ItemStack getStoredStack() {
        return inventory.getStackInSlot(0);
    }

    public boolean hasStoredStack() {
        return !getStoredStack().isEmpty();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler()
        {
            @Override
            protected void onContentsChanged(int slot) {
                inventoryChanged();
            }
        };
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
    }

    @Override
    public void clearContent() {
        ItemUtils.clearItems(inventory);
    }
}