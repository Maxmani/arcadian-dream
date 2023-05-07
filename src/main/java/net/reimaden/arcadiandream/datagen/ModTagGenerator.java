/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBlockTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalEnchantmentTags;
import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.block.Blocks;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.poi.PointOfInterestType;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.damage.ModDamageSources;
import net.reimaden.arcadiandream.enchantment.ModEnchantments;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.painting.ModPaintings;
import net.reimaden.arcadiandream.util.ModTags;
import net.reimaden.arcadiandream.villager.ModVillagers;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.registry.tag.BlockTags.*;
import static net.minecraft.registry.tag.DamageTypeTags.BYPASSES_ARMOR;
import static net.minecraft.registry.tag.ItemTags.*;
import static net.minecraft.registry.tag.PaintingVariantTags.PLACEABLE;
import static net.minecraft.registry.tag.PointOfInterestTypeTags.ACQUIRABLE_JOB_SITE;

public class ModTagGenerator {

    public static class BlockTags extends FabricTagProvider.BlockTagProvider {

        public BlockTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {

            // minecraft/tags/blocks
            getOrCreateTagBuilder(NEEDS_DIAMOND_TOOL)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.DRAGON_GEM_BLOCK, ModBlocks.RITUAL_SHRINE);
            // getOrCreateTagBuilder(NEEDS_IRON_TOOL);
            getOrCreateTagBuilder(NEEDS_STONE_TOOL)
                    .add(ModBlocks.MAKAITE_ORE, ModBlocks.RAW_MAKAITE_BLOCK, ModBlocks.MAKAITE_BLOCK);
            getOrCreateTagBuilder(BEACON_BASE_BLOCKS)
                    .add(ModBlocks.DRAGON_GEM_BLOCK, ModBlocks.MAKAITE_BLOCK);

            // minecraft/tags/blocks/mineable
            // fabric/tags/blocks/mineable
            getOrCreateTagBuilder(AXE_MINEABLE)
                    .add(ModBlocks.ONBASHIRA, ModBlocks.ONBASHIRA_PILLAR, ModBlocks.DANMAKU_CRAFTING_TABLE, ModBlocks.MYSTERIOUS_SEAL);
            getOrCreateTagBuilder(PICKAXE_MINEABLE)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.DRAGON_GEM_BLOCK, ModBlocks.MAKAITE_ORE, ModBlocks.RAW_MAKAITE_BLOCK, ModBlocks.MAKAITE_BLOCK,
                            ModBlocks.RITUAL_SHRINE);
            getOrCreateTagBuilder(FabricMineableTags.SWORD_MINEABLE)
                    .add(ModBlocks.MYSTERIOUS_SEAL);

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

            // minecraft/tags/items
            getOrCreateTagBuilder(BEACON_PAYMENT_ITEMS)
                    .add(ModItems.DRAGON_GEM, ModItems.MAKAITE_INGOT);
            getOrCreateTagBuilder(MUSIC_DISCS)
                    .add(ModItems.MUSIC_DISC_FAIRY_PLAYGROUND, ModItems.MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN);
            getOrCreateTagBuilder(FISHES)
                    .add(ModItems.LAMPREY, ModItems.COOKED_LAMPREY);

            // Block tags to item tags
            getOrCreateTagBuilder(ModTags.Items.OBSIDIAN_BLOCKS)
                    .add(Items.OBSIDIAN, Items.CRYING_OBSIDIAN);
            getOrCreateTagBuilder(ModTags.Items.DRAGON_GEM_ORES)
                    .add(ModItems.DRAGON_GEM_ORE, ModItems.DEEPSLATE_DRAGON_GEM_ORE, ModItems.END_STONE_DRAGON_GEM_ORE);
            getOrCreateTagBuilder(ConventionalItemTags.ORES)
                    .addTag(ModTags.Items.DRAGON_GEM_ORES)
                    .add(ModItems.MAKAITE_ORE);

            // Modded item tags
            getOrCreateTagBuilder(AXES)
                    .add(ModItems.MAKAITE_AXE);
            getOrCreateTagBuilder(HOES)
                    .add(ModItems.MAKAITE_HOE);
            getOrCreateTagBuilder(PICKAXES)
                    .add(ModItems.MAKAITE_PICKAXE);
            getOrCreateTagBuilder(SHOVELS)
                    .add(ModItems.MAKAITE_SHOVEL);
            getOrCreateTagBuilder(ConventionalItemTags.SPEARS)
                    .add(ModItems.NUE_TRIDENT);
            getOrCreateTagBuilder(SWORDS)
                    .add(ModItems.MAKAITE_SWORD, ModItems.HISOU_SWORD, ModItems.DEATH_SCYTHE);
            getOrCreateTagBuilder(ModTags.Items.HAMMERS)
                    .add(ModItems.MOCHI_MALLET, ModItems.MIRACLE_MALLET);
            getOrCreateTagBuilder(ConventionalItemTags.FOODS)
                    .add(ModItems.HEAVENLY_PEACH, ModItems.LAMPREY, ModItems.COOKED_LAMPREY);
            getOrCreateTagBuilder(ModTags.Items.ITEMS)
                    .add(ModItems.POWER_ITEM, ModItems.BIG_POWER_ITEM, ModItems.POINT_ITEM, ModItems.MAX_POINT_ITEM,
                            ModItems.BOMB_ITEM, ModItems.LIFE_FRAGMENT, ModItems.EXTEND_ITEM, ModItems.STAR_ITEM,
                            ModItems.FAITH_ITEM, ModItems.TIME_ORB);
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
                    .addTag(ModTags.Items.DANMAKU_DURATION_MODIFIERS)
                    .add(ModItems.TIME_ORB);
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

    public static class EntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {

        public EntityTypeTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(ModTags.EntityTypes.DANMAKU)
                    .add(ModEntities.CIRCLE_BULLET, ModEntities.BUBBLE_BULLET, ModEntities.AMULET_BULLET, ModEntities.STAR_BULLET);
            getOrCreateTagBuilder(ModTags.EntityTypes.FAIRIES)
                    .add(ModEntities.FAIRY, ModEntities.SUNFLOWER_FAIRY);
        }
    }

    public static class EnchantmentTypeTags extends FabricTagProvider.EnchantmentTagProvider {

        public EnchantmentTypeTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(ConventionalEnchantmentTags.ENTITY_DEFENSE_ENHANCEMENT)
                    .add(ModEnchantments.DANMAKU_PROTECTION);
            getOrCreateTagBuilder(ConventionalEnchantmentTags.WEAPON_DAMAGE_ENHANCEMENT)
                    .add(ModEnchantments.YOUKAI_BUSTER);
        }
    }

    public static class DamageTypeTags extends FabricTagProvider<DamageType> {

        public DamageTypeTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {

            // minecraft/tags/damage_type
            getOrCreateTagBuilder(BYPASSES_ARMOR)
                    .add(ModDamageSources.DANMAKU_SHARP);

            // Modded damage type tags
            getOrCreateTagBuilder(ModTags.DamageTypes.IS_DANMAKU)
                    .add(ModDamageSources.DANMAKU, ModDamageSources.DANMAKU_SHARP);
        }
    }

    public static class PaintingVariantTags extends FabricTagProvider<PaintingVariant> {

        public PaintingVariantTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.PAINTING_VARIANT, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(PLACEABLE)
                    .add(ModPaintings.REIMADEN);
        }
    }

    public static class PointOfInterestTypeTags extends FabricTagProvider<PointOfInterestType> {

        public PointOfInterestTypeTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.POINT_OF_INTEREST_TYPE, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(ACQUIRABLE_JOB_SITE)
                    .add(ModVillagers.ANTIQUARIAN_POI);
        }
    }

    public static class BiomeTags extends FabricTagProvider<Biome> {

        public BiomeTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.BIOME, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(ModTags.Biomes.ABANDONED_SHRINE_HAS_STRUCTURE)
                    .forceAddTag(ConventionalBiomeTags.FOREST);
        }
    }
}
