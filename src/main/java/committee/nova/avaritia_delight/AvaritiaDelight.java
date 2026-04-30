package committee.nova.avaritia_delight;

import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import committee.nova.avaritia_delight.init.registry.ADCreativeModeTabs;
import committee.nova.avaritia_delight.init.registry.ADItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(AvaritiaDelight.MOD_ID)
public class AvaritiaDelight {

    public static final String MOD_ID = "avaritia_delight";

    public AvaritiaDelight(IEventBus modEventBus, ModContainer modContainer) {
        ADItems.register(modEventBus);
        ADBlocks.register(modEventBus);
        ADBlockEntities.register(modEventBus);
        ADCreativeModeTabs.register(modEventBus);
    }

}
