/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.datagen.builders.RitualCraftingRecipeJsonBuilder;
import net.reimaden.arcadiandream.datagen.providers.ModRecipeProvider;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.util.ModTags;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends ModRecipeProvider {

    private static final int DEFAULT_COOKING_TIME = 200;
    private static final int BLASTING_SMOKING_COOKING_TIME = 100;
    private static final int CAMPFIRE_COOKING_TIME = 600;

    private static final ImmutableList<ItemConvertible> DRAGON_GEM_ORES =
            ImmutableList.of(ModBlocks.DRAGON_GEM_ORE, ModBlocks.DEEPSLATE_DRAGON_GEM_ORE, ModBlocks.END_STONE_DRAGON_GEM_ORE);
    private static final ImmutableList<ItemConvertible> MAKAITE_ORES =
            ImmutableList.of(ModItems.MAKAITE_ORE, ModItems.RAW_MAKAITE);
    private static final ImmutableList<ItemConvertible> HIHIIROKANE_ORES =
            ImmutableList.of(ModBlocks.HIHIIROKANE_ORE, ModBlocks.DEEPSLATE_HIHIIROKANE_ORE);

    public ModRecipeGenerator(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        // Shapeless Crafting recipes
        makeReversibleCompacting(exporter, RecipeCategory.MISC, ModItems.POWER_ITEM, RecipeCategory.MISC, ModItems.BIG_POWER_ITEM);
        makeReversibleCompacting(exporter, RecipeCategory.MISC, ModItems.POINT_ITEM, RecipeCategory.MISC, ModItems.MAX_POINT_ITEM);
        makeReversibleCompacting(exporter, RecipeCategory.MISC, ModItems.STAR_ITEM, RecipeCategory.MISC, ModItems.FAITH_ITEM);
        makeReversibleCompacting(exporter, RecipeCategory.MISC, ModItems.LIFE_FRAGMENT, RecipeCategory.MISC, ModItems.EXTEND_ITEM);
        makeReversibleCompacting(exporter, RecipeCategory.MISC, ModItems.RAW_MAKAITE, RecipeCategory.BUILDING_BLOCKS, ModItems.RAW_MAKAITE_BLOCK);
        makeReversibleCompacting(exporter, RecipeCategory.MISC, ModItems.MAKAITE_INGOT, RecipeCategory.BUILDING_BLOCKS, ModItems.MAKAITE_BLOCK);
        makeReversibleCompacting(exporter, RecipeCategory.MISC, ModItems.DRAGON_GEM, RecipeCategory.BUILDING_BLOCKS, ModBlocks.DRAGON_GEM_BLOCK);
        makeReversibleCompacting(exporter, RecipeCategory.MISC, ModItems.HIHIIROKANE_CHUNK, RecipeCategory.BUILDING_BLOCKS, ModBlocks.HIHIIROKANE_CHUNK_BLOCK);
        makeReversibleCompacting(exporter, RecipeCategory.MISC, ModItems.HIHIIROKANE_INGOT, RecipeCategory.BUILDING_BLOCKS, ModItems.HIHIIROKANE_BLOCK);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BOMB_ITEM, 3)
                .input('#', ModItems.EXTEND_ITEM)
                .input('X', ModTags.Items.ITEMS)
                .pattern("XXX")
                .pattern("X#X")
                .pattern("XXX")
                .criterion(RecipeProvider.hasItem(ModItems.EXTEND_ITEM),
                        RecipeProvider.conditionsFromItem(ModItems.EXTEND_ITEM))
                .offerTo(exporter);

        // Shaped Crafting recipes
        makeTools(exporter, ModItems.MAKAITE_AXE, ModItems.MAKAITE_HOE, ModItems.MAKAITE_PICKAXE,
                ModItems.MAKAITE_SHOVEL, ModItems.MAKAITE_SWORD, ModItems.MAKAITE_INGOT);
        makeArmor(exporter, ModItems.MAKAITE_BOOTS, ModItems.MAKAITE_CHESTPLATE,
                ModItems.MAKAITE_HELMET, ModItems.MAKAITE_LEGGINGS, ModItems.MAKAITE_INGOT);

        makePatterns(exporter, ModItems.SPREAD_PATTERN_TEMPLATE, ModItems.SPREAD_PATTERN);
        makePatterns(exporter, ModItems.RAY_PATTERN_TEMPLATE, ModItems.RAY_PATTERN);
        makePatterns(exporter, ModItems.RING_PATTERN_TEMPLATE, ModItems.RING_PATTERN);
        makePatterns(exporter, ModItems.ARC_PATTERN_TEMPLATE, ModItems.ARC_PATTERN);
        makePatterns(exporter, ModItems.DOUBLE_PATTERN_TEMPLATE, ModItems.DOUBLE_PATTERN);
        makePatterns(exporter, ModItems.TRIPLE_PATTERN_TEMPLATE, ModItems.TRIPLE_PATTERN);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.ORDINARY_HAT)
                .input('#', Items.BLACK_WOOL)
                .input('S', Items.STRING)
                .input('W', Items.WHITE_WOOL)
                .input('E', Items.ENDER_PEARL)
                .pattern(" # ")
                .pattern("SWS")
                .pattern("#E#")
                .criterion(RecipeProvider.hasItem(Items.ENDER_PEARL),
                        RecipeProvider.conditionsFromItem(Items.ENDER_PEARL))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.ONBASHIRA_PILLAR)
                .input('#', ItemTags.LOGS)
                .pattern("#")
                .pattern("#")
                .criterion("has_log", RecipeProvider.conditionsFromTag(ItemTags.LOGS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.RITUAL_SHRINE)
                .input('#', Blocks.AMETHYST_BLOCK)
                .input('D', ModItems.DRAGON_GEM)
                .pattern(" D ")
                .pattern("D#D")
                .pattern("###")
                .criterion(RecipeProvider.hasItem(Blocks.AMETHYST_BLOCK),
                        RecipeProvider.conditionsFromItem(Blocks.AMETHYST_BLOCK))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.DANMAKU_CRAFTING_TABLE)
                .input('#', ModTags.Items.BULLET_CORES)
                .input('C', Blocks.CRAFTING_TABLE)
                .pattern("###")
                .pattern("#C#")
                .pattern("###")
                .criterion("has_item", RecipeProvider.conditionsFromTag(ModTags.Items.BULLET_CORES))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.CIRCLE_BULLET_CORE)
                .input('#', ModItems.STAR_ITEM)
                .pattern(" # ")
                .pattern("# #")
                .pattern(" # ")
                .criterion(RecipeProvider.hasItem(ModItems.STAR_ITEM),
                        RecipeProvider.conditionsFromItem(ModItems.STAR_ITEM))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.BUBBLE_BULLET_CORE)
                .input('#', ModItems.STAR_ITEM)
                .pattern("###")
                .pattern("# #")
                .pattern("###")
                .criterion(RecipeProvider.hasItem(ModItems.STAR_ITEM),
                        RecipeProvider.conditionsFromItem(ModItems.STAR_ITEM))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.AMULET_BULLET_CORE)
                .input('#', ModItems.STAR_ITEM)
                .input('U', ModTags.Items.UNDEAD_PARTS)
                .pattern("UU")
                .pattern("##")
                .pattern("##")
                .criterion(RecipeProvider.hasItem(ModItems.STAR_ITEM),
                        RecipeProvider.conditionsFromItem(ModItems.STAR_ITEM))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.STAR_BULLET_CORE)
                .input('#', ModItems.STAR_ITEM)
                .input('U', Items.ARROW)
                .pattern("# #")
                .pattern(" U ")
                .pattern("# #")
                .criterion(RecipeProvider.hasItem(ModItems.STAR_ITEM),
                        RecipeProvider.conditionsFromItem(ModItems.STAR_ITEM))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.KUNAI_BULLET_CORE)
                .input('#', ModItems.STAR_ITEM)
                .input('U', ConventionalItemTags.IRON_INGOTS)
                .pattern("#")
                .pattern("U")
                .pattern("#")
                .criterion(RecipeProvider.hasItem(ModItems.STAR_ITEM),
                        RecipeProvider.conditionsFromItem(ModItems.STAR_ITEM))
                .offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.PELLET_BULLET_CORE)
                .input(ModItems.STAR_ITEM)
                .criterion(RecipeProvider.hasItem(ModItems.STAR_ITEM),
                        RecipeProvider.conditionsFromItem(ModItems.STAR_ITEM))
                .offerTo(exporter);

        // Smelting recipes
        makeSmelting(exporter, DRAGON_GEM_ORES, RecipeCategory.MISC, ModItems.DRAGON_GEM, 1.2f, DEFAULT_COOKING_TIME,
                RecipeProvider.getItemPath(ModItems.DRAGON_GEM));
        makeSmelting(exporter, MAKAITE_ORES, RecipeCategory.MISC, ModItems.MAKAITE_INGOT, 0.8f, DEFAULT_COOKING_TIME,
                RecipeProvider.getItemPath(ModItems.MAKAITE_INGOT));
        makeSmelting(exporter, List.of(ModItems.DEATH_SCYTHE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.1f, DEFAULT_COOKING_TIME,
                RecipeProvider.getItemPath(Items.IRON_NUGGET));
        makeSmelting(exporter, List.of(ModItems.LAMPREY), RecipeCategory.FOOD, ModItems.COOKED_LAMPREY, 0.35f, DEFAULT_COOKING_TIME,
                RecipeProvider.getItemPath(ModItems.COOKED_LAMPREY));
        makeSmelting(exporter, HIHIIROKANE_ORES, RecipeCategory.MISC, ModItems.HIHIIROKANE_CHUNK, 2.0f, DEFAULT_COOKING_TIME,
                RecipeProvider.getItemPath(ModItems.HIHIIROKANE_CHUNK));

        // Blasting recipes
        makeBlasting(exporter, DRAGON_GEM_ORES, RecipeCategory.MISC, ModItems.DRAGON_GEM, 1.2f, BLASTING_SMOKING_COOKING_TIME,
                RecipeProvider.getItemPath(ModItems.DRAGON_GEM));
        makeBlasting(exporter, MAKAITE_ORES, RecipeCategory.MISC, ModItems.MAKAITE_INGOT, 0.8f, BLASTING_SMOKING_COOKING_TIME,
                RecipeProvider.getItemPath(ModItems.MAKAITE_INGOT));
        makeBlasting(exporter, List.of(ModItems.DEATH_SCYTHE), RecipeCategory.MISC, Items.IRON_NUGGET, 0.1f, BLASTING_SMOKING_COOKING_TIME,
                RecipeProvider.getItemPath(Items.IRON_NUGGET));
        makeBlasting(exporter, HIHIIROKANE_ORES, RecipeCategory.MISC, ModItems.HIHIIROKANE_CHUNK, 2.0f, BLASTING_SMOKING_COOKING_TIME,
                RecipeProvider.getItemPath(ModItems.HIHIIROKANE_CHUNK));

        // Smoking recipes
        makeSmoking(exporter, List.of(ModItems.LAMPREY), RecipeCategory.FOOD, ModItems.COOKED_LAMPREY, 0.35f, BLASTING_SMOKING_COOKING_TIME,
                RecipeProvider.getItemPath(ModItems.COOKED_LAMPREY));

        // Campfire recipes
        makeCampfireCooking(exporter, List.of(ModItems.LAMPREY), RecipeCategory.FOOD, ModItems.COOKED_LAMPREY, 0.35f, CAMPFIRE_COOKING_TIME,
                RecipeProvider.getItemPath(ModItems.COOKED_LAMPREY));

        // Smithing recipes
        makeSmithingTemplateCopyingRecipePlain(exporter, ModItems.HIHIIROKANE_UPGRADE_SMITHING_TEMPLATE, Items.END_STONE, Items.NETHERITE_INGOT);
        makeHihiirokaneUpgradeRecipe(exporter, Items.NETHERITE_SWORD, RecipeCategory.COMBAT, ModItems.HIHIIROKANE_SWORD);
        makeHihiirokaneUpgradeRecipe(exporter, Items.NETHERITE_PICKAXE, RecipeCategory.TOOLS, ModItems.HIHIIROKANE_PICKAXE);
        makeHihiirokaneUpgradeRecipe(exporter, Items.NETHERITE_AXE, RecipeCategory.TOOLS, ModItems.HIHIIROKANE_AXE);
        makeHihiirokaneUpgradeRecipe(exporter, Items.NETHERITE_SHOVEL, RecipeCategory.TOOLS, ModItems.HIHIIROKANE_SHOVEL);
        makeHihiirokaneUpgradeRecipe(exporter, Items.NETHERITE_HOE, RecipeCategory.TOOLS, ModItems.HIHIIROKANE_HOE);
        makeHihiirokaneUpgradeRecipe(exporter, Items.NETHERITE_HELMET, RecipeCategory.COMBAT, ModItems.HIHIIROKANE_HELMET);
        makeHihiirokaneUpgradeRecipe(exporter, Items.NETHERITE_CHESTPLATE, RecipeCategory.COMBAT, ModItems.HIHIIROKANE_CHESTPLATE);
        makeHihiirokaneUpgradeRecipe(exporter, Items.NETHERITE_LEGGINGS, RecipeCategory.COMBAT, ModItems.HIHIIROKANE_LEGGINGS);
        makeHihiirokaneUpgradeRecipe(exporter, Items.NETHERITE_BOOTS, RecipeCategory.COMBAT, ModItems.HIHIIROKANE_BOOTS);

        // Ritual Crafting recipes
        RitualCraftingRecipeJsonBuilder.create(ModItems.NUE_TRIDENT)
                .input(Items.TRIDENT)
                .input(ConventionalItemTags.NETHERITE_INGOTS)
                .input(ConventionalItemTags.DIAMONDS)
                .input(Items.SPIDER_EYE)
                .input(Items.FIRE_CHARGE)
                .input(Items.SPIDER_EYE)
                .input(ConventionalItemTags.DIAMONDS)
                .input(ConventionalItemTags.NETHERITE_INGOTS)
                .input(Items.PRISMARINE_CRYSTALS, 2)
                .input(ModItems.MAKAITE_INGOT, 6)
                .offerTo(exporter, ritualCraftingId(ModItems.NUE_TRIDENT));

        RitualCraftingRecipeJsonBuilder.create(ModItems.MOCHI_MALLET)
                .input(Items.RABBIT_HIDE)
                .input(Items.STICK, 2)
                .input(Ingredient.fromTag(ItemTags.PLANKS), 3)
                .moonPhase(MoonPhases.FULL_MOON.getId())
                .offerTo(exporter, ritualCraftingId(ModItems.MOCHI_MALLET));

        RitualCraftingRecipeJsonBuilder.create(ModItems.WALL_PASSING_CHISEL)
                .input(Items.ENDER_PEARL)
                .input(ConventionalItemTags.GOLD_INGOTS)
                .input(Items.STICK)
                .input(ConventionalItemTags.GOLD_INGOTS)
                .offerTo(exporter, ritualCraftingId(ModItems.WALL_PASSING_CHISEL));

        RitualCraftingRecipeJsonBuilder.create(ModItems.IBUKI_GOURD)
                .input(Items.CHAIN)
                .input(ItemTags.TERRACOTTA)
                .input(Items.BUCKET)
                .input(Items.PAPER, 2)
                .offerTo(exporter, ritualCraftingId(ModItems.IBUKI_GOURD));

        RitualCraftingRecipeJsonBuilder.create(ModItems.HISOU_SWORD)
                .input(Items.BLAZE_ROD)
                .input(ModItems.DRAGON_GEM, 2)
                .offerTo(exporter, ritualCraftingId(ModItems.HISOU_SWORD));

        RitualCraftingRecipeJsonBuilder.create(ModItems.DEATH_SCYTHE)
                .input(Items.STICK, 3)
                .input(Ingredient.fromTag(ConventionalItemTags.IRON_INGOTS), 5)
                .input(Ingredient.fromTag(ItemTags.SOUL_FIRE_BASE_BLOCKS), 8)
                .dimension(Dimensions.THE_NETHER.getId())
                .offerTo(exporter, ritualCraftingId(ModItems.DEATH_SCYTHE));

        RitualCraftingRecipeJsonBuilder.create(ModItems.HOURAI_ELIXIR)
                .input(Items.NETHER_STAR)
                .input(Items.ENCHANTED_GOLDEN_APPLE)
                .input(ModItems.TIME_ORB)
                .input(Items.ENCHANTED_GOLDEN_APPLE)
                .input(Items.NETHER_STAR)
                .input(Items.ENCHANTED_GOLDEN_APPLE)
                .input(ModItems.TIME_ORB)
                .input(Items.ENCHANTED_GOLDEN_APPLE)
                .input(ModItems.LIFE_FRAGMENT, 8)
                .dimension(Dimensions.THE_END.getId())
                .offerTo(exporter, ritualCraftingId(ModItems.HOURAI_ELIXIR));

        RitualCraftingRecipeJsonBuilder.create(ModItems.MIRACLE_MALLET)
                .input(Items.NETHER_STAR)
                .input(Blocks.GOLD_BLOCK)
                .input(Blocks.EMERALD_BLOCK)
                .input(Blocks.REDSTONE_BLOCK)
                .input(Items.BLAZE_ROD)
                .input(Blocks.REDSTONE_BLOCK)
                .input(Blocks.EMERALD_BLOCK)
                .input(Blocks.GOLD_BLOCK, 3)
                .offerTo(exporter, ritualCraftingId(ModItems.MIRACLE_MALLET));

        RitualCraftingRecipeJsonBuilder.create(ModItems.MAGATAMA_NECKLACE)
                .input(Items.STRING, 3)
                .input(ModItems.DRAGON_GEM)
                .input(ModItems.BOMB_ITEM)
                .input(ModItems.DRAGON_GEM)
                .input(Items.STRING, 2)
                .offerTo(exporter, ritualCraftingId(ModItems.MAGATAMA_NECKLACE));

        RitualCraftingRecipeJsonBuilder.create(ModItems.GHASTLY_LANTERN)
                .input(Items.LANTERN)
                .input(ModItems.MAX_POINT_ITEM)
                .input(Items.PAPER)
                .input(ModItems.MAX_POINT_ITEM)
                .input(ModItems.LIFE_FRAGMENT)
                .input(ModItems.MAX_POINT_ITEM)
                .input(Items.PAPER)
                .input(ModItems.MAX_POINT_ITEM)
                .offerTo(exporter, ritualCraftingId(ModItems.GHASTLY_LANTERN));

        RitualCraftingRecipeJsonBuilder.create(ModItems.ICICLE_SWORD)
                .input(ItemTags.SWORDS)
                .input(ModItems.ENCHANTED_ICE, 2)
                .offerTo(exporter, ritualCraftingId(ModItems.ICICLE_SWORD));

        RitualCraftingRecipeJsonBuilder.create(ModItems.HIHIIROKANE_INGOT)
                .input(ModItems.HIHIIROKANE_CHUNK, 8)
                .input(ModItems.MAKAITE_INGOT, 8)
                .offerTo(exporter, ritualCraftingId(ModItems.HIHIIROKANE_INGOT));

        RitualCraftingRecipeJsonBuilder.create(ModItems.FAIRY_CHARM)
                .input(Items.SUNFLOWER)
                .input(ItemTags.FLOWERS, 3)
                .input(ModItems.BOMB_ITEM)
                .input(ItemTags.SAPLINGS, 3)
                .input(ModItems.DRAGON_GEM, 4)
                .offerTo(exporter, ritualCraftingId(ModItems.FAIRY_CHARM));
    }
}
