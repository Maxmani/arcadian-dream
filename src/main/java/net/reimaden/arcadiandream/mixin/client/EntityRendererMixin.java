package net.reimaden.arcadiandream.mixin.client;

import net.reimaden.arcadiandream.item.ModItems;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "renderLabelIfPresent", at = @At("HEAD"), cancellable = true)
    private void nueTrident(Entity entity, Text text, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        // Hide name if holding a Nue Trident
        if (entity instanceof LivingEntity livingEntity
                && (livingEntity.getMainHandStack().getItem().equals(ModItems.NUE_TRIDENT)
                || livingEntity.getOffHandStack().getItem().equals(ModItems.NUE_TRIDENT))) {
            ci.cancel();
        }
    }
}
