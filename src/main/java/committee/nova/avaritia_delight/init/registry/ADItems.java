package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.item.food.*;
import committee.nova.avaritia_delight.common.item.misc.EndestEggItem;
import committee.nova.avaritia_delight.common.item.tool.AvaritiaKnifeItem;
import committee.nova.avaritia_delight.common.item.tool.ExtremeSkilletItem;
import committee.nova.avaritia_delight.common.item.tool.InfinityKnifeItem;
import committee.nova.avaritia_delight.common.item.tool.NeutroniumKnifeItem;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import committee.nova.mods.avaritia.init.registry.ModToolTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ADItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AvaritiaDelight.MOD_ID);

    public static DeferredItem<Item> blaze_knife = ITEMS.register("blaze_knife",
            ()-> new AvaritiaKnifeItem(ModToolTiers.BLAZE, new Item.Properties().rarity(ModRarities.UNCOMMON).fireResistant().attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.BLAZE, 0, 0))));
    public static DeferredItem<Item> crystal_knife = ITEMS.register("crystal_knife",
            ()-> new AvaritiaKnifeItem(ModToolTiers.CRYSTAL, new Item.Properties().fireResistant().rarity(Rarity.EPIC).attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.CRYSTAL, 0, 0))));
    public static DeferredItem<Item> neutronium_knife = ITEMS.register("neutronium_knife",
            ()-> new NeutroniumKnifeItem(ModToolTiers.CRYSTAL, new Item.Properties().rarity(Rarity.EPIC).attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.CRYSTAL, 0, 0))));
    public static DeferredItem<Item> infinity_knife = ITEMS.register("infinity_knife",
            ()-> new InfinityKnifeItem(ModToolTiers.INFINITY, new Item.Properties().fireResistant().rarity(ModRarities.COSMIC.getValue()).attributes(InfinityKnifeItem.createAttributes(ModToolTiers.INFINITY, 0
                    , 0))));

    public static DeferredItem<Item> extreme_skillet = ITEMS.register("extreme_skillet",
            ()-> new ExtremeSkilletItem(ADBlocks.extreme_skillet.get(),new Item.Properties().fireResistant().stacksTo(1).rarity(ModRarities.COSMIC.getValue()).attributes(ExtremeSkilletItem.createAttributes(ModToolTiers.INFINITY, 0, 0))));



    public static DeferredItem<Item> blaze_tomato = ITEMS.register("blaze_tomato",
            ()-> new Item(new Item.Properties().fireResistant().food(ADFoods.CROPS)));
    public static DeferredItem<Item> blaze_tomato_sauce = ITEMS.register("blaze_tomato_sauce",
            ()-> new BlazeTomatoSauceItem(new Item.Properties().fireResistant().food(ADFoods.BLAZE_TOMATO_SAUCE)));

    public static DeferredItem<Item> diamond_lattice_fries = ITEMS.register("diamond_lattice_fries",
            ()-> new Item(new Item.Properties().food(ADFoods.CROPS)));
    public static DeferredItem<Item> diamond_lattice_potato = ITEMS.register("diamond_lattice_potato",
            ()-> new ItemNameBlockItem(ADBlocks.diamond_lattice_potatoes.get(),new Item.Properties().food(ADFoods.CROPS)));

    public static DeferredItem<Item> crystal_cabbage_leaf = ITEMS.register("crystal_cabbage_leaf",
            ()-> new Item(new Item.Properties().food(ADFoods.CROPS)));
    public static DeferredItem<Item> crystal_cabbage = ITEMS.register("crystal_cabbage",
            ()-> new Item(new Item.Properties().food(ADFoods.CROPS)));
    public static DeferredItem<Item> raw_crystal_pasta = ITEMS.register("raw_crystal_pasta",
            ()-> new Item(new Item.Properties()));

    public static DeferredItem<Item> neutronium_wheat = ITEMS.register("neutronium_wheat",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static DeferredItem<Item> neutronium_bread = ITEMS.register("neutronium_bread",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ADFoods.NEUTRONIUM_BREAD)));
    public static DeferredItem<Item> neutronium_wheat_dough = ITEMS.register("neutronium_wheat_dough",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ADFoods.CROPS)));

    public static DeferredItem<Item> cosmic_beef = ITEMS.register("cosmic_beef",
            ()-> new Item(new Item.Properties().food(ADFoods.COSMIC_BEEF)));
    public static DeferredItem<Item> cosmic_beef_cooked = ITEMS.register("cosmic_beef_cooked",
            ()-> new Item(new Item.Properties().rarity(ModRarities.UNCOMMON).food(ADFoods.COSMIC_BEEF_COOKED)));

    public static DeferredItem<Item> endest_egg = ITEMS.register("endest_egg",
            ()-> new EndestEggItem(new Item.Properties().rarity(Rarity.EPIC).stacksTo(16)));
    public static DeferredItem<Item> endest_fried_egg = ITEMS.register("endest_fried_egg",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ADFoods.ENDEST_FRIED_EGG)));
    public static DeferredItem<Item> endest_egg_sandwich = ITEMS.register("endest_egg_sandwich",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ADFoods.ENDEST_EGG_SANDWICH)));


    public static DeferredItem<Item> infinity_apple = ITEMS.register("infinity_apple",
            ()-> new InfinityAppleItem(new Item.Properties().rarity(ModRarities.COSMIC.getValue()).stacksTo(1).food(ADFoods.INFINITY_APPLE)));
    public static DeferredItem<Item> furious_cocktail = ITEMS.register("furious_cocktail",
            FuriousCocktailItem::new);
    public static DeferredItem<Item> how_did_we_get_here = ITEMS.register("how_did_we_get_here",
            HowDidWeGetHereItem::new);
    public static DeferredItem<Item> infinity_milk = ITEMS.register("infinity_milk",
            InfinityMilkItem::new);

    public static DeferredItem<Item> experience_jelly = ITEMS.register("experience_jelly",
            ()-> new ExperienceJellyItem(new Item.Properties().rarity(ADRarities.FUNCTION.getValue()).food(ADFoods.EXPERIENCE_JELLY)));


    public static DeferredItem<Item> blaze_tomato_seeds = ITEMS.register("blaze_tomato_seeds",
            ()-> new ItemNameBlockItem(ADBlocks.budding_blaze_tomatoes.get(),new Item.Properties().fireResistant()));
    public static DeferredItem<Item> neutronium_wheat_seeds = ITEMS.register("neutronium_wheat_seeds",
            ()-> new ItemNameBlockItem(ADBlocks.neutronium_wheats.get(),new Item.Properties().rarity(Rarity.EPIC)));
    public static DeferredItem<Item> crystal_cabbage_seeds = ITEMS.register("crystal_cabbage_seeds",
            ()-> new ItemNameBlockItem(ADBlocks.crystal_cabbages.get(),new Item.Properties()));

    public static DeferredItem<Item> neutronium_pot = ITEMS.register("neutronium_pot",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static DeferredItem<Item> neutronium_bowl = ITEMS.register("neutronium_bowl",
            ()-> new Item(new Item.Properties()));


    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }
}
