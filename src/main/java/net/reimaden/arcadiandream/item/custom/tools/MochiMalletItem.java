/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.tools;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MochiMalletItem extends ModHammerItem {

    private final float attackDamage;
    private final float attackSpeed;

    public MochiMalletItem(ToolMaterial material, int attackDamageIn, float attackSpeedIn, Settings settings) {
        super(material, attackDamageIn, attackSpeedIn, settings);
        this.attackDamage = attackDamageIn;
        this.attackSpeed = attackSpeedIn;
    }

    @Override // Increase stats based on the kill counter
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot slot) {
        final Multimap<EntityAttribute, EntityAttributeModifier> multimap = HashMultimap.create();

        if (slot == EquipmentSlot.MAINHAND) {
            multimap.put(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                    new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier",
                            this.attackDamage + MathHelper.clamp((float) getKills(stack), 0.0f, 500.0f) / 125,
                            EntityAttributeModifier.Operation.ADDITION));
            multimap.put(EntityAttributes.GENERIC_ATTACK_SPEED,
                    new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier",
                            this.attackSpeed + MathHelper.clamp((float) getKills(stack), 0.0f, 500.0f) / 416.666,
                            EntityAttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        NbtCompound nbt;
        if (stack.hasNbt()) {
            nbt = stack.getNbt();
        } else {
            nbt = new NbtCompound();
        }

        if (nbt != null && target.isDead()) {
            if (nbt.contains("kill_count")) {
                nbt.putInt("kill_count", nbt.getInt("kill_count") + 1);
            } else {
                nbt.putInt("kill_count", 1);
            }
        }
        stack.setNbt(nbt);

        return super.postHit(stack, target, attacker);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasNbt() && stack.getOrCreateNbt().getInt("kill_count") != 0) {
            tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + ".mochi_mallet.tooltip", getKills(stack)));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }

    private static int getKills(ItemStack stack) {
        return stack.getOrCreateNbt().getInt("kill_count");
    }
}
