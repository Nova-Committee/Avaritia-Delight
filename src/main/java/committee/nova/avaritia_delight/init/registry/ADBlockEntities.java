package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.client.render.ExtremeStoveRender;
import committee.nova.avaritia_delight.common.block.entity.ExtremeStoveBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ADBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, AvaritiaDelight.MOD_ID);

    public static final Supplier<BlockEntityType<ExtremeStoveBlockEntity>> EXTREME_STOVE_BE = BLOCK_ENTITIES.register("extreme_stove", () -> BlockEntityType.Builder.of(ExtremeStoveBlockEntity::new, ADBlocks.extreme_stove.get()).build( null));

    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup() {
        BlockEntityRenderers.register(EXTREME_STOVE_BE.get(), ExtremeStoveRender::new);
    }

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
