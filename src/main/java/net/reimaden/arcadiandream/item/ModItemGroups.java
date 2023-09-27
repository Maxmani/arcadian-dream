/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;

import java.util.Collections;

public class ModItemGroups {

    @SuppressWarnings("unused")
    public static final ItemGroup ITEMS = Registry.register(Registries.ITEM_GROUP, new Identifier(ArcadianDream.MOD_ID, "items"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + ArcadianDream.MOD_ID + ".items")).icon(() -> new ItemStack(ModItems.POWER_ITEM)).entries((displayContext, entries) -> {
                entries.add(ModItems.POWER_ITEM);
                entries.add(ModItems.BIG_POWER_ITEM);
                entries.add(ModItems.POINT_ITEM);
                entries.add(ModItems.MAX_POINT_ITEM);
                entries.add(ModItems.BOMB_ITEM);
                entries.add(ModItems.LIFE_FRAGMENT);
                entries.add(ModItems.EXTEND_ITEM);
                entries.add(ModItems.STAR_ITEM);
                entries.add(ModItems.FAITH_ITEM);
                entries.add(ModItems.TIME_ORB);
                entries.add(ModItems.DRAGON_GEM);
                entries.add(ModItems.RAW_MAKAITE);
                entries.add(ModItems.MAKAITE_INGOT);
                entries.add(ModItems.HIHIIROKANE_CHUNK);
                entries.add(ModItems.HIHIIROKANE_INGOT);
                entries.add(ModItems.ENCHANTED_ICE);
                entries.add(ModItems.WALL_PASSING_CHISEL);
                entries.add(ModItems.IBUKI_GOURD);
                entries.add(ModItems.HEALING_CHARM);
                entries.add(ModItems.HOURAI_ELIXIR);
                entries.add(ModItems.MAGATAMA_NECKLACE);
                entries.add(ModItems.GHASTLY_LANTERN);
                entries.add(ModItems.FAIRY_CHARM);
                entries.add(ModItems.HEAVENLY_PEACH);
                entries.add(ModItems.LAMPREY);
                entries.add(ModItems.COOKED_LAMPREY);
                entries.add(ModItems.ORDINARY_HAT);
                entries.add(ModItems.MAKAITE_HELMET);
                entries.add(ModItems.MAKAITE_CHESTPLATE);
                entries.add(ModItems.MAKAITE_LEGGINGS);
                entries.add(ModItems.MAKAITE_BOOTS);
                entries.add(ModItems.MAKAITE_SWORD);
                entries.add(ModItems.MAKAITE_PICKAXE);
                entries.add(ModItems.MAKAITE_AXE);
                entries.add(ModItems.MAKAITE_SHOVEL);
                entries.add(ModItems.MAKAITE_HOE);
                entries.add(ModItems.HIHIIROKANE_HELMET);
                entries.add(ModItems.HIHIIROKANE_CHESTPLATE);
                entries.add(ModItems.HIHIIROKANE_LEGGINGS);
                entries.add(ModItems.HIHIIROKANE_BOOTS);
                entries.add(ModItems.HIHIIROKANE_SWORD);
                entries.add(ModItems.HIHIIROKANE_PICKAXE);
                entries.add(ModItems.HIHIIROKANE_AXE);
                entries.add(ModItems.HIHIIROKANE_SHOVEL);
                entries.add(ModItems.HIHIIROKANE_HOE);
                entries.add(ModItems.NUE_TRIDENT);
                entries.add(ModItems.HISOU_SWORD);
                entries.add(ModItems.MOCHI_MALLET);
                entries.add(ModItems.DEATH_SCYTHE);
                entries.add(ModItems.MIRACLE_MALLET);
                entries.add(ModItems.FOLDING_CHAIR);
                entries.add(ModItems.ICICLE_SWORD);
                entries.add(ModItems.HIHIIROKANE_UPGRADE_SMITHING_TEMPLATE);
                entries.add(ModItems.SPREAD_PATTERN_TEMPLATE);
                entries.add(ModItems.RAY_PATTERN_TEMPLATE);
                entries.add(ModItems.RING_PATTERN_TEMPLATE);
                entries.add(ModItems.ARC_PATTERN_TEMPLATE);
                entries.add(ModItems.DOUBLE_PATTERN_TEMPLATE);
                entries.add(ModItems.TRIPLE_PATTERN_TEMPLATE);
                entries.add(ModItems.MUSIC_DISC_FAIRY_PLAYGROUND);
                entries.add(ModItems.MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN);
            }).build());
    @SuppressWarnings("unused")
    public static final ItemGroup BLOCKS = Registry.register(Registries.ITEM_GROUP, new Identifier(ArcadianDream.MOD_ID, "blocks"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + ArcadianDream.MOD_ID + ".blocks")).icon(() -> new ItemStack(ModBlocks.ONBASHIRA)).entries((displayContext, entries) -> {
                entries.add(ModBlocks.ONBASHIRA);
                entries.add(ModBlocks.ONBASHIRA_PILLAR);
                entries.add(ModBlocks.RITUAL_SHRINE);
                entries.add(ModBlocks.DANMAKU_CRAFTING_TABLE);
                entries.add(ModBlocks.MYSTERIOUS_SEAL);
                entries.add(ModBlocks.DRAGON_GEM_ORE);
                entries.add(ModBlocks.DEEPSLATE_DRAGON_GEM_ORE);
                entries.add(ModBlocks.END_STONE_DRAGON_GEM_ORE);
                entries.add(ModBlocks.MAKAITE_ORE);
                entries.add(ModBlocks.RAW_MAKAITE_BLOCK);
                entries.add(ModBlocks.DRAGON_GEM_BLOCK);
                entries.add(ModBlocks.MAKAITE_BLOCK);
                entries.add(ModBlocks.HIHIIROKANE_ORE);
                entries.add(ModBlocks.DEEPSLATE_HIHIIROKANE_ORE);
                entries.add(ModBlocks.HIHIIROKANE_CHUNK_BLOCK);
                entries.add(ModBlocks.HIHIIROKANE_BLOCK);
            }).build());
    @SuppressWarnings("unused")
    public static final ItemGroup DANMAKU = Registry.register(Registries.ITEM_GROUP, new Identifier(ArcadianDream.MOD_ID, "danmaku"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup." + ArcadianDream.MOD_ID + ".danmaku")).icon(() -> new ItemStack(ModItems.CIRCLE_SHOT)).entries((displayContext, entries) -> {
                entries.add(ModItems.BOMB_ITEM);
                entries.add(ModBlocks.DANMAKU_CRAFTING_TABLE);
                entries.add(ModItems.CIRCLE_BULLET_CORE);
                entries.add(ModItems.BUBBLE_BULLET_CORE);
                entries.add(ModItems.AMULET_BULLET_CORE);
                entries.add(ModItems.STAR_BULLET_CORE);
                entries.add(ModItems.KUNAI_BULLET_CORE);
                entries.add(ModItems.PELLET_BULLET_CORE);
                entries.add(ModItems.CIRCLE_SHOT);
                entries.add(ModItems.BUBBLE_SHOT);
                entries.add(ModItems.AMULET_SHOT);
                entries.add(ModItems.STAR_SHOT);
                entries.add(ModItems.KUNAI_SHOT);
                entries.add(ModItems.PELLET_SHOT);
                entries.add(ModItems.SPREAD_PATTERN);
                entries.add(ModItems.RAY_PATTERN);
                entries.add(ModItems.RING_PATTERN);
                entries.add(ModItems.ARC_PATTERN);
                entries.add(ModItems.DOUBLE_PATTERN);
                entries.add(ModItems.TRIPLE_PATTERN);
            }).build());

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering item groups for " + ArcadianDream.MOD_ID);

        addToExistingGroup();
    }

    private static void addToExistingGroup() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.add(ModItems.FAIRY_SPAWN_EGG);
            entries.add(ModItems.SUNFLOWER_FAIRY_SPAWN_EGG);
            entries.add(ModItems.ICE_FAIRY_SPAWN_EGG);
        });
        addSuspiciousStews();
    }

    private static void addSuspiciousStews() {
        ItemStack stack = new ItemStack(Items.SUSPICIOUS_STEW);
        SuspiciousStewItem.addEffectToStew(stack, StatusEffects.LEVITATION, 200);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            //noinspection UnstableApiUsage
            entries.addAfter(Items.SUSPICIOUS_STEW, Collections.singletonList(stack), ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
        });
    }
}
