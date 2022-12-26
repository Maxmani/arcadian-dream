/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.villager;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.item.ModItems;

public class ModVillagers {

    @SuppressWarnings("unused")
    public static final PointOfInterestType ANTIQUARIAN_POI = registerPOI("antiquarian_poi", ModBlocks.DANMAKU_CRAFTING_TABLE);
    public static final VillagerProfession ANTIQUARIAN = registerProfession("antiquarian",
            RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), new Identifier(ArcadianDream.MOD_ID, "antiquarian_poi")),
            SoundEvents.ENTITY_VILLAGER_WORK_TOOLSMITH);

    @SuppressWarnings("SameParameterValue")
    private static VillagerProfession registerProfession(String name, RegistryKey<PointOfInterestType> type, SoundEvent workSound) {
        return Registry.register(Registries.VILLAGER_PROFESSION, new Identifier(ArcadianDream.MOD_ID, name),
                new VillagerProfession(name, e -> e.matchesKey(type), e -> e.matchesKey(type), ImmutableSet.of(), ImmutableSet.of(), workSound));
    }

    @SuppressWarnings("SameParameterValue")
    private static PointOfInterestType registerPOI(String name, Block block) {
        return PointOfInterestHelper.register(new Identifier(ArcadianDream.MOD_ID, name),
                1, 1, ImmutableSet.copyOf(block.getStateManager().getStates()));
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering villagers for " + ArcadianDream.MOD_ID);
        registerTrades();
    }

    @SuppressWarnings("unused")
    public static void registerTrades() {
        int defaultMaxUses = 12;
        int commonMaxUses = 16;
        int rareMaxUses = 3;

        int noviceSellXP = 1;
        int noviceBuyXP = 2;
        int apprenticeSellXP = 5;
        int apprenticeBuyXP = 10;
        int journeymanSellXP = 10;
        int journeymanBuyXP = 20;
        int expertSellXP = 15;
        int expertBuyXP = 30;
        int masterTradeXP = 30;

        float lowPriceMultiplier = 0.05f;
        float highPriceMultiplier = 0.2f;

        // WIP
        TradeOfferHelper.registerVillagerOffers(ANTIQUARIAN, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.STAR_ITEM, 16),
                    new ItemStack(Items.EMERALD),
                    defaultMaxUses, noviceSellXP, lowPriceMultiplier
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.POWER_ITEM, 8),
                    new ItemStack(Items.EMERALD),
                    commonMaxUses, noviceSellXP, lowPriceMultiplier
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.POINT_ITEM, 8),
                    new ItemStack(Items.EMERALD),
                    commonMaxUses, noviceSellXP, lowPriceMultiplier
            ));
        });
        TradeOfferHelper.registerVillagerOffers(ANTIQUARIAN, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModItems.HEALING_CHARM),
                    defaultMaxUses, apprenticeBuyXP, highPriceMultiplier
            ));
        });
    }
}
