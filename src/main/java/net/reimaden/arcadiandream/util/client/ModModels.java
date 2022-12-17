/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util.client;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;

public class ModModels {

    public static void register() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider(((manager, out) ->
                out.accept(new ModelIdentifier("arcadiandream:nue_trident_inventory#inventory"))));
    }
}
