/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> OBSIDIAN_BLOCKS = createCommonTag("obsidian_blocks");
        public static final TagKey<Block> DRAGON_GEM_ORES = createTag("dragon_gem_ores");

        @SuppressWarnings("SameParameterValue")
        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(ArcadianDream.MOD_ID, name));
        }

        @SuppressWarnings("SameParameterValue")
        private static TagKey<Block> createCommonTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, new Identifier("c", name));
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

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(ArcadianDream.MOD_ID, name));
        }

        @SuppressWarnings("SameParameterValue")
        private static TagKey<Item> createCommonTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier("c", name));
        }
    }
}
