/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.item.custom.armor.OrdinaryArmorItem;
import net.reimaden.arcadiandream.item.custom.danmaku.OrbBulletItem;
import net.reimaden.arcadiandream.item.custom.misc.BombItem;
import net.reimaden.arcadiandream.item.custom.misc.ExtendItem;
import net.reimaden.arcadiandream.item.custom.misc.IbukiGourdItem;
import net.reimaden.arcadiandream.item.custom.misc.WallPassingChiselItem;
import net.reimaden.arcadiandream.item.custom.tools.MochiHammerItem;
import net.reimaden.arcadiandream.item.custom.tools.ModHoeItem;
import net.reimaden.arcadiandream.item.custom.tools.NueTridentItem;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class ModItems {

    // Items
    public static final Item POWER_ITEM = registerItem("power_item", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS)));
    public static final Item BIG_POWER_ITEM = registerItem("big_power_item", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS)));
    public static final Item POINT_ITEM = registerItem("point_item", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS)));
    public static final Item MAX_POINT_ITEM = registerItem("max_point_item", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS)));
    public static final Item BOMB_ITEM = registerItem("bomb_item", new BombItem(new FabricItemSettings().group(ModItemGroup.ITEMS).maxCount(8).rarity(Rarity.UNCOMMON)));
    public static final Item EXTEND_ITEM = registerItem("extend_item", new ExtendItem(new FabricItemSettings().group(ModItemGroup.ITEMS).maxCount(1).rarity(Rarity.UNCOMMON)));
    public static final Item STAR_ITEM = registerItem("star_item", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS).maxCount(512)));

    public static final Item DRAGON_GEM = registerItem("dragon_gem", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS)));
    public static final Item RAW_MAKAITE = registerItem("raw_makaite", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item MAKAITE_INGOT = registerItem("makaite_ingot", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item MAKAITE_INFUSED_NETHERITE_INGOT = registerItem("makaite_infused_netherite_ingot", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));

    public static final Item WALL_PASSING_CHISEL = registerItem("wall_passing_chisel", new WallPassingChiselItem(new FabricItemSettings().group(ModItemGroup.ITEMS).maxDamage(100)));
    public static final Item IBUKI_GOURD = registerItem("ibuki_gourd", new IbukiGourdItem(new FabricItemSettings().group(ModItemGroup.ITEMS).maxCount(1)));

    // Food
    public static final Item HEAVENLY_PEACH = registerItem("heavenly_peach", new Item(new FabricItemSettings().group(ModItemGroup.ITEMS).maxCount(16)
            .food(new FoodComponent.Builder().hunger(6).saturationModifier(0.4F).alwaysEdible()
                    .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE,600),1F).build())));

    // Armor
    public static final Item ORDINARY_HAT = registerItem("ordinary_hat", new OrdinaryArmorItem(ModArmorMaterials.ORDINARY, EquipmentSlot.HEAD,
            new FabricItemSettings().group(ModItemGroup.ITEMS)));

    public static final Item MAKAITE_HELMET = registerItem("makaite_helmet", new ArmorItem(ModArmorMaterials.MAKAITE, EquipmentSlot.HEAD,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item MAKAITE_CHESTPLATE = registerItem("makaite_chestplate", new ArmorItem(ModArmorMaterials.MAKAITE, EquipmentSlot.CHEST,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item MAKAITE_LEGGINGS = registerItem("makaite_leggings", new ArmorItem(ModArmorMaterials.MAKAITE, EquipmentSlot.LEGS,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item MAKAITE_BOOTS = registerItem("makaite_boots", new ArmorItem(ModArmorMaterials.MAKAITE, EquipmentSlot.FEET,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));

    // Tools
    public static final Item MAKAITE_SWORD = registerItem("makaite_sword", new SwordItem(ModToolMaterials.MAKAITE, 3, -2.4f,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item MAKAITE_PICKAXE = registerItem("makaite_pickaxe", new PickaxeItem(ModToolMaterials.MAKAITE, 1, -2.8f,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item MAKAITE_AXE = registerItem("makaite_axe", new AxeItem(ModToolMaterials.MAKAITE, 6, -3.1f,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item MAKAITE_SHOVEL = registerItem("makaite_shovel", new ShovelItem(ModToolMaterials.MAKAITE, 1.5f, -3.0f,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item MAKAITE_HOE = registerItem("makaite_hoe", new ModHoeItem(ModToolMaterials.MAKAITE, -2, -1.0f,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));

    // Weapons
    public static final Item NUE_TRIDENT = registerItem("nue_trident", new NueTridentItem(ModToolMaterials.MAKAITE_INFUSED_NETHERITE, 4, -2.9f,
            new FabricItemSettings().group(ModItemGroup.ITEMS).fireproof()));
    public static final Item HISOU_SWORD = registerItem("hisou_sword", new SwordItem(ModToolMaterials.HISOU, 3, -2.0f,
            new FabricItemSettings().group(ModItemGroup.ITEMS)));
    public static final Item MOCHI_HAMMER = registerItem("mochi_hammer", new MochiHammerItem(ModToolMaterials.MOCHI_HAMMER, 3, -3.2f,
            new FabricItemSettings().group(ModItemGroup.ITEMS)));

    // Danmaku
    public static final Item ORB_BULLET = registerItem("orb_bullet", new OrbBulletItem(new FabricItemSettings().group(ModItemGroup.DANMAKU)));

    // Music Discs
    public static final Item MUSIC_DISC_FAIRY_PLAYGROUND = registerItem("music_disc_fairy_playground", new MusicDiscItem(10, ModSounds.MUSIC_DISC_FAIRY_PLAYGROUND,
            new FabricItemSettings().group(ModItemGroup.ITEMS).maxCount(1).rarity(Rarity.RARE), 180));
    public static final Item MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN = registerItem("music_disc_the_shrine_long_forgotten", new MusicDiscItem(14, ModSounds.MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN,
            new FabricItemSettings().group(ModItemGroup.ITEMS).maxCount(1).rarity(Rarity.RARE), 134));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(ArcadianDream.MOD_ID, name), item);
    }

    // Block Items
    public static final Item ONBASHIRA = registerBlockItem("onbashira", new BlockItem(ModBlocks.ONBASHIRA,
            new FabricItemSettings().group(ModItemGroup.BLOCKS)));
    public static final Item ONBASHIRA_PILLAR = registerBlockItem("onbashira_pillar", new BlockItem(ModBlocks.ONBASHIRA_PILLAR,
            new FabricItemSettings().group(ModItemGroup.BLOCKS)));
    public static final Item RITUAL_SHRINE = registerBlockItem("ritual_shrine", new BlockItem(ModBlocks.RITUAL_SHRINE,
            new FabricItemSettings().group(ModItemGroup.BLOCKS)));

    public static final Item DRAGON_GEM_ORE = registerBlockItem("dragon_gem_ore", new BlockItem(ModBlocks.DRAGON_GEM_ORE,
            new FabricItemSettings().group(ModItemGroup.BLOCKS)));
    public static final Item DEEPSLATE_DRAGON_GEM_ORE = registerBlockItem("deepslate_dragon_gem_ore", new BlockItem(ModBlocks.DEEPSLATE_DRAGON_GEM_ORE,
            new FabricItemSettings().group(ModItemGroup.BLOCKS)));
    public static final Item END_STONE_DRAGON_GEM_ORE = registerBlockItem("end_stone_dragon_gem_ore", new BlockItem(ModBlocks.END_STONE_DRAGON_GEM_ORE,
            new FabricItemSettings().group(ModItemGroup.BLOCKS)));

    public static final Item MAKAITE_ORE = registerBlockItem("makaite_ore", new BlockItem(ModBlocks.MAKAITE_ORE,
            new FabricItemSettings().group(ModItemGroup.BLOCKS).fireproof()));

    public static final Item RAW_MAKAITE_BLOCK = registerBlockItem("raw_makaite_block", new BlockItem(ModBlocks.RAW_MAKAITE_BLOCK,
            new FabricItemSettings().group(ModItemGroup.BLOCKS).fireproof()));

    public static final Item DRAGON_GEM_BLOCK = registerBlockItem("dragon_gem_block", new BlockItem(ModBlocks.DRAGON_GEM_BLOCK,
            new FabricItemSettings().group(ModItemGroup.BLOCKS)));

    public static final Item MAKAITE_BLOCK = registerBlockItem("makaite_block", new BlockItem(ModBlocks.MAKAITE_BLOCK,
            new FabricItemSettings().group(ModItemGroup.BLOCKS).fireproof()));

    private static Item registerBlockItem(String name, BlockItem block) {
        return Registry.register(Registry.ITEM, new Identifier(ArcadianDream.MOD_ID, name), block);
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering items for " + ArcadianDream.MOD_ID);
    }
}
