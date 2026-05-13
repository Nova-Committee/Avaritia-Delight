package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.client.render.ExtremeSkilletRenderer;
import committee.nova.avaritia_delight.client.render.ExtremeStoveRender;
import committee.nova.avaritia_delight.common.block.entity.CropExtractorBlockEntity;
import committee.nova.avaritia_delight.common.block.entity.ExtremeCookingPotBlockEntity;
import committee.nova.avaritia_delight.common.block.entity.ExtremeSkilletBlockEntity;
import committee.nova.avaritia_delight.common.block.entity.ExtremeStoveBlockEntity;
import committee.nova.avaritia_delight.common.block.entity.InfinityCabinetBlockEntity;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
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

    public static final Supplier<BlockEntityType<ExtremeCookingPotBlockEntity>> EXTREME_COOKING_POT_BE = BLOCK_ENTITIES.register("extreme_cooking_pot", () -> BlockEntityType.Builder.of(ExtremeCookingPotBlockEntity::new, ADBlocks.extreme_cooking_pot.get()).build( null));

    public static final Supplier<BlockEntityType<CropExtractorBlockEntity>> CROP_EXTRACTOR_BE = BLOCK_ENTITIES.register("crop_extractor", () -> BlockEntityType.Builder.of(CropExtractorBlockEntity::new, ADBlocks.crop_extractor.get()).build( null));

    public static final Supplier<BlockEntityType<ExtremeSkilletBlockEntity>> EXTREME_SKILLET_BE = BLOCK_ENTITIES.register("extreme_skillet",
            () -> BlockEntityType.Builder.of(ExtremeSkilletBlockEntity::new, ADBlocks.extreme_skillet.get()).build(null));

    public static final Supplier<BlockEntityType<InfinityCabinetBlockEntity>> INFINITY_CABINET_BE = BLOCK_ENTITIES.register("infinity_cabinet",
            () -> BlockEntityType.Builder.of(InfinityCabinetBlockEntity::new, ADBlocks.infinity_cabinet.get()).build(null));
    @OnlyIn(Dist.CLIENT)
    public static void onClientSetup() {
        BlockEntityRenderers.register(EXTREME_STOVE_BE.get(), ExtremeStoveRender::new);
        BlockEntityRenderers.register(EXTREME_SKILLET_BE.get(), ExtremeSkilletRenderer::new);
    }

    public static void register(IEventBus bus) {
        BLOCK_ENTITIES.register(bus);
    }
}
