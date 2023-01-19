/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import net.minecraft.nbt.NbtCompound;

public class DataSaver {

    public static void addElixir(IEntityDataSaver player, byte level) {
        NbtCompound nbt = player.getPersistentData();
        byte elixirLevel = nbt.getByte("elixir");

        if (elixirLevel + level >= 3) {
            elixirLevel = 3;
        } else if (elixirLevel < 0) {
            elixirLevel = 0;
            elixirLevel += level;
        } else {
            elixirLevel += level;
        }

        nbt.putByte("elixir", elixirLevel);
    }

    public static void addReflection(IEntityDataSaver bullet, int reflection) {
        NbtCompound nbt = bullet.getPersistentData();
        int reflectionLevel = nbt.getByte("reflections");

        if (reflectionLevel < 0) {
            reflectionLevel = 0;
        }
        reflectionLevel += reflection;

        nbt.putInt("reflections", reflectionLevel);
    }
}
