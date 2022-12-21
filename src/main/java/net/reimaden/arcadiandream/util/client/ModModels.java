/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util.client;

import net.minecraft.data.client.Model;
import net.minecraft.data.client.TextureKey;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

import java.util.Optional;

public class ModModels {

    public static final Model HANDHELD_BIG = ModModels.item("handheld_big", TextureKey.LAYER0);

    @SuppressWarnings("SameParameterValue")
    private static Model item(String parent, TextureKey... requiredTextureKeys) {
        return new Model(Optional.of(new Identifier(ArcadianDream.MOD_ID, "item/" + parent)), Optional.empty(), requiredTextureKeys);
    }
}
