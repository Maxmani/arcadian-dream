/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class RitualCraftingRecipe implements Recipe<SimpleInventory> {

    private final Identifier id;
    private final ItemStack output;
    private final DefaultedList<Ingredient> recipeItems;
    private final String moonPhase;

    public RitualCraftingRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems, String moonPhase) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.moonPhase = moonPhase;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        boolean result;
        int index = 0;

        do {
            result = recipeItems.get(index).test(inventory.getStack(index));
            index++;
        } while (result && index < recipeItems.size());

        if (recipeItems.size() < inventory.size()) {
            result = false;
        }

        // If the recipe requires a specific moon phase, check for it
        // If a valid moon phase recipe is found, make sure it's during a night
        if ((!moonPhase.matches(String.valueOf(world.getMoonPhase())) && !moonPhase.isEmpty())
                || (moonPhase.matches(String.valueOf(world.getMoonPhase())) && world.isDay())) {
            result = false;
        }

        return result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return recipeItems;
    }

    @Override
    public ItemStack craft(SimpleInventory inventory) {
        return output;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput() {
        return output.copy();
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<RitualCraftingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "ritual_crafting";
    }

    public static class Serializer implements RecipeSerializer<RitualCraftingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "ritual_crafting";

        @Override
        public RitualCraftingRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "result"));

            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.of();

            String moon_phase = JsonHelper.getString(json, "moon_phase", "");

            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Ritual Crafting recipe");
            }
            if (ingredients.size() > 16) {
                throw new JsonParseException("Too many ingredients for Ritual Crafting recipe");
            }
            if (!moon_phase.isEmpty() && (Integer.parseInt(moon_phase) > 7 || Integer.parseInt(moon_phase) < 0)) {
                throw new JsonParseException("Invalid moon phase for Ritual Crafting recipe");
            }

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.add(i, Ingredient.EMPTY);
            }

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new RitualCraftingRecipe(id, output, inputs, moon_phase);
        }

        @Override
        public RitualCraftingRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            String moon_phase = buf.readString();
            return new RitualCraftingRecipe(id, output, inputs, moon_phase);
        }

        @Override
        public void write(PacketByteBuf buf, RitualCraftingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            buf.writeString(recipe.moonPhase);
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
        }
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }
}
