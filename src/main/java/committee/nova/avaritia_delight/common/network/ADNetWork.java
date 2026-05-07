package committee.nova.avaritia_delight.common.network;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.item.tool.ExtremeSkilletItem;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import vectorwing.farmersdelight.common.registry.ModDataComponents;

@EventBusSubscriber(modid = AvaritiaDelight.MOD_ID)
public class ADNetWork {
    @SubscribeEvent
    public static void registerPayloadHandlers(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(FlipExtremeSkilletPayload.TYPE, FlipExtremeSkilletPayload.STREAM_CODEC, ServerPayloadHandler::handleFlipSkillet);
    }


    public static class ServerPayloadHandler
    {
        public static void handleFlipSkillet(FlipExtremeSkilletPayload payload, IPayloadContext context) {
            ItemStack stack = context.player().getUseItem();
            if (stack.getItem() instanceof ExtremeSkilletItem) {
                stack.set(ModDataComponents.SKILLET_FLIP_TIMESTAMP.get(), context.player().level().getGameTime());
            }
        }
    }
}
