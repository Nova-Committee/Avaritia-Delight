package committee.nova.avaritia_delight.common.item.tool;

import committee.nova.mods.avaritia.api.iface.transform.IToolTransform;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class AvaritiaKnifeItem extends KnifeItem implements IToolTransform {
    public AvaritiaKnifeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }
}
