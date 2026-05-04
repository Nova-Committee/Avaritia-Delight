package committee.nova.avaritia_delight.init.data.provider.recipe;

import committee.nova.avaritia_delight.AvaritiaDelight;
import committee.nova.avaritia_delight.common.crafting.recipe.ExtremeCookingPotRecipe;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ExtremeCookingPotRecipeBuilder implements RecipeBuilder
{
    private final NonNullList<Ingredient> ingredients = NonNullList.create();
    private final Item result;
    private final ItemStack resultStack;
    private final int cookingTime;
    private final float experience;
    private final ItemStack container;
    private final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    @Nullable
    private String namespace;

    public ExtremeCookingPotRecipeBuilder(ItemLike result, int count, int cookingTime, float experience, @Nullable ItemLike container) {
        this(new ItemStack(result, count), cookingTime, experience, container);
    }

    public ExtremeCookingPotRecipeBuilder(ItemStack resultIn, int cookingTime, float experience, @Nullable ItemLike container) {
        this.result = resultIn.getItem();
        this.resultStack = resultIn;
        this.cookingTime = cookingTime;
        this.experience = experience;
        this.container = container != null ? new ItemStack(container) : ItemStack.EMPTY;

    }

    public static ExtremeCookingPotRecipeBuilder ExtremeCookingPotRecipe(ItemLike mainResult, int count, int cookingTime, float experience) {
        return new ExtremeCookingPotRecipeBuilder(mainResult, count, cookingTime, experience, null);
    }

    public static ExtremeCookingPotRecipeBuilder ExtremeCookingPotRecipe(ItemLike mainResult, int count, int cookingTime, float experience, ItemLike container) {
        return new ExtremeCookingPotRecipeBuilder(mainResult, count, cookingTime, experience, container);
    }

    public ExtremeCookingPotRecipeBuilder addIngredient(TagKey<Item> tagIn) {
        return addIngredient(Ingredient.of(tagIn));
    }

    public ExtremeCookingPotRecipeBuilder addIngredient(ItemLike itemIn) {
        return addIngredient(itemIn, 1);
    }

    public ExtremeCookingPotRecipeBuilder addIngredient(ItemLike itemIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            addIngredient(Ingredient.of(itemIn));
        }
        return this;
    }

    public ExtremeCookingPotRecipeBuilder addIngredient(Ingredient ingredientIn) {
        return addIngredient(ingredientIn, 1);
    }

    public ExtremeCookingPotRecipeBuilder addIngredient(Ingredient ingredientIn, int quantity) {
        for (int i = 0; i < quantity; ++i) {
            ingredients.add(ingredientIn);
        }
        return this;
    }

    @Override
    public RecipeBuilder group(@org.jetbrains.annotations.Nullable String p_176495_) {
        return this;
    }

    @Override
    public Item getResult() {
        return this.result;
    }

    @Override
    public ExtremeCookingPotRecipeBuilder unlockedBy(String criterionName, Criterion<?> criterionTrigger) {
        this.criteria.put(criterionName, criterionTrigger);
        return this;
    }

    public ExtremeCookingPotRecipeBuilder unlockedByItems(String criterionName, ItemLike... items) {
        return unlockedBy(criterionName, InventoryChangeTrigger.TriggerInstance.hasItems(items));
    }

    public ExtremeCookingPotRecipeBuilder unlockedByAnyIngredient(ItemLike... items) {
        this.criteria.put("has_any_ingredient", InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(items).build()));
        return this;
    }

    /**
     * Sets a custom namespace (mod ID) for the recipe. Use this only if the result isn't registered to the mod ID you want.
     */
    public ExtremeCookingPotRecipeBuilder setNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public static ResourceLocation getDefaultRecipeId(ItemLike itemLike) {
        return Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(itemLike.asItem()));
    }

    /**
     * Shorthand for saving recipes in the FD namespace.
     */
    public void saveToFD(RecipeOutput output) {
        this.setNamespace(AvaritiaDelight.MOD_ID).save(output);
    }

    public void save(RecipeOutput output) {
        ResourceLocation defaultLocation = getDefaultRecipeId(result);
        save(output, ResourceLocation.fromNamespaceAndPath(this.namespace != null ? namespace : defaultLocation.getNamespace(), defaultLocation.getPath()).withPrefix("cooking/"));
    }

    @Override
    public void save(RecipeOutput output, ResourceLocation id) {
        ResourceLocation recipeId = id;
        Advancement.Builder advancementBuilder = output.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancementBuilder::addCriterion);
        ExtremeCookingPotRecipe recipe = new ExtremeCookingPotRecipe(
                "",
                this.ingredients,
                this.resultStack,
                this.container,
                this.experience,
                this.cookingTime
        );
        output.accept(recipeId, recipe, advancementBuilder.build(id.withPrefix("recipes/")));
    }
}