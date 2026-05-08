package committee.nova.avaritia_delight.init.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ADFoods {
    public static final int EFFECT_TIME = 72000;

    public static final FoodProperties EXPERIENCE_JELLY = new FoodProperties.Builder()
            .nutrition(2).saturationModifier(0.525F).fast().effect(new MobEffectInstance(MobEffects.JUMP, 600, 0),1).alwaysEdible().build();
    public static final FoodProperties BLAZE_TOMATO_SAUCE = new FoodProperties.Builder()
            .nutrition(10).saturationModifier(0.65F).build();

    public static final FoodProperties NEUTRONIUM_BREAD = new FoodProperties.Builder()
            .nutrition(7).saturationModifier(0.525F).effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1200, 0),1).build();

    public static final FoodProperties COSMIC_BEEF = new FoodProperties.Builder()
            .nutrition(12).saturationModifier(0.8F).build();
    public static final FoodProperties COSMIC_BEEF_COOKED = new FoodProperties.Builder()
            .nutrition(15).saturationModifier(1.25F).build();

    public static final FoodProperties ENDEST_FRIED_EGG = new FoodProperties.Builder()
            .nutrition(9).saturationModifier(0.75F).build();
    public static final FoodProperties ENDEST_EGG_SANDWICH = new FoodProperties.Builder()
            .nutrition(15).saturationModifier(1.0F).effect(new MobEffectInstance(ADEffects.ENDEST, 400, 0),1).build();


    public static final FoodProperties INFINITY_APPLE = new FoodProperties.Builder()
            .nutrition(20).saturationModifier(1.0F)
            .effect(new MobEffectInstance(MobEffects.REGENERATION, EFFECT_TIME, 1), 1)
            .effect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, EFFECT_TIME, 1), 1)
            .effect(new MobEffectInstance(MobEffects.SATURATION, EFFECT_TIME, 0), 1)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, EFFECT_TIME, 4), 1)
            .effect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, EFFECT_TIME, 0), 1)
            .effect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, EFFECT_TIME, 4), 1)
            .effect(new MobEffectInstance(MobEffects.ABSORPTION, EFFECT_TIME, 4), 1)
            .alwaysEdible()
            .build();

    public static final FoodProperties CROPS = new FoodProperties.Builder()
            .nutrition(3).saturationModifier(1.0F).build();



}

