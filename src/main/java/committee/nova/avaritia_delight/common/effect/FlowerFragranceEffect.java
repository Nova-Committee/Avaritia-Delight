package committee.nova.avaritia_delight.common.effect;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.init.registry.ADEffects;
import committee.nova.avaritia_delight.init.registry.ADItems;
import committee.nova.mods.avaritia.common.net.S2CTotemPack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;

public class FlowerFragranceEffect extends MobEffect {

    private static final double ATTRACT_RANGE = 10.0;
    private static final double BASE_ATTRACT_STRENGTH = 0.5;

    public FlowerFragranceEffect() {
        super(MobEffectCategory.BENEFICIAL, 0XFFFFFF);
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

        attractNearbyEntities(entity, amplifier);

        return true;
    }

    private void attractNearbyEntities(LivingEntity entity, int amplifier) {
        double range = ATTRACT_RANGE + amplifier * 1.5;

        List<LivingEntity> nearbyEntities = entity.level().getEntitiesOfClass(
                LivingEntity.class,
                entity.getBoundingBox().inflate(range),
                target -> target != entity && target.isAlive()
        );

        for (LivingEntity target : nearbyEntities) {
            double distance = target.distanceTo(entity);

            if (distance <= 2.0) {
                continue;
            }

            if (target instanceof net.minecraft.world.entity.Mob mob) {
                double speed = 1.0 + amplifier * 0.15;

                mob.getNavigation().moveTo(entity, speed);

                mob.getLookControl().setLookAt(entity, 30.0F, 30.0F);
            }
        }
    }

    @EventBusSubscriber(modid = AvaritiaDelight.MOD_ID)
    public static class EffectHandlers {

        @SubscribeEvent
        public static void onLivingDamage(LivingDamageEvent.Pre event) {
            Entity sourceEntity = event.getSource().getEntity();

            if (sourceEntity == null) {
                return;
            }

            LivingEntity attacker = sourceEntity instanceof LivingEntity
                    ? (LivingEntity) sourceEntity
                    : null;

            if (attacker == null) {
                return;
            }

            if (attacker.hasEffect(ADEffects.FLOWER_FRAGRANCE)) {
                LivingEntity target = event.getEntity();
                target.addEffect(new MobEffectInstance(
                        MobEffects.WITHER,
                        100,
                        1
                ));
            }
        }

        @SubscribeEvent
        public static void onLivingDeath(LivingDeathEvent event) {
            LivingEntity dyingEntity = event.getEntity();

            if (dyingEntity.level().isClientSide) {
                return;
            }

            if (!dyingEntity.hasEffect(ADEffects.FLOWER_FRAGRANCE)) {
                return;
            }

            float health = dyingEntity.getHealth();
            float maxHealth = dyingEntity.getMaxHealth();

            if (health > maxHealth * 0.3f) {
                return;
            }

            List<LivingEntity> nearbyEntities = dyingEntity.level().getEntitiesOfClass(
                    LivingEntity.class,
                    dyingEntity.getBoundingBox().inflate(15.0),
                    target -> target != dyingEntity && target.isAlive()
            );

            if (nearbyEntities.isEmpty()) {
                return;
            }

            nearbyEntities.sort((a, b) -> {
                int priorityA = getEntityPriority(a);
                int priorityB = getEntityPriority(b);

                if (priorityA != priorityB) {
                    return Integer.compare(priorityA, priorityB);
                }

                double distA = a.distanceTo(dyingEntity);
                double distB = b.distanceTo(dyingEntity);
                return Double.compare(distA, distB);
            });

            LivingEntity sacrificedEntity = nearbyEntities.get(0);

            String entityType = getEntityTypeName(sacrificedEntity);

            sacrificedEntity.discard();

            dyingEntity.setHealth(dyingEntity.getMaxHealth());
            dyingEntity.invulnerableTime = 20;

            if (dyingEntity instanceof ServerPlayer player) {
                ItemStack totemStack = new ItemStack(Items.LILY_OF_THE_VALLEY);

                PacketDistributor.sendToPlayer(player, new S2CTotemPack(totemStack, player.getId()));

                player.removeAllEffects();

                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));

                player.sendSystemMessage(Component.literal(entityType+Component.translatable("message.avaritia_delight.flower_fragrance").getString()));
            } else {
                for (int i = 0; i < 20; i++) {
                    double offsetX = (dyingEntity.level().random.nextDouble() - 0.5) * 2.0;
                    double offsetY = dyingEntity.level().random.nextDouble() * 2.0;
                    double offsetZ = (dyingEntity.level().random.nextDouble() - 0.5) * 2.0;

                    dyingEntity.level().addParticle(
                            net.minecraft.core.particles.ParticleTypes.HEART,
                            dyingEntity.getX() + offsetX,
                            dyingEntity.getY() + offsetY,
                            dyingEntity.getZ() + offsetZ,
                            0, 0, 0
                    );
                }

                dyingEntity.level().playSound(null, dyingEntity.getX(), dyingEntity.getY(), dyingEntity.getZ(),
                        SoundEvents.TOTEM_USE, dyingEntity.getSoundSource(), 1.0F, 1.0F);
            }

            event.setCanceled(true);
        }

        private static int getEntityPriority(LivingEntity entity) {
            if (entity instanceof Enemy) {
                return 0;
            }

            if (entity.getType().getCategory() == MobCategory.CREATURE ||
                    entity instanceof Animal) {
                return 2;
            }

            return 1;
        }

        private static String getEntityTypeName(LivingEntity entity) {
            EntityType<?> type = entity.getType();

            String name = type.getDescription().getString();

            return name;
        }
    }
}
