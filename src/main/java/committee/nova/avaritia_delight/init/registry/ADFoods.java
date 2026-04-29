package committee.nova.avaritia_delight.init.registry;

import net.minecraft.world.food.FoodProperties;

public class ADFoods {
    public static final FoodProperties LITTLE_FOOD = new FoodProperties.Builder().nutrition(8).saturationModifier(0.6F).build();
    public static final FoodProperties LITTLE_FOOD_FAST = new FoodProperties.Builder().nutrition(8).saturationModifier(0.6F).fast().build();
    public static final FoodProperties NORMAL_FOOD = new FoodProperties.Builder().nutrition(16).saturationModifier(0.625F).build();
    public static final FoodProperties NORMAL_FOOD_FAST = new FoodProperties.Builder().nutrition(16).saturationModifier(0.625F).fast().build();
    public static final FoodProperties FAT_FOOD = new FoodProperties.Builder().nutrition(20).saturationModifier(0.8F).build();
    public static final FoodProperties FAT_FOOD_FAST = new FoodProperties.Builder().nutrition(20).saturationModifier(0.8F).fast().build();
}
