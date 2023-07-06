/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.custom.armor.OrdinaryHatItem;
import net.reimaden.arcadiandream.item.custom.consumables.GhastlyLanternItem;
import net.reimaden.arcadiandream.item.custom.consumables.HealingCharmItem;
import net.reimaden.arcadiandream.item.custom.consumables.HouraiElixirItem;
import net.reimaden.arcadiandream.item.custom.danmaku.*;
import net.reimaden.arcadiandream.item.custom.misc.*;
import net.reimaden.arcadiandream.item.custom.tools.*;
import net.reimaden.arcadiandream.sound.ModSounds;

import java.util.Collections;

public class ModItems {

    // Items
    public static final Item POWER_ITEM = registerItem("power_item", new Item(new FabricItemSettings()));
    public static final Item BIG_POWER_ITEM = registerItem("big_power_item", new Item(new FabricItemSettings()));
    public static final Item POINT_ITEM = registerItem("point_item", new Item(new FabricItemSettings()));
    public static final Item MAX_POINT_ITEM = registerItem("max_point_item", new Item(new FabricItemSettings()));
    public static final Item BOMB_ITEM = registerItem("bomb_item", new BombItem(new FabricItemSettings().maxCount(8).rarity(Rarity.UNCOMMON)));
    public static final Item LIFE_FRAGMENT = registerItem("life_fragment", new Item(new FabricItemSettings()));
    public static final Item EXTEND_ITEM = registerItem("extend_item", new ExtendItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item STAR_ITEM = registerItem("star_item", new Item(new FabricItemSettings()));
    public static final Item FAITH_ITEM = registerItem("faith_item", new Item(new FabricItemSettings()));
    public static final Item TIME_ORB = registerItem("time_orb", new Item(new FabricItemSettings().rarity(Rarity.RARE)));

    public static final Item DRAGON_GEM = registerItem("dragon_gem", new Item(new FabricItemSettings()));
    public static final Item RAW_MAKAITE = registerItem("raw_makaite", new Item(new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_INGOT = registerItem("makaite_ingot", new Item(new FabricItemSettings().fireproof()));
    public static final Item ENCHANTED_ICE = registerItem("enchanted_ice", new EnchantedIceItem(new FabricItemSettings().rarity(Rarity.UNCOMMON)));

    public static final Item WALL_PASSING_CHISEL = registerItem("wall_passing_chisel", new WallPassingChiselItem(new FabricItemSettings().maxDamage(100)));
    public static final Item IBUKI_GOURD = registerItem("ibuki_gourd", new IbukiGourdItem(new FabricItemSettings().maxCount(1)));
    public static final Item HEALING_CHARM = registerItem("healing_charm", new HealingCharmItem(new FabricItemSettings().maxCount(16)));
    public static final Item HOURAI_ELIXIR = registerItem("hourai_elixir", new HouraiElixirItem(new FabricItemSettings().maxCount(1).maxDamage(3).rarity(Rarity.EPIC)));
    public static final Item MAGATAMA_NECKLACE = registerItem("magatama_necklace", new MagatamaNecklaceItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item GHASTLY_LANTERN = registerItem("ghastly_lantern", new GhastlyLanternItem(new FabricItemSettings().maxCount(1)));

    public static final Item SPREAD_PATTERN_TEMPLATE = registerItem("spread_pattern_template", new PatternTemplateItem(new FabricItemSettings().maxCount(16)));
    public static final Item RAY_PATTERN_TEMPLATE = registerItem("ray_pattern_template", new PatternTemplateItem(new FabricItemSettings().maxCount(16)));
    public static final Item RING_PATTERN_TEMPLATE = registerItem("ring_pattern_template", new PatternTemplateItem(new FabricItemSettings().maxCount(16)));
    public static final Item ARC_PATTERN_TEMPLATE = registerItem("arc_pattern_template", new PatternTemplateItem(new FabricItemSettings().maxCount(16)));
    public static final Item DOUBLE_PATTERN_TEMPLATE = registerItem("double_pattern_template", new PatternTemplateItem(new FabricItemSettings().maxCount(16)));
    public static final Item TRIPLE_PATTERN_TEMPLATE = registerItem("triple_pattern_template", new PatternTemplateItem(new FabricItemSettings().maxCount(16)));
    public static final Item SPREAD_PATTERN = registerItem("spread_pattern", new PatternItem(new FabricItemSettings().maxCount(16)));
    public static final Item RAY_PATTERN = registerItem("ray_pattern", new PatternItem(new FabricItemSettings().maxCount(16)));
    public static final Item RING_PATTERN = registerItem("ring_pattern", new PatternItem(new FabricItemSettings().maxCount(16)));
    public static final Item ARC_PATTERN = registerItem("arc_pattern", new PatternItem(new FabricItemSettings().maxCount(16)));
    public static final Item DOUBLE_PATTERN = registerItem("double_pattern", new PatternItem(new FabricItemSettings().maxCount(16)));
    public static final Item TRIPLE_PATTERN = registerItem("triple_pattern", new PatternItem(new FabricItemSettings().maxCount(16)));

    // Food
    public static final Item HEAVENLY_PEACH = registerItem("heavenly_peach", new Item(new FabricItemSettings().maxCount(16)
            .food(ModFoodComponents.HEAVENLY_PEACH)));
    public static final Item LAMPREY = registerItem("lamprey", new Item(new FabricItemSettings()
            .food(ModFoodComponents.LAMPREY)));
    public static final Item COOKED_LAMPREY = registerItem("cooked_lamprey", new Item(new FabricItemSettings()
            .food(ModFoodComponents.COOKED_LAMPREY)));

    // Armor
    public static final Item ORDINARY_HAT = registerItem("ordinary_hat", new OrdinaryHatItem(ModArmorMaterials.ORDINARY, ArmorItem.Type.HELMET,
            new FabricItemSettings()));

    public static final Item MAKAITE_HELMET = registerItem("makaite_helmet", new ArmorItem(ModArmorMaterials.MAKAITE, ArmorItem.Type.HELMET,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_CHESTPLATE = registerItem("makaite_chestplate", new ArmorItem(ModArmorMaterials.MAKAITE, ArmorItem.Type.CHESTPLATE,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_LEGGINGS = registerItem("makaite_leggings", new ArmorItem(ModArmorMaterials.MAKAITE, ArmorItem.Type.LEGGINGS,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_BOOTS = registerItem("makaite_boots", new ArmorItem(ModArmorMaterials.MAKAITE, ArmorItem.Type.BOOTS,
            new FabricItemSettings().fireproof()));

    // Tools
    public static final Item MAKAITE_SWORD = registerItem("makaite_sword", new SwordItem(ModToolMaterials.MAKAITE, 3, -2.4f,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_PICKAXE = registerItem("makaite_pickaxe", new PickaxeItem(ModToolMaterials.MAKAITE, 1, -2.8f,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_AXE = registerItem("makaite_axe", new AxeItem(ModToolMaterials.MAKAITE, 6, -3.1f,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_SHOVEL = registerItem("makaite_shovel", new ShovelItem(ModToolMaterials.MAKAITE, 1.5f, -3.0f,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_HOE = registerItem("makaite_hoe", new HoeItem(ModToolMaterials.MAKAITE, -2, -1.0f,
            new FabricItemSettings().fireproof()));

    // Weapons
    public static final Item NUE_TRIDENT = registerItem("nue_trident", new NueTridentItem(ToolMaterials.NETHERITE, 5, -2.9f,
            new FabricItemSettings().fireproof()));
    public static final Item HISOU_SWORD = registerItem("hisou_sword", new SwordItem(ModToolMaterials.HISOU, 3, -2.0f,
            new FabricItemSettings()));
    public static final Item MOCHI_MALLET = registerItem("mochi_mallet", new MochiMalletItem(ModToolMaterials.MOCHI_MALLET, 3, -3.2f,
            new FabricItemSettings()));
    public static final Item DEATH_SCYTHE = registerItem("death_scythe", new DeathScytheItem(ToolMaterials.IRON, 4, -3.0f,
            new FabricItemSettings()));
    public static final Item ROUKANKEN = registerItem("roukanken", new RoukankenItem(ToolMaterials.NETHERITE, 10, -2.0f,
            new FabricItemSettings().rarity(Rarity.EPIC)));
    public static final Item MIRACLE_MALLET = registerItem("miracle_mallet", new MiracleMalletItem(ToolMaterials.DIAMOND, 3, -3.2f,
            new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item FOLDING_CHAIR = registerItem("folding_chair", new FoldingChairItem(ToolMaterials.IRON, 1, -3.1f,
            new FabricItemSettings().maxDamage(ToolMaterials.IRON.getDurability() - 50)));
    public static final Item ICICLE_SWORD = registerItem("icicle_sword", new IcicleSwordItem(ModToolMaterials.ENCHANTED_ICE, 3, -2.4f,
            new FabricItemSettings().rarity(Rarity.UNCOMMON)));

    // Danmaku
    public static final Item CIRCLE_BULLET_CORE = registerItem("circle_bullet_core", new BulletCoreItem(new FabricItemSettings()));
    public static final Item BUBBLE_BULLET_CORE = registerItem("bubble_bullet_core", new BulletCoreItem(new FabricItemSettings()));
    public static final Item AMULET_BULLET_CORE = registerItem("amulet_bullet_core", new BulletCoreItem(new FabricItemSettings()));
    public static final Item STAR_BULLET_CORE = registerItem("star_bullet_core", new BulletCoreItem(new FabricItemSettings()));
    public static final Item KUNAI_BULLET_CORE = registerItem("kunai_bullet_core", new BulletCoreItem(new FabricItemSettings()));
    public static final Item PELLET_BULLET_CORE = registerItem("pellet_bullet_core", new BulletCoreItem(new FabricItemSettings()));

    public static final Item CIRCLE_SHOT = registerItem("circle_shot", new CircleShotItem(new FabricItemSettings().maxDamage(100),
            2, 0.8f, 100, 2, 0.0f, 0.0f, "spread", 1,
            12, 2.0f, 200, 100, 90, 25));
    public static final Item BUBBLE_SHOT = registerItem("bubble_shot", new BubbleShotItem(new FabricItemSettings().maxDamage(250),
            4, 0.4f, 100, 10, 0.0f, 0.0f, "spread", 1,
            15, 0.8f, 200, 200, 90, 12));
    public static final Item AMULET_SHOT = registerItem("amulet_shot", new AmuletShotItem(new FabricItemSettings().maxDamage(100),
            1, 0.8f, 100, 5, 0.0f, 0.0f, "spread", 1,
            10, 1.8f, 200, 150, 90, 20));
    public static final Item STAR_SHOT = registerItem("star_shot", new StarShotItem(new FabricItemSettings().maxDamage(150),
            1, 0.65f, 100, 5, 0.0f, 0.0f, "spread", 1,
            10, 2.0f, 200, 120, 90, 25));
    public static final Item KUNAI_SHOT = registerItem("kunai_shot", new KunaiShotItem(new FabricItemSettings().maxDamage(125),
            1, 0.8f, 100, 5, 0.0f, 0.0f, "spread", 1,
            8, 1.8f, 200, 100, 90, 18));
    public static final Item PELLET_SHOT = registerItem("pellet_shot", new PelletShotItem(new FabricItemSettings().maxDamage(100),
            1, 1.2f, 80, 2, 0.0f, 0.0f, "spread", 1,
            6, 2.0f, 200, 100, 90, 40));

    // Music Discs
    public static final Item MUSIC_DISC_FAIRY_PLAYGROUND = registerItem("music_disc_fairy_playground", new MusicDiscItem(10, ModSounds.MUSIC_DISC_FAIRY_PLAYGROUND,
            new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 180));
    public static final Item MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN = registerItem("music_disc_the_shrine_long_forgotten", new MusicDiscItem(14, ModSounds.MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN,
            new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 134));

    // Spawn Eggs
    public static final Item FAIRY_SPAWN_EGG = registerItem("fairy_spawn_egg",
            new SpawnEggItem(ModEntities.FAIRY, 0x004BCC, 0xF6FF5F,
                    new FabricItemSettings()));
    public static final Item SUNFLOWER_FAIRY_SPAWN_EGG = registerItem("sunflower_fairy_spawn_egg",
            new SpawnEggItem(ModEntities.SUNFLOWER_FAIRY, 0xEFEFEF, 0xFBD405,
                    new FabricItemSettings()));
    public static final Item ICE_FAIRY_SPAWN_EGG = registerItem("ice_fairy_spawn_egg",
            new SpawnEggItem(ModEntities.ICE_FAIRY, 0xABF0FF, 0xC6FBFF,
                    new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ArcadianDream.MOD_ID, name), item);
    }

    // Block Items
    public static final Item ONBASHIRA = registerBlockItem("onbashira", new BlockItem(ModBlocks.ONBASHIRA,
            new FabricItemSettings()));
    public static final Item ONBASHIRA_PILLAR = registerBlockItem("onbashira_pillar", new BlockItem(ModBlocks.ONBASHIRA_PILLAR,
            new FabricItemSettings()));
    public static final Item RITUAL_SHRINE = registerBlockItem("ritual_shrine", new BlockItem(ModBlocks.RITUAL_SHRINE,
            new FabricItemSettings()));
    public static final Item DANMAKU_CRAFTING_TABLE = registerBlockItem("danmaku_crafting_table", new BlockItem(ModBlocks.DANMAKU_CRAFTING_TABLE,
            new FabricItemSettings()));
    public static final Item MYSTERIOUS_SEAL = registerBlockItem("mysterious_seal", new BlockItem(ModBlocks.MYSTERIOUS_SEAL,
            new FabricItemSettings().rarity(Rarity.RARE)));

    public static final Item DRAGON_GEM_ORE = registerBlockItem("dragon_gem_ore", new BlockItem(ModBlocks.DRAGON_GEM_ORE,
            new FabricItemSettings()));
    public static final Item DEEPSLATE_DRAGON_GEM_ORE = registerBlockItem("deepslate_dragon_gem_ore", new BlockItem(ModBlocks.DEEPSLATE_DRAGON_GEM_ORE,
            new FabricItemSettings()));
    public static final Item END_STONE_DRAGON_GEM_ORE = registerBlockItem("end_stone_dragon_gem_ore", new BlockItem(ModBlocks.END_STONE_DRAGON_GEM_ORE,
            new FabricItemSettings()));

    public static final Item MAKAITE_ORE = registerBlockItem("makaite_ore", new BlockItem(ModBlocks.MAKAITE_ORE,
            new FabricItemSettings().fireproof()));

    public static final Item RAW_MAKAITE_BLOCK = registerBlockItem("raw_makaite_block", new BlockItem(ModBlocks.RAW_MAKAITE_BLOCK,
            new FabricItemSettings().fireproof()));

    public static final Item DRAGON_GEM_BLOCK = registerBlockItem("dragon_gem_block", new BlockItem(ModBlocks.DRAGON_GEM_BLOCK,
            new FabricItemSettings()));

    public static final Item MAKAITE_BLOCK = registerBlockItem("makaite_block", new BlockItem(ModBlocks.MAKAITE_BLOCK,
            new FabricItemSettings().fireproof()));

    private static Item registerBlockItem(String name, BlockItem block) {
        return Registry.register(Registries.ITEM, new Identifier(ArcadianDream.MOD_ID, name), block);
    }

    // Mojang moment
    private static void addItemsToItemGroups() {
        addToItemGroup(ModItemGroups.ITEMS, POWER_ITEM);
        addToItemGroup(ModItemGroups.ITEMS, BIG_POWER_ITEM);
        addToItemGroup(ModItemGroups.ITEMS, POINT_ITEM);
        addToItemGroup(ModItemGroups.ITEMS, MAX_POINT_ITEM);
        addToItemGroup(ModItemGroups.ITEMS, BOMB_ITEM);
        addToItemGroup(ModItemGroups.ITEMS, LIFE_FRAGMENT);
        addToItemGroup(ModItemGroups.ITEMS, EXTEND_ITEM);
        addToItemGroup(ModItemGroups.ITEMS, STAR_ITEM);
        addToItemGroup(ModItemGroups.ITEMS, FAITH_ITEM);
        addToItemGroup(ModItemGroups.ITEMS, TIME_ORB);
        addToItemGroup(ModItemGroups.ITEMS, DRAGON_GEM);
        addToItemGroup(ModItemGroups.ITEMS, RAW_MAKAITE);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_INGOT);
        addToItemGroup(ModItemGroups.ITEMS, ENCHANTED_ICE);
        addToItemGroup(ModItemGroups.ITEMS, WALL_PASSING_CHISEL);
        addToItemGroup(ModItemGroups.ITEMS, IBUKI_GOURD);
        addToItemGroup(ModItemGroups.ITEMS, HEALING_CHARM);
        addToItemGroup(ModItemGroups.ITEMS, HOURAI_ELIXIR);
        addToItemGroup(ModItemGroups.ITEMS, MAGATAMA_NECKLACE);
        addToItemGroup(ModItemGroups.ITEMS, GHASTLY_LANTERN);
        addToItemGroup(ModItemGroups.ITEMS, HEAVENLY_PEACH);
        addToItemGroup(ModItemGroups.ITEMS, LAMPREY);
        addToItemGroup(ModItemGroups.ITEMS, COOKED_LAMPREY);
        addToItemGroup(ModItemGroups.ITEMS, ORDINARY_HAT);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_HELMET);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_CHESTPLATE);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_LEGGINGS);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_BOOTS);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_SWORD);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_PICKAXE);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_AXE);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_SHOVEL);
        addToItemGroup(ModItemGroups.ITEMS, MAKAITE_HOE);
        addToItemGroup(ModItemGroups.ITEMS, NUE_TRIDENT);
        addToItemGroup(ModItemGroups.ITEMS, HISOU_SWORD);
        addToItemGroup(ModItemGroups.ITEMS, MOCHI_MALLET);
        addToItemGroup(ModItemGroups.ITEMS, DEATH_SCYTHE);
        addToItemGroup(ModItemGroups.ITEMS, ROUKANKEN);
        addToItemGroup(ModItemGroups.ITEMS, MIRACLE_MALLET);
        addToItemGroup(ModItemGroups.ITEMS, FOLDING_CHAIR);
        addToItemGroup(ModItemGroups.ITEMS, ICICLE_SWORD);
        addToItemGroup(ModItemGroups.ITEMS, SPREAD_PATTERN_TEMPLATE);
        addToItemGroup(ModItemGroups.ITEMS, RAY_PATTERN_TEMPLATE);
        addToItemGroup(ModItemGroups.ITEMS, RING_PATTERN_TEMPLATE);
        addToItemGroup(ModItemGroups.ITEMS, ARC_PATTERN_TEMPLATE);
        addToItemGroup(ModItemGroups.ITEMS, DOUBLE_PATTERN_TEMPLATE);
        addToItemGroup(ModItemGroups.ITEMS, TRIPLE_PATTERN_TEMPLATE);
        addToItemGroup(ModItemGroups.ITEMS, MUSIC_DISC_FAIRY_PLAYGROUND);
        addToItemGroup(ModItemGroups.ITEMS, MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN);

        addToItemGroup(ModItemGroups.DANMAKU, BOMB_ITEM);
        addToItemGroup(ModItemGroups.DANMAKU, DANMAKU_CRAFTING_TABLE);
        addToItemGroup(ModItemGroups.DANMAKU, CIRCLE_BULLET_CORE);
        addToItemGroup(ModItemGroups.DANMAKU, BUBBLE_BULLET_CORE);
        addToItemGroup(ModItemGroups.DANMAKU, AMULET_BULLET_CORE);
        addToItemGroup(ModItemGroups.DANMAKU, STAR_BULLET_CORE);
        addToItemGroup(ModItemGroups.DANMAKU, KUNAI_BULLET_CORE);
        addToItemGroup(ModItemGroups.DANMAKU, PELLET_BULLET_CORE);
        addToItemGroup(ModItemGroups.DANMAKU, CIRCLE_SHOT);
        addToItemGroup(ModItemGroups.DANMAKU, BUBBLE_SHOT);
        addToItemGroup(ModItemGroups.DANMAKU, AMULET_SHOT);
        addToItemGroup(ModItemGroups.DANMAKU, STAR_SHOT);
        addToItemGroup(ModItemGroups.DANMAKU, KUNAI_SHOT);
        addToItemGroup(ModItemGroups.DANMAKU, PELLET_SHOT);
        addToItemGroup(ModItemGroups.DANMAKU, SPREAD_PATTERN);
        addToItemGroup(ModItemGroups.DANMAKU, RAY_PATTERN);
        addToItemGroup(ModItemGroups.DANMAKU, RING_PATTERN);
        addToItemGroup(ModItemGroups.DANMAKU, ARC_PATTERN);
        addToItemGroup(ModItemGroups.DANMAKU, DOUBLE_PATTERN);
        addToItemGroup(ModItemGroups.DANMAKU, TRIPLE_PATTERN);

        addToItemGroup(ModItemGroups.BLOCKS, ONBASHIRA);
        addToItemGroup(ModItemGroups.BLOCKS, ONBASHIRA_PILLAR);
        addToItemGroup(ModItemGroups.BLOCKS, RITUAL_SHRINE);
        addToItemGroup(ModItemGroups.BLOCKS, DANMAKU_CRAFTING_TABLE);
        addToItemGroup(ModItemGroups.BLOCKS, MYSTERIOUS_SEAL);
        addToItemGroup(ModItemGroups.BLOCKS, DRAGON_GEM_ORE);
        addToItemGroup(ModItemGroups.BLOCKS, DEEPSLATE_DRAGON_GEM_ORE);
        addToItemGroup(ModItemGroups.BLOCKS, END_STONE_DRAGON_GEM_ORE);
        addToItemGroup(ModItemGroups.BLOCKS, MAKAITE_ORE);
        addToItemGroup(ModItemGroups.BLOCKS, RAW_MAKAITE_BLOCK);
        addToItemGroup(ModItemGroups.BLOCKS, DRAGON_GEM_BLOCK);
        addToItemGroup(ModItemGroups.BLOCKS, MAKAITE_BLOCK);

        addToItemGroup(ItemGroups.SPAWN_EGGS, FAIRY_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, SUNFLOWER_FAIRY_SPAWN_EGG);
        addToItemGroup(ItemGroups.SPAWN_EGGS, ICE_FAIRY_SPAWN_EGG);

        addSuspiciousStews();
    }

    private static void addToItemGroup(ItemGroup group, Item item) {
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.add(item));
    }

    @SuppressWarnings("SameParameterValue")
    private static void addToItemGroup(ItemGroup group, ItemStack stack, ItemGroup.StackVisibility visibility) {
        //noinspection UnstableApiUsage
        ItemGroupEvents.modifyEntriesEvent(group).register(entries -> entries.addAfter(Items.SUSPICIOUS_STEW, Collections.singletonList(stack), visibility));
    }

    private static void addSuspiciousStews() {
        ItemStack stack = new ItemStack(Items.SUSPICIOUS_STEW);
        SuspiciousStewItem.addEffectToStew(stack, StatusEffects.LEVITATION, 200);
        addToItemGroup(ItemGroups.FOOD_AND_DRINK, stack, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void addCompostables() {
        addToComposterRegistry(HEAVENLY_PEACH, 0.65f);
    }

    @SuppressWarnings("SameParameterValue")
    private static void addToComposterRegistry(Item item, float chance) {
        CompostingChanceRegistry.INSTANCE.add(item, chance);
    }

    private static void addFuel() {
        addToFuelRegistry(ONBASHIRA, 300);
        addToFuelRegistry(ONBASHIRA_PILLAR, 300);
        addToFuelRegistry(DANMAKU_CRAFTING_TABLE, 300);
        addToFuelRegistry(MOCHI_MALLET, 200);
        addToFuelRegistry(ORDINARY_HAT, 100);
    }

    private static void addToFuelRegistry(Item item, int fuelTime) {
        FuelRegistry.INSTANCE.add(item, fuelTime);
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering items for " + ArcadianDream.MOD_ID);

        addItemsToItemGroups();
        addCompostables();
        addFuel();
    }
}
