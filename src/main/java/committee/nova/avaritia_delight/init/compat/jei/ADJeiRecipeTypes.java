package committee.nova.avaritia_delight.init.compat.jei;

import committee.nova.avaritia_delight.common.crafting.recipe.ExtremeCookingPotRecipe;
import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ADJeiRecipeTypes {


    public static final RecipeType<RecipeHolder<ExtremeCookingPotRecipe>> EXTREME_COOKING = RecipeType.createFromVanilla(ADRecipeTypes.EXTREME_COOKING.get());

}
