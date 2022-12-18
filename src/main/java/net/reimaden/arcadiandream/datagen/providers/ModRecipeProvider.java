/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.CookingRecipeJsonBuilder;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("SameParameterValue")
public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    protected enum MOON_PHASES {
        FULL_MOON,
        WANING_GIBBOUS,
        LAST_QUARTER,
        WANING_CRESCENT,
        NEW_MOON,
        WAXING_CRESCENT,
        FIRST_QUARTER,
        WAXING_GIBBOUS
    }

    protected static Identifier ritualCraftingId(Item item) {
        return new Identifier("ritual_crafting/"
                + RecipeProvider.getRecipeName(item)
                + "_from_ritual_crafting");
    }

    protected static void makeReversibleCompacting(Consumer<RecipeJsonProvider> exporter,
                                                   ItemConvertible input, ItemConvertible compacted) {
        ShapelessRecipeJsonBuilder.create(input, 9)
                .input(compacted)
                .criterion(RecipeProvider.hasItem(compacted),
                        RecipeProvider.conditionsFromItem(compacted))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(compacted)
                .input('#', input)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .criterion(RecipeProvider.hasItem(input),
                        RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter);
    }

    protected static void makeShapeless(Consumer<RecipeJsonProvider> exporter, ItemConvertible output,
                                     ItemConvertible input, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(output, outputCount)
                .input(input).group(group)
                .criterion(RecipeProvider.hasItem(input),
                        RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(ArcadianDream.MOD_ID, RecipeProvider.convertBetween(output, input)));
    }

    private static void makeMultipleOptions(Consumer<RecipeJsonProvider> exporter, CookingRecipeSerializer<?> serializer,
                                            List<ItemConvertible> inputs, ItemConvertible output, float experience,
                                            int cookingTime, String group, String baseIdString) {
        for (ItemConvertible itemConvertible : inputs) {
            CookingRecipeJsonBuilder.create(Ingredient.ofItems(itemConvertible), output, experience, cookingTime, serializer)
                    .group(group)
                    .criterion(RecipeProvider.hasItem(itemConvertible),
                            RecipeProvider.conditionsFromItem(itemConvertible))
                    .offerTo(exporter, new Identifier(ArcadianDream.MOD_ID,
                            RecipeProvider.getItemPath(output) + baseIdString + "_" + RecipeProvider.getItemPath(itemConvertible)));
        }
    }

    protected static void makeSmelting(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs,
                                    ItemConvertible output, float experience, int cookingTime, String group) {
        makeMultipleOptions(exporter, RecipeSerializer.SMELTING, inputs, output, experience, cookingTime, group, "_from_smelting");
    }

    protected static void makeBlasting(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs,
                                    ItemConvertible output, float experience, int cookingTime, String group) {
        makeMultipleOptions(exporter, RecipeSerializer.BLASTING, inputs, output, experience, cookingTime, group, "_from_blasting");
    }

    private static void makeAxe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(output)
                .input('#', Items.STICK)
                .input('X', material)
                .pattern("XX")
                .pattern("X#")
                .pattern(" #")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makeHoe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(output)
                .input('#', Items.STICK)
                .input('X', material)
                .pattern("XX")
                .pattern(" #")
                .pattern(" #")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makePickaxe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(output)
                .input('#', Items.STICK)
                .input('X', material)
                .pattern("XXX")
                .pattern(" # ")
                .pattern(" # ")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makeShovel(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(output)
                .input('#', Items.STICK)
                .input('X', material)
                .pattern("X")
                .pattern("#")
                .pattern("#")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makeSword(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(output)
                .input('#', Items.STICK)
                .input('X', material)
                .pattern("X")
                .pattern("X")
                .pattern("#")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    protected static void makeTools(Consumer<RecipeJsonProvider> exporter, ItemConvertible axe, ItemConvertible hoe,
                                    ItemConvertible pickaxe, ItemConvertible shovel, ItemConvertible sword, ItemConvertible material) {
        makeAxe(exporter, axe, material);
        makeHoe(exporter, hoe, material);
        makePickaxe(exporter, pickaxe, material);
        makeShovel(exporter, shovel, material);
        makeSword(exporter, sword, material);
    }

    private static void makeBoots(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(output)
                .input('X', material)
                .pattern("X X")
                .pattern("X X")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makeChestplate(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(output)
                .input('X', material)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makeHelmet(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(output)
                .input('X', material)
                .pattern("XXX")
                .pattern("X X")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makeLeggings(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(output)
                .input('X', material)
                .pattern("XXX")
                .pattern("X X")
                .pattern("X X")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    protected static void makeArmor(Consumer<RecipeJsonProvider> exporter, ItemConvertible boots, ItemConvertible chestplate,
                                    ItemConvertible helmet, ItemConvertible leggings, ItemConvertible material) {
        makeBoots(exporter, boots, material);
        makeChestplate(exporter, chestplate, material);
        makeHelmet(exporter, helmet, material);
        makeLeggings(exporter, leggings, material);

    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {}
}
