/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin.client;

import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.item.ModItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Predicate;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final MinecraftClient client;

    @ModifyArg(method = "updateTargetedEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileUtil;raycast(Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/util/math/Box;Ljava/util/function/Predicate;D)Lnet/minecraft/util/hit/EntityHitResult;"), index = 4)
    private Predicate<Entity> arcadiandream$ignoreBullets(Predicate<Entity> original) {
        final ClientPlayerEntity player = client.player;
        if (player == null) return original;

        //noinspection OptionalGetWithoutIsPresent
        if (TrinketsApi.getTrinketComponent(player).get().isEquipped(ModItems.MAGATAMA_NECKLACE)
                && player.getItemCooldownManager().getCooldownProgress(ModItems.MAGATAMA_NECKLACE, 0.0f) == 0) {
            return original;
        }
        return entity -> !entity.isSpectator() && entity.canHit() && !(entity instanceof BaseBulletEntity);
    }
}
