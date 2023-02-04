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

    public static final Identifier INTERACT_WITH_RITUAL_SHRINE = registerStat("interact_with_ritual_shrine", StatFormatter.DEFAULT);
    public static final Identifier INTERACT_WITH_ONBASHIRA = registerStat("interact_with_onbashira", StatFormatter.DEFAULT);
    public static final Identifier INTERACT_WITH_DANMAKU_CRAFTING_TABLE = registerStat("interact_with_danmaku_crafting_table", StatFormatter.DEFAULT);

    @SuppressWarnings("SameParameterValue")
    private static Identifier registerStat(String id, StatFormatter formatter) {
        Identifier identifier = new Identifier(ArcadianDream.MOD_ID, id);
        Registry.register(Registries.CUSTOM_STAT, id, identifier);
        Stats.CUSTOM.getOrCreateStat(identifier, formatter);
        return identifier;
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering statistics for " + ArcadianDream.MOD_ID);
    }
}
