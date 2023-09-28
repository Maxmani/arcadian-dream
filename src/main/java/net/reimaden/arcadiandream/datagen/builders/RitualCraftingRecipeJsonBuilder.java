/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen.builders;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.data.server.recipe.CraftingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.recipe.RitualCraftingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class RitualCraftingRecipeJsonBuilder implements CraftingRecipeJsonBuilder {

    private final Item output;
    private final int outputCount;
    private final List<Ingredient> inputs = Lists.newArrayList();
    private byte moonPhase = -1;
    private String dimension = "";

    public RitualCraftingRecipeJsonBuilder(ItemConvertible output, int outputCount) {
        this.output = output.asItem();
        this.outputCount = outputCount;
    }

    public static RitualCraftingRecipeJsonBuilder create(ItemConvertible output) {
        return new RitualCraftingRecipeJsonBuilder(output, 1);
    }

    public static RitualCraftingRecipeJsonBuilder create(ItemConvertible output, int outputCount) {
        return new RitualCraftingRecipeJsonBuilder(output, outputCount);
    }

    public RitualCraftingRecipeJsonBuilder input(TagKey<Item> tag) {
        return input(Ingredient.fromTag(tag));
    }

    public RitualCraftingRecipeJsonBuilder input(TagKey<Item> tag, int size) {
        return input(Ingredient.fromTag(tag), size);
    }

    public RitualCraftingRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return input(itemProvider, 1);
    }

    public RitualCraftingRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
        for (int i = 0; i < size; ++i) {
            input(Ingredient.ofItems(itemProvider));
        }
        return this;
    }

    public RitualCraftingRecipeJsonBuilder input(Ingredient ingredient) {
        return input(ingredient, 1);
    }

    public RitualCraftingRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for (int i = 0; i < size; ++i) {
            inputs.add(ingredient);
        }
        return this;
    }

    public RitualCraftingRecipeJsonBuilder moonPhase(byte moonPhase) {
        this.moonPhase = moonPhase;
        return this;
    }

    public RitualCraftingRecipeJsonBuilder dimension(String dimension) {
        this.dimension = dimension.toLowerCase();
        return this;
    }

    @Override
    public CraftingRecipeJsonBuilder criterion(String name, CriterionConditions conditions) {
        return null;
    }

    @Override
    public CraftingRecipeJsonBuilder group(@Nullable String group) {
        return null;
    }

    @Override
    public Item getOutputItem() {
        return output;
    }

    @Override
    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        exporter.accept(new RitualCraftingRecipeJsonProvider(recipeId, output, outputCount, inputs, moonPhase, dimension));
    }

    public static class RitualCraftingRecipeJsonProvider implements RecipeJsonProvider {

        private final Identifier recipeId;
        private final Item output;
        private final int outputCount;
        private final List<Ingredient> inputs;
        private final byte moonPhase;
        private final String dimension;

        public RitualCraftingRecipeJsonProvider(Identifier recipeId, Item output, int outputCount, List<Ingredient> inputs, byte moonPhase, String dimension) {
            this.recipeId = recipeId;
            this.output = output;
            this.outputCount = outputCount;
            this.inputs = inputs;
            this.moonPhase = moonPhase;
            this.dimension = dimension;
        }

        @Override
        public void serialize(JsonObject json) {
            JsonArray jsonArray = new JsonArray();
            for (Ingredient ingredient : inputs) {
                jsonArray.add(ingredient.toJson());
            }
            json.add("ingredients", jsonArray);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("item", Registries.ITEM.getId(output).toString());
            if (outputCount > 1) {
                jsonObject.addProperty("count", outputCount);
            }
            JsonPrimitive jsonMoon = new JsonPrimitive(moonPhase);
            if (moonPhase != -1) {
                json.add("moon_phase", jsonMoon);
            }
            JsonPrimitive jsonDimension = new JsonPrimitive(dimension);
            if (!dimension.isEmpty()) {
                json.add("dimension", jsonDimension);
            }
            json.add("result", jsonObject);
        }

        @Override
        public Identifier getRecipeId() {
            return recipeId;
        }

        @Override
        public RecipeSerializer<?> getSerializer() {
            return RitualCraftingRecipe.Serializer.INSTANCE;
        }

        @Nullable
        @Override
        public JsonObject toAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public Identifier getAdvancementId() {
            return null;
        }
    }
}
