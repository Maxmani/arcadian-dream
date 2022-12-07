package net.reimaden.arcadiandream.mixin;

import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ThrownEntity.class)
public abstract class ThrownEntityMixin {

    ThrownEntity bullet = ((ThrownEntity) (Object) this);

    // Prevent bullets from stopping in midair
    @ModifyConstant(method = "tick", constant = @Constant(floatValue = 0.99f))
    private float danmakuAirFriction(float value) {
        if (bullet instanceof BaseBulletEntity) {
            return 1f;
        }
        return value;
    }

    // Prevent bullets from stopping in water
    @ModifyConstant(method = "tick", constant = @Constant(floatValue = 0.8f))
    private float danmakuWaterFriction(float value) {
        if (bullet instanceof BaseBulletEntity) {
            return 1f;
        }
        return value;
    }
}
