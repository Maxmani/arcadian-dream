/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.model;

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.model.trinket.MagatamaNecklaceModel;

public class ModEntityModelLayers {

    public static final EntityModelLayer MAGATAMA_NECKLACE = registerModelLayer("magatama_necklace");

    @SuppressWarnings("SameParameterValue")
    private static EntityModelLayer registerModelLayer(String name) {
        return new EntityModelLayer(new Identifier(ArcadianDream.MOD_ID, name), "main");
    }

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(MAGATAMA_NECKLACE, MagatamaNecklaceModel::getTexturedModelData);
    }
}
