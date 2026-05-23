package committee.nova.avaritia_delight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AbstractStewBlock extends Block {

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(1.0D, 0.0D, 1.0D, 15.0D, 10.0D, 15.0D),
            Block.box(1.0D, 11.0D, 1.0D, 15.0D, 13.0D, 3.0D),
            Block.box(1.0D, 11.0D, 13.0D, 15.0D, 13.0D, 15.0D),
            Block.box(1.0D, 11.0D, 3.0D, 3.0D, 13.0D, 13.0D),
            Block.box(13.0D, 11.0D, 3.0D, 15.0D, 13.0D, 13.0D),
            Block.box(15.0D, 11.0D, 5.0D, 16.0D, 12.0D, 11.0D),
            Block.box(0.0D, 11.0D, 5.0D, 1.0D, 12.0D, 11.0D)
    );

    public AbstractStewBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            RandomSource random = level.random;
            for (int i = 0; i < 5; i++) {
                double x = pos.getX() + 0.3D + random.nextDouble() * 0.4D;
                double y = pos.getY() + 0.75D;
                double z = pos.getZ() + 0.3D + random.nextDouble() * 0.4D;
                level.addParticle(new BlockParticleOption(ParticleTypes.BLOCK, state), x, y, z, 0.0D, 0.05D, 0.0D);
            }
            level.playLocalSound(pos, SoundEvents.GENERIC_EAT, SoundSource.BLOCKS, 1.0F, 1.0F, false);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }

}
