/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.villager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.poi.PointOfInterestType;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseShotItem;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.ColorMap;
import net.reimaden.arcadiandream.util.ModTags;

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
    private static final int WANDERING_TRADER_XP = 1;
    private static final float LOW_PRICE_MULTIPLIER = 0.05f;
    private static final float HIGH_PRICE_MULTIPLIER = 0.2f;

    private static final Random RANDOM = Random.create();

    private static final String ANTIQUARIAN_NAME = "antiquarian";
    public static final PointOfInterestType ANTIQUARIAN_POI = registerPOI(ANTIQUARIAN_NAME, ModBlocks.DANMAKU_CRAFTING_TABLE);
    public static final VillagerProfession ANTIQUARIAN = registerProfession(ANTIQUARIAN_NAME,
            RegistryKey.of(Registries.POINT_OF_INTEREST_TYPE.getKey(), new Identifier(ArcadianDream.MOD_ID, ANTIQUARIAN_NAME)),
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
        registerWanderingTraderTrades();
    }

    @SuppressWarnings("CodeBlock2Expr")
    private static void registerTrades() {
        // Novice trades
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
        // Apprentice trades
        TradeOfferHelper.registerVillagerOffers(ANTIQUARIAN, 2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 12),
                    new ItemStack(ModItems.FAITH_ITEM),
                    DEFAULT_MAX_USES, APPRENTICE_BUY_XP, LOW_PRICE_MULTIPLIER
            ));
        });
        // Journeyman trades
        TradeOfferHelper.registerVillagerOffers(ANTIQUARIAN, 3, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 16),
                    new ItemStack(ModItems.HEALING_CHARM),
                    DEFAULT_MAX_USES, JOURNEYMAN_BUY_XP, HIGH_PRICE_MULTIPLIER
            ));
        });
        // Expert trades
        TradeOfferHelper.registerVillagerOffers(ANTIQUARIAN, 4, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 32),
                    getVillagerBullet(),
                    RARE_MAX_USES, EXPERT_BUY_XP, HIGH_PRICE_MULTIPLIER
            ));
        });
        // Master trades
        TradeOfferHelper.registerVillagerOffers(ANTIQUARIAN, 5, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 42),
                    new ItemStack(ModItems.BOMB_ITEM),
                    RARE_MAX_USES, MASTER_TRADE_XP, HIGH_PRICE_MULTIPLIER
            ));
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 32),
                    getVillagerBullet(),
                    RARE_MAX_USES, MASTER_TRADE_XP, HIGH_PRICE_MULTIPLIER
            ));
        });
    }

    private static ItemStack getVillagerBullet() {
        final ImmutableList<Item> shots = ModTags.SHOTS;
        ItemStack stack = new ItemStack(shots.get(RANDOM.nextInt(shots.size())));
        BaseShotItem shot = (BaseShotItem) stack.getItem();
        shot.setPower(stack, 10 - RANDOM.nextInt(5));
        shot.setSpeed(stack, 0.6f + RANDOM.nextFloat() * 0.6f);
        shot.setDuration(stack, 40 + RANDOM.nextInt(41));
        shot.setCooldown(stack, 30 + RANDOM.nextInt(31));
        shot.setDensity(stack, 3 + RANDOM.nextInt(6));
        shot.setColor(stack, ColorMap.getRandomBulletColor(RANDOM));
        return stack;
    }

    @SuppressWarnings("CodeBlock2Expr")
    private static void registerWanderingTraderTrades() {
        // Ordinary trades
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 12),
                    new ItemStack(ModItems.GHASTLY_LANTERN),
                    RARE_MAX_USES, WANDERING_TRADER_XP, LOW_PRICE_MULTIPLIER
            ));
        });
        // Special trades
        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                    new ItemStack(Items.EMERALD, 8),
                    new ItemStack(ModItems.LIFE_FRAGMENT),
                    RARE_MAX_USES, WANDERING_TRADER_XP, LOW_PRICE_MULTIPLIER
            ));
        });
    }
}
