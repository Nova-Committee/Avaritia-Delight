package committee.nova.avaritia_delight.common.crafting.recipe;

import committee.nova.avaritia_delight.init.data.provider.ADRecipeSerializers;
import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import org.jetbrains.annotations.NotNull;

public class EXCookingRecipe extends AbstractCookingRecipe {

    public EXCookingRecipe(
            String group,
            CookingBookCategory category,
            Ingredient ingredient,
            ItemStack result,
            float experience,
            int cookingTime
    ) {
        super(
                ADRecipeTypes.EX_COOKING.get(),
                group,
                category,
                ingredient,
                result,
                experience,
                cookingTime
        );
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return super.getIngredients();
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ADRecipeSerializers.EX_COOKING.get();
    }
}