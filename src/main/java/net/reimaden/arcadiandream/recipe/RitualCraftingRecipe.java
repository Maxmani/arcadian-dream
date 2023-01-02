/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
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
    private final byte moonPhase;
    private final String dimension;

    public RitualCraftingRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems, byte moonPhase, String dimension) {
        this.id = id;
        this.output = output;
        this.recipeItems = recipeItems;
        this.moonPhase = moonPhase;
        this.dimension = dimension;
    }

    @Override
    public boolean matches(SimpleInventory inventory, World world) {
        if (world.isClient()) {
            return false;
        }

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
        if ((moonPhase != world.getMoonPhase() && moonPhase != -1)
                || (moonPhase == world.getMoonPhase()) && world.isDay()) {
            result = false;
        }

        // If the recipe requires a specific dimension, check for it
        if (!dimension.equalsIgnoreCase(world.getRegistryKey().getValue().getPath()) && !dimension.isEmpty()) {
            result = false;
        }

        return result;
    }

    public byte getMoonPhase() {
        return moonPhase;
    }

    public String getDimension() {
        return dimension;
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

            byte moonPhase = JsonHelper.getByte(json, "moon_phase", (byte) -1);
            String dimension = JsonHelper.getString(json, "dimension", "");

            if (ingredients.isEmpty()) {
                throw new JsonParseException("No ingredients for Ritual Crafting recipe");
            }
            if (ingredients.size() > 16) {
                throw new JsonParseException("Too many ingredients for Ritual Crafting recipe");
            }
            if (moonPhase != -1 && (moonPhase > 7 || moonPhase < 0)) {
                throw new JsonParseException("Invalid moon phase for Ritual Crafting recipe");
            }
            if (moonPhase != -1 && !dimension.isEmpty()) {
                throw new JsonParseException("Ritual Crafting cannot have both a moon phase requirement and a dimension requirement");
            }

            for (int i = 0; i < ingredients.size(); i++) {
                inputs.add(i, Ingredient.EMPTY);
            }

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            return new RitualCraftingRecipe(id, output, inputs, moonPhase, dimension);
        }

        @Override
        public RitualCraftingRecipe read(Identifier id, PacketByteBuf buf) {
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);

            inputs.replaceAll(ignored -> Ingredient.fromPacket(buf));

            ItemStack output = buf.readItemStack();
            byte moonPhase = buf.readByte();
            String dimension = buf.readString();

            return new RitualCraftingRecipe(id, output, inputs, moonPhase, dimension);
        }

        @Override
        public void write(PacketByteBuf buf, RitualCraftingRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ing : recipe.getIngredients()) {
                ing.write(buf);
            }
            buf.writeItemStack(recipe.getOutput());
            buf.writeByte(recipe.moonPhase);
            buf.writeString(recipe.dimension);
        }
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }
}
