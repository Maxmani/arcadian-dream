/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A class that maps color names to their respective integer values.
 * These values are used across the mod, mostly for bullet colors.
 */
public class ColorMap {

    public static final int DEFAULT_COLOR = 16711680;
    private static final Map<Integer, String> colorMap = new LinkedHashMap<>();

    static {
        colorMap.put(DEFAULT_COLOR, "red");
        colorMap.put(65280, "green");
        colorMap.put(1908223, "blue");
        colorMap.put(16770048, "yellow");
        colorMap.put(10494192, "purple");
        colorMap.put(65535, "cyan");
        colorMap.put(16753920, "orange");
        colorMap.put(3847130, "light_blue");
        colorMap.put(16711935, "magenta");
        colorMap.put(12582656, "lime");
        colorMap.put(16777215, "white");
        colorMap.put(657930, "black");
        colorMap.put(8421504, "light_gray");
        colorMap.put(4210752, "gray");
        colorMap.put(15961002, "pink");
        colorMap.put(9849600, "brown");
    }

    private ColorMap() {}

    public static int getColorInt(String colorName) {
        for (Map.Entry<Integer, String> entry : colorMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(colorName)) {
                return entry.getKey();
            }
        }

        throw new IllegalArgumentException("Invalid color name: " + colorName);
    }

    public static String getColorName(int color) {
        return colorMap.get(color);
    }

    public static MutableText getTranslationKey(int color) {
        String colorName = getColorName(color);
        return Text.translatable("color.minecraft." + colorName);
    }

    @SuppressWarnings("unused")
    public static int getRandomColor(Random random) {
        int index = random.nextInt(colorMap.size());
        return (int) colorMap.keySet().toArray()[index];
    }

    // Includes only sane colors for bullets
    public static int getRandomBulletColor(Random random) {
        int index = random.nextInt(colorMap.size() - 5);
        return (int) colorMap.keySet().toArray()[index];
    }

    public static boolean matchesMap(int color) {
        return colorMap.containsKey(color);
    }
}
