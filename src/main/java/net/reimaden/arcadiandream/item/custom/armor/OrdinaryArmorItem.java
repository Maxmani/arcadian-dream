/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.armor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.model.HelmetModel;
import net.reimaden.arcadiandream.model.ModModelHandler;

public class OrdinaryArmorItem extends ModArmorItem{

    public OrdinaryArmorItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        this.slot = slot;
    }

    @Override
    protected BipedEntityModel<LivingEntity> provideArmorModelForSlot(EquipmentSlot slot) {
        EntityModelLoader models = MinecraftClient.getInstance().getEntityModelLoader();
        ModelPart root = models.getModelPart(ModModelHandler.ORDINARY);
        return new HelmetModel(root, slot);
    }

    @Override
    public Identifier getArmorTexture(ItemStack stack, EquipmentSlot slot) {
        return new Identifier(ArcadianDream.MOD_ID, "textures/models/armor/ordinary_armor.png");
    }
}
