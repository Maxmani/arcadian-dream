/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
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
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.sound.ModSounds;

@SuppressWarnings("unused")
public class ModVillagers {

    private static final int DEFAULT_MAX_USES = 12;
    private static final int COMMON_MAX_USES = 16;
    private static final int RARE_MAX_USES = 3;
    private static final int NOVICE_SELL_XP = 1;
    private static final int NOVICE_BUY_XP = 2;
    private static final int APPRENTICE_SELL_XP = 5;
    private static final int APPRENTICE_BUY_XP = 10;
    private static final int JOURNEYMAN_SELL_XP = 10;
    private static final int JOURNEYMAN_BUY_XP = 20;
    private static final int EXPERT_SELL_XP = 15;
    private static final int EXPERT_BUY_XP = 30;
    private static final int MASTER_TRADE_XP = 30;
    private static final float LOW_PRICE_MULTIPLIER = 0.05f;
    private static final float HIGH_PRICE_MULTIPLIER = 0.2f;

    public static final PointOfInterestType ANTIQUARIAN_POI = registerPOI("antiquarian_poi", ModBlocks.DANMAKU_CRAFTING_TABLE);
    public static final VillagerProfession ANTIQUARIAN = registerProfession("antiquarian",
            RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), new Identifier(ArcadianDream.MOD_ID, "antiquarian_poi")),
            ModSounds.ENTITY_VILLAGER_WORK_ANTIQUARIAN);

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

    public static void registerTrades() {
        // WIP
        TradeOfferHelper.registerVillagerOffers(ANTIQUARIAN, 1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.STAR_ITEM, 16),
                    new ItemStack(Items.EMERALD),
                    DEFAULT_MAX_USES, NOVICE_SELL_XP, LOW_PRICE_MULTIPLIER
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.POWER_ITEM, 8),
                    new ItemStack(Items.EMERALD),
                    COMMON_MAX_USES, NOVICE_SELL_XP, LOW_PRICE_MULTIPLIER
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(ModItems.POINT_ITEM, 8),
                    new ItemStack(Items.EMERALD),
                    COMMON_MAX_USES, NOVICE_SELL_XP, LOW_PRICE_MULTIPLIER
            ));
        });
        TradeOfferHelper.registerVillagerOffers(ANTIQUARIAN, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModItems.HEALING_CHARM),
                    DEFAULT_MAX_USES, APPRENTICE_BUY_XP, HIGH_PRICE_MULTIPLIER
            ));
        });
    }
}
