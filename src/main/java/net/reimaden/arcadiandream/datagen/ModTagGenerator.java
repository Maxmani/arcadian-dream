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
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.poi.PointOfInterestType;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.damage.ModDamageSources;
import net.reimaden.arcadiandream.effect.ModEffects;
import net.reimaden.arcadiandream.enchantment.ModEnchantments;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.painting.ModPaintings;
import net.reimaden.arcadiandream.util.ModTags;
import net.reimaden.arcadiandream.villager.ModVillagers;

import java.util.concurrent.CompletableFuture;

import static net.minecraft.registry.tag.BlockTags.*;
import static net.minecraft.registry.tag.DamageTypeTags.BYPASSES_ARMOR;
import static net.minecraft.registry.tag.EntityTypeTags.*;
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
            // fabric/tags/blocks
            getOrCreateTagBuilder(ModTags.Blocks.NEEDS_TOOL_LEVEL_4)
                    .addTag(ModTags.Blocks.HIHIIROKANE_ORES)
                    .add(ModBlocks.HIHIIROKANE_CHUNK_BLOCK, ModBlocks.HIHIIROKANE_BLOCK);
            getOrCreateTagBuilder(NEEDS_DIAMOND_TOOL)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.DRAGON_GEM_BLOCK, ModBlocks.RITUAL_SHRINE);
            // getOrCreateTagBuilder(NEEDS_IRON_TOOL);
            getOrCreateTagBuilder(NEEDS_STONE_TOOL)
                    .add(ModBlocks.MAKAITE_ORE, ModBlocks.RAW_MAKAITE_BLOCK, ModBlocks.MAKAITE_BLOCK);
            getOrCreateTagBuilder(BEACON_BASE_BLOCKS)
                    .add(ModBlocks.DRAGON_GEM_BLOCK, ModBlocks.MAKAITE_BLOCK, ModBlocks.HIHIIROKANE_BLOCK);

            // minecraft/tags/blocks/mineable
            // fabric/tags/blocks/mineable
            getOrCreateTagBuilder(AXE_MINEABLE)
                    .add(ModBlocks.ONBASHIRA, ModBlocks.ONBASHIRA_PILLAR, ModBlocks.DANMAKU_CRAFTING_TABLE, ModBlocks.MYSTERIOUS_SEAL);
            getOrCreateTagBuilder(PICKAXE_MINEABLE)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .addTag(ModTags.Blocks.HIHIIROKANE_ORES)
                    .add(ModBlocks.DRAGON_GEM_BLOCK, ModBlocks.MAKAITE_ORE, ModBlocks.RAW_MAKAITE_BLOCK, ModBlocks.MAKAITE_BLOCK,
                            ModBlocks.RITUAL_SHRINE, ModBlocks.HIHIIROKANE_CHUNK_BLOCK, ModBlocks.HIHIIROKANE_BLOCK);
            getOrCreateTagBuilder(FabricMineableTags.SWORD_MINEABLE)
                    .add(ModBlocks.MYSTERIOUS_SEAL);

            // Modded block tags
            getOrCreateTagBuilder(ModTags.Blocks.OBSIDIAN_BLOCKS)
                    .add(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN);
            getOrCreateTagBuilder(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.DRAGON_GEM_ORE, ModBlocks.DEEPSLATE_DRAGON_GEM_ORE, ModBlocks.END_STONE_DRAGON_GEM_ORE);
            getOrCreateTagBuilder(ConventionalBlockTags.ORES)
                    .addTag(ModTags.Blocks.DRAGON_GEM_ORES)
                    .add(ModBlocks.MAKAITE_ORE)
                    .addTag(ModTags.Blocks.HIHIIROKANE_ORES);
            getOrCreateTagBuilder(ModTags.Blocks.FAIRIES_SPAWNABLE_ON)
                    .forceAddTag(FOXES_SPAWNABLE_ON)
                    .forceAddTag(ICE)
                    .add(Blocks.MUD);
            getOrCreateTagBuilder(ModTags.Blocks.HIHIIROKANE_ORES)
                    .add(ModBlocks.HIHIIROKANE_ORE, ModBlocks.DEEPSLATE_HIHIIROKANE_ORE);
            getOrCreateTagBuilder(ConventionalBlockTags.VILLAGER_JOB_SITES)
                    .add(ModBlocks.DANMAKU_CRAFTING_TABLE);
        }
    }

    public static class ItemTags extends FabricTagProvider.ItemTagProvider {

        public ItemTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, BlockTagProvider blockTagProvider) {
            super(output, completableFuture, blockTagProvider);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {

            // minecraft/tags/items
            getOrCreateTagBuilder(AXES)
                    .add(ModItems.MAKAITE_AXE, ModItems.HIHIIROKANE_AXE);
            getOrCreateTagBuilder(HOES)
                    .add(ModItems.MAKAITE_HOE, ModItems.HIHIIROKANE_HOE);
            getOrCreateTagBuilder(PICKAXES)
                    .add(ModItems.MAKAITE_PICKAXE, ModItems.HIHIIROKANE_PICKAXE);
            getOrCreateTagBuilder(SHOVELS)
                    .add(ModItems.MAKAITE_SHOVEL, ModItems.HIHIIROKANE_SHOVEL);
            getOrCreateTagBuilder(SWORDS)
                    .add(ModItems.MAKAITE_SWORD, ModItems.HISOU_SWORD, ModItems.DEATH_SCYTHE, ModItems.ICICLE_SWORD,
                            ModItems.HIHIIROKANE_SWORD);
            getOrCreateTagBuilder(TOOLS)
                    .addTag(ConventionalItemTags.SPEARS)
                    .addTag(ModTags.Items.HAMMERS);
            getOrCreateTagBuilder(TRIMMABLE_ARMOR)
                    .add(ModItems.MAKAITE_HELMET, ModItems.MAKAITE_CHESTPLATE, ModItems.MAKAITE_LEGGINGS, ModItems.MAKAITE_BOOTS,
                            ModItems.HIHIIROKANE_HELMET, ModItems.HIHIIROKANE_CHESTPLATE, ModItems.HIHIIROKANE_LEGGINGS, ModItems.HIHIIROKANE_BOOTS);
            getOrCreateTagBuilder(BEACON_PAYMENT_ITEMS)
                    .add(ModItems.DRAGON_GEM, ModItems.MAKAITE_INGOT, ModItems.HIHIIROKANE_INGOT);
            getOrCreateTagBuilder(MUSIC_DISCS)
                    .add(ModItems.MUSIC_DISC_FAIRY_PLAYGROUND, ModItems.MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN);
            getOrCreateTagBuilder(FISHES)
                    .add(ModItems.LAMPREY, ModItems.COOKED_LAMPREY);

            // Block tags to item tags
            copy(ModTags.Blocks.OBSIDIAN_BLOCKS, ModTags.Items.OBSIDIAN_BLOCKS);
            copy(ModTags.Blocks.DRAGON_GEM_ORES, ModTags.Items.DRAGON_GEM_ORES);
            copy(ConventionalBlockTags.ORES, ConventionalItemTags.ORES);
            copy(ModTags.Blocks.HIHIIROKANE_ORES, ModTags.Items.HIHIIROKANE_ORES);
            copy(ConventionalBlockTags.VILLAGER_JOB_SITES, ConventionalItemTags.VILLAGER_JOB_SITES);

            // Modded item tags
            getOrCreateTagBuilder(ConventionalItemTags.SPEARS)
                    .add(ModItems.NUE_TRIDENT);
            getOrCreateTagBuilder(ModTags.Items.HAMMERS)
                    .add(ModItems.MOCHI_MALLET, ModItems.MIRACLE_MALLET, ModItems.FOLDING_CHAIR);
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
                    .add(ModItems.TIME_ORB, ModItems.ENCHANTED_ICE);
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
            getOrCreateTagBuilder(ModTags.Items.RAW_MEAT)
                    .add(ModItems.LAMPREY);
            getOrCreateTagBuilder(ModTags.Items.COOKED_MEAT)
                    .add(ModItems.COOKED_LAMPREY);
            getOrCreateTagBuilder(ConventionalItemTags.RAW_ORES)
                    .add(ModItems.RAW_MAKAITE, ModItems.HIHIIROKANE_CHUNK);
            getOrCreateTagBuilder(ConventionalItemTags.GEMS)
                    .add(ModItems.DRAGON_GEM);
            getOrCreateTagBuilder(ConventionalItemTags.INGOTS)
                    .add(ModItems.MAKAITE_INGOT, ModItems.HIHIIROKANE_INGOT);
        }
    }

    public static class EntityTypeTags extends FabricTagProvider.EntityTypeTagProvider {

        public EntityTypeTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
            super(output, completableFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {

            // minecraft/tags/entity_type
            getOrCreateTagBuilder(FALL_DAMAGE_IMMUNE)
                    .addTag(ModTags.EntityTypes.FAIRIES);
            getOrCreateTagBuilder(FREEZE_IMMUNE_ENTITY_TYPES)
                    .add(ModEntities.ICE_FAIRY);
            getOrCreateTagBuilder(POWDER_SNOW_WALKABLE_MOBS)
                    .add(ModEntities.ICE_FAIRY);

            // Modded entity tags
            getOrCreateTagBuilder(ModTags.EntityTypes.DANMAKU)
                    .add(ModEntities.CIRCLE_BULLET, ModEntities.BUBBLE_BULLET, ModEntities.AMULET_BULLET, ModEntities.STAR_BULLET,
                            ModEntities.KUNAI_BULLET, ModEntities.PELLET_BULLET);
            getOrCreateTagBuilder(ModTags.EntityTypes.FAIRIES)
                    .add(ModEntities.FAIRY, ModEntities.SUNFLOWER_FAIRY, ModEntities.ICE_FAIRY);
            getOrCreateTagBuilder(ModTags.EntityTypes.FREEZING_DANMAKU_CAPABLE)
                    .add(ModEntities.ICE_FAIRY);
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

    public static class StatusEffectTags extends FabricTagProvider<StatusEffect> {

        public StatusEffectTags(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.STATUS_EFFECT, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup arg) {
            getOrCreateTagBuilder(ModTags.StatusEffects.UNREMOVABLE)
                    .add(ModEffects.ELIXIR_FATIGUE);
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
