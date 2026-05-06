package committee.nova.avaritia_delight.init.compat.jei.category;

import com.mojang.blaze3d.MethodsReturnNonnullByDefault;
import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.crafting.recipe.CropExtractorRecipe;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CropExtractorRecipeCategory implements IRecipeCategory<RecipeHolder<CropExtractorRecipe>> {

    protected final IDrawableAnimated arrow;
    private final Component title;
    private final IDrawable background;
    private final IDrawable icon;

    public CropExtractorRecipeCategory(IGuiHelper helper) {
        title = TextUtils.JEI("crop_extractor");
        ResourceLocation widgetBackgroundImage = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "textures/gui/jei/crop_extractor.png");
        ResourceLocation interfaceImage = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID, "textures/gui/crop_extractor.png");
        background = helper.createDrawable(widgetBackgroundImage, 0, 0, 176, 166);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ADBlocks.crop_extractor.get()));

        arrow = helper.drawableBuilder(interfaceImage, 177, 47, 26, 17)
                .buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }

    @Override
    public RecipeType<RecipeHolder<CropExtractorRecipe>> getRecipeType() {
        return ADJeiRecipeTypes.CROP_EXTRACTOR;
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
    public void setRecipe(IRecipeLayoutBuilder builder, RecipeHolder<CropExtractorRecipe> holder, IFocusGroup focusGroup) {
        CropExtractorRecipe recipe = holder.value();

        builder.addSlot(RecipeIngredientRole.INPUT, 36, 21)
                .addItemStacks(java.util.Arrays.asList(recipe.getInput().getItems()));

        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 12)
                .addItemStack(recipe.getOutput1());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 117, 12)
                .addItemStack(recipe.getOutput2());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 99, 30)
                .addItemStack(recipe.getOutput3());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 117, 30)
                .addItemStack(recipe.getOutput4());
    }

    @Override
    public void draw(RecipeHolder<CropExtractorRecipe> holder, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        background.draw(guiGraphics, 0, 0);
        arrow.draw(guiGraphics, 64, 24);
    }

    @Override
    public void getTooltip(ITooltipBuilder tooltip, RecipeHolder<CropExtractorRecipe> recipe, IRecipeSlotsView recipeSlotsView, double mouseX, double mouseY) {
        if (isCursorInsideBounds(64, 24, 26, 17, mouseX, mouseY)) {
            int extractionTime = recipe.value().getExtractionTime();
            if (extractionTime > 0) {
                int extractionTimeSeconds = extractionTime / 20;
                tooltip.add(Component.translatable("gui.jei.category.smelting.time.seconds", extractionTimeSeconds));
            }
        }
    }

    private boolean isCursorInsideBounds(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= x && mouseX < x + width && mouseY >= y && mouseY < y + height;
    }
}
