package committee.nova.avaritia_delight.common.item.food;

import committee.nova.avaritia_delight.init.registry.ADFoods;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
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

import java.util.List;

public class RecordFragmentCookieItem extends Item {

    public RecordFragmentCookieItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, stack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }

        if (!level.isClientSide) {
            if (livingEntity instanceof Player player) {
                player.getFoodData().eat(ADFoods.NEUTRONIUM_BREAD);

                List<SoundEvent> sounds = BuiltInRegistries.SOUND_EVENT
                        .stream()
                        .toList();

                if (!sounds.isEmpty()) {
                    RandomSource random = level.getRandom();

                    SoundEvent sound = sounds.get(random.nextInt(sounds.size()));

                    level.playSound(
                            null,
                            player.getX(),
                            player.getY(),
                            player.getZ(),
                            sound,
                            SoundSource.PLAYERS,
                            1.0F,
                            0.8F + random.nextFloat() * 0.4F
                    );
                }
            }
        }

        stack.consume(1, livingEntity);

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
