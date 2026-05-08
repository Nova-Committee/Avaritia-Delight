package committee.nova.avaritia_delight.common.item.tool;

import committee.nova.avaritia_delight.init.registry.ADEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class NeutroniumKnifeItem extends AvaritiaKnifeItem{

    public NeutroniumKnifeItem(Tier tier, Properties properties) {
        super(tier, properties);
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.level().isClientSide){
            target.addEffect(new MobEffectInstance(ADEffects.OVERWEIGHT, 100));
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
