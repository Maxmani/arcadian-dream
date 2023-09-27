/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
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

public class ModItems {

    public static final int SWORD_ATTACK_DAMAGE = 3;
    public static final float SWORD_ATTACK_SPEED = -2.4f;
    public static final int PICKAXE_ATTACK_DAMAGE = 1;
    public static final float PICKAXE_ATTACK_SPEED = -2.8f;
    public static final int AXE_ATTACK_DAMAGE = 6;
    public static final float AXE_ATTACK_SPEED = -3.1f;
    public static final float SHOVEL_ATTACK_DAMAGE = 1.5f;
    public static final float SHOVEL_ATTACK_SPEED = -3.0f;
    public static final int HOE_ATTACK_DAMAGE = -2;
    public static final float HOE_ATTACK_SPEED = -1.0f;

    // Items ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    public static final Item HIHIIROKANE_CHUNK = registerItem("hihiirokane_chunk", new Item(new FabricItemSettings()));
    public static final Item HIHIIROKANE_INGOT = registerItem("hihiirokane_ingot", new Item(new FabricItemSettings().fireproof()));

    public static final Item WALL_PASSING_CHISEL = registerItem("wall_passing_chisel", new WallPassingChiselItem(new FabricItemSettings().maxDamage(100)));
    public static final Item IBUKI_GOURD = registerItem("ibuki_gourd", new IbukiGourdItem(new FabricItemSettings().maxCount(1)));
    public static final Item HEALING_CHARM = registerItem("healing_charm", new HealingCharmItem(new FabricItemSettings().maxCount(16)));
    public static final Item HOURAI_ELIXIR = registerItem("hourai_elixir", new HouraiElixirItem(new FabricItemSettings().maxCount(1).maxDamage(3).rarity(Rarity.EPIC)));
    public static final Item MAGATAMA_NECKLACE = registerItem("magatama_necklace", new MagatamaNecklaceItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item GHASTLY_LANTERN = registerItem("ghastly_lantern", new GhastlyLanternItem(new FabricItemSettings().maxCount(1)));
    public static final Item FAIRY_CHARM = registerItem("fairy_charm", new FairyCharmItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));

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

    public static final Item HIHIIROKANE_UPGRADE_SMITHING_TEMPLATE = registerItem("hihiirokane_upgrade_smithing_template", ModSmithingTemplateItem.createHihiirokaneUpgrade());
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Food ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item HEAVENLY_PEACH = registerItem("heavenly_peach", new Item(new FabricItemSettings().maxCount(16)
            .food(ModFoodComponents.HEAVENLY_PEACH)));
    public static final Item LAMPREY = registerItem("lamprey", new Item(new FabricItemSettings()
            .food(ModFoodComponents.LAMPREY)));
    public static final Item COOKED_LAMPREY = registerItem("cooked_lamprey", new Item(new FabricItemSettings()
            .food(ModFoodComponents.COOKED_LAMPREY)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Armor ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    public static final Item HIHIIROKANE_HELMET = registerItem("hihiirokane_helmet", new ArmorItem(ModArmorMaterials.HIHIIROKANE, ArmorItem.Type.HELMET,
            new FabricItemSettings().fireproof()));
    public static final Item HIHIIROKANE_CHESTPLATE = registerItem("hihiirokane_chestplate", new ArmorItem(ModArmorMaterials.HIHIIROKANE, ArmorItem.Type.CHESTPLATE,
            new FabricItemSettings().fireproof()));
    public static final Item HIHIIROKANE_LEGGINGS = registerItem("hihiirokane_leggings", new ArmorItem(ModArmorMaterials.HIHIIROKANE, ArmorItem.Type.LEGGINGS,
            new FabricItemSettings().fireproof()));
    public static final Item HIHIIROKANE_BOOTS = registerItem("hihiirokane_boots", new ArmorItem(ModArmorMaterials.HIHIIROKANE, ArmorItem.Type.BOOTS,
            new FabricItemSettings().fireproof()));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Tools ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item MAKAITE_SWORD = registerItem("makaite_sword", new SwordItem(ModToolMaterials.MAKAITE, SWORD_ATTACK_DAMAGE, SWORD_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_PICKAXE = registerItem("makaite_pickaxe", new PickaxeItem(ModToolMaterials.MAKAITE, PICKAXE_ATTACK_DAMAGE, PICKAXE_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_AXE = registerItem("makaite_axe", new AxeItem(ModToolMaterials.MAKAITE, AXE_ATTACK_DAMAGE, AXE_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_SHOVEL = registerItem("makaite_shovel", new ShovelItem(ModToolMaterials.MAKAITE, SHOVEL_ATTACK_DAMAGE, SHOVEL_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_HOE = registerItem("makaite_hoe", new HoeItem(ModToolMaterials.MAKAITE, HOE_ATTACK_DAMAGE, HOE_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));

    public static final Item HIHIIROKANE_SWORD = registerItem("hihiirokane_sword", new HihiirokaneSwordItem(ModToolMaterials.HIHIIROKANE, SWORD_ATTACK_DAMAGE, SWORD_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));
    public static final Item HIHIIROKANE_PICKAXE = registerItem("hihiirokane_pickaxe", new HihiirokanePickaxeItem(ModToolMaterials.HIHIIROKANE, PICKAXE_ATTACK_DAMAGE, PICKAXE_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));
    public static final Item HIHIIROKANE_AXE = registerItem("hihiirokane_axe", new HihiirokaneAxeItem(ModToolMaterials.HIHIIROKANE, AXE_ATTACK_DAMAGE, AXE_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));
    public static final Item HIHIIROKANE_SHOVEL = registerItem("hihiirokane_shovel", new HihiirokaneShovelItem(ModToolMaterials.HIHIIROKANE, SHOVEL_ATTACK_DAMAGE, SHOVEL_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));
    public static final Item HIHIIROKANE_HOE = registerItem("hihiirokane_hoe", new HihiirokaneHoeItem(ModToolMaterials.HIHIIROKANE, HOE_ATTACK_DAMAGE, HOE_ATTACK_SPEED,
            new FabricItemSettings().fireproof()));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Weapons /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item NUE_TRIDENT = registerItem("nue_trident", new NueTridentItem(ToolMaterials.NETHERITE, 5, -2.9f,
            new FabricItemSettings().fireproof()));
    public static final Item HISOU_SWORD = registerItem("hisou_sword", new SwordItem(ModToolMaterials.HISOU, 3, -2.0f,
            new FabricItemSettings()));
    public static final Item MOCHI_MALLET = registerItem("mochi_mallet", new MochiMalletItem(ModToolMaterials.MOCHI_MALLET, 3, -3.2f,
            new FabricItemSettings()));
    public static final Item DEATH_SCYTHE = registerItem("death_scythe", new DeathScytheItem(ToolMaterials.IRON, 4, -3.0f,
            new FabricItemSettings()));
    public static final Item MIRACLE_MALLET = registerItem("miracle_mallet", new MiracleMalletItem(ToolMaterials.DIAMOND, 3, -3.2f,
            new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item FOLDING_CHAIR = registerItem("folding_chair", new FoldingChairItem(ToolMaterials.IRON, 1, -3.1f,
            new FabricItemSettings().maxDamage(ToolMaterials.IRON.getDurability() - 50)));
    public static final Item ICICLE_SWORD = registerItem("icicle_sword", new IcicleSwordItem(ModToolMaterials.ENCHANTED_ICE, 3, -2.4f,
            new FabricItemSettings().rarity(Rarity.UNCOMMON)));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Danmaku /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Music Discs /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item MUSIC_DISC_FAIRY_PLAYGROUND = registerItem("music_disc_fairy_playground", new MusicDiscItem(10, ModSounds.MUSIC_DISC_FAIRY_PLAYGROUND,
            new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 180));
    public static final Item MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN = registerItem("music_disc_the_shrine_long_forgotten", new MusicDiscItem(14, ModSounds.MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN,
            new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 134));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Spawn Eggs //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public static final Item FAIRY_SPAWN_EGG = registerItem("fairy_spawn_egg",
            new SpawnEggItem(ModEntities.FAIRY, 0x004BCC, 0xF6FF5F,
                    new FabricItemSettings()));
    public static final Item SUNFLOWER_FAIRY_SPAWN_EGG = registerItem("sunflower_fairy_spawn_egg",
            new SpawnEggItem(ModEntities.SUNFLOWER_FAIRY, 0xEFEFEF, 0xFBD405,
                    new FabricItemSettings()));
    public static final Item ICE_FAIRY_SPAWN_EGG = registerItem("ice_fairy_spawn_egg",
            new SpawnEggItem(ModEntities.ICE_FAIRY, 0xABF0FF, 0xC6FBFF,
                    new FabricItemSettings()));
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ArcadianDream.MOD_ID, name), item);
    }

    // Block Items
    // The ones that don't require any special settings are in the ModBlocks class
    public static final Item MYSTERIOUS_SEAL = registerBlockItem("mysterious_seal", new BlockItem(ModBlocks.MYSTERIOUS_SEAL,
            new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item MAKAITE_ORE = registerBlockItem("makaite_ore", new BlockItem(ModBlocks.MAKAITE_ORE,
            new FabricItemSettings().fireproof()));
    public static final Item RAW_MAKAITE_BLOCK = registerBlockItem("raw_makaite_block", new BlockItem(ModBlocks.RAW_MAKAITE_BLOCK,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_BLOCK = registerBlockItem("makaite_block", new BlockItem(ModBlocks.MAKAITE_BLOCK,
            new FabricItemSettings().fireproof()));
    public static final Item HIHIIROKANE_BLOCK = registerBlockItem("hihiirokane_block", new BlockItem(ModBlocks.HIHIIROKANE_BLOCK,
            new FabricItemSettings().fireproof()));

    private static Item registerBlockItem(String name, BlockItem block) {
        return Registry.register(Registries.ITEM, new Identifier(ArcadianDream.MOD_ID, name), block);
    }

    private static void addCompostables() {
        addToComposterRegistry(HEAVENLY_PEACH, 0.65f);
    }

    @SuppressWarnings("SameParameterValue")
    private static void addToComposterRegistry(Item item, float chance) {
        CompostingChanceRegistry.INSTANCE.add(item, chance);
    }

    private static void addFuel() {
        addToFuelRegistry(ModBlocks.ONBASHIRA.asItem(), 300);
        addToFuelRegistry(ModBlocks.ONBASHIRA_PILLAR.asItem(), 300);
        addToFuelRegistry(ModBlocks.DANMAKU_CRAFTING_TABLE.asItem(), 300);
        addToFuelRegistry(MOCHI_MALLET, 200);
        addToFuelRegistry(ORDINARY_HAT, 100);
    }

    private static void addToFuelRegistry(Item item, int fuelTime) {
        FuelRegistry.INSTANCE.add(item, fuelTime);
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering items for " + ArcadianDream.MOD_ID);

        addCompostables();
        addFuel();
    }
}
