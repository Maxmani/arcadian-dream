package net.reimaden.arcadiandream.mixin;

import net.reimaden.arcadiandream.item.ModItems;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Shadow public abstract void disableShield(boolean sprinting);

    @ModifyVariable(method = "tickMovement", at = @At("STORE"), index = 2)
    private Box increasePickupRange(Box value) {
        PlayerEntity player = ((PlayerEntity) (Object) this);
        ItemStack stack = player.getEquippedStack(EquipmentSlot.HEAD);
        if(stack.isOf(ModItems.ORDINARY_HAT)) {
            return value.expand(2);
        }
        return value;
    }

    @Inject(method = "takeShieldHit", at = @At("TAIL"))
    private void disableShield(LivingEntity attacker, CallbackInfo ci) {
        if (attacker.getMainHandStack().isOf(ModItems.HISOU_SWORD)) {
            this.disableShield(true);
        }
    }
}
