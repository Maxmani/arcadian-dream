/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.util.ModTags;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.registry.tag.BlockTags.*;
import static net.minecraft.registry.tag.ItemTags.*;

public class ModTagGenerator {

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {

        public BlockTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {

            // "minecraft/tags/blocks"
            getOrCreateTagBuilder(NEEDS_DIAMOND_TOOL)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.DRAGON_GEM_BLOCK)
                    .add(ModBlocks.RITUAL_SHRINE);
            getOrCreateTagBuilder(NEEDS_IRON_TOOL)
                    .add(ModBlocks.MAKAITE_ORE)
                    .add(ModBlocks.RAW_MAKAITE_BLOCK)
                    .add(ModBlocks.MAKAITE_BLOCK);
            getOrCreateTagBuilder(BEACON_BASE_BLOCKS)
                    .add(ModBlocks.DRAGON_GEM_BLOCK)
                    .add(ModBlocks.MAKAITE_BLOCK);

            // "minecraft/tags/blocks/mineable"
            getOrCreateTagBuilder(AXE_MINEABLE)
                    .add(ModBlocks.ONBASHIRA)
                    .add(ModBlocks.ONBASHIRA_PILLAR)
                    .add(ModBlocks.DANMAKU_CRAFTING_TABLE);
            getOrCreateTagBuilder(PICKAXE_MINEABLE)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.DRAGON_GEM_BLOCK)
                    .add(ModBlocks.MAKAITE_ORE)
                    .add(ModBlocks.RAW_MAKAITE_BLOCK)
                    .add(ModBlocks.MAKAITE_BLOCK)
                    .add(ModBlocks.RITUAL_SHRINE);

            // Modded block tags
            getOrCreateTagBuilder(ModTags.Blocks.OBSIDIAN_BLOCKS)
                    .add(Blocks.OBSIDIAN)
                    .add(Blocks.CRYING_OBSIDIAN);
            getOrCreateTagBuilder(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.DRAGON_GEM_ORE)
                    .add(ModBlocks.DEEPSLATE_DRAGON_GEM_ORE)
                    .add(ModBlocks.END_STONE_DRAGON_GEM_ORE);
            getOrCreateTagBuilder(ConventionalBlockTags.ORES)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.MAKAITE_ORE);
        }
    }

    public static class ItemTags extends FabricTagProvider.ItemTagProvider {

        public ItemTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {

            // "minecraft/tags/items"
            getOrCreateTagBuilder(BEACON_PAYMENT_ITEMS)
                    .add(ModItems.DRAGON_GEM)
                    .add(ModItems.MAKAITE_INGOT)
                    .add(ModItems.MAKAITE_INFUSED_NETHERITE_INGOT);

            // Block tags to item tags
            getOrCreateTagBuilder(ModTags.Items.OBSIDIAN_BLOCKS)
                    .add(Items.OBSIDIAN)
                    .add(Items.CRYING_OBSIDIAN);
            getOrCreateTagBuilder(ModTags.Items.DRAGON_GEM_ORES)
                    .add(ModItems.DRAGON_GEM_ORE)
                    .add(ModItems.DEEPSLATE_DRAGON_GEM_ORE)
                    .add(ModItems.END_STONE_DRAGON_GEM_ORE);
            getOrCreateTagBuilder(ConventionalItemTags.ORES)
                    .addTag(ModTags.Items.DRAGON_GEM_ORES)
                    .add(ModItems.MAKAITE_ORE);

            // Modded item tags
            getOrCreateTagBuilder(ModTags.Items.RAW_ORES)
                    .add(Items.RAW_IRON)
                    .add(Items.RAW_COPPER)
                    .add(Items.RAW_GOLD)
                    .add(ModItems.RAW_MAKAITE);
            getOrCreateTagBuilder(ConventionalItemTags.AXES)
                    .add(ModItems.MAKAITE_AXE);
            getOrCreateTagBuilder(ConventionalItemTags.HOES)
                    .add(ModItems.MAKAITE_HOE);
            getOrCreateTagBuilder(ConventionalItemTags.PICKAXES)
                    .add(ModItems.MAKAITE_PICKAXE);
            getOrCreateTagBuilder(ConventionalItemTags.SHOVELS)
                    .add(ModItems.MAKAITE_SHOVEL);
            getOrCreateTagBuilder(ConventionalItemTags.SPEARS)
                    .add(ModItems.NUE_TRIDENT);
            getOrCreateTagBuilder(ConventionalItemTags.SWORDS)
                    .add(ModItems.MAKAITE_SWORD)
                    .add(ModItems.HISOU_SWORD)
                    .add(ModItems.DEATH_SCYTHE);
            getOrCreateTagBuilder(ConventionalItemTags.FOODS)
                    .add(ModItems.HEAVENLY_PEACH);
            getOrCreateTagBuilder(ModTags.Items.ITEMS)
                    .add(ModItems.POWER_ITEM)
                    .add(ModItems.BIG_POWER_ITEM)
                    .add(ModItems.POINT_ITEM)
                    .add(ModItems.MAX_POINT_ITEM)
                    .add(ModItems.BOMB_ITEM)
                    .add(ModItems.EXTEND_ITEM)
                    .add(ModItems.STAR_ITEM);
        }
    }
}
