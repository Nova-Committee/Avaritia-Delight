package committee.nova.avaritia_delight.common.block.entity;

import committee.nova.avaritia_delight.common.block.InfinityBasketBlock;
import committee.nova.avaritia_delight.common.menu.InfinityBasketMenu;
import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import vectorwing.farmersdelight.common.block.entity.Basket;
import vectorwing.farmersdelight.common.block.entity.inventory.BasketInvWrapper;

import java.util.function.BooleanSupplier;

@EventBusSubscriber(modid = "avaritia_delight")
public class InfinityBasketBlockEntity extends RandomizableContainerBlockEntity implements Basket {

    public static final int MAX_STACK = Integer.MAX_VALUE;

    private NonNullList<ItemStack> items = NonNullList.withSize(243, ItemStack.EMPTY);

    private int transferCooldown = -1;

    public InfinityBasketBlockEntity(BlockPos pos, BlockState state) {
        super(ADBlockEntities.INFINITY_BASKET_BE.get(), pos, state);
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ADBlockEntities.INFINITY_BASKET_BE.get(),
                (be, context) -> new BasketInvWrapper(be)
        );
    }

    @Override
    protected void loadAdditional(CompoundTag compound, HolderLookup.Provider registries) {

        super.loadAdditional(compound, registries);

        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);

        if (!this.tryLoadLootTable(compound)) {
            ContainerHelper.loadAllItems(compound, this.items, registries);
        }

        this.transferCooldown = compound.getInt("TransferCooldown");
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider registries) {

        super.saveAdditional(compound, registries);

        if (!this.trySaveLootTable(compound)) {
            ContainerHelper.saveAllItems(compound, this.items, registries);
        }

        compound.putInt("TransferCooldown", this.transferCooldown);
    }

    @Override
    public int getContainerSize() {
        return 243;
    }

    @Override
    public ItemStack removeItem(int index, int count) {

        this.unpackLootTable(null);

        return ContainerHelper.removeItem(this.getItems(), index, count);
    }

    @Override
    public void setItem(int index, ItemStack stack) {

        this.unpackLootTable(null);

        this.getItems().set(index, stack);

        if (stack.getCount() > MAX_STACK) {
            stack.setCount(MAX_STACK);
        }

        this.setChanged();
    }

    @Override
    public int getMaxStackSize() {
        return MAX_STACK;
    }

    @Override
    protected Component getDefaultName() {
        return ADBlocks.infinity_basket.get().getName();
    }

    @Override
    protected NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory inventory) {
        return new InfinityBasketMenu(id, inventory, this);
    }

    @Override
    public void setCooldown(int ticks) {
        this.transferCooldown = ticks;
    }

    public boolean isOnCooldown() {
        return this.transferCooldown > 0;
    }

    @Override
    public boolean isOnCustomCooldown() {
        return this.transferCooldown > 8;
    }

    @Override
    public void tryTransfer(BooleanSupplier transfer) {

        if (this.level != null && !this.level.isClientSide) {

            if (!this.isOnCooldown()
                    && this.getBlockState().getValue(BlockStateProperties.ENABLED)) {

                boolean moved = false;

                if (!this.isFull()) {
                    moved = transfer.getAsBoolean();
                }

                if (moved) {

                    this.setCooldown(8);

                    this.setChanged();
                }
            }
        }
    }

    protected boolean isFull() {

        for (ItemStack stack : this.items) {

            if (stack.isEmpty()) {
                return false;
            }

            if (stack.getCount() < MAX_STACK) {
                return false;
            }
        }

        return true;
    }

    @Override
    public double getLevelX() {
        return this.worldPosition.getX() + 0.5D;
    }

    @Override
    public double getLevelY() {
        return this.worldPosition.getY() + 0.5D;
    }

    @Override
    public double getLevelZ() {
        return this.worldPosition.getZ() + 0.5D;
    }

    public static void pushItemsTick(
            Level level,
            BlockPos pos,
            BlockState state,
            InfinityBasketBlockEntity blockEntity
    ) {

        --blockEntity.transferCooldown;

        if (!blockEntity.isOnCooldown()) {

            blockEntity.setCooldown(0);

            int facing = state.getValue(InfinityBasketBlock.FACING).get3DDataValue();

            blockEntity.tryTransfer(() ->
                    blockEntity.collectItems(level, facing)
            );
        }
    }
}