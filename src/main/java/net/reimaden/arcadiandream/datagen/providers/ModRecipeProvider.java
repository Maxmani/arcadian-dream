/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen.providers;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

@SuppressWarnings("SameParameterValue")
public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    protected enum MoonPhases {
        FULL_MOON((byte) 0),
        WANING_GIBBOUS((byte) 1),
        LAST_QUARTER((byte) 2),
        WANING_CRESCENT((byte) 3),
        NEW_MOON((byte) 4),
        WAXING_CRESCENT((byte) 5),
        FIRST_QUARTER((byte) 6),
        WAXING_GIBBOUS((byte) 7);

        private final byte id;

        MoonPhases(byte id) {
            this.id = id;
        }

        public byte getId() {
            return id;
        }
    }

    protected enum Dimensions {
        OVERWORLD("overworld"),
        THE_NETHER("the_nether"),
        THE_END("the_end");

        private final String id;

        Dimensions(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }

    protected static Identifier ritualCraftingId(Item item) {
        return new Identifier("ritual_crafting/"
                + RecipeProvider.getRecipeName(item)
                + "_from_ritual_crafting");
    }

    private static Identifier getIdentifier(Item item) {
        return new Identifier(ArcadianDream.MOD_ID, RecipeProvider.getRecipeName(item));
    }

    protected static void makeReversibleCompacting(Consumer<RecipeJsonProvider> exporter, RecipeCategory reverseCategory,
                                                   ItemConvertible baseItem, RecipeCategory compactingCategory, ItemConvertible compactItem) {
        ShapelessRecipeJsonBuilder.create(reverseCategory, baseItem, 9)
                .input(compactItem)
                .criterion(RecipeProvider.hasItem(compactItem),
                        RecipeProvider.conditionsFromItem(compactItem))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(compactingCategory, compactItem)
                .input('#', baseItem)
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .criterion(RecipeProvider.hasItem(baseItem),
                        RecipeProvider.conditionsFromItem(baseItem))
                .offerTo(exporter);
    }

    @SuppressWarnings("unused")
    protected static void makeShapeless(Consumer<RecipeJsonProvider> exporter, ItemConvertible output,
                                        ItemConvertible input, @Nullable String group, int outputCount) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, output, outputCount)
                .input(input).group(group)
                .criterion(RecipeProvider.hasItem(input),
                        RecipeProvider.conditionsFromItem(input))
                .offerTo(exporter, new Identifier(ArcadianDream.MOD_ID, RecipeProvider.convertBetween(output, input)));
    }

    private static void makeMultipleOptions(Consumer<RecipeJsonProvider> exporter, RecipeSerializer<? extends AbstractCookingRecipe> serializer,
                                            List<ItemConvertible> inputs, RecipeCategory category, ItemConvertible output, float experience,
                                            int cookingTime, String group, String baseIdString) {
        for (ItemConvertible itemConvertible : inputs) {
            CookingRecipeJsonBuilder.create(Ingredient.ofItems(itemConvertible), category, output, experience, cookingTime, serializer)
                    .group(group)
                    .criterion(RecipeProvider.hasItem(itemConvertible),
                            RecipeProvider.conditionsFromItem(itemConvertible))
                    .offerTo(exporter, new Identifier(ArcadianDream.MOD_ID,
                            RecipeProvider.getItemPath(output) + baseIdString + "_" + RecipeProvider.getItemPath(itemConvertible)));
        }
    }

    protected static void makeSmelting(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, RecipeCategory category,
                                    ItemConvertible output, float experience, int cookingTime, String group) {
        makeMultipleOptions(exporter, RecipeSerializer.SMELTING, inputs, category, output, experience, cookingTime, group, "_from_smelting");
    }

    protected static void makeBlasting(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, RecipeCategory category,
                                    ItemConvertible output, float experience, int cookingTime, String group) {
        makeMultipleOptions(exporter, RecipeSerializer.BLASTING, inputs, category, output, experience, cookingTime, group, "_from_blasting");
    }

    protected static void makeSmoking(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, RecipeCategory category,
                                    ItemConvertible output, float experience, int cookingTime, String group) {
        makeMultipleOptions(exporter, RecipeSerializer.SMOKING, inputs, category, output, experience, cookingTime, group, "_from_smoking");
    }

    protected static void makeCampfireCooking(Consumer<RecipeJsonProvider> exporter, List<ItemConvertible> inputs, RecipeCategory category,
                                      ItemConvertible output, float experience, int cookingTime, String group) {
        makeMultipleOptions(exporter, RecipeSerializer.CAMPFIRE_COOKING, inputs, category, output, experience, cookingTime, group, "_from_campfire_cooking");
    }

    private static void makeAxe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output)
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output)
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output)
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, output)
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
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
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .input('X', material)
                .pattern("X X")
                .pattern("X X")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makeChestplate(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .input('X', material)
                .pattern("X X")
                .pattern("XXX")
                .pattern("XXX")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makeHelmet(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
                .input('X', material)
                .pattern("XXX")
                .pattern("X X")
                .criterion(RecipeProvider.hasItem(material),
                        RecipeProvider.conditionsFromItem(material))
                .offerTo(exporter);
    }

    private static void makeLeggings(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible material) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, output)
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

    private static void makePattern(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible template) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, output)
                .input('#', template)
                .input('X', ModTags.Items.ITEMS)
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .criterion(RecipeProvider.hasItem(template),
                        RecipeProvider.conditionsFromItem(template))
                .offerTo(exporter);
    }

    private static void makePatternCopy(Consumer<RecipeJsonProvider> exporter, ItemConvertible pattern) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, pattern, 2)
                .input('#', pattern)
                .input('X', ModTags.Items.ITEMS)
                .input('Y', ModTags.Items.SHOTS)
                .pattern("X#X")
                .pattern("XYX")
                .pattern("XXX")
                .criterion(RecipeProvider.hasItem(pattern),
                        RecipeProvider.conditionsFromItem(pattern))
                .offerTo(exporter, new Identifier(ArcadianDream.MOD_ID, "copy_" + RecipeProvider.getRecipeName(pattern)));
    }

    protected static void makePatterns(Consumer<RecipeJsonProvider> exporter, ItemConvertible template, ItemConvertible pattern) {
        makePattern(exporter, pattern, template);
        makePatternCopy(exporter, pattern);
    }

    @SuppressWarnings("unused")
    protected static void makeSmithingTemplateCopyingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible template,
                                                            ItemConvertible resource) {
        makeSmithingTemplateCopyingRecipePlain(exporter, template, resource, Items.DIAMOND);
    }

    @SuppressWarnings("unused")
    protected static void makeSmithingTemplateCopyingRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible template,
                                                            TagKey<Item> resource) {
        makeSmithingTemplateCopyingRecipePlain(exporter, template, resource, Items.DIAMOND);
    }

    protected static void makeSmithingTemplateCopyingRecipePlain(Consumer<RecipeJsonProvider> exporter, ItemConvertible template,
                                                                 ItemConvertible resource, ItemConvertible main) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, template, 2)
                .input('#', main)
                .input('C', resource)
                .input('S', template)
                .pattern("#S#")
                .pattern("#C#")
                .pattern("###")
                .criterion(RecipeProvider.hasItem(template),
                        RecipeProvider.conditionsFromItem(template))
                .offerTo(exporter);
    }

    protected static void makeSmithingTemplateCopyingRecipePlain(Consumer<RecipeJsonProvider> exporter, ItemConvertible template,
                                                                 TagKey<Item> resource, ItemConvertible main) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, template, 2)
                .input('#', main)
                .input('C', resource)
                .input('S', template)
                .pattern("#S#")
                .pattern("#C#")
                .pattern("###")
                .criterion(RecipeProvider.hasItem(template),
                        RecipeProvider.conditionsFromItem(template))
                .offerTo(exporter);
    }

    protected static void makeHihiirokaneUpgradeRecipe(Consumer<RecipeJsonProvider> exporter, Item input, RecipeCategory category, Item result) {
        SmithingTransformRecipeJsonBuilder.create(Ingredient.ofItems(ModItems.HIHIIROKANE_UPGRADE_SMITHING_TEMPLATE),
                        Ingredient.ofItems(input), Ingredient.ofItems(ModItems.HIHIIROKANE_INGOT), category, result)
                .criterion(RecipeProvider.hasItem(ModItems.HIHIIROKANE_INGOT),
                        RecipeProvider.conditionsFromItem(ModItems.HIHIIROKANE_INGOT))
                .offerTo(exporter, getIdentifier(result) + "_smithing");
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {}
}
