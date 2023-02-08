/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.entity.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum FairyVariant {

    BLUE(0),
    RED(1),
    GREEN(2);

    private static final FairyVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator
            .comparingInt(FairyVariant::getId)).toArray(FairyVariant[]::new);
    private final int id;

    FairyVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static FairyVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
