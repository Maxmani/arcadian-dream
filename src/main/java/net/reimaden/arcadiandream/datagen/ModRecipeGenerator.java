/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.RecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.tag.ItemTags;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.datagen.builders.RitualCraftingRecipeJsonBuilder;
import net.reimaden.arcadiandream.datagen.providers.ModRecipeProvider;
import net.reimaden.arcadiandream.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGenerator extends ModRecipeProvider {

    public ModRecipeGenerator(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<RecipeJsonProvider> exporter) {

        // Shapeless Crafting recipes
        makeReversibleCompacting(exporter, ModItems.POWER_ITEM, ModItems.BIG_POWER_ITEM);
        makeReversibleCompacting(exporter, ModItems.POINT_ITEM, ModItems.MAX_POINT_ITEM);
        makeReversibleCompacting(exporter, ModItems.RAW_MAKAITE, ModItems.RAW_MAKAITE_BLOCK);
        makeReversibleCompacting(exporter, ModItems.MAKAITE_INGOT, ModItems.MAKAITE_BLOCK);
        makeReversibleCompacting(exporter, ModItems.DRAGON_GEM, ModItems.DRAGON_GEM_BLOCK);

        makeShapeless(exporter, ModItems.BOMB_ITEM, ModItems.EXTEND_ITEM, null, 3);

        // Shaped Crafting recipes
        makeTools(exporter, ModItems.MAKAITE_AXE, ModItems.MAKAITE_HOE, ModItems.MAKAITE_PICKAXE,
                ModItems.MAKAITE_SHOVEL, ModItems.MAKAITE_SWORD, ModItems.MAKAITE_INGOT);
        makeArmor(exporter, ModItems.MAKAITE_BOOTS, ModItems.MAKAITE_CHESTPLATE,
                ModItems.MAKAITE_HELMET, ModItems.MAKAITE_LEGGINGS, ModItems.MAKAITE_INGOT);

        ShapedRecipeJsonBuilder.create(ModItems.ORDINARY_HAT)
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

        ShapedRecipeJsonBuilder.create(ModBlocks.ONBASHIRA_PILLAR)
                .input('#', ItemTags.LOGS)
                .pattern("#")
                .pattern("#")
                .criterion("has_log", RecipeProvider.conditionsFromTag(ItemTags.LOGS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(ModBlocks.RITUAL_SHRINE)
                .input('#', Blocks.AMETHYST_BLOCK)
                .input('D', ModItems.DRAGON_GEM)
                .pattern(" D ")
                .pattern("D#D")
                .pattern("###")
                .criterion(RecipeProvider.hasItem(Blocks.AMETHYST_BLOCK),
                        RecipeProvider.conditionsFromItem(Blocks.AMETHYST_BLOCK))
                .offerTo(exporter);

        // Smelting recipes
        makeSmelting(exporter, List.of(ModBlocks.DRAGON_GEM_ORE, ModBlocks.DEEPSLATE_DRAGON_GEM_ORE, ModBlocks.END_STONE_DRAGON_GEM_ORE),
                ModItems.DRAGON_GEM, 1.2f, 200, RecipeProvider.getItemPath(ModItems.DRAGON_GEM));
        makeSmelting(exporter, List.of(ModBlocks.MAKAITE_ORE, ModItems.RAW_MAKAITE),
                ModItems.MAKAITE_INGOT, 0.8f, 200, RecipeProvider.getItemPath(ModItems.MAKAITE_INGOT));

        // Blasting recipes
        makeBlasting(exporter, List.of(ModBlocks.DRAGON_GEM_ORE, ModBlocks.DEEPSLATE_DRAGON_GEM_ORE, ModBlocks.END_STONE_DRAGON_GEM_ORE),
                ModItems.DRAGON_GEM, 1.2f, 100, RecipeProvider.getItemPath(ModItems.DRAGON_GEM));
        makeBlasting(exporter, List.of(ModBlocks.MAKAITE_ORE, ModItems.RAW_MAKAITE),
                ModItems.MAKAITE_INGOT, 0.8f, 100, RecipeProvider.getItemPath(ModItems.MAKAITE_INGOT));

        // Ritual Crafting recipes
        RitualCraftingRecipeJsonBuilder.create(ModItems.MAKAITE_INFUSED_NETHERITE_INGOT)
                .input(ModItems.MAKAITE_INGOT)
                .input(Items.NETHERITE_INGOT)
                .offerTo(exporter, ritualCraftingId(ModItems.MAKAITE_INFUSED_NETHERITE_INGOT));

        RitualCraftingRecipeJsonBuilder.create(ModItems.NUE_TRIDENT)
                .input(Items.TRIDENT)
                .input(ModItems.MAKAITE_INFUSED_NETHERITE_INGOT)
                .input(Items.DIAMOND)
                .input(Items.SPIDER_EYE)
                .input(Items.FIRE_CHARGE)
                .input(Items.SPIDER_EYE)
                .input(Items.DIAMOND)
                .input(ModItems.MAKAITE_INFUSED_NETHERITE_INGOT)
                .input(Items.PRISMARINE_CRYSTALS)
                .input(Items.PRISMARINE_CRYSTALS)
                .offerTo(exporter, ritualCraftingId(ModItems.NUE_TRIDENT));

        RitualCraftingRecipeJsonBuilder.create(ModItems.MOCHI_HAMMER)
                .input(Items.RABBIT_HIDE)
                .input(Items.STICK)
                .input(Items.STICK)
                .input(ItemTags.PLANKS)
                .moonPhase(String.valueOf(MOON_PHASES.FULL_MOON.ordinal()))
                .offerTo(exporter, ritualCraftingId(ModItems.MOCHI_HAMMER));
    }
}
