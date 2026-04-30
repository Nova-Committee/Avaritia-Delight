package committee.nova.avaritia_delight.common.block;

import com.mojang.serialization.MapCodec;
import committee.nova.avaritia_delight.common.block.entity.ExtremeStoveBlockEntity;
import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.avaritia_delight.init.registry.ADItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import vectorwing.farmersdelight.common.block.AbstractStoveBlock;
import vectorwing.farmersdelight.common.registry.ModSounds;

public class ExtremeStoveBlock extends AbstractStoveBlock {
    public static final MapCodec<ExtremeStoveBlock> CODEC =
            simpleCodec(ExtremeStoveBlock::new);

    public ExtremeStoveBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public MapCodec<ExtremeStoveBlock> codec() {
        return CODEC;
    }
    @Override
    public ItemInteractionResult useItemOn(
            ItemStack heldStack,
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            InteractionHand hand,
            BlockHitResult hit
    ) {
        if (state.getValue(LIT)) {
            ItemInteractionResult extinguishResult =
                    this.tryToExtinguish(
                            heldStack,
                            state,
                            level,
                            pos,
                            player,
                            hand,
                            hit
                    );

            if (extinguishResult
                    != ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION) {
                return extinguishResult;
            }
        } else {
            ItemInteractionResult igniteResult =
                    this.tryToIgnite(
                            heldStack,
                            state,
                            level,
                            pos,
                            player,
                            hand,
                            hit
                    );

            if (igniteResult
                    != ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION) {
                return igniteResult;
            }
        }

        if (heldStack.is(ADItems.cosmic_beef.get())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);

            if (blockEntity instanceof ExtremeStoveBlockEntity stove) {
                boolean placed = stove.placeFood(
                        player,
                        heldStack,
                        null
                );

                if (placed) {
                    return ItemInteractionResult.SUCCESS;
                }
            }
        }

        return this.tryToPlaceFoodItem(
                heldStack,
                state,
                level,
                pos,
                player,
                hand,
                hit
        );
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ExtremeStoveBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
            Level level,
            BlockState state,
            BlockEntityType<T> blockEntityType
    ) {
        return createTickerHelper(
                blockEntityType,
                ADBlockEntities.EXTREME_STOVE_BE.get(),
                level.isClientSide
                        ? ExtremeStoveBlockEntity::particleTick
                        : ExtremeStoveBlockEntity::serverTick
        );
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if ((Boolean)state.getValue(LIT)) {
            double x = (double)pos.getX() + (double)0.5F;
            double y = (double)pos.getY();
            double z = (double)pos.getZ() + (double)0.5F;
            if (random.nextInt(10) == 0) {
                level.playLocalSound(x, y, z, (SoundEvent) ModSounds.BLOCK_STOVE_CRACKLE.get(), SoundSource.BLOCKS, 1.0F, 1.0F, false);
            }

            Direction direction = (Direction)state.getValue(HorizontalDirectionalBlock.FACING);
            Direction.Axis direction$axis = direction.getAxis();
            double horizontalOffset = random.nextDouble() * 0.6 - 0.3;
            double xOffset = direction$axis == Direction.Axis.X ? (double)direction.getStepX() * 0.52 : horizontalOffset;
            double yOffset = random.nextDouble() * (double)6.0F / (double)16.0F;
            double zOffset = direction$axis == Direction.Axis.Z ? (double)direction.getStepZ() * 0.52 : horizontalOffset;
            level.addParticle(ParticleTypes.SMOKE, x + xOffset, y + yOffset, z + zOffset, (double)0.0F, (double)0.0F, (double)0.0F);
            level.addParticle(ParticleTypes.SOUL_FIRE_FLAME, x + xOffset, y + yOffset, z + zOffset, (double)0.0F, (double)0.0F, (double)0.0F);
        }
    }
}