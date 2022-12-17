/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.painting;

import net.reimaden.arcadiandream.ArcadianDream;
import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

@SuppressWarnings("unused")
public class ModPaintings {

    public static final PaintingVariant REIMADEN = registerPainting("reimaden", new PaintingVariant(16, 16));

    private static PaintingVariant registerPainting(String name, PaintingVariant paintingVariant) {
        return Registry.register(Registry.PAINTING_VARIANT, new Identifier(ArcadianDream.MOD_ID, name), paintingVariant);
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering paintings for " + ArcadianDream.MOD_ID);
    }
}
