/*
 * Copyright (c) 2022 Maxmani and contributors.
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
        public static final TagKey<Item> RAW_ORES = createCommonTag("raw_ores");

        @SuppressWarnings("SameParameterValue")
        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(ArcadianDream.MOD_ID, name));
        }

        private static TagKey<Item> createCommonTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier("c", name));
        }
    }
}
