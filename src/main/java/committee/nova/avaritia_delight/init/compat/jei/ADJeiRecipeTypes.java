package committee.nova.avaritia_delight.init.compat.jei;

import committee.nova.avaritia_delight.common.crafting.recipe.CropExtractorRecipe;
import committee.nova.avaritia_delight.common.crafting.recipe.EXCookingRecipe;
import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ADJeiRecipeTypes {


    @SuppressWarnings("unchecked")
    public static final RecipeType<RecipeHolder<?>> EXTREME_COOKING = RecipeType.create(
            ADRecipeTypes.EXTREME_COOKING.getId().getNamespace(),
            ADRecipeTypes.EXTREME_COOKING.getId().getPath(),
            (Class) RecipeHolder.class
    );

    @SuppressWarnings("unchecked")
    public static final RecipeType<RecipeHolder<EXCookingRecipe>> EX_COOKING = RecipeType.create(
            ADRecipeTypes.EX_COOKING.getId().getNamespace(),
            ADRecipeTypes.EX_COOKING.getId().getPath(),
            (Class) RecipeHolder.class
    );

    @SuppressWarnings("unchecked")
    public static final RecipeType<RecipeHolder<CropExtractorRecipe>> CROP_EXTRACTOR = RecipeType.create(
            ADRecipeTypes.CROP_EXTRACTOR.getId().getNamespace(),
            ADRecipeTypes.CROP_EXTRACTOR.getId().getPath(),
            (Class) RecipeHolder.class
    );

}
