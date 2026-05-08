package committee.nova.avaritia_delight.common.entity;

import committee.nova.avaritia_delight.init.registry.ADEntities;
import committee.nova.avaritia_delight.init.registry.ADItems;
import committee.nova.mods.avaritia.common.entity.GapingVoidEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class EndestEggEntity extends ThrowableItemProjectile {

    private LivingEntity shooter;

    public EndestEggEntity(EntityType<? extends EndestEggEntity> p_37473_, Level p_37474_) {
        super(p_37473_, p_37474_);
    }

    public EndestEggEntity(Level p_37481_, LivingEntity p_37482_) {
        super(ADEntities.ENDEST_EGG.get(), p_37482_, p_37481_);
    }

    public EndestEggEntity(Level p_37476_, double p_37477_, double p_37478_, double p_37479_) {
        super(ADEntities.ENDEST_EGG.get(), p_37477_, p_37478_, p_37479_, p_37476_);
    }

    @Override
    public void handleEntityEvent(byte p_37484_) {
        if (p_37484_ == 3) {
            double d0 = 0.08;

            for (int i = 0; i < 8; i++) {
                this.level()
                        .addParticle(
                                new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                                this.getX(),
                                this.getY(),
                                this.getZ(),
                                ((double)this.random.nextFloat() - 0.5) * 0.08,
                                ((double)this.random.nextFloat() - 0.5) * 0.08,
                                ((double)this.random.nextFloat() - 0.5) * 0.08
                        );
            }
        }
    }

    public void setShooter(LivingEntity shooter) {
        this.shooter = shooter;
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult pos) {
        super.onHitEntity(pos);
        Entity entity = pos.getEntity();

        entity.hurt(this.damageSources().thrown(this, getOwner()), 0.0F);

        if (!level().isClientSide) {
            GapingVoidEntity ent;
            if (shooter != null) {
                ent = new GapingVoidEntity(level(), shooter);
            } else ent = new GapingVoidEntity(level());

            Direction dir = entity.getDirection();
            Vec3 offset;
            offset = new Vec3(dir.getStepX(), dir.getStepY(), dir.getStepZ());
            if (shooter != null) {
                ent.setUser(shooter);
            }
            ent.moveTo(entity.getX() + offset.x * 0.25, entity.getY() + offset.y * 0.25, entity.getZ() + offset.z * 0.25, entity.getYRot(), 0.0F);
            level().addFreshEntity(ent);

            remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        super.onHitBlock(result);
        BlockPos pos = result.getBlockPos();

        if (!level().isClientSide) {

            GapingVoidEntity ent;
            if (shooter != null) {
                ent = new GapingVoidEntity(level(), shooter);

            } else ent = new GapingVoidEntity(level());
            Direction dir = result.getDirection();
            Vec3 offset;
            offset = new Vec3(dir.getStepX(), dir.getStepY(), dir.getStepZ());
            if (shooter != null) {
                ent.setUser(shooter);
            }
            ent.moveTo(pos.getX() + offset.x * 0.25, pos.getY() + offset.y * 0.25, pos.getZ() + offset.z * 0.25, getYRot(), 0.0F);
            level().addFreshEntity(ent);

            remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected Item getDefaultItem() {
        return ADItems.endest_egg.get();
    }
}
