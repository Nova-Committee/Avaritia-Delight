package committee.nova.avaritia_delight.common.block;

import javax.annotation.Nullable;

import committee.nova.avaritia_delight.common.block.entity.InfinityCabinetBlockEntity;
import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.mods.avaritia.common.component.ClusterContainerContents;
import committee.nova.mods.avaritia.init.registry.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Objects;

public class InfinityCabinetBlock extends BarrelBlock implements EntityBlock {

    public InfinityCabinetBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InfinityCabinetBlockEntity(pos, state);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof InfinityCabinetBlockEntity) {
                InfinityCabinetBlockEntity tile = (InfinityCabinetBlockEntity)blockEntity;
                ItemStack dropStack = new ItemStack(this);
                dropStack.set(ModDataComponents.CLUSTER_CONTAINER, ClusterContainerContents.fromItems(tile.cabinet.getItems()));
                Block.popResource(level, pos, dropStack);
            }

            super.onRemove(state, level, pos, newState, isMoving);
        }

    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult result) {
        if (player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(Objects.requireNonNull(this.getMenuProvider(state, level, pos)), pos);
        }

        return InteractionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, ADBlockEntities.INFINITY_CABINET_BE.get(), InfinityCabinetBlockEntity::tick);
    }
}
