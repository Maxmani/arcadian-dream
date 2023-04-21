/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModScreenHandlers {

    public static ScreenHandlerType<DanmakuCraftingScreenHandler> DANMAKU_CRAFTING_SCREEN_HANDLER;

    public static void register() {
        DANMAKU_CRAFTING_SCREEN_HANDLER = registerScreenHandler("danmaku_crafting", DanmakuCraftingScreenHandler::new);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends ScreenHandler> ScreenHandlerType<T> registerScreenHandler(String name, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(ArcadianDream.MOD_ID, name), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        HandledScreens.register(DANMAKU_CRAFTING_SCREEN_HANDLER, DanmakuCraftingScreen::new);
    }
}
