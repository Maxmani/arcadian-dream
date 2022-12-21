/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.model;

import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.custom.armor.ModArmorItem;

public class ModArmorRenderer {

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void register() {
        Item[] armors = Registries.ITEM.stream()
                .filter(i -> i instanceof ModArmorItem
                        && Registries.ITEM.getKey(i).get().getValue().getNamespace().equals(ArcadianDream.MOD_ID))
                .toArray(Item[]::new);

        ArmorRenderer renderer = (matrices, vertexConsumer, stack, entity, slot, light, original) -> {
            ModArmorItem armor = (ModArmorItem) stack.getItem();
            BipedEntityModel<LivingEntity> model = armor.getArmorModel();
            Identifier texture = armor.getArmorTexture(stack, slot);
            original.copyBipedStateTo(model);
            ArmorRenderer.renderPart(matrices, vertexConsumer, light, stack, model, texture);
        };
        ArmorRenderer.register(renderer, armors);
    }
}
