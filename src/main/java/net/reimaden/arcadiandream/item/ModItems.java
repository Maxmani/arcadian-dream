/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.item.custom.armor.OrdinaryHatItem;
import net.reimaden.arcadiandream.item.custom.consumables.HealingCharmItem;
import net.reimaden.arcadiandream.item.custom.consumables.HouraiElixirItem;
import net.reimaden.arcadiandream.item.custom.danmaku.OrbBulletItem;
import net.reimaden.arcadiandream.item.custom.misc.*;
import net.reimaden.arcadiandream.item.custom.tools.DeathScytheItem;
import net.reimaden.arcadiandream.item.custom.tools.MochiMalletItem;
import net.reimaden.arcadiandream.item.custom.tools.ModHoeItem;
import net.reimaden.arcadiandream.item.custom.tools.NueTridentItem;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    // Items
    public static final Item POWER_ITEM = registerItem("power_item", new Item(new FabricItemSettings()));
    public static final Item BIG_POWER_ITEM = registerItem("big_power_item", new Item(new FabricItemSettings()));
    public static final Item POINT_ITEM = registerItem("point_item", new Item(new FabricItemSettings()));
    public static final Item MAX_POINT_ITEM = registerItem("max_point_item", new Item(new FabricItemSettings()));
    public static final Item BOMB_ITEM = registerItem("bomb_item", new BombItem(new FabricItemSettings().maxCount(8).rarity(Rarity.UNCOMMON)));
    public static final Item EXTEND_ITEM = registerItem("extend_item", new ExtendItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item STAR_ITEM = registerItem("star_item", new Item(new FabricItemSettings()));

    public static final Item DRAGON_GEM = registerItem("dragon_gem", new Item(new FabricItemSettings()));
    public static final Item RAW_MAKAITE = registerItem("raw_makaite", new Item(new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_INGOT = registerItem("makaite_ingot", new Item(new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_INFUSED_NETHERITE_INGOT = registerItem("makaite_infused_netherite_ingot", new Item(new FabricItemSettings().fireproof()));

    public static final Item WALL_PASSING_CHISEL = registerItem("wall_passing_chisel", new WallPassingChiselItem(new FabricItemSettings().maxDamage(100)));
    public static final Item IBUKI_GOURD = registerItem("ibuki_gourd", new IbukiGourdItem(new FabricItemSettings().maxCount(1)));
    public static final Item HEALING_CHARM = registerItem("healing_charm", new HealingCharmItem(new FabricItemSettings().maxCount(16)));
    public static final Item HOURAI_ELIXIR = registerItem("hourai_elixir", new HouraiElixirItem(new FabricItemSettings().maxCount(1).maxDamage(3).rarity(Rarity.EPIC)));

    // Food
    public static final Item HEAVENLY_PEACH = registerItem("heavenly_peach", new Item(new FabricItemSettings().maxCount(16)
            .food(ModFoodComponents.HEAVENLY_PEACH)));

    // Armor
    public static final Item ORDINARY_HAT = registerItem("ordinary_hat", new OrdinaryHatItem(ModArmorMaterials.ORDINARY, EquipmentSlot.HEAD,
            new FabricItemSettings()));

    public static final Item MAKAITE_HELMET = registerItem("makaite_helmet", new ArmorItem(ModArmorMaterials.MAKAITE, EquipmentSlot.HEAD,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_CHESTPLATE = registerItem("makaite_chestplate", new ArmorItem(ModArmorMaterials.MAKAITE, EquipmentSlot.CHEST,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_LEGGINGS = registerItem("makaite_leggings", new ArmorItem(ModArmorMaterials.MAKAITE, EquipmentSlot.LEGS,
            new FabricItemSettings().fireproof()));
    public static final Item MAKAITE_BOOTS = registerItem("makaite_boots", new ArmorItem(ModArmorMaterials.MAKAITE, EquipmentSlot.FEET,
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
    public static final Item MAKAITE_HOE = registerItem("makaite_hoe", new ModHoeItem(ModToolMaterials.MAKAITE, -2, -1.0f,
            new FabricItemSettings().fireproof()));

    // Weapons
    public static final Item NUE_TRIDENT = registerItem("nue_trident", new NueTridentItem(ModToolMaterials.MAKAITE_INFUSED_NETHERITE, 4, -2.9f,
            new FabricItemSettings().fireproof()));
    public static final Item HISOU_SWORD = registerItem("hisou_sword", new SwordItem(ModToolMaterials.HISOU, 3, -2.0f,
            new FabricItemSettings()));
    public static final Item MOCHI_MALLET = registerItem("mochi_mallet", new MochiMalletItem(ModToolMaterials.MOCHI_MALLET, 3, -3.2f,
            new FabricItemSettings()));
    public static final Item DEATH_SCYTHE = registerItem("death_scythe", new DeathScytheItem(ToolMaterials.IRON, 4, -3.0f,
            new FabricItemSettings()));

    // Danmaku
    public static final Item ORB_BULLET = registerItem("orb_bullet", new OrbBulletItem(new FabricItemSettings(),
            3, 1.0f, 200, 2, 0.0f, 0.0f, "spread", 1, 1, 0));

    // Music Discs
    public static final Item MUSIC_DISC_FAIRY_PLAYGROUND = registerItem("music_disc_fairy_playground", new MusicDiscItem(10, ModSounds.MUSIC_DISC_FAIRY_PLAYGROUND,
            new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 180));
    public static final Item MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN = registerItem("music_disc_the_shrine_long_forgotten", new MusicDiscItem(14, ModSounds.MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN,
            new FabricItemSettings().maxCount(1).rarity(Rarity.RARE), 134));

    private static Item registerItem(String name, Item item) {
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.ITEMS).register(entries -> {
            entries.add(POWER_ITEM);
            entries.add(BIG_POWER_ITEM);
            entries.add(POINT_ITEM);
            entries.add(MAX_POINT_ITEM);
            entries.add(BOMB_ITEM);
            entries.add(EXTEND_ITEM);
            entries.add(STAR_ITEM);
            entries.add(DRAGON_GEM);
            entries.add(RAW_MAKAITE);
            entries.add(MAKAITE_INGOT);
            entries.add(MAKAITE_INFUSED_NETHERITE_INGOT);
            entries.add(WALL_PASSING_CHISEL);
            entries.add(IBUKI_GOURD);
            entries.add(HEALING_CHARM);
            entries.add(HOURAI_ELIXIR);
            entries.add(HEAVENLY_PEACH);
            entries.add(ORDINARY_HAT);
            entries.add(MAKAITE_HELMET);
            entries.add(MAKAITE_CHESTPLATE);
            entries.add(MAKAITE_LEGGINGS);
            entries.add(MAKAITE_BOOTS);
            entries.add(MAKAITE_SWORD);
            entries.add(MAKAITE_PICKAXE);
            entries.add(MAKAITE_AXE);
            entries.add(MAKAITE_SHOVEL);
            entries.add(MAKAITE_HOE);
            entries.add(NUE_TRIDENT);
            entries.add(HISOU_SWORD);
            entries.add(MOCHI_MALLET);
            entries.add(DEATH_SCYTHE);
            entries.add(MUSIC_DISC_FAIRY_PLAYGROUND);
            entries.add(MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN);
        });
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.DANMAKU).register(entries -> {
            entries.add(BOMB_ITEM);
            entries.add(DANMAKU_CRAFTING_TABLE);
            entries.add(ORB_BULLET);
        });

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
        ItemGroupEvents.modifyEntriesEvent(ModItemGroups.BLOCKS).register(entries -> {
            entries.add(ONBASHIRA);
            entries.add(ONBASHIRA_PILLAR);
            entries.add(RITUAL_SHRINE);
            entries.add(DANMAKU_CRAFTING_TABLE);
            entries.add(DRAGON_GEM_ORE);
            entries.add(DEEPSLATE_DRAGON_GEM_ORE);
            entries.add(END_STONE_DRAGON_GEM_ORE);
            entries.add(MAKAITE_ORE);
            entries.add(RAW_MAKAITE_BLOCK);
            entries.add(DRAGON_GEM_BLOCK);
            entries.add(MAKAITE_BLOCK);
        });

        return Registry.register(Registries.ITEM, new Identifier(ArcadianDream.MOD_ID, name), block);
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering items for " + ArcadianDream.MOD_ID);
        // ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> entries.add(MY_ITEM));
    }
}
