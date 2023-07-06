/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.reimaden.arcadiandream.block.client.ModBlockRenderLayers;
import net.reimaden.arcadiandream.block.entity.client.ModBlockEntityRenderer;
import net.reimaden.arcadiandream.entity.client.ModEntityRenderers;
import net.reimaden.arcadiandream.gui.ModScreenHandlers;
import net.reimaden.arcadiandream.model.ModEntityModelLayers;
import net.reimaden.arcadiandream.model.trinket.ModTrinketRenderers;
import net.reimaden.arcadiandream.networking.ModMessages;
import net.reimaden.arcadiandream.particle.ModParticles;
import net.reimaden.arcadiandream.util.client.ModModelPredicateProvider;
import net.reimaden.arcadiandream.util.client.ModColorProviders;
import net.reimaden.arcadiandream.util.client.ModModelProviders;
import net.reimaden.arcadiandream.util.client.StaminaHUD;

public class ArcadianDreamClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModModelProviders.register();
        ModEntityRenderers.register();
        ModParticles.registerClient();
        ModColorProviders.register();
        ModMessages.registerS2CPackets();
        ModMessages.registerC2SPackets();
        HudRenderCallback.EVENT.register(new StaminaHUD());
        ModModelPredicateProvider.register();
        ModBlockEntityRenderer.register();
        ModScreenHandlers.registerClient();
        ModBlockRenderLayers.register();
        ModEntityModelLayers.register();
        ModTrinketRenderers.register();
    }
}
