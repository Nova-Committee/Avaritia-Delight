package committee.nova.avaritia_delight.init.compat.jei.category;

import committee.nova.avaritia_delight.common.crafting.recipe.EXCookingRecipe;
import committee.nova.avaritia_delight.init.compat.jei.ADJeiRecipeTypes;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.library.plugins.vanilla.cooking.AbstractCookingCategory;
import mezz.jei.library.util.RecipeUtil;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;

public class ExtremeStoveRecipeCategory extends AbstractCookingCategory<EXCookingRecipe> {

    public ExtremeStoveRecipeCategory(IGuiHelper guiHelper) {
        super(
                guiHelper,
                ADJeiRecipeTypes.EX_COOKING,
                ADBlocks.extreme_stove.get(),
                "block.avaritia_delight.extreme_stove",
                200
        );

    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<EXCookingRecipe> recipeHolder, IFocusGroup focuses) {
        EXCookingRecipe recipe = (EXCookingRecipe)(recipeHolder.value());
        builder.addInputSlot(1, 19).setStandardSlotBackground().addIngredients((Ingredient)recipe.getIngredients().getFirst());
        builder.addOutputSlot(61, 19).setOutputSlotBackground().addItemStack(RecipeUtil.getResultItem(recipe));
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, RecipeHolder<EXCookingRecipe> recipeHolder, IFocusGroup focuses) {
        EXCookingRecipe recipe = (EXCookingRecipe)(recipeHolder.value());
        int cookTime = recipe.getCookingTime();
        if (cookTime <= 0) {
            cookTime = this.regularCookTime;
        }

        builder.addAnimatedRecipeArrow(cookTime).setPosition(26, 17);

        this.addExperience(builder, recipeHolder);
        this.addCookTime(builder, recipeHolder);
    }
}
