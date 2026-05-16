package committee.nova.avaritia_delight.common.item.food;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class InfinityFoodItem extends Item {

    public InfinityFoodItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player){
            FoodProperties foodproperties = this.getFoodProperties(stack, livingEntity);
            if (foodproperties != null) {
                player.getFoodData().eat(foodproperties);

                if (!level.isClientSide) {
                    for (FoodProperties.PossibleEffect mobeffectinstance : foodproperties.effects()) {
                        livingEntity.addEffect(new MobEffectInstance(mobeffectinstance.effect()));
                    }
                }

                if (player instanceof ServerPlayer serverPlayer){
                    CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
                    serverPlayer.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(level, player, hand);
    }

    public int getUseDuration(ItemStack p_42933_, LivingEntity p_345727_) {
        return 32;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack p_41452_) {
        return UseAnim.EAT;
    }
}
