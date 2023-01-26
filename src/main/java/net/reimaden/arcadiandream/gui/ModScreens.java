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
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModScreens {

    public static ScreenHandlerType<DanmakuCraftingGuiDescription> DANMAKU_CRAFTING;

    public static void register() {
        DANMAKU_CRAFTING = Registry.register(Registries.SCREEN_HANDLER, new Identifier(ArcadianDream.MOD_ID, "danmaku_crafting"),
                new ScreenHandlerType<>((syncId, inventory) ->
                        new DanmakuCraftingGuiDescription(syncId, inventory, ScreenHandlerContext.EMPTY)));
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        //noinspection RedundantTypeArguments
        HandledScreens.<DanmakuCraftingGuiDescription, DanmakuCraftingScreen>register(ModScreens.DANMAKU_CRAFTING,
                (gui, inventory, title) -> new DanmakuCraftingScreen(gui, inventory.player.getInventory(), title));
    }
}
