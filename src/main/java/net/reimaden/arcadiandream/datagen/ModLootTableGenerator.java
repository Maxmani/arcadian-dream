/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.LootingEnchantLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;

import java.util.function.BiConsumer;

public class ModLootTableGenerator {

    public static class BlockLoot extends FabricBlockLootTableProvider {

        public BlockLoot(FabricDataOutput dataOutput) {
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
            addDrop(ModBlocks.DANMAKU_CRAFTING_TABLE);
            addDropWithSilkTouch(ModBlocks.MYSTERIOUS_SEAL);
        }
    }

    public static class EntityLoot extends SimpleFabricLootTableProvider {

        public EntityLoot(FabricDataOutput output) {
            super(output, LootContextTypes.ENTITY);
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> consumer) {
            consumer.accept(ModEntities.FAIRY.getLootTableId(), LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(0.0f, 1.0f))
                            .with(ItemEntry.builder(ModItems.POWER_ITEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                    )
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(0.0f, 1.0f))
                            .with(ItemEntry.builder(ModItems.POINT_ITEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                    )
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(0.0f, 1.0f))
                            .with(ItemEntry.builder(ModItems.STAR_ITEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.MUSIC_DISC_FAIRY_PLAYGROUND))
                            .conditionally(KilledByPlayerLootCondition.builder())
                            .conditionally(RandomChanceWithLootingLootCondition.builder(0.001f, 0.001f))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.BOMB_ITEM))
                            .conditionally(KilledByPlayerLootCondition.builder())
                            .conditionally(RandomChanceWithLootingLootCondition.builder(0.01f, 0.005f))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.EXTEND_ITEM))
                            .conditionally(KilledByPlayerLootCondition.builder())
                            .conditionally(RandomChanceWithLootingLootCondition.builder(0.004f, 0.005f))
                    )
            );
            consumer.accept(ModEntities.SUNFLOWER_FAIRY.getLootTableId(), LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(0.0f, 1.0f))
                            .with(ItemEntry.builder(ModItems.POWER_ITEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 7.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                    )
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(0.0f, 1.0f))
                            .with(ItemEntry.builder(ModItems.POINT_ITEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 7.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                    )
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(0.0f, 1.0f))
                            .with(ItemEntry.builder(ModItems.STAR_ITEM))
                            .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 7.0f)))
                            .apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f)))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.MUSIC_DISC_FAIRY_PLAYGROUND))
                            .conditionally(KilledByPlayerLootCondition.builder())
                            .conditionally(RandomChanceWithLootingLootCondition.builder(0.003f, 0.003f))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.BOMB_ITEM))
                            .conditionally(KilledByPlayerLootCondition.builder())
                            .conditionally(RandomChanceWithLootingLootCondition.builder(0.05f, 0.015f))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.EXTEND_ITEM))
                            .conditionally(KilledByPlayerLootCondition.builder())
                            .conditionally(RandomChanceWithLootingLootCondition.builder(0.01f, 0.01f))
                    )
            );
        }
    }
}
