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
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.ItemLore;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import java.util.ArrayList;
import java.util.List;

public class ADItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AvaritiaDelight.MOD_ID);

    public static DeferredItem<Item> neutronium_pot = ITEMS.register("neutronium_pot",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC)));
    public static DeferredItem<Item> neutronium_bowl = ITEMS.register("neutronium_bowl",
            ()-> new Item(new Item.Properties()));

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
            ()-> new ExtremeSkilletItem(ADBlocks.extreme_skillet.get(),new Item.Properties().fireResistant().durability(9999).stacksTo(1).rarity(ModRarities.COSMIC.getValue()).attributes(ExtremeSkilletItem.createAttributes(ModToolTiers.INFINITY, 0, 0))));

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
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ADFoods.ENDEST_EGG_SANDWICH).component(DataComponents.LORE,
                    createEffectLore(
                    ADFoods.ENDEST_EGG_SANDWICH.effects()
                            .stream()
                            .map(FoodProperties.PossibleEffect::effect)
                            .toList()
            ))));

    public static DeferredItem<Item> star_pie_crust = ITEMS.register("star_pie_crust",
            ()-> new Item(new Item.Properties().rarity(Rarity.EPIC).food(ADFoods.STAR_PIE_CRUST)));

    public static DeferredItem<Item> slice_star_pie = ITEMS.register("slice_of_star_pie",
            ()-> new Item(new Item.Properties().rarity(ModRarities.COSMIC.getValue()).food(ADFoods.STAR_PIE_SLICE).component(DataComponents.LORE,
                    createEffectLore(
                            ADFoods.STAR_PIE_SLICE.effects()
                                    .stream()
                                    .map(FoodProperties.PossibleEffect::effect)
                                    .toList()
                    ))));

    public static DeferredItem<Item> slice_endest_pie = ITEMS.register("slice_of_endest_pie",
            ()-> new Item(new Item.Properties().rarity(ModRarities.COSMIC.getValue()).food(ADFoods.ENDEST_PIE_SLICE).component(DataComponents.LORE,
                    createEffectLore(
                            ADFoods.ENDEST_PIE_SLICE.effects()
                                    .stream()
                                    .map(FoodProperties.PossibleEffect::effect)
                                    .toList()
                    ))));

    public static DeferredItem<Item> slice_of_endless_cake = ITEMS.register("slice_of_endless_cake",
            ()-> new SliceOfEndlessCakeItem(new Item.Properties().rarity(ADRarities.COMMEMORATION.getValue()).food(ADFoods.ENDLESS_CAKE_SLICE)));

    public static DeferredItem<Item> infinity_fries = ITEMS.register("infinity_fries",
            ()-> new ConsumableItem(new Item.Properties().rarity(Rarity.EPIC).food(ADFoods.INFINITY_FRIES).craftRemainder(ADItems.neutronium_bowl.get())));
    public static DeferredItem<Item> infinity_salad = ITEMS.register("infinity_salad",
            ()-> new ConsumableItem(new Item.Properties().rarity(Rarity.EPIC).food(ADFoods.INFINITY_SALAD).craftRemainder(ADItems.neutronium_bowl.get())));
    public static DeferredItem<Item> infinity_taco = ITEMS.register("infinity_taco",
            ()-> new InfinityFoodItem(new Item.Properties().rarity(ModRarities.COSMIC.getValue()).food(ADFoods.INFINITY_TACO)));
    public static DeferredItem<Item> infinity_large_hamburger = ITEMS.register("infinity_large_hamburger",
            ()-> new InfinityFoodItem(new Item.Properties().rarity(ModRarities.COSMIC.getValue()).food(ADFoods.INFINITY_LARGE_HAMBURGER)));
    public static DeferredItem<Item> infinity_catalyst_cookie = ITEMS.register("infinity_catalyst_cookie",
            ()-> new InfinityCatalystCookieItem(new Item.Properties().rarity(ModRarities.COSMIC.getValue()).food(ADFoods.INFINITY_CATALYST_COOKIE).stacksTo(1)));
    public static DeferredItem<Item> infinity_apple = ITEMS.register("infinity_apple",
            ()-> new InfinityFoodItem(new Item.Properties().rarity(ModRarities.COSMIC.getValue()).stacksTo(1).food(ADFoods.INFINITY_APPLE)));

    public static DeferredItem<Item> mobs_stew = ITEMS.register("mobs_stew",
            () -> new InfinityBlockFoodItem(ADBlocks.mobs_stew.get(), new Item.Properties().food(ADFoods.MOBS_STEW).rarity(ModRarities.COSMIC.getValue())));
    public static DeferredItem<Item> ultimate_gourmet_stew = ITEMS.register("ultimate_gourmet_stew",
            () -> new InfinityBlockFoodItem(ADBlocks.ultimate_gourmet_stew.get(), new Item.Properties().food(ADFoods.ULTIMATE_GOURMET_STEW).rarity(ModRarities.COSMIC.getValue())));

    public static DeferredItem<Item> infinity_flowers_tea = ITEMS.register("infinity_flowers_tea",
            ()-> new DrinkableItem(new Item.Properties().rarity(ModRarities.COSMIC.getValue()).food(ADFoods.INFINITY_FLOWERS_TEA).component(DataComponents.LORE,
                    createEffectLore(
                            ADFoods.INFINITY_FLOWERS_TEA.effects()
                                    .stream()
                                    .map(FoodProperties.PossibleEffect::effect)
                                    .toList()
                    ))));
    public static DeferredItem<Item> furious_cocktail = ITEMS.register("furious_cocktail",
            FuriousCocktailItem::new);
    public static DeferredItem<Item> how_did_we_get_here = ITEMS.register("how_did_we_get_here",
            HowDidWeGetHereItem::new);
    public static DeferredItem<Item> infinity_milk = ITEMS.register("infinity_milk",
            InfinityMilkItem::new);

    public static DeferredItem<Item> experience_jelly = ITEMS.register("experience_jelly",
            ()-> new ExperienceJellyItem(new Item.Properties().rarity(ADRarities.FUNCTION.getValue()).food(ADFoods.EXPERIENCE_JELLY)));
    public static DeferredItem<Item> record_fragment_cookie = ITEMS.register("record_fragment_cookie",
            ()-> new RecordFragmentCookieItem(new Item.Properties().food(ADFoods.NEUTRONIUM_BREAD)));


    public static DeferredItem<Item> blaze_tomato_seeds = ITEMS.register("blaze_tomato_seeds",
            ()-> new ItemNameBlockItem(ADBlocks.budding_blaze_tomatoes.get(),new Item.Properties().fireResistant()));
    public static DeferredItem<Item> neutronium_wheat_seeds = ITEMS.register("neutronium_wheat_seeds",
            ()-> new ItemNameBlockItem(ADBlocks.neutronium_wheats.get(),new Item.Properties().rarity(Rarity.EPIC)));
    public static DeferredItem<Item> crystal_cabbage_seeds = ITEMS.register("crystal_cabbage_seeds",
            ()-> new ItemNameBlockItem(ADBlocks.crystal_cabbages.get(),new Item.Properties()));


    private static ItemLore createEffectLore(List<MobEffectInstance> effects) {
        List<Component> lore = new ArrayList<>();

        PotionContents.addPotionTooltip(
                effects,
                component -> lore.add(
                        component.copy().withStyle(style -> style.withItalic(false))
                ),
                1.0F,
                20.0F
        );

        return new ItemLore(lore);
    }


    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }
}
