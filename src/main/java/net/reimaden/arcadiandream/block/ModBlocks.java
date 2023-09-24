/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.block.MapColor;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.custom.*;

public class ModBlocks {

    private static final int DRAGON_GEM_MIN_XP = 5;
    private static final int DRAGON_GEM_MAX_XP = 10;

    // Blocks
    public static final Block ONBASHIRA = registerBlock("onbashira",
            new OnbashiraBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).mapColor(MapColor.SPRUCE_BROWN).strength(2.0f, 1.5f).nonOpaque()));
    public static final Block ONBASHIRA_PILLAR = registerBlock("onbashira_pillar",
            new OnbashiraPillarBlock(FabricBlockSettings.copyOf(ONBASHIRA)));
    public static final Block RITUAL_SHRINE = registerBlock("ritual_shrine",
            new RitualShrineBlock(FabricBlockSettings.create().mapColor(MapColor.PURPLE).strength(4.0f, 6.0F)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().luminance(7)));
    public static final Block DANMAKU_CRAFTING_TABLE = registerBlock("danmaku_crafting_table",
            new DanmakuCraftingTableBlock(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE).mapColor(MapColor.DARK_RED).strength(2.5f)));
    public static final Block MYSTERIOUS_SEAL = registerWithoutItem("mysterious_seal",
            new MysteriousSealBlock(FabricBlockSettings.create().strength(0.1f).noCollision()
                    .sounds(BlockSoundGroup.AZALEA_LEAVES).luminance(5)));

    // Ores
    public static final Block DRAGON_GEM_ORE = registerBlock("dragon_gem_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(6.0f, 3.0f)
                    .luminance(6).requiresTool(), UniformIntProvider.create(DRAGON_GEM_MIN_XP, DRAGON_GEM_MAX_XP)));
    public static final Block DEEPSLATE_DRAGON_GEM_ORE = registerBlock("deepslate_dragon_gem_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(DRAGON_GEM_ORE).strength(7.5f, 3.0f)
                    .mapColor(MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE), UniformIntProvider.create(DRAGON_GEM_MIN_XP, DRAGON_GEM_MAX_XP)));
    public static final Block END_STONE_DRAGON_GEM_ORE = registerBlock("end_stone_dragon_gem_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(DRAGON_GEM_ORE).strength(12.0f, 9.0f)
                    .mapColor(MapColor.PALE_YELLOW), UniformIntProvider.create(DRAGON_GEM_MIN_XP, DRAGON_GEM_MAX_XP)));

    public static final Block MAKAITE_ORE = registerWithoutItem("makaite_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).mapColor(MapColor.DARK_RED).strength(4.0f, 3.0f)
                    .requiresTool().sounds(BlockSoundGroup.NETHER_ORE)));

    public static final Block HIHIIROKANE_ORE = registerBlock("hihiirokane_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(Blocks.STONE).strength(20.0f, 12.0f).requiresTool()));
    public static final Block DEEPSLATE_HIHIIROKANE_ORE = registerBlock("deepslate_hihiirokane_ore",
            new ExperienceDroppingBlock(FabricBlockSettings.copyOf(HIHIIROKANE_ORE).strength(30.0F, 12.0f)
                    .mapColor(MapColor.DEEPSLATE_GRAY).sounds(BlockSoundGroup.DEEPSLATE)));

    // Raw Ore Blocks
    public static final Block RAW_MAKAITE_BLOCK = registerWithoutItem("raw_makaite_block",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).mapColor(MapColor.ORANGE).strength(5.0f, 6.0f)
                    .requiresTool()));

    public static final Block HIHIIROKANE_CHUNK_BLOCK = registerBlock("hihiirokane_chunk_block",
            new Block(FabricBlockSettings.copyOf(Blocks.STONE).mapColor(MapColor.DARK_RED).strength(40.0f, 20.0f)
                    .requiresTool()));

    // Metal Blocks
    public static final Block DRAGON_GEM_BLOCK = registerBlock("dragon_gem_block",
            new DragonGemBlock(FabricBlockSettings.create().mapColor(MapColor.BRIGHT_TEAL).strength(5.0f, 6.0f)
                    .sounds(BlockSoundGroup.METAL).luminance(9).requiresTool()));

    public static final Block MAKAITE_BLOCK = registerWithoutItem("makaite_block",
            new Block(FabricBlockSettings.create().mapColor(MapColor.ORANGE).strength(5.0f, 6.0f)
                    .sounds(BlockSoundGroup.METAL).requiresTool()));

    public static final Block HIHIIROKANE_BLOCK = registerWithoutItem("hihiirokane_block",
            new Block(FabricBlockSettings.create().mapColor(MapColor.DARK_RED).strength(50.0f, 20.0f)
                    .sounds(BlockSoundGroup.NETHERITE).requiresTool()));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return registerWithoutItem(name, block);
    }

    // Mostly used for blocks that require special item settings
    private static Block registerWithoutItem(String name, Block block) {
        return Registry.register(Registries.BLOCK, new Identifier(ArcadianDream.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, new Identifier(ArcadianDream.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering blocks for " + ArcadianDream.MOD_ID);
    }
}
