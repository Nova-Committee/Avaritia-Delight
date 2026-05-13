package committee.nova.avaritia_delight.init.data.provider;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.init.registry.ADItems;
import committee.nova.avaritia_delight.init.registry.ADTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.Tags;
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
        tag(ModTags.Items.KNIVES)
                .add(ADItems.blaze_knife.get())
                .add(ADItems.crystal_knife.get())
                .add(ADItems.neutronium_knife.get())
                .add(ADItems.infinity_knife.get())
        ;
        tag(committee.nova.mods.avaritia.init.registry.ModTags.IMMORTAL_ITEM)
                .add(ADItems.infinity_knife.get())
                .add(ADItems.infinity_apple.get())
        ;

        tag(Tags.Items.CROPS)
                .add(ADItems.crystal_cabbage.get())
                .add(ADItems.blaze_tomato.get())
                .add(ADItems.diamond_lattice_potato.get())
                .add(ADItems.neutronium_wheat.get())
        ;
        tag(Tags.Items.SEEDS)
                .add(ADItems.blaze_tomato_seeds.get())
                .add(ADItems.crystal_cabbage_seeds.get())
                .add(ADItems.diamond_lattice_potato.get())
                .add(ADItems.neutronium_wheat_seeds.get())
        ;
        tag(Tags.Items.FOODS)
                .add(ADItems.blaze_tomato.get())
                .add(ADItems.blaze_tomato_sauce.get())
                .add(ADItems.crystal_cabbage.get())
                .add(ADItems.crystal_cabbage_leaf.get())
                .add(ADItems.raw_crystal_pasta.get())
                .add(ADItems.diamond_lattice_potato.get())
                .add(ADItems.diamond_lattice_fries.get())
                .add(ADItems.neutronium_bread.get())
                .add(ADItems.neutronium_wheat_dough.get())
                .add(ADItems.cosmic_beef.get())
                .add(ADItems.cosmic_beef_cooked.get())
                .add(ADItems.experience_jelly.get())
                .add(ADItems.infinity_catalyst_cookie.get())
                .add(ADItems.infinity_apple.get())
                .add(ADItems.infinity_flowers_tea.get())
                .add(ADItems.furious_cocktail.get())
                .add(ADItems.how_did_we_get_here.get())
                .add(ADItems.infinity_milk.get())
                .add(ADItems.endest_fried_egg.get())
                .add(ADItems.endest_egg_sandwich.get())
                .add(ADItems.star_pie_crust.get())
                .add(ADItems.slice_star_pie.get())
                .add(ADItems.slice_endest_pie.get())
                .add(ADItems.record_fragment_cookie.get())
                .add(ADItems.slice_of_endless_cake.get())
        ;
    }
}
