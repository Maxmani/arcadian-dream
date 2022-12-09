package net.reimaden.arcadiandream.statistic;

import net.minecraft.stat.StatFormatter;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModStats {

    public static final Identifier INTERACT_WITH_RITUAL_SHRINE = new Identifier(ArcadianDream.MOD_ID, "interact_with_ritual_shrine");
    public static final Identifier INTERACT_WITH_ONBASHIRA = new Identifier(ArcadianDream.MOD_ID, "interact_with_onbashira");

    public static void register() {
        Registry.register(Registry.CUSTOM_STAT, "interact_with_ritual_shrine", INTERACT_WITH_RITUAL_SHRINE);
        Registry.register(Registry.CUSTOM_STAT, "interact_with_onbashira", INTERACT_WITH_ONBASHIRA);

        Stats.CUSTOM.getOrCreateStat(INTERACT_WITH_RITUAL_SHRINE, StatFormatter.DEFAULT);
        Stats.CUSTOM.getOrCreateStat(INTERACT_WITH_ONBASHIRA, StatFormatter.DEFAULT);
    }
}
