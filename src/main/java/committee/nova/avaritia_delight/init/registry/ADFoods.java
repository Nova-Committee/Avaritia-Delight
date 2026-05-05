package committee.nova.avaritia_delight.init.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ADFoods {
    private static final int EFFECT_TIME = Integer.MAX_VALUE;

    public static final FoodProperties EXPERIENCE_JELLY = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.5F).fast().effect(new MobEffectInstance(MobEffects.JUMP, 600, 0),1).alwaysEdible().build();
    public static final FoodProperties BLAZE_TOMATO_SAUCE = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(1.2F).build();

    public static final FoodProperties CROPS = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0F).build();



}

