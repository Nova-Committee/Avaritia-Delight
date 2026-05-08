package committee.nova.avaritia_delight.common.effect;

import committee.nova.avaritia_delight.init.registry.ADEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

public class OverWeightEffect extends MobEffect {

    public OverWeightEffect() {
        super(MobEffectCategory.HARMFUL, 0);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        Vec3 motion = entity.getDeltaMovement();

        if (motion.y > 0) {
            entity.setDeltaMovement(motion.x, 0, motion.z);
        }

        if (entity.isInWater()) {
            entity.setDeltaMovement(
                    entity.getDeltaMovement().x,
                    -0.08D,
                    entity.getDeltaMovement().z
            );
        }

        if (entity.onClimbable()) {
            entity.setDeltaMovement(
                    entity.getDeltaMovement().x,
                    Math.min(entity.getDeltaMovement().y, -0.15D),
                    entity.getDeltaMovement().z
            );
        }

        entity.hasImpulse = false;

        return true;
    }

    @EventBusSubscriber
    public static class JumpHandler {
        @SubscribeEvent
        public static void onLivingJump(LivingEvent.LivingJumpEvent event) {
            LivingEntity entity = event.getEntity();

            if (entity.hasEffect(ADEffects.OVERWEIGHT)) {
                Vec3 motion = entity.getDeltaMovement();
                DamageSource damagesource = entity.level().damageSources().dryOut();
                entity.setDeltaMovement(motion.x, 0, motion.z);
                entity.hurt(damagesource,1.0F);
            }
        }
    }
}