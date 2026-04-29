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

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> CREATIVE_TAB = TABS.register("expand_group", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tab.avaritia_delight"))
            .icon(ADItems.infinity_knife.get()::getDefaultInstance)
            .displayItems((parameters, output) -> {
                output.accept(ADItems.blaze_knife.get());
                output.accept(ADItems.crystal_knife.get());
                output.accept(ADItems.neutronium_knife.get());
                output.accept(ADItems.infinity_knife.get());
                output.accept(ADItems.blaze_tomato.get());
            }).build());
    public static void register(IEventBus bus){
        TABS.register(bus);
    }

}
