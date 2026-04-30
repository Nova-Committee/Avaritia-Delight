package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.item.tool.AvaritiaKnifeItem;
import committee.nova.avaritia_delight.common.item.tool.InfinityKnifeItem;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import committee.nova.mods.avaritia.init.registry.ModToolTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ADItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AvaritiaDelight.MOD_ID);

    public static DeferredItem<Item> blaze_knife = ITEMS.register("blaze_knife",
            ()-> new AvaritiaKnifeItem(ModToolTiers.BLAZE, new Item.Properties().rarity(ModRarities.UNCOMMON).fireResistant().attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.BLAZE, -13, 0))));
    public static DeferredItem<Item> crystal_knife = ITEMS.register("crystal_knife",
            ()-> new AvaritiaKnifeItem(ModToolTiers.CRYSTAL, new Item.Properties().fireResistant().rarity(Rarity.EPIC).attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.CRYSTAL, -30, 0))));
    public static DeferredItem<Item> neutronium_knife = ITEMS.register("neutronium_knife",
            ()-> new AvaritiaKnifeItem(ModToolTiers.CRYSTAL, new Item.Properties().rarity(Rarity.EPIC).attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.CRYSTAL, -30, 0))));
    public static DeferredItem<Item> infinity_knife = ITEMS.register("infinity_knife",
            ()-> new InfinityKnifeItem(ModToolTiers.INFINITY, new Item.Properties().fireResistant().rarity(ModRarities.COSMIC.getValue()).attributes(InfinityKnifeItem.createAttributes(ModToolTiers.INFINITY, -50, 0))));

    public static DeferredItem<Item> blaze_tomato = ITEMS.register("blaze_tomato",
            ()-> new Item(new Item.Properties().fireResistant()));

    public static DeferredItem<Item> diamond_lattice_fries = ITEMS.register("diamond_lattice_fries",
            ()-> new Item(new Item.Properties()));
    public static DeferredItem<Item> diamond_lattice_potato = ITEMS.register("diamond_lattice_potato",
            ()-> new Item(new Item.Properties()));

    public static DeferredItem<Item> crystal_cabbage_leaf = ITEMS.register("crystal_cabbage_leaf",
            ()-> new Item(new Item.Properties()));
    public static DeferredItem<Item> crystal_cabbage = ITEMS.register("crystal_cabbage",
            ()-> new Item(new Item.Properties()));
    public static DeferredItem<Item> raw_crystal_pasta = ITEMS.register("raw_crystal_pasta",
            ()-> new Item(new Item.Properties()));

    public static DeferredItem<Item> neutronium_wheat = ITEMS.register("neutronium_wheat",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static DeferredItem<Item> neutronium_bread = ITEMS.register("neutronium_bread",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static DeferredItem<Item> neutronium_wheat_dough = ITEMS.register("neutronium_wheat_dough",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static DeferredItem<Item> cosmic_beef = ITEMS.register("cosmic_beef",
            ()-> new Item(new Item.Properties()));
    public static DeferredItem<Item> cosmic_beef_cooked = ITEMS.register("cosmic_beef_cooked",
            ()-> new Item(new Item.Properties().rarity(ModRarities.UNCOMMON)));

    public static DeferredItem<Item> infinity_apple = ITEMS.register("infinity_apple",
            ()-> new Item(new Item.Properties().fireResistant().rarity(ModRarities.COSMIC.getValue())));
    public static DeferredItem<Item> furious_cocktail = ITEMS.register("furious_cocktail",
            ()-> new Item(new Item.Properties().fireResistant().rarity(ModRarities.COSMIC.getValue())));
    public static DeferredItem<Item> how_did_we_get_here = ITEMS.register("how_did_we_get_here",
            ()-> new Item(new Item.Properties().fireResistant().rarity(ModRarities.COSMIC.getValue())));
    public static DeferredItem<Item> infinity_milk = ITEMS.register("infinity_milk",
            ()-> new Item(new Item.Properties().fireResistant().rarity(ModRarities.COSMIC.getValue())));

    public static DeferredItem<Item> blaze_tomato_seeds = ITEMS.register("blaze_tomato_seeds",
            ()-> new Item(new Item.Properties().fireResistant()));
    public static DeferredItem<Item> neutronium_wheat_seeds = ITEMS.register("neutronium_wheat_seeds",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static DeferredItem<Item> crystal_cabbage_seeds = ITEMS.register("crystal_cabbage_seeds",
            ()-> new Item(new Item.Properties()));


    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }
}
