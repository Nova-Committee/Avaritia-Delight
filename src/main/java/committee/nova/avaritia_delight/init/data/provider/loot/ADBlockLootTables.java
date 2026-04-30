package committee.nova.avaritia_delight.init.data.provider.loot;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Name: Avaritia-forge / ModBlockLootTables
 * Author: cnlimiter
 * CreateTime: 2023/8/24 13:39
 * Description:
 */

public class ADBlockLootTables extends BlockLootSubProvider {

    public ADBlockLootTables(HolderLookup.Provider registries) {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags(), registries);
    }

    @Override
    protected void generate() {
        for (var block : ADBlocks.BLOCKS.getEntries()) {
            dropSelf(block.get());
        }
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.stream()
                .filter(block -> AvaritiaDelight.MOD_ID.equals(Objects.requireNonNull(BuiltInRegistries.BLOCK.getKey(block)).getNamespace()))
                .collect(Collectors.toSet());
    }
}
