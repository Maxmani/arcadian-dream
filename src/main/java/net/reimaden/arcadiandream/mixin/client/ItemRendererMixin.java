/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.model.ModModelProviders;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    private BakedModel arcadiandream$use3dModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        // Give the Nue Trident and the Folding Chair their separate handheld models, similarly to the Trident or the Spyglass
        if (renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND && renderMode != ModelTransformationMode.FIXED) {
            if (stack.isOf(ModItems.NUE_TRIDENT)) {
                return ((ItemRendererAccessor) this).arcadiandream$getModels().getModelManager().getModel(ModModelProviders.NUE_TRIDENT);
            } else if (stack.isOf(ModItems.FOLDING_CHAIR)) {
                return ((ItemRendererAccessor) this).arcadiandream$getModels().getModelManager().getModel(ModModelProviders.FOLDING_CHAIR);
            }
        }

        return value;
    }
}
