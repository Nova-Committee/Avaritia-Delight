package committee.nova.avaritia_delight.common.effect;

import committee.nova.avaritia_delight.init.registry.ADEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;

public class GravityEffect extends MobEffect {

    public GravityEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xC7D2FF);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {

        Vec3 motion = entity.getDeltaMovement();

        if (!entity.onGround() && motion.y < 0) {

            entity.setDeltaMovement(
                    motion.x,
                    motion.y * 0.7D,
                    motion.z
            );
        }

        if (!entity.onGround()) {
            entity.setDeltaMovement(
                    entity.getDeltaMovement().multiply(1.04, 1.0, 1.04)
            );
        }

        return true;
    }

    @EventBusSubscriber
    public static class Handler {
        @SubscribeEvent
        public static void onFall(LivingFallEvent event) {

            LivingEntity entity = event.getEntity();

            if (entity.hasEffect(ADEffects.GRAVITY)) {

                event.setCanceled(true);

            }
        }
        @SubscribeEvent
        public static void onJump(LivingEvent.LivingJumpEvent event) {

            LivingEntity entity = event.getEntity();

            if (entity.hasEffect(ADEffects.GRAVITY)) {

                Vec3 motion = entity.getDeltaMovement();

                entity.setDeltaMovement(
                        motion.x,
                        motion.y + 0.5D,
                        motion.z
                );
            }
        }
    }
}