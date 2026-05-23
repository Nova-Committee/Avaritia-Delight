package committee.nova.avaritia_delight.common.block;

import committee.nova.avaritia_delight.common.item.food.InfinityFoodItem;
import committee.nova.avaritia_delight.init.registry.ADFoods;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MobsStewBlock extends AbstractStewBlock{

    public MobsStewBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            player.getFoodData().eat(2,0.2F);
            for (FoodProperties.PossibleEffect possibleEffect : ADFoods.MOBS_STEW.effects()) {
                player.addEffect(new MobEffectInstance(possibleEffect.effect()));
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }


}
