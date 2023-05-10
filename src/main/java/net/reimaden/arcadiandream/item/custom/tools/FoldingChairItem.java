/*
 * Copyright (c) 2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.item.custom.tools;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.sound.ModSounds;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FoldingChairItem extends ModHammerItem {

    public FoldingChairItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.takeKnockback(ArcadianDream.CONFIG.foldingChairOptions.knockbackStrength(),
                MathHelper.sin(attacker.getYaw() * ((float)Math.PI / 180)), -MathHelper.cos(attacker.getYaw() * ((float)Math.PI / 180)));
        attacker.getWorld().playSound(null, target.getX(), target.getY(), target.getZ(), ModSounds.ITEM_FOLDING_CHAIR_HIT,
                attacker.getSoundCategory(), 1.0f, 0.8f + attacker.getRandom().nextFloat() * 0.4f);

        return super.postHit(stack, target, attacker);
    }

    @Override
    public List<Enchantment> isInvalid() {
        return List.of(Enchantments.SWEEPING, Enchantments.LOOTING, Enchantments.FIRE_ASPECT);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("item." + ArcadianDream.MOD_ID + "." + this + ".tooltip"));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
