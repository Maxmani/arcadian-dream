package net.reimaden.arcadiandream.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.custom.DragonGemBlock;
import net.reimaden.arcadiandream.block.custom.RitualShrineBlock;
import net.reimaden.arcadiandream.block.custom.OnbashiraBlock;
import net.reimaden.arcadiandream.block.custom.OnbashiraPillarBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

public class ModBlocks {

    // Blocks
    public static final Block ONBASHIRA = registerBlock("onbashira",
            new OnbashiraBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 1.5f)
                    .sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Block ONBASHIRA_PILLAR = registerBlock("onbashira_pillar",
            new OnbashiraPillarBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0f, 1.5f)
                    .sounds(BlockSoundGroup.WOOD).nonOpaque()));
    public static final Block RITUAL_SHRINE = registerBlock("ritual_shrine",
            new RitualShrineBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f, 6.0F)
                    .sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().luminance(7)));

    // Ores
    public static final Block DRAGON_GEM_ORE = registerBlock("dragon_gem_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(6.0f, 3.0f)
                    .luminance(6).requiresTool(), UniformIntProvider.create(5, 10)));
    public static final Block DEEPSLATE_DRAGON_GEM_ORE = registerBlock("deepslate_dragon_gem_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(7.5f, 3.0f).sounds(BlockSoundGroup.DEEPSLATE)
                    .luminance(6).requiresTool(), UniformIntProvider.create(5, 10)));
    public static final Block END_STONE_DRAGON_GEM_ORE = registerBlock("end_stone_dragon_gem_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(12.0f, 9.0f)
                    .luminance(6).requiresTool(), UniformIntProvider.create(5, 10)));

    public static final Block MAKAITE_ORE = registerBlock("makaite_ore",
            new OreBlock(FabricBlockSettings.of(Material.STONE).strength(4.0f, 3.0f)
                    .requiresTool().sounds(BlockSoundGroup.NETHER_ORE)));

    // Raw Ore Blocks
    public static final Block RAW_MAKAITE_BLOCK = registerBlock("raw_makaite_block",
            new Block(FabricBlockSettings.of(Material.STONE).strength(5.0f, 6.0f)
                    .requiresTool()));

    // Metal Blocks
    public static final Block DRAGON_GEM_BLOCK = registerBlock("dragon_gem_block",
            new DragonGemBlock(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL)
                    .luminance(9).requiresTool()));

    public static final Block MAKAITE_BLOCK = registerBlock("makaite_block",
            new Block(FabricBlockSettings.of(Material.METAL).strength(5.0f, 6.0f).sounds(BlockSoundGroup.METAL).requiresTool()));

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(ArcadianDream.MOD_ID, name), block);
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering blocks for " + ArcadianDream.MOD_ID);
    }
}
