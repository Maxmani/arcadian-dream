package net.reimaden.arcadiandream.mixin;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.reimaden.arcadiandream.entity.custom.BaseBulletEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.particle.ModParticles;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Pair;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void useExtendItem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        Item item = ModItems.EXTEND_ITEM;

        if (source.isOutOfWorld()) {
            cir.setReturnValue(false);
        } else {
            if (TrinketsApi.getTrinketComponent(livingEntity).get().isEquipped(item)) {

                // Do the usual Totem stuff
                livingEntity.setHealth(livingEntity.getMaxHealth());
                livingEntity.clearStatusEffects();
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 4));
                livingEntity.world.sendEntityStatus(this, (byte)160);
                cir.setReturnValue(true);

                // Clear bullets
                for (BaseBulletEntity bulletEntity : world.getNonSpectatingEntities(BaseBulletEntity.class,
                        new Box(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.getX(), livingEntity.getY(), livingEntity.getZ()).expand(24.0, 24.0, 24.0))) {

                    if (bulletEntity.getOwner() != livingEntity) {
                        bulletEntity.kill();
                        ((ServerWorld) world).spawnParticles(ModParticles.BULLET_CANCEL, bulletEntity.getX(), bulletEntity.getY(), bulletEntity.getZ(), 1, 0, 0, 0, 0);
                    }
                }

                // Remove item
                if (TrinketsApi.getTrinketComponent(livingEntity).isPresent()) {
                    List<Pair<SlotReference, ItemStack>> list = TrinketsApi.getTrinketComponent(livingEntity).get().getEquipped(item);
                    if (list.size() > 0) {
                        ItemStack itemStack = list.get(0).getRight();
                        if (itemStack != null) {
                            itemStack.decrement(1);
                        }
                    }
                }
            }
            if (livingEntity instanceof ServerPlayerEntity serverPlayerEntity) {
                serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(item));
            }
        }
    }
}
