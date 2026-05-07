package committee.nova.avaritia_delight.util;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

import java.util.List;

public class EffectUtil {
    public static Item.Properties applyEffects(Item.Properties settings, List<Holder<MobEffect>> effects, int duration , boolean alwaysEdible) {
        FoodProperties.Builder builder = new FoodProperties.Builder();
        for (Holder<MobEffect> effect : effects)
            builder.effect(() -> new MobEffectInstance(effect, duration), 1);
        if (alwaysEdible){
            builder.alwaysEdible();
        }
        return settings.food(builder.build());
    }
}
