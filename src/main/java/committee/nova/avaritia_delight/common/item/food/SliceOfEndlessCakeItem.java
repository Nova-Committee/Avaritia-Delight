package committee.nova.avaritia_delight.common.item.food;

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

public class SliceOfEndlessCakeItem extends Item {

    public SliceOfEndlessCakeItem(Properties properties) {
        super(properties);
    }

    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, stack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide) {
            if (livingEntity instanceof Player player) {
                player.getFoodData().eat(2, 0.1F);
            }
            livingEntity.removeEffect(MobEffects.POISON);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));

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
