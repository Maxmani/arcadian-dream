package net.reimaden.arcadiandream.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.reimaden.arcadiandream.item.ModItems;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.condition.MatchToolLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.util.Identifier;

public class ModLootTableModifiers {

    private static final Identifier END_CITY_STRUCTURE_TREASURE_ID = new Identifier("minecraft", "chests/end_city_treasure");
    private static final Identifier OAK_LEAVES_ID = new Identifier("minecraft", "blocks/oak_leaves");

    public static void modify() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {

            if (END_CITY_STRUCTURE_TREASURE_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.35f))
                        .with(ItemEntry.builder(ModItems.DRAGON_GEM))
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 2.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }

            if (OAK_LEAVES_ID.equals(id)) {
                LootPool.Builder poolBuilder = LootPool.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.10f))
                        .with(ItemEntry.builder(ModItems.HEAVENLY_PEACH))
                        .conditionally(MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(ModItems.HISOU_SWORD)))
                        .conditionally(LocationCheckLootCondition.builder(LocationPredicate.Builder.create().y(NumberRange.FloatRange.atLeast(150))))
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                tableBuilder.pool(poolBuilder.build());
            }
        });
    }
}
