/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FireBlock;
import net.minecraft.block.TntBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.util.TntHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireBlock.class)
public class FireBlockMixin {

    @Inject(method = "trySpreadingFire", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$preventRemovingTnt(World world, BlockPos pos, int spreadFactor, Random random, int currentAge, CallbackInfo ci) {
        BlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        if (block instanceof TntBlock && !TntHelper.canIgnite(world, pos)) {
            ci.cancel();
        }
    }
}
