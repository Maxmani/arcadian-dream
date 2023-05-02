/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.client;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.entity.client.renderers.*;

public class ModEntityRenderers {

    public static void register() {
        // Danmaku
        EntityRendererRegistry.register(ModEntities.CIRCLE_BULLET, ctx -> new BulletEntityRenderer(ctx, 1.0f));
        EntityRendererRegistry.register(ModEntities.BUBBLE_BULLET, ctx -> new BubbleBulletEntityRenderer(ctx, 4.0f));
        EntityRendererRegistry.register(ModEntities.AMULET_BULLET, ctx -> new FlatBulletEntityRenderer(ctx, 1.0f));
        EntityRendererRegistry.register(ModEntities.STAR_BULLET, ctx -> new StarBulletEntityRenderer(ctx, 2.0f));
        EntityRendererRegistry.register(ModEntities.KUNAI_BULLET, ctx -> new FlatBulletEntityRenderer(ctx, 1.0f));
        EntityRendererRegistry.register(ModEntities.PELLET_BULLET, ctx -> new BulletEntityRenderer(ctx, 1.0f));

        // Mobs
        EntityRendererRegistry.register(ModEntities.FAIRY, FairyEntityRenderer::new);
        EntityRendererRegistry.register(ModEntities.SUNFLOWER_FAIRY, SunflowerFairyEntityRenderer::new);
    }
}
