package net.reimaden.arcadiandream.util;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.block.entity.MysteriousSealBlockEntity;

public class TntHelper {

    public static boolean canIgnite(World world, BlockPos pos) {
        return BlockPos.streamOutwards(pos, MysteriousSealBlockEntity.RANGE, MysteriousSealBlockEntity.RANGE, MysteriousSealBlockEntity.RANGE)
                .noneMatch(seal -> world.getBlockState(seal).getBlock() == ModBlocks.MYSTERIOUS_SEAL);
    }
}
