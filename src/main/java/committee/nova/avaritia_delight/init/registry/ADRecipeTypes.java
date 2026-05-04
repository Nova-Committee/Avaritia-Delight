package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.crafting.recipe.ExtremeCookingPotRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ADRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPES = DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, AvaritiaDelight.MOD_ID);

    public static final DeferredHolder<RecipeType<?>, RecipeType<ExtremeCookingPotRecipe>> EXTREME_COOKING = recipe("extreme_cooking", () -> RecipeType.simple(ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "extreme_cooking")));

    public static <T extends Recipe<?>> DeferredHolder<RecipeType<?>, RecipeType<T>> recipe(String name, Supplier<RecipeType<T>> type) {
        return RECIPES.register(name, type);
    }

    public static void register(IEventBus bus){
        RECIPES.register(bus);
    }
}
