package committee.nova.avaritia_delight.client;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.avaritia_delight.init.registry.ADMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = AvaritiaDelight.MOD_ID, value = Dist.CLIENT)
public class ADClient {

    @SubscribeEvent
    public static void clientSetUp(FMLClientSetupEvent event) {
        ADBlockEntities.onClientSetup();
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        ADMenus.onClientSetup(event);
    }
}
