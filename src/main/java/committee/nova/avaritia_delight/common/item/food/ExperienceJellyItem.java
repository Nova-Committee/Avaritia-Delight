package committee.nova.avaritia_delight.common.item.food;

import committee.nova.avaritia_delight.init.registry.ADRarities;
import committee.nova.avaritia_delight.util.EffectUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.enchantment.EnchantedItemInUse;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class ExperienceJellyItem extends Item {

    public ExperienceJellyItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player) {
            if (player instanceof ServerPlayer serverPlayer) {
                int i = this.repairPlayerItems(serverPlayer, 100);
                if (i > 0) {
                    player.giveExperiencePoints(i);
                }
            }
        }
        return super.finishUsingItem(stack, level, livingEntity);
    }

    private int repairPlayerItems(ServerPlayer player, int value) {
        Optional<EnchantedItemInUse> optional = EnchantmentHelper.getRandomItemWith(EnchantmentEffectComponents.REPAIR_WITH_XP, player, ItemStack::isDamaged);
        if (optional.isPresent()) {
            ItemStack itemstack = optional.get().itemStack();
            int i = EnchantmentHelper.modifyDurabilityToRepairFromXp(player.serverLevel(), itemstack, (int) (value * itemstack.getXpRepairRatio()));
            int j = Math.min(i, itemstack.getDamageValue());
            itemstack.setDamageValue(itemstack.getDamageValue() - j);
            if (j > 0) {
                int k = value - j * value / i;
                if (k > 0) {
                    return this.repairPlayerItems(player, k);
                }
            }

            return 0;
        } else {
            return value;
        }
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.EAT;
    }
}
