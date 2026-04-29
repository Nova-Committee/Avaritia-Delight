package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.item.tool.AvaritiaKnifeItem;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import committee.nova.mods.avaritia.init.registry.ModToolTiers;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.item.KnifeItem;

public class ADItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AvaritiaDelight.MOD_ID);

    public static DeferredItem<Item> blaze_knife = ITEMS.register("blaze_knife",
            ()-> new AvaritiaKnifeItem(ModToolTiers.BLAZE, new Item.Properties().fireResistant().attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.BLAZE, -13, 0))));
    public static DeferredItem<Item> crystal_knife = ITEMS.register("crystal_knife",
            ()-> new AvaritiaKnifeItem(Tiers.GOLD, new Item.Properties().fireResistant().attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.CRYSTAL, -30, 0))));
    public static DeferredItem<Item> neutronium_knife = ITEMS.register("neutronium_knife",
            ()-> new AvaritiaKnifeItem(Tiers.GOLD, new Item.Properties().fireResistant().attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.CRYSTAL, -30, 0))));
    public static DeferredItem<Item> infinity_knife = ITEMS.register("infinity_knife",
            ()-> new AvaritiaKnifeItem(Tiers.GOLD, new Item.Properties().fireResistant().attributes(AvaritiaKnifeItem.createAttributes(ModToolTiers.INFINITY, -50, 0))));

    public static DeferredItem<Item> blaze_tomato = ITEMS.register("blaze_tomato",
            ()-> new Item(new Item.Properties().fireResistant().food(ADFoods.BLAZE_TOMATO)));


    public static void register(IEventBus bus){
        ITEMS.register(bus);
    }
}
