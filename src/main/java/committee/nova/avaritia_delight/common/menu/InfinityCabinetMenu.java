package committee.nova.avaritia_delight.common.menu;

import committee.nova.avaritia_delight.common.block.entity.InfinityCabinetBlockEntity;
import committee.nova.avaritia_delight.common.container.slot.InfinityCabinetSlot;
import committee.nova.avaritia_delight.init.registry.ADMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InfinityCabinetMenu extends AbstractContainerMenu {

    private final InfinityCabinetBlockEntity tile;
    private final Container container;

    public InfinityCabinetMenu(int id, Inventory inventory, RegistryFriendlyByteBuf buf) {
        this(id, inventory, (InfinityCabinetBlockEntity) inventory.player.level().getBlockEntity(buf.readBlockPos()));
    }

    public InfinityCabinetMenu(int id, Inventory inventory, InfinityCabinetBlockEntity tile) {
        super(ADMenus.infinity_cabinet.get(), id);
        this.tile = tile;
        this.container = tile.cabinet;
        this.container.startOpen(inventory.player);

        int y;
        for (y = 0; y < 9; y++) {
            for (int i = 0; i < 27; i++) {
                this.addSlot(new InfinityCabinetSlot(container, i + y * 27, 8 + i * 18, 18 + y * 18));
            }
        }

        for (y = 0; y < 3; y++) {
            for (int i = 0; i < 9; i++) {
                this.addSlot(new Slot(inventory, i + y * 9 + 9, 170 + i * 18, 194 + y * 18));
            }
        }
        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(inventory, x, 170 + x * 18, 252));
        }
    }

    public InfinityCabinetBlockEntity getTile() {
        return this.tile;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack resultStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();

            if (index < 243) {
                if (!this.moveItemStackTo(slotStack, 243, 279, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 243 && index < 279) {
                if (!this.moveItemStackTo(slotStack, 0, 243, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (slotStack.getCount() == resultStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotStack);
        }
        return resultStack;
    }

    protected boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean flag = false;
        int i = startIndex;
        if (reverseDirection) {
            i = endIndex - 1;
        }

        if (!stack.isEmpty()) {
            while (reverseDirection ? i >= startIndex : i < endIndex) {
                Slot slot = this.slots.get(i);
                ItemStack itemStack = slot.getItem();
                if (!itemStack.isEmpty() && ItemStack.isSameItemSameComponents(stack, itemStack)) {
                    int j = itemStack.getCount() + stack.getCount();
                    int maxSize = slot instanceof InfinityCabinetSlot ? Integer.MAX_VALUE : Math.min(slot.getMaxStackSize(), stack.getMaxStackSize());
                    if (j <= maxSize && j > 0) {
                        stack.setCount(0);
                        itemStack.setCount(j);
                        slot.setChanged();
                        flag = true;
                    } else if (itemStack.getCount() < maxSize) {
                        stack.shrink(maxSize - itemStack.getCount());
                        itemStack.setCount(maxSize);
                        slot.setChanged();
                        flag = true;
                    }
                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty()) {
            if (reverseDirection) {
                i = endIndex - 1;
            } else {
                i = startIndex;
            }

            while (reverseDirection ? i >= startIndex : i < endIndex) {
                Slot slot1 = this.slots.get(i);
                ItemStack itemStack1 = slot1.getItem();
                if (itemStack1.isEmpty() && slot1.mayPlace(stack)) {
                    int maxSize = slot1 instanceof InfinityCabinetSlot ? Integer.MAX_VALUE : Math.min(slot1.getMaxStackSize(), stack.getMaxStackSize());
                    slot1.set(stack.split(Math.min(stack.getCount(), maxSize)));
                    slot1.setChanged();
                    flag = true;
                    break;
                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }
        return flag;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    @Override
    public boolean stillValid(Player player) {
        BlockPos pos = this.tile.getBlockPos();
        return this.tile.getLevel().getBlockState(pos).getBlock() == this.tile.getBlockState().getBlock() && player.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
    }
}
