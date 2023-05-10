/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin.client;

import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.item.ItemStack;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.util.client.ModModelProviders;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow private @Final ItemModels models;

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    private BakedModel arcadiandream$guiModel(BakedModel defaultModel, ItemStack stack, ModelTransformationMode rendermode) {
        // Give the Nue Trident and the Folding Chair their separate inventory sprites, similar to the Trident or the Spyglass
        if (rendermode == ModelTransformationMode.GUI || rendermode == ModelTransformationMode.GROUND || rendermode == ModelTransformationMode.FIXED) {
            if (stack.isOf(ModItems.NUE_TRIDENT)) {
                return models.getModelManager().getModel(ModModelProviders.NUE_TRIDENT);
            } else if (stack.isOf(ModItems.FOLDING_CHAIR)) {
                return models.getModelManager().getModel(ModModelProviders.FOLDING_CHAIR);
            }
        }

        return defaultModel;
    }
}
