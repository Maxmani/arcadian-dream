/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block.entity.client;

import net.minecraft.client.render.block.entity.BlockEntityRendererFactories;
import net.reimaden.arcadiandream.block.entity.ModBlockEntities;

public class ModBlockEntityRenderer {

    public static void register() {
        BlockEntityRendererFactories.register(ModBlockEntities.ONBASHIRA, OnbashiraBlockEntityRenderer::new);
        BlockEntityRendererFactories.register(ModBlockEntities.RITUAL_SHRINE, RitualShrineBlockEntityRenderer::new);
    }
}
