/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.reimaden.arcadiandream.block.entity.client.ModBlockEntityRenderer;
import net.reimaden.arcadiandream.entity.client.EntityRenderer;
import net.reimaden.arcadiandream.gui.ModScreens;
import net.reimaden.arcadiandream.model.ModArmorRenderer;
import net.reimaden.arcadiandream.model.ModModelHandler;
import net.reimaden.arcadiandream.networking.ModMessages;
import net.reimaden.arcadiandream.particle.ModParticles;
import net.reimaden.arcadiandream.util.ModModelPredicateProvider;
import net.reimaden.arcadiandream.util.client.ModColorProviders;
import net.reimaden.arcadiandream.util.client.ModModelProviders;

public class ArcadianDreamClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModModelProviders.register();
        EntityRenderer.register();
        ModParticles.registerClient();
        ModColorProviders.register();
        ModMessages.registerS2CPackets();
        ModModelPredicateProvider.register();
        ModModelHandler.register((loc, def) -> EntityModelLayerRegistry.registerModelLayer(loc, () -> def));
        ModArmorRenderer.register();
        ModBlockEntityRenderer.register();
        ModScreens.registerClient();
    }
}
