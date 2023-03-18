/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
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
                    .add(ModBlocks.DRAGON_GEM_BLOCK, ModBlocks.RITUAL_SHRINE);
            getOrCreateTagBuilder(NEEDS_IRON_TOOL)
                    .add(ModBlocks.MAKAITE_ORE, ModBlocks.RAW_MAKAITE_BLOCK, ModBlocks.MAKAITE_BLOCK);
            getOrCreateTagBuilder(BEACON_BASE_BLOCKS)
                    .add(ModBlocks.DRAGON_GEM_BLOCK, ModBlocks.MAKAITE_BLOCK);

            // "minecraft/tags/blocks/mineable"
            getOrCreateTagBuilder(AXE_MINEABLE)
                    .add(ModBlocks.ONBASHIRA, ModBlocks.ONBASHIRA_PILLAR, ModBlocks.DANMAKU_CRAFTING_TABLE);
            getOrCreateTagBuilder(PICKAXE_MINEABLE)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.DRAGON_GEM_BLOCK, ModBlocks.MAKAITE_ORE, ModBlocks.RAW_MAKAITE_BLOCK, ModBlocks.MAKAITE_BLOCK,
                            ModBlocks.RITUAL_SHRINE);

            // Modded block tags
            getOrCreateTagBuilder(ModTags.Blocks.OBSIDIAN_BLOCKS)
                    .add(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN);
            getOrCreateTagBuilder(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.DRAGON_GEM_ORE, ModBlocks.DEEPSLATE_DRAGON_GEM_ORE, ModBlocks.END_STONE_DRAGON_GEM_ORE);
            getOrCreateTagBuilder(ConventionalBlockTags.ORES)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.MAKAITE_ORE);
            getOrCreateTagBuilder(ModTags.Blocks.FAIRIES_SPAWNABLE_ON)
                    .forceAddTag(FOXES_SPAWNABLE_ON)
                    .forceAddTag(ICE)
                    .add(Blocks.MUD);
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
                    .add(ModItems.DRAGON_GEM, ModItems.MAKAITE_INGOT);
            getOrCreateTagBuilder(MUSIC_DISCS)
                    .add(ModItems.MUSIC_DISC_FAIRY_PLAYGROUND, ModItems.MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN);

            // Block tags to item tags
            getOrCreateTagBuilder(ModTags.Items.OBSIDIAN_BLOCKS)
                    .add(Items.OBSIDIAN, Items.CRYING_OBSIDIAN);
            getOrCreateTagBuilder(ModTags.Items.DRAGON_GEM_ORES)
                    .add(ModItems.DRAGON_GEM_ORE, ModItems.DEEPSLATE_DRAGON_GEM_ORE, ModItems.END_STONE_DRAGON_GEM_ORE);
            getOrCreateTagBuilder(ConventionalItemTags.ORES)
                    .addTag(ModTags.Items.DRAGON_GEM_ORES)
                    .add(ModItems.MAKAITE_ORE);

            // Modded item tags
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
                    .add(ModItems.MAKAITE_SWORD, ModItems.HISOU_SWORD, ModItems.DEATH_SCYTHE);
            getOrCreateTagBuilder(ModTags.Items.HAMMERS)
                    .add(ModItems.MOCHI_MALLET, ModItems.MIRACLE_MALLET);
            getOrCreateTagBuilder(ConventionalItemTags.FOODS)
                    .add(ModItems.HEAVENLY_PEACH);
            getOrCreateTagBuilder(ModTags.Items.ITEMS)
                    .add(ModItems.POWER_ITEM, ModItems.BIG_POWER_ITEM, ModItems.POINT_ITEM, ModItems.MAX_POINT_ITEM,
                            ModItems.BOMB_ITEM, ModItems.EXTEND_ITEM, ModItems.STAR_ITEM, ModItems.FAITH_ITEM);
            getOrCreateTagBuilder(ModTags.Items.BULLET_CORES)
                    .add(ModTags.BULLET_CORES.toArray(new Item[0]));
            getOrCreateTagBuilder(ModTags.Items.SHOTS)
                    .add(ModTags.SHOTS.toArray(new Item[0]));
            getOrCreateTagBuilder(ModTags.Items.DANMAKU_REPAIR_ITEMS)
                    .add(ModItems.FAITH_ITEM);
            getOrCreateTagBuilder(ModTags.Items.DANMAKU_MODIFIERS)
                    .addTag(ModTags.Items.DANMAKU_POWER_MODIFIERS)
                    .addTag(ModTags.Items.DANMAKU_DENSITY_MODIFIERS)
                    .addTag(ModTags.Items.DANMAKU_SPEED_MODIFIERS)
                    .addTag(ModTags.Items.DANMAKU_DURATION_MODIFIERS);
            getOrCreateTagBuilder(ModTags.Items.DANMAKU_POWER_MODIFIERS)
                    .add(ModItems.POWER_ITEM);
            getOrCreateTagBuilder(ModTags.Items.DANMAKU_DENSITY_MODIFIERS)
                    .add(ModItems.BIG_POWER_ITEM);
            getOrCreateTagBuilder(ModTags.Items.DANMAKU_SPEED_MODIFIERS)
                    .add(ModItems.POINT_ITEM);
            getOrCreateTagBuilder(ModTags.Items.DANMAKU_DURATION_MODIFIERS)
                    .add(ModItems.STAR_ITEM);
            getOrCreateTagBuilder(ModTags.Items.UNDEAD_PARTS)
                    .add(Items.ROTTEN_FLESH, Items.BONE, Items.PHANTOM_MEMBRANE, Items.ZOMBIE_HEAD,
                            Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL);
        }
    }
}
