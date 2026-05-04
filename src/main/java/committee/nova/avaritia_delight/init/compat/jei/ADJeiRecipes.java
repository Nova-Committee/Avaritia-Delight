package committee.nova.avaritia_delight.init.compat.jei;

import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.crafting.*;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import java.util.List;

public class ADJeiRecipes {


    private final RecipeManager recipeManager;

    public ADJeiRecipes() {
        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;

        if (level != null) {
            this.recipeManager = level.getRecipeManager();
        } else {
            throw new NullPointerException("Minecraft level must not be null.");
        }
    }

    public List<RecipeHolder<?>> getAllCookingRecipes() {
        List<RecipeHolder<?>> allRecipes = new java.util.ArrayList<>();
        allRecipes.addAll(recipeManager.getAllRecipesFor(ADRecipeTypes.EXTREME_COOKING.get()));
        allRecipes.addAll(recipeManager.getAllRecipesFor(ModRecipeTypes.COOKING.get()));
        return allRecipes;
    }

}
