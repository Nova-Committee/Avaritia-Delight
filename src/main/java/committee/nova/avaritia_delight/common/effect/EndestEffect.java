package committee.nova.avaritia_delight.common.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class EndestEffect extends MobEffect {

    private static final double ATTRACT_RANGE = 16.0;
    private static final double KILL_RANGE = 2.5;
    private static final double BASE_ATTRACT_STRENGTH = 1;

    public EndestEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00CC00);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide) {
            return true;
        }

        double range = ATTRACT_RANGE + amplifier * 2.0;
        double killRange = KILL_RANGE + amplifier * 0.3;

        List<LivingEntity> nearbyEntities = entity.level().getEntitiesOfClass(
                LivingEntity.class,
                entity.getBoundingBox().inflate(range),
                target -> target != entity &&
                        target.isAlive() &&
                        target instanceof Enemy
        );

        for (LivingEntity target : nearbyEntities) {
            Vec3 position = target.position();
            Vec3 center = entity.position();

            double distance = position.distanceTo(center);

            if (distance <= killRange) {
                target.hurt(entity.damageSources().magic(), Float.MAX_VALUE);
            } else {
                Vec3 direction = center.subtract(position).normalize();

                double strength = BASE_ATTRACT_STRENGTH * (1.0 + amplifier * 0.15);

                double distanceFactor = 1.0 - (distance / range);
                strength *= distanceFactor;

                Vec3 currentMotion = target.getDeltaMovement();

                double targetMotionX = direction.x * strength;
                double targetMotionY = direction.y * strength * 0.75;
                double targetMotionZ = direction.z * strength;

                double smoothingFactor = 0.3;

                Vec3 newMotion = new Vec3(
                        currentMotion.x + (targetMotionX - currentMotion.x) * smoothingFactor,
                        currentMotion.y + (targetMotionY - currentMotion.y) * smoothingFactor,
                        currentMotion.z + (targetMotionZ - currentMotion.z) * smoothingFactor
                );

                target.setDeltaMovement(newMotion);

                target.hurtMarked = true;
            }
        }

        return true;
    }
}
