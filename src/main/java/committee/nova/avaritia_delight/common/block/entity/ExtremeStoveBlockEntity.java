package committee.nova.avaritia_delight.common.block.entity;

import committee.nova.avaritia_delight.init.mixin.StoveAccessor;
import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.avaritia_delight.init.registry.ADItems;
import committee.nova.avaritia_delight.init.registry.ADTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec2;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.AbstractStoveBlock;
import vectorwing.farmersdelight.common.block.entity.AbstractStoveBlockEntity;
import vectorwing.farmersdelight.common.utility.ItemUtils;

public class ExtremeStoveBlockEntity extends AbstractStoveBlockEntity {

    public ExtremeStoveBlockEntity(BlockPos pos, BlockState state) {
        super(
                ADBlockEntities.EXTREME_STOVE_BE.get(),
                pos,
                state,
                RecipeType.CAMPFIRE_COOKING
        );
    }

    public static void particleTick(
            Level level,
            BlockPos pos,
            BlockState state,
            ExtremeStoveBlockEntity stoveEntity
    ) {
        if (!stoveEntity.isEmpty()) {
            stoveEntity.addSmokeParticles();
        }
    }

    public void addSmokeParticles() {
        assert this.level != null;

        ItemStackHandler items = this.getItems();

        for(int i = 0; i < items.getSlots(); ++i) {
            if (!items.getStackInSlot(i).isEmpty() && !(this.level.random.nextFloat() >= 0.2F)) {
                Vec2 itemOffset = this.getStoveItemOffset(i);
                Direction direction = (Direction)this.getBlockState().getValue(AbstractStoveBlock.FACING);
                if (direction.get2DDataValue() % 2 != 0) {
                    itemOffset = new Vec2(itemOffset.y, itemOffset.x);
                }

                double x = (double)this.worldPosition.getX() + (double)0.5F - (double)((float)direction.getStepX() * itemOffset.x) + (double)((float)direction.getClockWise().getStepX() * itemOffset.x);
                double y = (double)this.worldPosition.getY() + (double)1.0F;
                double z = (double)this.worldPosition.getZ() + (double)0.5F - (double)((float)direction.getStepZ() * itemOffset.y) + (double)((float)direction.getClockWise().getStepZ() * itemOffset.y);

                for(int k = 0; k < 3; ++k) {
                    this.level.addParticle(ParticleTypes.SMOKE, x, y, z, (double)0.0F, 5.0E-4, (double)0.0F);
                }
            }
        }

    }

    @Override
    protected int getInventorySlotCount() {
        return 6;
    }

    @Override
    public Vec2 getStoveItemOffset(int index) {
        Vec2[] offsets = new Vec2[]{
                new Vec2(0.3F, 0.2F),
                new Vec2(0.0F, 0.2F),
                new Vec2(-0.3F, 0.2F),
                new Vec2(0.3F, -0.2F),
                new Vec2(0.0F, -0.2F),
                new Vec2(-0.3F, -0.2F)
        };
        return offsets[index];
    }

    @Override
    public boolean placeFood(
            @Nullable Entity entity,
            ItemStack foodStackToPlace,
            RecipeHolder<? extends AbstractCookingRecipe> recipe
    ) {
        int emptySlotIndex = this.getNextEmptySlot();

        if (emptySlotIndex < 0) {
            return false;
        }

        StoveAccessor accessor = (StoveAccessor) this;

        ItemStack placed = foodStackToPlace.split(1);

        if (placed.is(ADItems.cosmic_beef.get())) {
            accessor.getCookingTime()[emptySlotIndex] = 200;
        } else {
            accessor.getCookingTime()[emptySlotIndex] =
                    placed.is(ADTags.LONG_TIME_COOK)
                            ? 200
                            : 20;
        }

        accessor.getCookingProgress()[emptySlotIndex] = 0;

        this.getItems().setStackInSlot(emptySlotIndex, placed);
        this.setChanged();

        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }

        return true;
    }

    public static void serverTick(
            Level level,
            BlockPos pos,
            BlockState state,
            ExtremeStoveBlockEntity stoveEntity
    ) {
        if (!stoveEntity.isEmpty()) {
            if (stoveEntity.shouldDropItems()) {
                stoveEntity.dropAllItems();
                stoveEntity.setChanged();
            } else {
                if (state.getValue(AbstractStoveBlock.LIT)) {
                    stoveEntity.cookAndOutputItemsCustom();
                } else {
                    stoveEntity.coolItemsCustom();
                }
            }
        }
    }
    private void cookAndOutputItemsCustom() {
        assert this.level != null;

        StoveAccessor accessor = (StoveAccessor) this;

        boolean didChange = false;

        for (int i = 0; i < this.getItems().getSlots(); ++i) {
            ItemStack ingredient = this.getItems().getStackInSlot(i);

            if (!ingredient.isEmpty()) {
                didChange = true;
                accessor.getCookingProgress()[i]++;

                if (accessor.getCookingProgress()[i]
                        >= accessor.getCookingTime()[i]) {

                    ItemStack result;

                    if (ingredient.is(
                            ADItems.cosmic_beef.get())) {

                        result = new ItemStack(
                                ADItems.cosmic_beef_cooked.get()
                        );

                    } else {

                        SingleRecipeInput input =
                                new SingleRecipeInput(ingredient);

                        result = this.getCookingRecipe(ingredient)
                                .map(recipe ->
                                        recipe.value().assemble(
                                                input,
                                                this.level.registryAccess()
                                        ))
                                .orElse(ingredient);
                    }

                    if (result.isItemEnabled(
                            this.level.enabledFeatures())) {

                        ItemUtils.spawnItemEntity(
                                this.level,
                                result.copy(),
                                this.worldPosition.getX() + 0.5D,
                                this.worldPosition.getY() + 1.0D,
                                this.worldPosition.getZ() + 0.5D,
                                this.level.random.nextGaussian() * 0.01D,
                                0.1D,
                                this.level.random.nextGaussian() * 0.01D
                        );

                        this.getItems().setStackInSlot(
                                i,
                                ItemStack.EMPTY
                        );

                        BlockState state = this.getBlockState();

                        this.level.sendBlockUpdated(
                                this.worldPosition,
                                state,
                                state,
                                3
                        );

                        this.level.gameEvent(
                                GameEvent.BLOCK_CHANGE,
                                this.worldPosition,
                                GameEvent.Context.of(state)
                        );
                    }
                }
            }
        }

        if (didChange) {
            this.setChanged();
        }
    }

    private void coolItemsCustom() {
        StoveAccessor accessor = (StoveAccessor) this;

        boolean didChange = false;

        for (int i = 0; i < this.getItems().getSlots(); ++i) {
            int progress = accessor.getCookingProgress()[i];

            if (progress > 0) {
                didChange = true;

                accessor.getCookingProgress()[i] =
                        Math.max(
                                progress - 2,
                                0
                        );
            }
        }

        if (didChange) {
            this.setChanged();
        }
    }
}