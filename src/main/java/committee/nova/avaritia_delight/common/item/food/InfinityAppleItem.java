package committee.nova.avaritia_delight.common.item.food;

import committee.nova.avaritia_delight.init.registry.ADFoods;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

import static committee.nova.avaritia_delight.init.registry.ADFoods.EFFECT_TIME;

public class InfinityAppleItem extends Item {
    public InfinityAppleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player){
            player.getFoodData().eat(ADFoods.INFINITY_APPLE);
            if (player instanceof ServerPlayer serverPlayer){
                CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer,stack);
                serverPlayer.awardStat(Stats.ITEM_USED.get(this));
            }
        }
        if (!level.isClientSide)
            livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, EFFECT_TIME, 1), livingEntity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EFFECT_TIME, 1), livingEntity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.SATURATION, EFFECT_TIME, 0), livingEntity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, EFFECT_TIME, 4), livingEntity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, EFFECT_TIME, 0), livingEntity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, EFFECT_TIME, 4), livingEntity);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, EFFECT_TIME, 4), livingEntity);
        return stack;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player,InteractionHand hand) {
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
