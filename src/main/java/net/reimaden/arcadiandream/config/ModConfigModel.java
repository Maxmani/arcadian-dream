/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import net.reimaden.arcadiandream.ArcadianDream;

@SuppressWarnings("unused")
@Modmenu(modId = ArcadianDream.MOD_ID)
@Config(name = ArcadianDream.MOD_ID, wrapperName = "ArcadianDreamConfig")
public class ModConfigModel {

    @SectionHeader("items")

    @Nest
    public ChiselOptions chiselOptions = new ChiselOptions();

    public static class ChiselOptions {
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public boolean canUse = true;

        @RangeConstraint(min = 1, max = 64)
        public int maxDistance = 16;
    }

    @Nest
    public NueTridentOptions nueTridentOptions = new NueTridentOptions();

    public static class NueTridentOptions {
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public boolean canHaveImpaling = true;
    }

    @Nest
    public HisouSwordOptions hisouSwordOptions = new HisouSwordOptions();

    public static class HisouSwordOptions {
        @RestartRequired
        @RangeConstraint(min = 64, max = 256)
        public int minHeightForPeaches = 196;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public boolean canDisableShields = true;
    }

    @Nest
    public MochiMalletOptions mochiMalletOptions = new MochiMalletOptions();

    public static class MochiMalletOptions {
        public boolean lowViolence = false;
    }

    @Nest
    public HouraiElixirOptions houraiElixirOptions = new HouraiElixirOptions();

    public static class HouraiElixirOptions {
        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public boolean canDrink = false;

        public boolean nerfElixir = true;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        public boolean allowDying = false;

        @Sync(Option.SyncMode.OVERRIDE_CLIENT)
        @RangeConstraint(min = 1, max = 5)
        public int elixirFatigueThreshold = 1;
    }

    @Nest
    public FoldingChairOptions foldingChairOptions = new FoldingChairOptions();

    public static class FoldingChairOptions {
        @RangeConstraint(min = 0.0, max = 10.0, decimalPlaces = 1)
        public double knockbackStrength = 1.5;
    }

    @Nest
    public IcicleSwordOptions icicleSwordOptions = new IcicleSwordOptions();

    public static class IcicleSwordOptions {
        public boolean meltsInstantly = true;
    }

    @Nest
    public FairyCharmOptions fairyCharmOptions = new FairyCharmOptions();

    public static class FairyCharmOptions {
        public boolean canPreventSpawning = true;

        @RangeConstraint(min = 25, max = 128, decimalPlaces = 0)
        public double distance = 128;
    }

    @SectionHeader("danmaku")

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RangeConstraint(min = 1, max = 4)
    public int danmakuCooldownMultiplier = 1;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    @RangeConstraint(min = 1, max = 4)
    public float danmakuDamageMultiplier = 1;

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean cooldownPerBulletType = true;

    @SectionHeader("mobs")

    @Nest
    public FairyOptions fairyOptions = new FairyOptions();

    public static class FairyOptions {
        public boolean spawnFairies = true;
        public boolean spawnSunflowerFairies = true;
        public boolean spawnIceFairies = true;
    }
}
