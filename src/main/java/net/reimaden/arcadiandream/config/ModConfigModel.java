package net.reimaden.arcadiandream.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.*;
import net.reimaden.arcadiandream.ArcadianDream;

@SuppressWarnings("unused")
@Modmenu(modId = ArcadianDream.MOD_ID)
@Config(name = "arcadian-dream", wrapperName = "ArcadianDreamConfig")
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
}
