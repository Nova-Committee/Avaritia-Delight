package committee.nova.avaritia_delight.init.compat.jei;

import committee.nova.avaritia_delight.common.crafting.recipe.ExtremeCookingPotRecipe;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

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

    public List<RecipeHolder<ExtremeCookingPotRecipe>> getCookingPotRecipes() {
        return recipeManager.getAllRecipesFor(ADRecipeTypes.EXTREME_COOKING.get());
    }

}
