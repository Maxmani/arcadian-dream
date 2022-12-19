/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin.client;

import net.reimaden.arcadiandream.item.ModItems;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.item.ItemStack;
import net.reimaden.arcadiandream.util.client.ModModels;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @Shadow private @Final ItemModels models;

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At("HEAD"), argsOnly = true)
    private BakedModel guiModel(BakedModel defaultModel, ItemStack stack, ModelTransformation.Mode rendermode) {
        // Give the Nue Trident its separate inventory sprite, similar to the Trident or the Spyglass
        if ((rendermode == ModelTransformation.Mode.GUI || rendermode == ModelTransformation.Mode.GROUND || rendermode == ModelTransformation.Mode.FIXED)
                && stack.isOf(ModItems.NUE_TRIDENT)) {
            return models.getModelManager().getModel(ModModels.NUE_TRIDENT);
        }

        return defaultModel;
    }
}
