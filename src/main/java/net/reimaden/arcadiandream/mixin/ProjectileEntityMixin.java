package net.reimaden.arcadiandream.mixin;

import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileEntity.class)
public class ProjectileEntityMixin {

    ProjectileEntity bullet = ((ProjectileEntity) (Object) this);

    @Inject(method = "setVelocity(Lnet/minecraft/entity/Entity;FFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/ProjectileEntity;setVelocity(Lnet/minecraft/util/math/Vec3d;)V"), cancellable = true)
    private void preventMomentum(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence, CallbackInfo ci) {
        if (bullet instanceof BaseBulletEntity) {
            ci.cancel();
        }
    }
}
