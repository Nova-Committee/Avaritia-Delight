package committee.nova.avaritia_delight.common.item.food;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.alchemy.PotionContents;

import java.util.List;

public class EndestEggSandwichItem extends Item {

    public EndestEggSandwichItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> componentList, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, tooltipContext, componentList, tooltipFlag);
        PotionContents potioncontents = stack.get(DataComponents.POTION_CONTENTS);
        if (potioncontents != null) {
            potioncontents.addPotionTooltip(componentList::add, 1.0F, tooltipContext.tickRate());
        }
    }
}
