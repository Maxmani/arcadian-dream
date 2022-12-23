/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.advancement;

import net.minecraft.advancement.criterion.Criteria;

public class ModCriteria {

    public static RitualCraftingCriterion RITUAL_CRAFTING;

    public static void register() {
        RITUAL_CRAFTING = Criteria.register(new RitualCraftingCriterion());
    }
}
