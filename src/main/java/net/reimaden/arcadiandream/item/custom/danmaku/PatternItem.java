/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.danmaku;

import net.reimaden.arcadiandream.item.custom.misc.PatternTemplateItem;
import org.jetbrains.annotations.NotNull;

public class PatternItem extends PatternTemplateItem {

    public PatternItem(Settings settings) {
        super(settings);
    }

    @Override
    protected @NotNull String getString() {
        return "_pattern";
    }
}
