package committee.nova.avaritia_delight.init.data.provider;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.crafting.recipe.ExtremeCookingPotRecipe;
import committee.nova.mods.avaritia.common.crafting.recipe.ShapedTableCraftingRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ADRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, AvaritiaDelight.MOD_ID);

    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> EXTREME_COOKING = serializer("extreme_cooking", ExtremeCookingPotRecipe.Serializer::new);


    public static DeferredHolder<RecipeSerializer<?>, RecipeSerializer<?>> serializer(String name, Supplier<RecipeSerializer<?>> serializer) {
        return SERIALIZERS.register(name, serializer);
    }
    public static void register(IEventBus bus){
        SERIALIZERS.register(bus);
    }
}
