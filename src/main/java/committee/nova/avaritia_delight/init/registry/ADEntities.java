package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.entity.EndestEggEntity;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ADEntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, AvaritiaDelight.MOD_ID);

    public static final DeferredHolder<EntityType<?>, EntityType<EndestEggEntity>> ENDEST_EGG = ENTITIES.register("endest_egg",
            () -> EntityType.Builder.<EndestEggEntity>of(EndestEggEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10)
                    .build(ResourceLocation.fromNamespaceAndPath(AvaritiaDelight.MOD_ID,"endest_egg").toString()));

    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup() {
        EntityRenderers.register(ADEntities.ENDEST_EGG.get(), ThrownItemRenderer::new);
    }

    public static void register(IEventBus bus) {ENTITIES.register(bus);}

}
