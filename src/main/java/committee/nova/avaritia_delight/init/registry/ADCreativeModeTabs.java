package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ADCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AvaritiaDelight.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = TABS.register("delight_group", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tab.avaritia_delight"))
            .icon(ADBlocks.extreme_stove.get().asItem()::getDefaultInstance)
            .displayItems((parameters, output) -> {
                output.accept(ADItems.blaze_knife.get());
                output.accept(ADItems.crystal_knife.get());
                output.accept(ADItems.neutronium_knife.get());
                output.accept(ADItems.infinity_knife.get());
                output.accept(ADItems.blaze_tomato_seeds.get());
                output.accept(ADItems.blaze_tomato.get());
                output.accept(ADItems.blaze_tomato_sauce.get());
                output.accept(ADItems.diamond_lattice_potato.get());
                output.accept(ADItems.diamond_lattice_fries.get());
                output.accept(ADItems.crystal_cabbage_seeds.get());
                output.accept(ADItems.crystal_cabbage.get());
                output.accept(ADItems.crystal_cabbage_leaf.get());
                output.accept(ADItems.neutronium_wheat_seeds.get());
                output.accept(ADItems.neutronium_wheat.get());
                output.accept(ADItems.neutronium_wheat_dough.get());
                output.accept(ADItems.neutronium_bread.get());
                output.accept(ADItems.cosmic_beef.get());
                output.accept(ADItems.cosmic_beef_cooked.get());
                output.accept(ADItems.endest_egg.get());
                output.accept(ADItems.endest_fried_egg.get());
                output.accept(ADItems.star_pie_crust.get());
                output.accept(ADBlocks.star_pie.get());
                output.accept(ADItems.slice_star_pie.get());
                output.accept(ADBlocks.endest_pie.get());
                output.accept(ADItems.slice_endest_pie.get());
                output.accept(ADItems.endest_egg_sandwich.get());
                output.accept(ADItems.record_fragment_cookie.get());
                output.accept(ADItems.experience_jelly.get());
                output.accept(ADItems.slice_of_endless_cake.get());
                output.accept(ADItems.infinity_fries.get());
                output.accept(ADItems.infinity_salad.get());
                output.accept(ADItems.infinity_taco.get());
                output.accept(ADItems.infinity_catalyst_cookie.get());
                output.accept(ADItems.infinity_large_hamburger.get());
                output.accept(ADItems.infinity_apple.get());
                output.accept(ADItems.infinity_flowers_tea.get());
                output.accept(ADItems.furious_cocktail.get());
                output.accept(ADItems.how_did_we_get_here.get());
                output.accept(ADItems.infinity_milk.get());
                output.accept(ADItems.neutronium_bowl.get());
                output.accept(ADItems.neutronium_pot.get());
                output.accept(ADBlocks.diamond_lattice_potato_crate.get());
                output.accept(ADBlocks.blaze_tomato_crate.get());
                output.accept(ADBlocks.crystal_cabbage_crate.get());
                output.accept(ADBlocks.neutronium_hay_bale.get());
                output.accept(ADBlocks.crop_extractor.get());
                output.accept(ADBlocks.infinity_cabinet.get());
                output.accept(ADBlocks.infinity_basket.get());
                output.accept(ADBlocks.extreme_stove.get());
                output.accept(ADBlocks.extreme_cooking_pot.get());
                output.accept(ADItems.extreme_skillet.get());
            }).build());
    public static void register(IEventBus bus){
        TABS.register(bus);
    }

}
