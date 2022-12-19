/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util.client;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModModels {

    public static final ModelIdentifier NUE_TRIDENT = new ModelIdentifier(ArcadianDream.MOD_ID, "nue_trident_inventory", "inventory");

    public static void register() {
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) ->
                out.accept(NUE_TRIDENT));
    }
}
