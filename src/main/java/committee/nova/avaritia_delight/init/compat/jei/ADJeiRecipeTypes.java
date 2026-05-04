package committee.nova.avaritia_delight.init.compat.jei;

import committee.nova.avaritia_delight.common.crafting.recipe.ExtremeCookingPotRecipe;
import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ADJeiRecipeTypes {


    @SuppressWarnings("unchecked")
    public static final RecipeType<RecipeHolder<?>> EXTREME_COOKING = RecipeType.create(
            ADRecipeTypes.EXTREME_COOKING.getId().getNamespace(),
            ADRecipeTypes.EXTREME_COOKING.getId().getPath(),
            (Class) RecipeHolder.class
    );

}
