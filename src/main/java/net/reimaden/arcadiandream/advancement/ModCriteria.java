/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.advancement;

import net.minecraft.advancement.criterion.Criteria;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModCriteria {

    public static final ModCriterion RITUAL_CRAFTING = registerCriteria("ritual_crafting");

    @SuppressWarnings("SameParameterValue")
    private static ModCriterion registerCriteria(String name) {
            return Criteria.register(new ModCriterion(name));
    }

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering items for " + ArcadianDream.MOD_ID);
    }
}
