/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.mixin;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.world.World;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.advancement.ModCriteria;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.util.IEntityDataSaver;
import net.reimaden.arcadiandream.util.ItemBreakUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    private final LivingEntity entity = (LivingEntity) (Object) this;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$useExtendItem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            cir.setReturnValue(false);
        } else {
            final Item item = ModItems.EXTEND_ITEM;
            int cancelled = 0;

            if (TrinketsApi.getTrinketComponent(entity).get().isEquipped(item)) {

                // Do the usual Totem stuff
                entity.setHealth(entity.getMaxHealth());
                entity.clearStatusEffects();
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 60, 4));
                entity.world.sendEntityStatus(this, (byte)160);
                cir.setReturnValue(true);

                // Clear bullets
                for (BaseBulletEntity bulletEntity : world.getNonSpectatingEntities(BaseBulletEntity.class,
                        entity.getBoundingBox().expand(24.0, 24.0, 24.0))) {

                    if (bulletEntity.getOwner() != entity) {
                        bulletEntity.discard();
                        bulletEntity.cancelParticle((ServerWorld) world);
                        cancelled++;
                    }
                }

                if (cancelled > 0) ModCriteria.BULLETS_CANCELLED.trigger((ServerPlayerEntity) entity, cancelled, true);

                // Remove item
                if (TrinketsApi.getTrinketComponent(entity).isPresent()) {
                    List<Pair<SlotReference, ItemStack>> list = TrinketsApi.getTrinketComponent(entity).get().getEquipped(item);
                    if (list.size() > 0) {
                        ItemStack itemStack = list.get(0).getRight();
                        if (itemStack != null) {
                            itemStack.decrement(1);
                        }
                    }
                }
            }
            if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
                serverPlayerEntity.incrementStat(Stats.USED.getOrCreateStat(item));
            }
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;isDead()Z", ordinal = 1), cancellable = true)
    private void arcadiandream$preventDeath(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!world.isClient() && ((IEntityDataSaver) entity).getPersistentData().getByte("elixir") >= 3) {
            if (entity.getHealth() - amount <= 0 && !source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                entity.setHealth(entity.getMaxHealth());

                world.playSound(null, entity.getX(), entity.getY(), entity.getZ(),
                        ModSounds.ENTITY_GENERIC_RESURRECT, entity.getSoundCategory(), 1.0f, 1.0f);
                if (entity instanceof PlayerEntity player) {
                    player.sendMessage(Text.translatable(ArcadianDream.MOD_ID + ".message.resurrection"), true);
                }

                cir.setReturnValue(false);
            }
        }
    }

    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$effectImmunity(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        if (!world.isClient() && ((IEntityDataSaver) entity).getPersistentData().getByte("elixir") >= 2
                && effect.getEffectType().getCategory() != StatusEffectCategory.BENEFICIAL) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "playEquipmentBreakEffects", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$disableBreakEffects(ItemStack stack, CallbackInfo ci) {
        if (ItemBreakUtil.isDisallowed(stack.getItem())) {
            ci.cancel();
        }
    }
}
