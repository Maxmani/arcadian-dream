/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block.client;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import net.reimaden.arcadiandream.block.ModBlocks;

public class ModBlockRenderLayers {

    public static void register() {
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.MYSTERIOUS_SEAL, RenderLayer.getCutout());
    }
}
