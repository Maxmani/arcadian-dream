package net.reimaden.arcadiandream.item.custom;

import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.util.EnchantmentHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

public class NueTridentItem extends ModSpearItem implements EnchantmentHandler {

    public NueTridentItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean isExplicitlyValid(Enchantment enchantment) {
        if (ArcadianDream.CONFIG.nueTridentOptions.canHaveImpaling()) {
            return enchantment.equals(Enchantments.IMPALING);
        }
        return false;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 100, 0, true, true, true), attacker);
        return super.postHit(stack, target, attacker);
    }
}
