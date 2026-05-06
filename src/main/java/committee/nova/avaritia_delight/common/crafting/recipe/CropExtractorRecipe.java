package committee.nova.avaritia_delight.common.crafting.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import committee.nova.avaritia_delight.init.data.provider.ADRecipeSerializers;
import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class CropExtractorRecipe implements Recipe<RecipeWrapper> {

    private final String group;
    private final Ingredient input;
    private final ItemStack output1;
    private final ItemStack output2;
    private final ItemStack output3;
    private final ItemStack output4;
    private final int extractionTime;

    public CropExtractorRecipe(String group, Ingredient input, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4, int extractionTime) {
        this.group = group;
        this.input = input;
        this.output1 = output1;
        this.output2 = output2;
        this.output3 = output3;
        this.output4 = output4;
        this.extractionTime = extractionTime;
    }


    public CropExtractorRecipe(Ingredient input, ItemStack output1, ItemStack output2, ItemStack output3, ItemStack output4, int extractionTime) {
        this("", input, output1, output2, output3, output4, extractionTime);
    }


    public Ingredient getInput() {
        return input;
    }

    public ItemStack getOutput1() {
        return output1;
    }

    public ItemStack getOutput2() {
        return output2;
    }

    public ItemStack getOutput3() {
        return output3;
    }

    public ItemStack getOutput4() {
        return output4;
    }

    public int getExtractionTime() {
        return extractionTime;
    }

    @Override
    public boolean matches(RecipeWrapper inventory, Level level) {
        if (level.isClientSide()) {
            return false;
        }
        return input.test(inventory.getItem(0));
    }

    @Override
    public ItemStack assemble(RecipeWrapper inventory, HolderLookup.Provider registries) {
        return output1.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return output1.copy();
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ADRecipeSerializers.CROP_EXTRACTOR.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ADRecipeTypes.CROP_EXTRACTOR.get();
    }

    public static class Serializer implements RecipeSerializer<CropExtractorRecipe> {

        private static final MapCodec<CropExtractorRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(CropExtractorRecipe::getGroup),
                Ingredient.CODEC.fieldOf("input").forGetter(CropExtractorRecipe::getInput),

                ItemStack.CODEC.fieldOf("output1").forGetter(CropExtractorRecipe::getOutput1),

                ItemStack.CODEC.optionalFieldOf("output2", ItemStack.EMPTY).forGetter(CropExtractorRecipe::getOutput2),
                ItemStack.CODEC.optionalFieldOf("output3", ItemStack.EMPTY).forGetter(CropExtractorRecipe::getOutput3),
                ItemStack.CODEC.optionalFieldOf("output4", ItemStack.EMPTY).forGetter(CropExtractorRecipe::getOutput4),

                Codec.INT.optionalFieldOf("extraction_time", 200).forGetter(CropExtractorRecipe::getExtractionTime)
        ).apply(inst, CropExtractorRecipe::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, CropExtractorRecipe> STREAM_CODEC = StreamCodec.of(
                Serializer::toNetwork,
                Serializer::fromNetwork
        );

        public Serializer() {
        }

        @Override
        public MapCodec<CropExtractorRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CropExtractorRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static CropExtractorRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            Ingredient input = Ingredient.CONTENTS_STREAM_CODEC.decode(buffer);

            ItemStack output1 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack output2 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack output3 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            ItemStack output4 = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);

            int extractionTime = buffer.readVarInt();
            return new CropExtractorRecipe(group, input, output1, output2, output3, output4, extractionTime);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, CropExtractorRecipe recipe) {
            buffer.writeUtf(recipe.group);
            Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.input);

            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output1);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output2);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output3);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.output4);

            buffer.writeVarInt(recipe.extractionTime);
        }
    }
}
