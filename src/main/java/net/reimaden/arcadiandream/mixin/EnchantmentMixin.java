package net.reimaden.arcadiandream.mixin;

import net.reimaden.arcadiandream.util.EnchantmentHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class EnchantmentMixin {

    @Shadow @Final public EnchantmentTarget type;

    @Inject(method = "isAcceptableItem", at = @At("HEAD"), cancellable = true)
    private void isAcceptable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof EnchantmentHandler) {
            if (((EnchantmentHandler) stack.getItem()).isExplicitlyValid((Enchantment) (Object) this)) {
                cir.setReturnValue(true);
            }

            boolean contains = ((EnchantmentHandler) stack.getItem()).getEnchantmentTypes().contains(type);
            boolean itemAccepts = !((EnchantmentHandler) stack.getItem()).isInvalid().contains((Enchantment) (Object) this);

            if (contains && itemAccepts) {
                cir.setReturnValue(true);
            }
        }
    }
}
