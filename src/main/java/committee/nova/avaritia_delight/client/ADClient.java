package committee.nova.avaritia_delight.client;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.client.render.ExtremeSkilletItemRenderer;
import committee.nova.avaritia_delight.common.ADEnumParameters;
import committee.nova.avaritia_delight.common.item.tool.ExtremeSkilletItem;
import committee.nova.avaritia_delight.common.network.FlipExtremeSkilletPayload;
import committee.nova.avaritia_delight.init.registry.ADBlockEntities;
import committee.nova.avaritia_delight.init.registry.ADEntities;
import committee.nova.avaritia_delight.init.registry.ADItems;
import committee.nova.avaritia_delight.init.registry.ADMenus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.item.component.ItemStackWrapper;
import vectorwing.farmersdelight.common.registry.ModDataComponents;

@EventBusSubscriber(modid = AvaritiaDelight.MOD_ID, value = Dist.CLIENT)
public class ADClient {

    @SubscribeEvent
    public static void clientSetUp(FMLClientSetupEvent event) {
        ADBlockEntities.onClientSetup();
        ADEntities.onClientSetup();
        event.enqueueWork(() -> ItemProperties.register(ADItems.extreme_skillet.get(), ResourceLocation.withDefaultNamespace("cooking"),
                (stack, world, entity, s) -> stack.getOrDefault(ModDataComponents.SKILLET_INGREDIENT, ItemStackWrapper.EMPTY).getStack().isEmpty() ? 0 : 1)
        );
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        ADMenus.onClientSetup(event);
    }

    @SubscribeEvent
    public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions()
        {
            BlockEntityWithoutLevelRenderer renderer = new ExtremeSkilletItemRenderer();

            @Override
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return renderer;
            }

            @Override
            public HumanoidModel.ArmPose getArmPose(LivingEntity living, InteractionHand hand, ItemStack stack) {
                return stack.has(ModDataComponents.SKILLET_FLIP_TIMESTAMP.get()) ? ADEnumParameters.PROXY_SKILLET_FLIP.getValue() : null;
            }
        }, ADItems.extreme_skillet.get());
    }

    @SubscribeEvent
    public static void preClientTick(ClientTickEvent.Pre event) { // Run this on pre so inputs don't get eaten up.
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player != null && player.isUsingItem()) {
            ItemStack useItem = player.getUseItem();
            if (useItem.getItem() instanceof ExtremeSkilletItem && !useItem.has(ModDataComponents.SKILLET_FLIP_TIMESTAMP.get())) {
                while (mc.options.keyAttack.consumeClick()) {
                    PacketDistributor.sendToServer(FlipExtremeSkilletPayload.INSTANCE);
                }
            }
        }
    }
}
