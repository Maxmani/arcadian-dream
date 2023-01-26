/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.statistic;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModStats {

    public static final Identifier INTERACT_WITH_RITUAL_SHRINE = new Identifier(ArcadianDream.MOD_ID, "interact_with_ritual_shrine");
    public static final Identifier INTERACT_WITH_ONBASHIRA = new Identifier(ArcadianDream.MOD_ID, "interact_with_onbashira");

    public static void register() {
        Registry.register(Registries.CUSTOM_STAT, "interact_with_ritual_shrine", INTERACT_WITH_RITUAL_SHRINE);
        Registry.register(Registries.CUSTOM_STAT, "interact_with_onbashira", INTERACT_WITH_ONBASHIRA);

        Stats.CUSTOM.getOrCreateStat(INTERACT_WITH_RITUAL_SHRINE, StatFormatter.DEFAULT);
        Stats.CUSTOM.getOrCreateStat(INTERACT_WITH_ONBASHIRA, StatFormatter.DEFAULT);
    }
}
