package committee.nova.avaritia_delight.common.crafting.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import committee.nova.avaritia_delight.init.data.provider.ADRecipeSerializers;
import committee.nova.avaritia_delight.init.registry.ADBlocks;
import committee.nova.avaritia_delight.init.registry.ADRecipeTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.util.RecipeMatcher;
import net.neoforged.neoforge.items.wrapper.RecipeWrapper;

public class ExtremeCookingPotRecipe implements Recipe<RecipeWrapper>
{
    public static final int INPUT_SLOTS = 81;

    private final String group;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ItemStack container;
    private final ItemStack containerOverride;
    private final float experience;
    private final int cookTime;

    public ExtremeCookingPotRecipe(String group, NonNullList<Ingredient> inputItems, ItemStack output, ItemStack container, float experience, int cookTime) {
        this.group = group;

        this.inputItems = inputItems;
        this.output = output;

        if (!container.isEmpty()) {
            this.container = container;
        } else if (!output.getCraftingRemainingItem().isEmpty()) {
            this.container = output.getCraftingRemainingItem();
        } else {
            this.container = ItemStack.EMPTY;
        }

        this.containerOverride = container;
        this.experience = experience;
        this.cookTime = cookTime;
    }

    @Override
    public String getGroup() {
        return this.group;
    }


    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return this.output;
    }

    public ItemStack getOutputContainer() {
        return this.container;
    }

    public ItemStack getContainerOverride() {
        return this.containerOverride;
    }

    @Override
    public ItemStack assemble(RecipeWrapper inv, HolderLookup.Provider provider) {
        return this.output.copy();
    }

    public float getExperience() {
        return this.experience;
    }

    public int getCookTime() {
        return this.cookTime;
    }

    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        java.util.List<ItemStack> inputs = new java.util.ArrayList<>();
        int occupiedSlots = 0;

        for (int j = 0; j < INPUT_SLOTS; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++occupiedSlots;
                inputs.add(itemstack);
            }
        }

        return occupiedSlots == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.inputItems.size();
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ADRecipeSerializers.EXTREME_COOKING.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ADRecipeTypes.EXTREME_COOKING.get();
    }

    @Override
    public ItemStack getToastSymbol() {
        return new ItemStack(ADBlocks.extreme_cooking_pot.get());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExtremeCookingPotRecipe that = (ExtremeCookingPotRecipe) o;

        if (Float.compare(that.getExperience(), getExperience()) != 0) return false;
        if (getCookTime() != that.getCookTime()) return false;
        if (!getGroup().equals(that.getGroup())) return false;
        if (!inputItems.equals(that.inputItems)) return false;
        if (!output.equals(that.output)) return false;
        return container.equals(that.container);
    }

    @Override
    public int hashCode() {
        int result = getGroup().hashCode();
        result = 31 * result + inputItems.hashCode();
        result = 31 * result + output.hashCode();
        result = 31 * result + container.hashCode();
        result = 31 * result + (getExperience() != 0.0f ? Float.floatToIntBits(getExperience()) : 0);
        result = 31 * result + getCookTime();
        return result;
    }

    public static class Serializer implements RecipeSerializer<ExtremeCookingPotRecipe>
    {
        private static final MapCodec<ExtremeCookingPotRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(ExtremeCookingPotRecipe::getGroup),
                Ingredient.LIST_CODEC_NONEMPTY.fieldOf("ingredients").xmap(ingredients -> {
                    NonNullList<Ingredient> nonNullList = NonNullList.create();
                    nonNullList.addAll(ingredients);
                    return nonNullList;
                }, ingredients -> ingredients).forGetter(ExtremeCookingPotRecipe::getIngredients),
                ItemStack.STRICT_CODEC.fieldOf("result").forGetter(r -> r.output),
                ItemStack.STRICT_CODEC.optionalFieldOf("container", ItemStack.EMPTY).forGetter(ExtremeCookingPotRecipe::getContainerOverride),
                Codec.FLOAT.optionalFieldOf("experience", 0.0F).forGetter(ExtremeCookingPotRecipe::getExperience),
                Codec.INT.optionalFieldOf("cookingtime", 200).forGetter(ExtremeCookingPotRecipe::getCookTime)
        ).apply(inst, ExtremeCookingPotRecipe::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ExtremeCookingPotRecipe> STREAM_CODEC = StreamCodec.of(ExtremeCookingPotRecipe.Serializer::toNetwork, ExtremeCookingPotRecipe.Serializer::fromNetwork);

        public Serializer() {
        }

        @Override
        public MapCodec<ExtremeCookingPotRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ExtremeCookingPotRecipe> streamCodec() {
            return STREAM_CODEC;
        }

        private static ExtremeCookingPotRecipe fromNetwork(RegistryFriendlyByteBuf buffer) {
            String group = buffer.readUtf();
            int i = buffer.readVarInt();
            NonNullList<Ingredient> inputItems = NonNullList.withSize(i, Ingredient.EMPTY);

            inputItems.replaceAll(ignored -> Ingredient.CONTENTS_STREAM_CODEC.decode(buffer));

            ItemStack output = ItemStack.STREAM_CODEC.decode(buffer);
            ItemStack container = ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer);
            float experience = buffer.readFloat();
            int cookTime = buffer.readVarInt();
            return new ExtremeCookingPotRecipe(group, inputItems, output, container, experience, cookTime);
        }

        private static void toNetwork(RegistryFriendlyByteBuf buffer, ExtremeCookingPotRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeVarInt(recipe.inputItems.size());

            for (Ingredient ingredient : recipe.inputItems) {
                Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, ingredient);
            }

            ItemStack.STREAM_CODEC.encode(buffer, recipe.output);
            ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, recipe.container);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.cookTime);
        }
    }
}