/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.item.ModItems;

public class ModLootTableGenerator {

    public static class BlockLoot extends FabricBlockLootTableProvider {

        protected BlockLoot(FabricDataOutput dataOutput) {
            super(dataOutput);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.DRAGON_GEM_BLOCK);
            addDrop(ModBlocks.DRAGON_GEM_ORE, oreDrops(ModBlocks.DRAGON_GEM_ORE, ModItems.DRAGON_GEM));
            addDrop(ModBlocks.DEEPSLATE_DRAGON_GEM_ORE, oreDrops(ModBlocks.DEEPSLATE_DRAGON_GEM_ORE, ModItems.DRAGON_GEM));
            addDrop(ModBlocks.END_STONE_DRAGON_GEM_ORE, oreDrops(ModBlocks.END_STONE_DRAGON_GEM_ORE, ModItems.DRAGON_GEM));
            addDrop(ModBlocks.MAKAITE_BLOCK);
            addDrop(ModBlocks.RAW_MAKAITE_BLOCK);
            addDrop(ModBlocks.MAKAITE_ORE, oreDrops(ModBlocks.MAKAITE_ORE, ModItems.RAW_MAKAITE));
            addDrop(ModBlocks.RITUAL_SHRINE);
            addDrop(ModBlocks.ONBASHIRA);
            addDrop(ModBlocks.ONBASHIRA_PILLAR);
        }
    }
}
