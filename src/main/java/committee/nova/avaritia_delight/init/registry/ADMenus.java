package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.client.screen.CropExtractorScreen;
import committee.nova.avaritia_delight.client.screen.ExtremeCookingPotScreen;
import committee.nova.avaritia_delight.client.screen.InfinityBasketScreen;
import committee.nova.avaritia_delight.client.screen.InfinityCabinetScreen;
import committee.nova.avaritia_delight.common.menu.CropExtractorMenu;
import committee.nova.avaritia_delight.common.menu.ExtremeCookingPotMenu;
import committee.nova.avaritia_delight.common.menu.InfinityBasketMenu;
import committee.nova.avaritia_delight.common.menu.InfinityCabinetMenu;
import committee.nova.mods.avaritia.client.screen.craft.SculkCraftScreen;
import committee.nova.mods.avaritia.common.menu.NeutronRingMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ADMenus {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, AvaritiaDelight.MOD_ID);

    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup(RegisterMenuScreensEvent event) {
        event.register(extreme_cooking_pot.get(), ExtremeCookingPotScreen::new);
        event.register(crop_extractor.get(), CropExtractorScreen::new);
        event.register(infinity_cabinet.get(), InfinityCabinetScreen::new);
        event.register(infinity_basket.get(), InfinityBasketScreen::new);
    }

    public static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>, MenuType<T>> menu(String name, Supplier<? extends MenuType<T>> container) {
        return MENUS.register(name, container);
    }

    public static DeferredHolder<MenuType<?>, MenuType<ExtremeCookingPotMenu>> extreme_cooking_pot = menu("extreme_cooking_pot",
            () -> new MenuType<>((IContainerFactory<ExtremeCookingPotMenu>) ExtremeCookingPotMenu::new, FeatureFlagSet.of()));
    public static DeferredHolder<MenuType<?>, MenuType<CropExtractorMenu>> crop_extractor = menu("crop_extractor",
            () -> new MenuType<>((IContainerFactory<CropExtractorMenu>) CropExtractorMenu::new, FeatureFlagSet.of()));

    public static DeferredHolder<MenuType<?>, MenuType<InfinityCabinetMenu>> infinity_cabinet = menu("infinity_cabinet",
            () -> new MenuType<>((IContainerFactory<InfinityCabinetMenu>) InfinityCabinetMenu::new, FeatureFlagSet.of()));
    public static DeferredHolder<MenuType<?>, MenuType<InfinityBasketMenu>> infinity_basket = menu("infinity_basket",
            () -> new MenuType<>((IContainerFactory<InfinityBasketMenu>) InfinityBasketMenu::new, FeatureFlagSet.of()));


    public static void register(IEventBus bus){
        MENUS.register(bus);
    }
}
