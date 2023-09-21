/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.model;

import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.minecraft.client.util.ModelIdentifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModModelProviders {

    public static final ModelIdentifier NUE_TRIDENT = registerModel("nue_trident_in_hand");
    public static final ModelIdentifier FOLDING_CHAIR = registerModel("folding_chair_in_hand");

    private static ModelIdentifier registerModel(String path) {
        return new ModelIdentifier(ArcadianDream.MOD_ID, path, "inventory");
    }

    public static void register() {
        ModelLoadingPlugin.register(pluginContext -> pluginContext.addModels(NUE_TRIDENT, FOLDING_CHAIR));
    }
}
