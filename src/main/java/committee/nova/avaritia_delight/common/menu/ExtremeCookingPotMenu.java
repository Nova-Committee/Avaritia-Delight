package committee.nova.avaritia_delight.common.menu;


import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.block.entity.ExtremeCookingPotBlockEntity;
import committee.nova.avaritia_delight.common.block.entity.container.ExtremeCookingPotResultSlot;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import committee.nova.avaritia_delight.init.registry.ADMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotMealSlot;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.Objects;

public class ExtremeCookingPotMenu extends AbstractContainerMenu
{
    public static final ResourceLocation EMPTY_CONTAINER_SLOT_BOWL = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "item/empty_container_slot_bowl");

    public static final int INDEX_MEAL = 81;
    public static final int INDEX_CONTAINER = 82;
    public static final int INDEX_OUTPUT = 83;

    public final ExtremeCookingPotBlockEntity blockEntity;
    public final ItemStackHandler inventory;
    private final ContainerData cookingPotData;
    private final ContainerLevelAccess canInteractWithCallable;
    protected final Level level;

    public ExtremeCookingPotMenu(final int windowId, final Inventory playerInventory, final FriendlyByteBuf data) {
        this(windowId, playerInventory, getBlockEntity(playerInventory, data), new SimpleContainerData(4));
    }

    public ExtremeCookingPotMenu(final int windowId, final Inventory playerInventory, final ExtremeCookingPotBlockEntity blockEntity, ContainerData cookingPotData) {
        super(ADMenus.extreme_cooking_pot.get(), windowId);
        this.blockEntity = blockEntity;
        this.inventory = blockEntity.getInventory();
        this.cookingPotData = cookingPotData;
        this.level = playerInventory.player.level();
        this.canInteractWithCallable = ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos());

        // Ingredient Slots - 2 Rows x 3 Columns
        int startX = 39;
        int startY = 46;
        int inputStartX = 8;
        int inputStartY = 18;
        int borderSlotSize = 18;
        for (int row = 0; row < 9; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new SlotItemHandler(inventory, (row * 9) + column,
                        inputStartX + (column * borderSlotSize),
                        inputStartY + (row * borderSlotSize)));
            }
        }

        this.addSlot(new CookingPotMealSlot(inventory, INDEX_MEAL, 206, 92));
        this.addSlot(new SlotItemHandler(inventory, INDEX_CONTAINER, 174, 125));
        this.addSlot(new ExtremeCookingPotResultSlot(playerInventory.player, blockEntity, inventory, INDEX_OUTPUT, 206, 125));

        // Main Player Inventory
        int startPlayerInvY = startY * 4 + 12;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, startX + (column * borderSlotSize),
                        startPlayerInvY + (row * borderSlotSize)));
            }
        }

        // Hotbar
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, startX + (column * borderSlotSize), 254));
        }

        this.addDataSlots(cookingPotData);
    }


    private static ExtremeCookingPotBlockEntity getBlockEntity(final Inventory playerInventory, final FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "playerInventory cannot be null");
        Objects.requireNonNull(data, "data cannot be null");
        final BlockEntity blockEntity = playerInventory.player.level().getBlockEntity(data.readBlockPos());
        if (blockEntity instanceof ExtremeCookingPotBlockEntity cookingPot) {
            return cookingPot;
        }
        throw new IllegalStateException("Block entity is not correct! " + blockEntity);
    }

    @Override
    public boolean stillValid(Player playerIn) {
        return stillValid(canInteractWithCallable, playerIn, ADBlocks.extreme_cooking_pot.get());
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int index) {
        int indexInventoryStart = INDEX_OUTPUT + 1;
        int indexInventoryEnd = indexInventoryStart + 36;
        ItemStack slotStackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            slotStackCopy = slotStack.copy();
            if (index == INDEX_OUTPUT) {
                if (!this.moveItemStackTo(slotStack, indexInventoryStart, indexInventoryEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index > INDEX_OUTPUT) {
                if (slotStack.is(ModTags.Items.SERVING_CONTAINERS) || slotStack.is(blockEntity.getContainer().getItem())) {
                    if (!this.moveItemStackTo(slotStack, INDEX_CONTAINER, INDEX_OUTPUT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.moveItemStackTo(slotStack, 0, INDEX_MEAL, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(slotStack, indexInventoryStart, indexInventoryEnd, false)) {
                return ItemStack.EMPTY;
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

    public int getCookProgressionScaled() {
        int i = this.cookingPotData.get(0);
        int j = this.cookingPotData.get(1);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }

    public boolean isHeated() {
        return blockEntity.isHeated();
    }

}