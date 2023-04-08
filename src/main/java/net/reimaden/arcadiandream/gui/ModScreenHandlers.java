/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {

    public static ScreenHandlerType<DanmakuCraftingScreenHandler> DANMAKU_CRAFTING_SCREEN_HANDLER;

    public static void register() {
        DANMAKU_CRAFTING_SCREEN_HANDLER = new ScreenHandlerType<>(DanmakuCraftingScreenHandler::new, FeatureFlags.VANILLA_FEATURES);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        HandledScreens.register(DANMAKU_CRAFTING_SCREEN_HANDLER, DanmakuCraftingScreen::new);
    }
}
