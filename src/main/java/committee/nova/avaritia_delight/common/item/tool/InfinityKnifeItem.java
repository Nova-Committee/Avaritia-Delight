package committee.nova.avaritia_delight.common.item.tool;

import committee.nova.mods.avaritia.api.common.enchant.InitEnchantment;
import committee.nova.mods.avaritia.api.iface.item.IUndamageable;
import committee.nova.mods.avaritia.api.iface.item.InitEnchantItem;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InfinityKnifeItem extends AvaritiaKnifeItem implements IUndamageable, InitEnchantItem{

    private final InitEnchantment initEnchantment = new InitEnchantment(Enchantments.LOOTING, 10);

    public InfinityKnifeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public int getInitEnchantLevel(ItemStack stack, Holder<Enchantment> enchantmentHolder) {
        if (enchantmentHolder.is(Enchantments.LOOTING)) {
            return 10;
        }
        return 0;
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag isAdvanced) {
        this.initEnchantment.appendHoverText(context, tooltipComponents);
    }

}
