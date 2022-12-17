/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block;

import net.minecraft.block.LeveledCauldronBlock;
import net.minecraft.block.cauldron.CauldronBehavior;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.reimaden.arcadiandream.item.custom.danmaku.DyeableBullet;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.item.custom.danmaku.BaseBulletItem;
import net.reimaden.arcadiandream.statistic.ModStats;

// I might get rid of this later, for now it's pretty cool to have around
public class ModCauldronBehavior {

    private static final CauldronBehavior CLEAN_BULLET = (state, world, pos, player, hand, stack) -> {
        Item item = stack.getItem();
        if (!(item instanceof BaseBulletItem)) {
            return ActionResult.PASS;
        }
        DyeableBullet dyeableBullet = (DyeableBullet) item;
        if (!dyeableBullet.hasColor(stack)) {
            return ActionResult.PASS;
        }
        if (!world.isClient) {
            dyeableBullet.removeColor(stack);
            player.incrementStat(ModStats.CLEAN_BULLET);
            LeveledCauldronBlock.decrementFluidLevel(state, world, pos);
        }
        return ActionResult.success(world.isClient);
    };

    public static void register() {
        CauldronBehavior.WATER_CAULDRON_BEHAVIOR.put(ModItems.ORB_BULLET, CLEAN_BULLET);
    }
}
