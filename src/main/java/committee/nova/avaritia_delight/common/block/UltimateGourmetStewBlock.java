package committee.nova.avaritia_delight.common.block;

import committee.nova.avaritia_delight.init.registry.ADFoods;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class UltimateGourmetStewBlock extends AbstractStewBlock{

    public UltimateGourmetStewBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide) {
            player.getFoodData().eat(2,0.2F);
            for (FoodProperties.PossibleEffect possibleEffect : ADFoods.ULTIMATE_GOURMET_STEW.effects()) {
                player.addEffect(new MobEffectInstance(possibleEffect.effect()));
            }
        }
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }


}
