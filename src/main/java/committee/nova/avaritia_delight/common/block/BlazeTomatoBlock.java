package committee.nova.avaritia_delight.common.block;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.CommonHooks;
import committee.nova.avaritia_delight.init.registry.ADItems;

import javax.annotation.Nullable;

@SuppressWarnings("deprecation")
public class BlazeTomatoBlock extends CropBlock {
    public static final IntegerProperty VINE_AGE = BlockStateProperties.AGE_3;
    public static final BooleanProperty ROPELOGGED = BooleanProperty.create("ropelogged");
    private static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    public BlazeTomatoBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(getAgeProperty(), 0).setValue(ROPELOGGED, false));
    }

    protected BlazeTomatoBlock(Properties properties, boolean dummy) {
        super(properties);
    }


    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        int age = state.getValue(getAgeProperty());
        boolean isMature = age == getMaxAge();
        return !isMature && stack.is(Items.BONE_MEAL) ? ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION : super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        int age = state.getValue(getAgeProperty());
        boolean isMature = age == getMaxAge();
        if (isMature) {
            int quantity = 1 + level.random.nextInt(2);
            popResource(level, pos, new ItemStack(ADItems.blaze_tomato.get(), quantity));

            level.playSound(null, pos, net.minecraft.sounds.SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlock(pos, state.setValue(getAgeProperty(), 0), 2);
            return InteractionResult.SUCCESS;
        } else {
            return super.useWithoutItem(state, level, pos, player, hit);
        }
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(level, pos)) {
            level.destroyBlock(pos, true);
            if (state.getValue(BlazeTomatoBlock.ROPELOGGED)) {
                destroyAndPlaceRope(level, pos);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (!level.isAreaLoaded(pos, 1)) return;

        if (level.getRawBrightness(pos, 0) >= 9) {
            int age = this.getAge(state);
            if (age < this.getMaxAge()) {
                float speed = 5;
                if (CommonHooks.canCropGrow(level, pos, state, random.nextInt((int) (25.0F / speed) + 1) == 0)) {
                    level.setBlock(pos, state.setValue(getAgeProperty(), age + 1), 2);
                    CommonHooks.fireCropGrowPost(level, pos, state);
                }
            }
            climbRopeAbove(level, pos);
        }
    }

    @Override
    public BlockState getStateForAge(int age) {
        return this.defaultBlockState().setValue(this.getAgeProperty(), age);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return VINE_AGE;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return ADItems.blaze_tomato_seeds.get();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VINE_AGE, ROPELOGGED);
    }

    public boolean canClimbBlock(BlockState stateAbove) {
        return stateAbove.is(net.minecraft.world.level.block.Blocks.CHAIN);
    }

    public void climbRopeAbove(ServerLevel level, BlockPos pos) {
        BlockPos posAbove = pos.above();
        BlockState stateAbove = level.getBlockState(posAbove);
        if (canClimbBlock(stateAbove)) {
            int vineHeight;
            for (vineHeight = 1; level.getBlockState(pos.below(vineHeight)).is(this); ++vineHeight) {
            }
            if (vineHeight < 3) {
                level.setBlockAndUpdate(posAbove, defaultBlockState());
            }
        }
    }

    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return super.getBonemealAgeIncrease(level) / 2;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        if (!this.isMaxAge(state)) {
            return true;
        }

        BlockPos.MutableBlockPos mutablePos = pos.mutable();
        for (int height = 0; height < 2; height++) {
            mutablePos.move(Direction.UP);
            BlockState nextState = level.getBlockState(mutablePos);
            if (canClimbBlock(nextState)) {
                return true;
            }
            if (nextState.is(this) && !isMaxAge(nextState)) {
                return true;
            } else if (!nextState.is(this)) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        int newAge = this.getAge(state) + this.getBonemealAgeIncrease(level);
        if (newAge <= this.getMaxAge()) {
            level.setBlockAndUpdate(pos, state.setValue(getAgeProperty(), newAge));
            if (random.nextFloat() < 0.3F) {
                climbRopeAbove(level, pos);
            }
        } else {
            BlockState aboveState = level.getBlockState(pos.above());
            if (canClimbBlock(level.getBlockState(pos.above()))) {
                climbRopeAbove(level, pos);
            } else if (aboveState.is(this) && isValidBonemealTarget(level, pos, aboveState)) {
                performBonemeal(level, random, pos.above(), aboveState);
            }
        }
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);

        if (belowState.is(this)) {
            return hasGoodCropConditions(level, pos);
        }

        return super.canSurvive(state, level, pos);
    }

    public boolean hasGoodCropConditions(LevelReader level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) >= 8 || level.canSeeSky(pos);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        if (!state.canSurvive(level, currentPos)) {
            level.scheduleTick(currentPos, this, 1);
        }

        return state;
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack) {
        boolean isRopelogged = state.getValue(BlazeTomatoBlock.ROPELOGGED);
        super.playerDestroy(level, player, pos, state, blockEntity, stack);

        if (isRopelogged) {
            destroyAndPlaceRope(level, pos);
        }
    }

    public static void destroyAndPlaceRope(Level level, BlockPos pos) {
        level.setBlockAndUpdate(pos, net.minecraft.world.level.block.Blocks.CHAIN.defaultBlockState());
    }
}
