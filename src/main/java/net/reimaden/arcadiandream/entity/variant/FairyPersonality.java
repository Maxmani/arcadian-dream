/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum FairyPersonality {

    AGGRESSIVE(0),
    CURIOUS(1),
    CONFIDENT(2),
    PLAYFUL(3),
    SPONTANEOUS(4),
    TIMID(5);

    private static final FairyPersonality[] BY_ID = Arrays.stream(values()).sorted(Comparator
            .comparingInt(FairyPersonality::getId)).toArray(FairyPersonality[]::new);
    private final int id;

    FairyPersonality(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static FairyPersonality byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
