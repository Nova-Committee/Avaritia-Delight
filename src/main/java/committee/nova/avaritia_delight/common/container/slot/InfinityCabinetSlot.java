package committee.nova.avaritia_delight.common.container.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class InfinityCabinetSlot extends Slot {

    public InfinityCabinetSlot(Container container, int slot, int x, int y) {
        super(container, slot, x, y);
    }

    @Override
    public int getMaxStackSize() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int getMaxStackSize(ItemStack stack) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return true;
    }
}
