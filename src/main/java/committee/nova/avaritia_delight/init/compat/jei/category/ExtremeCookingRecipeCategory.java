package committee.nova.avaritia_delight.init.compat.jei.category;

import com.mojang.blaze3d.MethodsReturnNonnullByDefault;
import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.crafting.recipe.ExtremeCookingPotRecipe;
import committee.nova.avaritia_delight.init.compat.jei.ADJeiRecipeTypes;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.utility.ClientRenderUtils;
import vectorwing.farmersdelight.common.utility.RecipeUtils;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Arrays;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ExtremeCookingRecipeCategory implements IRecipeCategory<RecipeHolder<ExtremeCookingPotRecipe>> {

    protected final IDrawable heatIndicator;
    protected final IDrawable timeIcon;
    protected final IDrawable expIcon;
    protected final IDrawableAnimated arrow;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;


    public ExtremeCookingRecipeCategory(IGuiHelper helper) {
        title = TextUtils.JEI("extreme_cooking");
        ResourceLocation widgetBackgroundImage = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "textures/gui/jei/extreme_cooking_pot.png");
        ResourceLocation interfaceImage = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "textures/gui/extreme_cooking_pot.png");
        background = helper.createDrawable(widgetBackgroundImage, 0, 0, 190, 163);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ADBlocks.extreme_cooking_pot.get()));
        heatIndicator = helper.createDrawable(interfaceImage, 234, 0, 17, 15);
        timeIcon = helper.createDrawable(interfaceImage, 234, 32, 8, 11);
        expIcon = helper.createDrawable(interfaceImage, 234, 43, 9, 9);
        arrow = helper.drawableBuilder(interfaceImage, 234, 15, 21, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }


    @Override
    public RecipeType<RecipeHolder<ExtremeCookingPotRecipe>> getRecipeType() {
        return ADJeiRecipeTypes.EXTREME_COOKING;
    }

    @Override
    public Component getTitle() {
        return this.title;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return this.icon;
    }

    @SuppressWarnings("removal")
    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<ExtremeCookingPotRecipe> holder, IFocusGroup focusGroup) {
        ExtremeCookingPotRecipe recipe = holder.value();
        NonNullList<Ingredient> recipeIngredients = recipe.getIngredients();
        ItemStack resultStack = RecipeUtils.getResultItem(recipe);
        ItemStack containerStack = recipe.getOutputContainer();

        int borderSlotSize = 18;
        for (int row = 0; row < 9; ++row) {
            for (int column = 0; column < 9; ++column) {
                int inputIndex = row * 9 + column;
                if (inputIndex < recipeIngredients.size()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, (column * borderSlotSize) + 2, (row * borderSlotSize) + 2)
                            .addItemStacks(Arrays.asList(recipeIngredients.get(inputIndex).getItems()));
                }
            }
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 168, 74).addItemStack(resultStack);

        if (!containerStack.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.CATALYST, 168, 99).addItemStack(containerStack);
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 168, 137).addItemStack(resultStack);
    }

    @Override
    public void draw(RecipeHolder<ExtremeCookingPotRecipe> holder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
        arrow.draw(guiGraphics, 147, 73);
        heatIndicator.draw(guiGraphics, 152, 49);
        timeIcon.draw(guiGraphics, 139, 5);
        if (holder.value().getExperience() > 0) {
            expIcon.draw(guiGraphics, 138, 24);
        }
    }


    @Override
    public void getTooltip(ITooltipBuilder tooltip, RecipeHolder<ExtremeCookingPotRecipe> recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (ClientRenderUtils.isCursorInsideBounds(136, 2, 22, 28, mouseX, mouseY)) {
            int cookTime = recipe.value().getCookTime();
            if (cookTime > 0) {
                int cookTimeSeconds = cookTime / 20;
                tooltip.add(Component.translatable("gui.jei.category.smelting.time.seconds", cookTimeSeconds));
            }
            float experience = recipe.value().getExperience();
            if (experience > 0) {
                tooltip.add(Component.translatable("gui.jei.category.smelting.experience", experience));
            }
        }
    }
}

