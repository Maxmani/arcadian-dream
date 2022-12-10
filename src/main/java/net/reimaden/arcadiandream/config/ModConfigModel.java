package net.reimaden.arcadiandream.config;

import io.wispforest.owo.config.Option;
import io.wispforest.owo.config.annotation.Config;
import io.wispforest.owo.config.annotation.Modmenu;
import io.wispforest.owo.config.annotation.RangeConstraint;
import io.wispforest.owo.config.annotation.Sync;
import net.reimaden.arcadiandream.ArcadianDream;

@SuppressWarnings("unused")
@Modmenu(modId = ArcadianDream.MOD_ID)
@Config(name = "arcadian-dream", wrapperName = "ArcadianDreamConfig")
public class ModConfigModel {

    @Sync(Option.SyncMode.OVERRIDE_CLIENT)
    public boolean canUseChisel = true;

    @RangeConstraint(min = 2, max = 100)
    public int maxChiselDistance = 16;
}
