/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * A class that maps color names to their respective integer values.
 * These values are used across the mod, mostly for bullet colors.
 */
public class ColorMap {
    private static final Map<Integer, String> colorMap = new HashMap<>();

    static {
        colorMap.put(16711680, "red");
        colorMap.put(65280, "green");
        colorMap.put(255, "blue");
        colorMap.put(16776960, "yellow");
        colorMap.put(10494192, "purple");
        colorMap.put(65535, "cyan");
        colorMap.put(16777215, "white");
        colorMap.put(0, "black");
        colorMap.put(8421504, "light_gray");
        colorMap.put(4210752, "gray");
        colorMap.put(16761035, "pink");
        colorMap.put(9849600, "brown");
        colorMap.put(16753920, "orange");
        colorMap.put(11393254, "light_blue");
        colorMap.put(16711935, "magenta");
        colorMap.put(12582656, "lime");
    }

    public static Integer getColorInt(String colorName) {
        for (Map.Entry<Integer, String> entry : colorMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(colorName)) {
                return entry.getKey();
            }
        }

        return null;
    }

    public static String getColorName(int color) {
        return colorMap.get(color);
    }

    public static MutableText getTranslationKey(int color) {
        String colorName = getColorName(color);
        return Text.translatable("color.minecraft." + colorName);
    }
}
