package committee.nova.avaritia_delight.init.data.provider.loot;

import com.google.common.collect.ImmutableList;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.packs.VanillaLootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Name: Avaritia-forge / ModLootTables
 * Author: cnlimiter
 * CreateTime: 2023/8/24 13:38
 * Description:
 */

public class ADLootTables extends LootTableProvider {
    public ADLootTables(PackOutput output, CompletableFuture<HolderLookup.Provider> future) {
        super(output, Collections.emptySet(), VanillaLootTableProvider.create(output, future).getTables(), future);
    }

    @Override
    public @NotNull List<SubProviderEntry> getTables() {
        return ImmutableList.of(
                new SubProviderEntry(ADBlockLootTables::new, LootContextParamSets.BLOCK)
        );
    }

}
