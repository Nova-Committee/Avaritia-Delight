package committee.nova.avaritia_delight.common.block.entity;

import committee.nova.avaritia_delight.common.menu.InfinityCabinetMenu;
import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.mods.avaritia.common.component.ClusterContainerContents;
import committee.nova.mods.avaritia.init.registry.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ContainerOpenersCounter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class InfinityCabinetBlockEntity extends BlockEntity implements MenuProvider {

    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        @Override
        protected void onOpen(Level level, BlockPos pos, BlockState state) {
            InfinityCabinetBlockEntity.this.playSound(state, SoundEvents.BARREL_OPEN);
            InfinityCabinetBlockEntity.this.setOpen(state, true);
        }

        @Override
        protected void onClose(Level level, BlockPos pos, BlockState state) {
            InfinityCabinetBlockEntity.this.playSound(state, SoundEvents.BARREL_CLOSE);
            InfinityCabinetBlockEntity.this.setOpen(state, false);
        }

        @Override
        protected void openerCountChanged(Level level, BlockPos pos, BlockState state, int count, int openCount) {
            InfinityCabinetBlockEntity.this.signalOpenCount(level, pos, state, count, openCount);
        }

        @Override
        protected boolean isOwnContainer(Player player) {
            if (!(player.containerMenu instanceof InfinityCabinetMenu)) {
                return false;
            } else {
                InfinityCabinetMenu menu = (InfinityCabinetMenu) player.containerMenu;
                InfinityCabinetBlockEntity tile = menu.getTile();
                return tile == InfinityCabinetBlockEntity.this;
            }
        }
    };

    public final SimpleContainer cabinet = new SimpleContainer(300) {
        @Override
        public void setChanged() {
            InfinityCabinetBlockEntity.this.setChanged();
        }

        @Override
        public int getMaxStackSize() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getMaxStackSize(net.minecraft.world.item.ItemStack stack) {
            return this.getMaxStackSize();
        }

        @Override
        public void fillStackedContents(StackedContents stackedContents) {
            for (net.minecraft.world.item.ItemStack itemStack : this.getItems()) {
                stackedContents.accountStack(itemStack, Integer.MAX_VALUE);
            }
        }

        @Override
        public void startOpen(Player player) {
            if (!InfinityCabinetBlockEntity.this.remove && !player.isSpectator()) {
                InfinityCabinetBlockEntity.this.openersCounter.incrementOpeners(player, InfinityCabinetBlockEntity.this.getLevel(), InfinityCabinetBlockEntity.this.getBlockPos(), InfinityCabinetBlockEntity.this.getBlockState());
            }
        }

        @Override
        public void stopOpen(Player player) {
            if (!InfinityCabinetBlockEntity.this.remove && !player.isSpectator()) {
                InfinityCabinetBlockEntity.this.openersCounter.decrementOpeners(player, InfinityCabinetBlockEntity.this.getLevel(), InfinityCabinetBlockEntity.this.getBlockPos(), InfinityCabinetBlockEntity.this.getBlockState());
            }
        }
    };

    public InfinityCabinetBlockEntity(BlockPos pos, BlockState state) {
        super(ADBlockEntities.INFINITY_CABINET_BE.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, InfinityCabinetBlockEntity blockEntity) {
        blockEntity.recheckOpen();
    }

    protected void signalOpenCount(Level level, BlockPos pos, BlockState state, int eventId, int eventParam) {
        net.minecraft.world.level.block.Block block = state.getBlock();
        level.blockEvent(pos, block, 1, eventParam);
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.avaritia_delight.infinity_cabinet");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new InfinityCabinetMenu(id, inventory, this);
    }

    private void setOpen(BlockState state, boolean open) {
        assert this.level != null;
        this.level.setBlock(this.getBlockPos(), state.setValue(BarrelBlock.OPEN, open), 3);
    }

    private void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.getValue(BarrelBlock.FACING).getNormal();
        double d = (double) this.worldPosition.getX() + 0.5 + (double) vec3i.getX() / 2.0;
        double e = (double) this.worldPosition.getY() + 0.5 + (double) vec3i.getY() / 2.0;
        double f = (double) this.worldPosition.getZ() + 0.5 + (double) vec3i.getZ() / 2.0;
        assert this.level != null;
        this.level.playSound(null, d, e, f, soundEvent, SoundSource.BLOCKS, 0.5F, this.level.random.nextFloat() * 0.1F + 0.9F);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);

        NonNullList<ItemStack> itemStacks = NonNullList.withSize(300, ItemStack.EMPTY);
        componentInput.getOrDefault(ModDataComponents.CLUSTER_CONTAINER, ClusterContainerContents.EMPTY).copyInto(itemStacks);

        if (!itemStacks.isEmpty()) {
            for (int i = 0; i < itemStacks.size(); i++) {
                this.cabinet.getItems().set(i, itemStacks.get(i));
            }
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder builder) {
        super.collectImplicitComponents(builder);

        List<ItemStack> itemStacks = this.cabinet.getItems();
        builder.set(ModDataComponents.CLUSTER_CONTAINER, ClusterContainerContents.fromItems(itemStacks));
    }
}
