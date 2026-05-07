package committee.nova.avaritia_delight.init.registry;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ADBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(AvaritiaDelight.MOD_ID);

    public static DeferredBlock<Block> extreme_stove = itemBlock("extreme_stove", () -> new ExtremeStoveBlock(BlockBehaviour.Properties.of().strength(5F,2000F)));

    public static DeferredBlock<Block> extreme_cooking_pot = itemBlock("extreme_cooking_pot", () -> new ExtremeCookingPotBlock(BlockBehaviour.Properties.of().strength(5F,2000F)));

    public static DeferredBlock<Block> crop_extractor = itemBlock("crop_extractor", () -> new CropExtractorBlock(BlockBehaviour.Properties.of().strength(3F, 10F)));

    public static DeferredBlock<Block> extreme_skillet = BLOCKS.register("extreme_skillet", () -> new ExtremeSkilletBlock(BlockBehaviour.Properties.of().strength(3F, 10F)));

    public static final DeferredBlock<Block> diamond_lattice_potatoes = itemBlock("diamond_lattice_potatoes",
            () -> new DiamondLatticePotatoBlock(Block.Properties.ofFullCopy(Blocks.POTATOES)));
    public static final DeferredHolder<Block, BlazeTomatoBlock> blaze_tomatoes = itemBlock("blaze_tomatoes",
            () -> new BlazeTomatoBlock(Block.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP)));
    public static final DeferredBlock<Block> budding_blaze_tomatoes = itemBlock("budding_blaze_tomatoes",
            () -> new BuddingBlazeTomatoBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> crystal_cabbages = itemBlock("crystal_cabbages",
            () -> new CrystalCabbageBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));
    public static final DeferredBlock<Block> neutronium_wheats = itemBlock("neutronium_wheats",
            () -> new NeutroniumWheatBlock(Block.Properties.ofFullCopy(Blocks.WHEAT)));

    public static final DeferredBlock<Block> diamond_lattice_potato_crate = itemBlock("diamond_lattice_potato_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> blaze_tomato_crate = itemBlock("blaze_tomato_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> crystal_cabbage_crate = itemBlock("crystal_cabbage_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).strength(2.0F, 3.0F).sound(SoundType.WOOD)));
    public static final DeferredBlock<Block> neutronium_hay_bale = itemBlock("neutronium_hay_bale", () -> new HayBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.HAY_BLOCK)));

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
