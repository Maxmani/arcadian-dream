/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.KilledByPlayerLootCondition;
import net.minecraft.loot.condition.RandomChanceWithLootingLootCondition;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.loot.function.*;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.enchantment.ModEnchantments;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.loot.ModLootTables;
import net.reimaden.arcadiandream.util.ModTags;

import java.util.function.BiConsumer;

public class ModLootTableGenerator {

    public static class BlockLoot extends FabricBlockLootTableProvider {

        public BlockLoot(FabricDataOutput dataOutput) {
            super(dataOutput);
        }

        @Override
        public void generate() {
            addDrop(ModBlocks.DRAGON_GEM_BLOCK);
            addDrop(ModBlocks.DRAGON_GEM_ORE, this::dragonGemOreDrops);
            addDrop(ModBlocks.DEEPSLATE_DRAGON_GEM_ORE, this::dragonGemOreDrops);
            addDrop(ModBlocks.END_STONE_DRAGON_GEM_ORE, this::dragonGemOreDrops);
            addDrop(ModBlocks.MAKAITE_BLOCK);
            addDrop(ModBlocks.RAW_MAKAITE_BLOCK);
            addDrop(ModBlocks.MAKAITE_ORE, oreDrops(ModBlocks.MAKAITE_ORE, ModItems.RAW_MAKAITE));
            addDrop(ModBlocks.RITUAL_SHRINE);
            addDrop(ModBlocks.ONBASHIRA);
            addDrop(ModBlocks.ONBASHIRA_PILLAR);
            addDrop(ModBlocks.DANMAKU_CRAFTING_TABLE);
            addDropWithSilkTouch(ModBlocks.MYSTERIOUS_SEAL);
            addDrop(ModBlocks.HIHIIROKANE_ORE, this::hihiirokaneOreDrops);
            addDrop(ModBlocks.DEEPSLATE_HIHIIROKANE_ORE, this::hihiirokaneOreDrops);
            addDrop(ModBlocks.HIHIIROKANE_CHUNK_BLOCK);
            addDrop(ModBlocks.HIHIIROKANE_BLOCK);
        }

        private LootTable.Builder dragonGemOreDrops(Block block) {
            return oreDrops(block, ModItems.DRAGON_GEM);
        }

        private LootTable.Builder hihiirokaneOreDrops(Block block) {
            return BlockLootTableGenerator.dropsWithSilkTouch(block, applyExplosionDecay(block, ItemEntry.builder(ModItems.HIHIIROKANE_CHUNK).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f))).apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
        }
    }

    public static class EntityLoot extends SimpleFabricLootTableProvider {

        public EntityLoot(FabricDataOutput output) {
            super(output, LootContextTypes.ENTITY);
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> consumer) {
            consumer.accept(ModEntities.FAIRY.getLootTableId(), LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(1.0f, 3.0f))
                            .with(ItemEntry.builder(ModItems.POWER_ITEM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(ModItems.POINT_ITEM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(ModItems.STAR_ITEM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.MUSIC_DISC_FAIRY_PLAYGROUND).conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.001f, 0.001f)))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.BOMB_ITEM).conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.01f, 0.005f)))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.EXTEND_ITEM).conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.004f, 0.005f)))
                    )
            );
            consumer.accept(ModEntities.SUNFLOWER_FAIRY.getLootTableId(), LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(1.0f, 4.0f))
                            .with(ItemEntry.builder(ModItems.POWER_ITEM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 7.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(ModItems.POINT_ITEM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 7.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(ModItems.STAR_ITEM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 7.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.MUSIC_DISC_FAIRY_PLAYGROUND).conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.003f, 0.003f)))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.BOMB_ITEM).conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.05f, 0.015f)))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.EXTEND_ITEM).conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.01f, 0.01f)))
                    )
            );
            consumer.accept(ModEntities.ICE_FAIRY.getLootTableId(), LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(1.0f, 4.0f))
                            .with(ItemEntry.builder(ModItems.POWER_ITEM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(ModItems.POINT_ITEM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(ModItems.STAR_ITEM).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 5.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.MUSIC_DISC_FAIRY_PLAYGROUND).conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.002f, 0.002f)))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.BOMB_ITEM).conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.02f, 0.007f)))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(ModItems.EXTEND_ITEM).conditionally(KilledByPlayerLootCondition.builder()).conditionally(RandomChanceWithLootingLootCondition.builder(0.006f, 0.006f)))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(Items.ICE).weight(50).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 3.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(Items.PACKED_ICE).weight(30).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 2.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(Items.BLUE_ICE).weight(18).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))).apply(LootingEnchantLootFunction.builder(UniformLootNumberProvider.create(0.0f, 1.0f))))
                            .with(ItemEntry.builder(ModItems.ENCHANTED_ICE).weight(2))
                            .conditionally(RandomChanceWithLootingLootCondition.builder(0.7f, 0.1f))
                    )
            );
        }
    }

    public static class ChestLoot extends SimpleFabricLootTableProvider {

        public ChestLoot(FabricDataOutput output) {
            super(output, LootContextTypes.CHEST);
        }

        @Override
        public void accept(BiConsumer<Identifier, LootTable.Builder> consumer) {
            consumer.accept(ModLootTables.ABANDONED_SHRINE_TREASURE_CHEST, LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(1.0f, 3.0f))
                            .with(ItemEntry.builder(ModItems.MUSIC_DISC_THE_SHRINE_LONG_FORGOTTEN).weight(15))
                            .with(ItemEntry.builder(Items.DIAMOND).weight(30).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
                            .with(ItemEntry.builder(Items.GOLD_INGOT).weight(55).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 10.0f))))
                    )
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(2.0f, 3.0f))
                            .with(ItemEntry.builder(ModItems.MAKAITE_INGOT).weight(48).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 6.0f))))
                            .with(ItemEntry.builder(Items.EMERALD).weight(15).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f))))
                            .with(ItemEntry.builder(Items.GOLD_INGOT).weight(27).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 5.0f))))
                            .with(ItemEntry.builder(ModItems.DRAGON_GEM).weight(5))
                            .with(ItemEntry.builder(ModItems.HEALING_CHARM).weight(5).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f))))
                    )
                    .pool(LootPool.builder().rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(ItemEntry.builder(Items.BOOK).weight(20).apply(new EnchantRandomlyLootFunction.Builder().add(ModEnchantments.DANMAKU_PROTECTION).add(ModEnchantments.YOUKAI_BUSTER)))
                            .with(ItemEntry.builder(ModItems.MAKAITE_HELMET).weight(20).apply(EnchantRandomlyLootFunction.builder()))
                            .with(ItemEntry.builder(ModItems.MAKAITE_CHESTPLATE).weight(20).apply(EnchantRandomlyLootFunction.builder()))
                            .with(ItemEntry.builder(ModItems.MAKAITE_LEGGINGS).weight(20).apply(EnchantRandomlyLootFunction.builder()))
                            .with(ItemEntry.builder(ModItems.MAKAITE_BOOTS).weight(20).apply(EnchantRandomlyLootFunction.builder()))
                    )
            );
            consumer.accept(ModLootTables.ABANDONED_SHRINE_DANMAKU_CHEST, LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(3.0f, 6.0f))
                            .with(TagEntry.expandBuilder(ModTags.Items.BULLET_CORES).weight(5))
                            .with(ItemEntry.builder(ModItems.FAITH_ITEM).weight(10).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
                            .with(ItemEntry.builder(ModItems.POWER_ITEM).weight(35).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 12.0f))))
                            .with(ItemEntry.builder(ModItems.POINT_ITEM).weight(35).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 12.0f))))
                            .with(ItemEntry.builder(ModItems.BIG_POWER_ITEM).weight(10).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
                            .with(ItemEntry.builder(ModItems.RING_PATTERN_TEMPLATE).weight(5))
                    )
            );
            consumer.accept(ModLootTables.ABANDONED_SHRINE_SUPPLY_CHEST, LootTable.builder()
                    .pool(LootPool.builder().rolls(UniformLootNumberProvider.create(3.0f, 10.0f))
                            .with(ItemEntry.builder(Items.PAPER).weight(18).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 12.0f))))
                            .with(ItemEntry.builder(Items.BOOK).weight(7).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 3.0f))))
                            .with(ItemEntry.builder(Items.POTATO).weight(8).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 4.0f))))
                            .with(ItemEntry.builder(Items.POISONOUS_POTATO).weight(16).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 6.0f))))
                            .with(ItemEntry.builder(Items.WHEAT).weight(8).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(8.0f, 21.0f))))
                            .with(ItemEntry.builder(Items.ROTTEN_FLESH).weight(18).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3.0f, 8.0f))))
                            .with(ItemEntry.builder(ModItems.BOMB_ITEM).weight(5))
                            .with(ItemEntry.builder(Items.IRON_INGOT).weight(12).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 6.0f))))
                            .with(ItemEntry.builder(Items.SUSPICIOUS_STEW).weight(8).apply(SetStewEffectLootFunction.builder().withEffect(StatusEffects.LEVITATION, UniformLootNumberProvider.create(7.0f, 10.0f))))
                    )
            );
        }
    }
}
