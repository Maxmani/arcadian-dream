/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.compat;

import net.minecraft.text.Text;
import net.reimaden.arcadiandream.ArcadianDream;

public class TooltipHelper {

    private TooltipHelper() {}

    public static Text moonPhase(byte moonPhase) {
        Text tooltip;
        switch (moonPhase) {
            case 0 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_0");
            case 1 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_1");
            case 2 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_2");
            case 3 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_3");
            case 4 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_4");
            case 5 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_5");
            case 6 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_6");
            case 7 -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_7");
            default -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.moon_phase_invalid");
        }

        return Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.requirement", tooltip);
    }

    public static Text dimension(String dimension) {
        Text tooltip;
        switch (dimension) {
            case "overworld" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.dimension_overworld");
            case "the_nether" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.dimension_nether");
            case "the_end" -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.dimension_end");
            default -> tooltip = Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.dimension_unknown", dimension);
        }

        return Text.translatable(ArcadianDream.MOD_ID + ".ritual_crafting.requirement", tooltip);
    }
}
