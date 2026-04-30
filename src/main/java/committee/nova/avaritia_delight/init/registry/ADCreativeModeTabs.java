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
            .icon(ADItems.infinity_knife.get()::getDefaultInstance)
            .displayItems((parameters, output) -> {
                output.accept(ADItems.blaze_knife.get());
                output.accept(ADItems.crystal_knife.get());
                output.accept(ADItems.neutronium_knife.get());
                output.accept(ADItems.infinity_knife.get());
                output.accept(ADItems.blaze_tomato_seeds.get());
                output.accept(ADItems.blaze_tomato.get());
                output.accept(ADItems.diamond_lattice_potato.get());
                output.accept(ADItems.diamond_lattice_fries.get());
                output.accept(ADItems.crystal_cabbage_seeds.get());
                output.accept(ADItems.crystal_cabbage.get());
                output.accept(ADItems.crystal_cabbage_leaf.get());
                output.accept(ADItems.raw_crystal_pasta.get());
                output.accept(ADItems.neutronium_wheat_seeds.get());
                output.accept(ADItems.neutronium_wheat.get());
                output.accept(ADItems.neutronium_wheat_dough.get());
                output.accept(ADItems.neutronium_bread.get());
                output.accept(ADItems.cosmic_beef.get());
                output.accept(ADItems.cosmic_beef_cooked.get());
                output.accept(ADItems.infinity_apple.get());
                output.accept(ADItems.furious_cocktail.get());
                output.accept(ADItems.how_did_we_get_here.get());
                output.accept(ADItems.infinity_milk.get());
                output.accept(ADBlocks.extreme_stove.get());
            }).build());
    public static void register(IEventBus bus){
        TABS.register(bus);
    }

}
