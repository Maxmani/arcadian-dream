/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item;

import net.minecraft.item.trim.ArmorTrimMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModArmorTrimMaterials {

    public static final RegistryKey<ArmorTrimMaterial> MAKAITE = createArmorTrim("makaite");

    @SuppressWarnings("SameParameterValue")
    private static RegistryKey<ArmorTrimMaterial> createArmorTrim(String name) {
        return RegistryKey.of(RegistryKeys.TRIM_MATERIAL, new Identifier(ArcadianDream.MOD_ID, name));
    }
}
