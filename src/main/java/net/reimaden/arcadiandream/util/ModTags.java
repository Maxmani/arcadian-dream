/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.ModItems;

public class ModTags {

    public static final ImmutableList<Item> BULLET_CORES = ImmutableList.of(
            ModItems.CIRCLE_BULLET_CORE, ModItems.BUBBLE_BULLET_CORE, ModItems.AMULET_BULLET_CORE, ModItems.STAR_BULLET_CORE,
            ModItems.KUNAI_BULLET_CORE, ModItems.PELLET_BULLET_CORE
    );
    public static final ImmutableList<Item> SHOTS = ImmutableList.of(
            ModItems.CIRCLE_SHOT, ModItems.BUBBLE_SHOT, ModItems.AMULET_SHOT, ModItems.STAR_SHOT,
            ModItems.KUNAI_SHOT, ModItems.PELLET_SHOT
    );

    public static class Blocks {

        public static final TagKey<Block> OBSIDIAN_BLOCKS = createCommonTag("obsidian_blocks");
        public static final TagKey<Block> DRAGON_GEM_ORES = createTag("dragon_gem_ores");
        public static final TagKey<Block> FAIRIES_SPAWNABLE_ON = createTag("fairies_spawnable_on");
        public static final TagKey<Block> HIHIIROKANE_ORES = createTag("hihiirokane_ores");
        public static final TagKey<Block> NEEDS_TOOL_LEVEL_4 = createFabricTag("needs_tool_level_4");

        private static TagKey<Block> createTag(String name) {
            return create(ArcadianDream.MOD_ID, name);
        }

        @SuppressWarnings("SameParameterValue")
        private static TagKey<Block> createCommonTag(String name) {
            return create("c", name);
        }

        @SuppressWarnings("SameParameterValue")
        private static TagKey<Block> createFabricTag(String name) {
            return create("fabric", name);
        }

        private static TagKey<Block> create(String namespace, String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(namespace, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> OBSIDIAN_BLOCKS = createCommonTag("obsidian_blocks");
        public static final TagKey<Item> DRAGON_GEM_ORES = createTag("dragon_gem_ores");
        public static final TagKey<Item> ITEMS = createTag("items");
        public static final TagKey<Item> BULLET_CORES = createTag("bullet_cores");
        public static final TagKey<Item> SHOTS = createTag("shots");
        public static final TagKey<Item> DANMAKU_REPAIR_ITEMS = createTag("danmaku_repair_items");
        public static final TagKey<Item> DANMAKU_MODIFIERS = createTag("danmaku_modifiers");
        public static final TagKey<Item> DANMAKU_POWER_MODIFIERS = createTag("danmaku_power_modifiers");
        public static final TagKey<Item> DANMAKU_DENSITY_MODIFIERS = createTag("danmaku_density_modifiers");
        public static final TagKey<Item> DANMAKU_SPEED_MODIFIERS = createTag("danmaku_speed_modifiers");
        public static final TagKey<Item> DANMAKU_DURATION_MODIFIERS = createTag("danmaku_duration_modifiers");
        public static final TagKey<Item> HAMMERS = createCommonTag("hammers");
        public static final TagKey<Item> UNDEAD_PARTS = createCommonTag("undead_parts");
        public static final TagKey<Item> RAW_MEAT = createCommonTag("raw_meat");
        public static final TagKey<Item> COOKED_MEAT = createCommonTag("cooked_meat");
        public static final TagKey<Item> HIHIIROKANE_ORES = createTag("hihiirokane_ores");

        private static TagKey<Item> createTag(String name) {
            return create(ArcadianDream.MOD_ID, name);
        }

        private static TagKey<Item> createCommonTag(String name) {
            return create("c", name);
        }

        private static TagKey<Item> create(String namespace, String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(namespace, name));
        }
    }

    public static class EntityTypes {

        public static final TagKey<EntityType<?>> DANMAKU = createTag("danmaku");
        public static final TagKey<EntityType<?>> FAIRIES = createTag("fairies");
        public static final TagKey<EntityType<?>> FREEZING_DANMAKU_CAPABLE = createTag("freezing_danmaku_capable");

        private static TagKey<EntityType<?>> createTag(String name) {
            return create(ArcadianDream.MOD_ID, name);
        }

        @SuppressWarnings("unused")
        private static TagKey<EntityType<?>> createCommonTag(String name) {
            return create("c", name);
        }

        private static TagKey<EntityType<?>> create(String namespace, String name) {
            return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(namespace, name));
        }
    }

    public static class DamageTypes {

        public static final TagKey<DamageType> IS_DANMAKU = createTag("is_danmaku");

        @SuppressWarnings("SameParameterValue")
        private static TagKey<DamageType> createTag(String name) {
            return create(ArcadianDream.MOD_ID, name);
        }

        @SuppressWarnings("unused")
        private static TagKey<DamageType> createCommonTag(String name) {
            return create("c", name);
        }

        private static TagKey<DamageType> create(String namespace, String name) {
            return TagKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier(namespace, name));
        }
    }

    public static class StatusEffects {

        public static final TagKey<StatusEffect> UNREMOVABLE = createTag("unremovable");

        @SuppressWarnings("SameParameterValue")
        private static TagKey<StatusEffect> createTag(String name) {
            return create(ArcadianDream.MOD_ID, name);
        }

        @SuppressWarnings("unused")
        private static TagKey<StatusEffect> createCommonTag(String name) {
            return create("c", name);
        }

        private static TagKey<StatusEffect> create(String namespace, String name) {
            return TagKey.of(RegistryKeys.STATUS_EFFECT, new Identifier(namespace, name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> ABANDONED_SHRINE_HAS_STRUCTURE = createTag("has_structure/abandoned_shrine");

        @SuppressWarnings("SameParameterValue")
        private static TagKey<Biome> createTag(String name) {
            return create(ArcadianDream.MOD_ID, name);
        }

        @SuppressWarnings("unused")
        private static TagKey<Biome> createCommonTag(String name) {
            return create("c", name);
        }

        private static TagKey<Biome> create(String namespace, String name) {
            return TagKey.of(RegistryKeys.BIOME, new Identifier(namespace, name));
        }
    }
}
