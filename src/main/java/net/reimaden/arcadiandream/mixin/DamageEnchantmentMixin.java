package net.reimaden.arcadiandream.mixin;

import net.minecraft.enchantment.DamageEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.ImpalingEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentMixin {

    @Inject(method = "canAccept", at = @At("HEAD"), cancellable = true)
    private void denyImpaling(Enchantment other, CallbackInfoReturnable<Boolean> cir) {
        if (other instanceof ImpalingEnchantment) {
            cir.setReturnValue(false);
        }
    }
}
