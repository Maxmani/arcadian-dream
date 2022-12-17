/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin.client;

import dev.emi.trinkets.api.TrinketsApi;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.particle.ModParticles;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {

    @Final @Shadow private MinecraftClient client;

    @Inject(method = "getActiveTotemOfUndying", at = @At("RETURN"), cancellable = true)
    private static void getExtendItem(PlayerEntity player, CallbackInfoReturnable<ItemStack> cir) {
        Item item = ModItems.EXTEND_ITEM;
        if (TrinketsApi.getTrinketComponent(player).get().isEquipped(item)) {
            cir.setReturnValue(item.getDefaultStack());
        }
    }

    @Inject(method = "onEntityStatus", at = @At("RETURN"))
    public void onEntityStatus(EntityStatusS2CPacket packet, CallbackInfo ci) {
        ClientPlayNetworkHandler instance = (ClientPlayNetworkHandler) (Object) this;
        Entity entity = packet.getEntity(instance.getWorld());
        if (packet.getStatus() == (byte)160) {
            this.client.particleManager.addEmitter(entity, ModParticles.EXTEND, 30);
            instance.getWorld().playSound(entity.getX(), entity.getY(), entity.getZ(), ModSounds.ITEM_EXTEND_ITEM_USE, entity.getSoundCategory(), 1.0F, 1.0F, false);
            if (entity == this.client.player) {
                this.client.gameRenderer.showFloatingItem(new ItemStack(ModItems.EXTEND_ITEM));
            }
        }
    }
}
