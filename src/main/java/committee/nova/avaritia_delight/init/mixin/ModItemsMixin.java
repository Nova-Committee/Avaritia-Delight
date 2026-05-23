package committee.nova.avaritia_delight.init.mixin;

import committee.nova.avaritia_delight.init.registry.ADBlocks;
import committee.nova.mods.avaritia.init.registry.ModCreativeModeTabs;
import committee.nova.mods.avaritia.init.registry.ModFoods;
import committee.nova.mods.avaritia.init.registry.ModItems;
import committee.nova.mods.avaritia.init.registry.ModRarities;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(value = ModItems.class, remap = false)
public class ModItemsMixin {

    @Inject(method = "item(Ljava/lang/String;Ljava/util/function/Supplier;Z)Lnet/neoforged/neoforge/registries/DeferredItem;", at = @At("HEAD"), cancellable = true)
    private static void onItemRegister(String name, Supplier<Item> item, boolean exist, CallbackInfoReturnable<DeferredItem<Item>> cir) {
        if ("ultimate_stew".equals(name)) {
            DeferredItem<Item> blockItem = ModItems.ITEMS.register(name, () -> new BlockItem(
                    ADBlocks.ultimate_stew.get(),
                    new Item.Properties()
                            .rarity(ModRarities.EPIC)
                            .stacksTo(1)
                            .food(ModFoods.ultimate_stew)
                            .component(DataComponents.ITEM_NAME, Component.translatable("item.avaritia.ultimate_stew"))
            ));
            ModCreativeModeTabs.ACCEPT_ITEM.add(blockItem);
            cir.setReturnValue(blockItem);
        }
    }
}
