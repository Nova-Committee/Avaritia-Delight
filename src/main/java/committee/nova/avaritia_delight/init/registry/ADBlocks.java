package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.block.ExtremeStoveBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ADBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AvaritiaDelight.MOD_ID);

    public static DeferredBlock<Block> extreme_stove = itemBlock("extreme_stove", () -> new ExtremeStoveBlock(BlockBehaviour.Properties.of().strength(5F,2000F)));



    private static <T extends Block> DeferredBlock<T> itemBlock(String name, Supplier<T> block){
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        itemBlock(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> DeferredBlock<T> itemBlock(String name, Supplier<T> block, Rarity rarity) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        itemBlock(name, toReturn, rarity);
        return toReturn;
    }

    private static <T extends Block> void itemBlock(String name, DeferredBlock<T> block, Rarity rarity) {
        ADItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().rarity(rarity)));
    }


    private static <T extends Block> void itemBlock(String name, DeferredBlock<T> block){
        ADItems.ITEMS.register(name,()-> new BlockItem(block.get(),new Item.Properties()));
    }

    public static void register(IEventBus bus){
        BLOCKS.register(bus);
    }
}
