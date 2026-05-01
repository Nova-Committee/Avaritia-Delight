package committee.nova.avaritia_delight.common.item.food;

import java.util.List;

import committee.nova.avaritia_delight.util.EffectUtil;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import org.jetbrains.annotations.NotNull;

public class HowDidWeGetHereItem extends Item {

    public HowDidWeGetHereItem() {
        super(EffectUtil.applyEffects(new Properties().stacksTo(1).rarity(ModRarities.COSMIC.getValue()), List.of(
                MobEffects.MOVEMENT_SPEED,
                MobEffects.MOVEMENT_SLOWDOWN,
                MobEffects.DAMAGE_BOOST,
                MobEffects.JUMP,
                MobEffects.REGENERATION,
                MobEffects.FIRE_RESISTANCE,
                MobEffects.WATER_BREATHING,
                MobEffects.INVISIBILITY,
                MobEffects.NIGHT_VISION,
                MobEffects.WEAKNESS,
                MobEffects.POISON,
                MobEffects.WITHER,
                MobEffects.DIG_SPEED,
                MobEffects.DIG_SLOWDOWN,
                MobEffects.LEVITATION,
                MobEffects.GLOWING,
                MobEffects.ABSORPTION,
                MobEffects.HUNGER,
                MobEffects.CONFUSION,
                MobEffects.DAMAGE_RESISTANCE,
                MobEffects.SLOW_FALLING,
                MobEffects.CONDUIT_POWER,
                MobEffects.DOLPHINS_GRACE,
                MobEffects.BLINDNESS,
                MobEffects.BAD_OMEN,
                MobEffects.HERO_OF_THE_VILLAGE,
                MobEffects.DARKNESS,
                MobEffects.OOZING,
                MobEffects.INFESTED,
                MobEffects.WIND_CHARGED,
                MobEffects.WEAVING,
                MobEffects.TRIAL_OMEN,
                MobEffects.RAID_OMEN
        ), Integer.MAX_VALUE));
    }

    @Override
    public @NotNull UseAnim getUseAnimation(@NotNull ItemStack stack) {
        return UseAnim.DRINK;
    }
}
