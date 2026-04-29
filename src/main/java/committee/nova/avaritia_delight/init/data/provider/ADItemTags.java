package committee.nova.avaritia_delight.init.data.provider;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.init.registry.ADItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import vectorwing.farmersdelight.common.tag.ModTags;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class ADItemTags extends IntrinsicHolderTagsProvider<Item> {

    public ADItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> future, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, Registries.ITEM, future, block -> block.builtInRegistryHolder().key(), AvaritiaDelight.MOD_ID, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return "Avaritia Delight Item Tags";
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.KNIVES).add(ADItems.blaze_knife.get());
        tag(ModTags.Items.KNIVES).add(ADItems.crystal_knife.get());
        tag(ModTags.Items.KNIVES).add(ADItems.neutronium_knife.get());
        tag(ModTags.Items.KNIVES).add(ADItems.infinity_knife.get());
        tag(committee.nova.mods.avaritia.init.registry.ModTags.IMMORTAL_ITEM).add(ADItems.infinity_knife.get());
    }
}
