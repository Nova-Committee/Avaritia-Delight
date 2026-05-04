package committee.nova.avaritia_delight.init.compat.jei;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.client.screen.ExtremeCookingPotScreen;
import committee.nova.avaritia_delight.common.menu.ExtremeCookingPotMenu;
import committee.nova.avaritia_delight.init.compat.jei.category.ExtremeCookingRecipeCategory;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import committee.nova.avaritia_delight.init.registry.ADMenus;
import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@JeiPlugin
public class ADJeiPlugin implements IModPlugin {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID,"jei_plugin");


    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new ExtremeCookingRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ADJeiRecipes modRecipes = new ADJeiRecipes();
        registration.addRecipes(ADJeiRecipeTypes.EXTREME_COOKING, modRecipes.getAllCookingRecipes());
    }
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ADBlocks.extreme_cooking_pot.get()), ADJeiRecipeTypes.EXTREME_COOKING);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ExtremeCookingPotScreen.class, 172, 90, 21, 17, ADJeiRecipeTypes.EXTREME_COOKING);
    }

    @Override
    public void registerRecipeTransferHandlers(IRecipeTransferRegistration registration) {
        registration.addRecipeTransferHandler(ExtremeCookingPotMenu.class, ADMenus.extreme_cooking_pot.get(),ADJeiRecipeTypes.EXTREME_COOKING, 0, 81, 84, 36);
    }
    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }
}
