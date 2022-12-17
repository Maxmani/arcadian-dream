/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.compat;

/**
 * Locations of the Ritual Crafting slots in a recipe viewer GUI.
 */
public interface IOnbashiraLocations {

    int[][] ONBASHIRAS = {
            // Inner onbashiras X, Y
            {84, 30},
            {120, 48},
            {138, 84},
            {120, 120},
            {84, 138},
            {48, 120},
            {30, 84},
            {48, 48},
            // Outer onbashiras X, Y
            {57, 12},
            {111, 12},
            {156, 57},
            {156, 111},
            {111, 156},
            {56, 156},
            {12, 111},
            {12, 57}
    };

    int[] OUTPUT_SLOT = {84, 84};
}
