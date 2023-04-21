/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.advancement;

import net.minecraft.advancement.criterion.Criteria;
import net.reimaden.arcadiandream.ArcadianDream;

public class ModCriteria {

    public static final RitualCraftingCriterion RITUAL_CRAFTING = Criteria.register(new RitualCraftingCriterion());
    public static final DanmakuModifiedCriterion DANMAKU_MODIFIED = Criteria.register(new DanmakuModifiedCriterion());
    public static final BulletsCancelledCriterion BULLETS_CANCELLED = Criteria.register(new BulletsCancelledCriterion());

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering criteria for " + ArcadianDream.MOD_ID);
    }
}
