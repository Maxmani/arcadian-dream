package net.reimaden.arcadiandream.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.util.TntHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TntBlock.class)
public class TntBlockMixin {

    @ModifyExpressionValue(method = "onBlockAdded", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isReceivingRedstonePower(Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean arcadiandream$preventIgnitionOnBlockAdded(boolean original, BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify) {
        return original && TntHelper.canIgnite(world, pos);
    }

    @ModifyExpressionValue(method = "neighborUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;isReceivingRedstonePower(Lnet/minecraft/util/math/BlockPos;)Z"))
    private boolean arcadiandream$preventIgnitionUpdate(boolean original, BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        return original && TntHelper.canIgnite(world, pos);
    }

    @ModifyExpressionValue(method = "onBreak", at = @At(value = "INVOKE", target = "Ljava/lang/Boolean;booleanValue()Z"))
    private boolean arcadiandream$preventIgnitionOnBreak(boolean original, World world, BlockPos pos, BlockState state, PlayerEntity player) {
        // Has a side effect of the block not being dropped if it's unstable (this is a loot table thing).
        // Should be fine since punching an unstable TNT block would explode it anyway.
        return original && TntHelper.canIgnite(world, pos);
    }

    @ModifyExpressionValue(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private boolean arcadiandream$preventIgnitionWithFlintAndSteel(boolean original, BlockState state, World world, BlockPos pos, PlayerEntity player2, Hand hand, BlockHitResult hit) {
        return original && TntHelper.canIgnite(world, pos);
    }

    @ModifyExpressionValue(method = "onUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 1))
    private boolean arcadiandream$preventIgnitionWithFireCharge(boolean original, BlockState state, World world, BlockPos pos, PlayerEntity player2, Hand hand, BlockHitResult hit) {
        return original && TntHelper.canIgnite(world, pos);
    }

    @ModifyExpressionValue(method = "onProjectileHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;isOnFire()Z"))
    private boolean arcadiandream$preventIgnitionOnProjectileHit(boolean original, World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        return original && TntHelper.canIgnite(world, hit.getBlockPos());
    }
}
