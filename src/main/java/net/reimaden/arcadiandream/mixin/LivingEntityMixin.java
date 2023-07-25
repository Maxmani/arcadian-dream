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
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.advancement.ModCriteria;
import net.reimaden.arcadiandream.effect.ModEffects;
import net.reimaden.arcadiandream.entity.custom.danmaku.BaseBulletEntity;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.item.ModToolMaterials;
import net.reimaden.arcadiandream.statistic.ModStats;
import net.reimaden.arcadiandream.util.IEntityDataSaver;
import net.reimaden.arcadiandream.util.ItemBreakUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryUseTotem", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$useExtendItem(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        final LivingEntity entity = (LivingEntity) (Object) this;

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
                entity.getWorld().sendEntityStatus(this, (byte)160);
                cir.setReturnValue(true);

                // Clear bullets
                for (BaseBulletEntity bulletEntity : getWorld().getNonSpectatingEntities(BaseBulletEntity.class,
                        entity.getBoundingBox().expand(24.0, 24.0, 24.0))) {

                    if (bulletEntity.getOwner() != entity) {
                        bulletEntity.discard();
                        bulletEntity.cancelParticle((ServerWorld) getWorld());
                        cancelled++;
                    }
                }

                if (cancelled > 0) {
                    ModCriteria.BULLETS_CANCELLED.trigger((ServerPlayerEntity) entity, cancelled, true);
                    ((ServerPlayerEntity) entity).increaseStat(ModStats.BULLETS_CANCELLED, cancelled);
                }

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

    @Inject(method = "canHaveStatusEffect", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$effectImmunity(StatusEffectInstance effect, CallbackInfoReturnable<Boolean> cir) {
        final LivingEntity entity = (LivingEntity) (Object) this;

        final StatusEffect effectType = effect.getEffectType();
        if (((IEntityDataSaver) entity).arcadiandream$getPersistentData().getByte("elixir") >= 2
                && (effectType.getCategory() == StatusEffectCategory.HARMFUL && effectType != ModEffects.ELIXIR_FATIGUE)) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "playEquipmentBreakEffects", at = @At("HEAD"), cancellable = true)
    private void arcadiandream$disableBreakEffects(ItemStack stack, CallbackInfo ci) {
        if (ItemBreakUtil.isDisallowed(stack.getItem())) {
            ci.cancel();
        }
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    private void arcadiandream$meltIcicleSword(CallbackInfo ci) {
        final LivingEntity entity = (LivingEntity) (Object) this;

        ItemStack mainStack = entity.getMainHandStack();
        ItemStack offStack = entity.getOffHandStack();

        Biome currentBiome = getWorld().getBiome(entity.getBlockPos()).value();
        float temperature = currentBiome.getTemperature();

        boolean isInWarmBiome = temperature > 0.5f;
        boolean canEvaporate = getWorld().getDimension().ultrawarm();
        boolean canMelt = isInWarmBiome && isAffectedByDaylight();

        if (ArcadianDream.CONFIG.icicleSwordOptions.meltsInstantly() && canEvaporate) {
            decrementStackAndSpawnParticles(mainStack);
            decrementStackAndSpawnParticles(offStack);
        } else if (canMelt || canEvaporate) {
            damageStack(mainStack, temperature, Hand.MAIN_HAND);
            damageStack(offStack, temperature, Hand.OFF_HAND);
        }
    }

    @Unique
    private void decrementStackAndSpawnParticles(ItemStack stack) {
        final LivingEntity entity = (LivingEntity) (Object) this;

        if (isEnchantedIceTool(stack)) {
            stack.decrement(1);
            double x = getBlockPos().getX();
            double y = getBlockPos().getY();
            double z = getBlockPos().getZ();
            getWorld().playSound(entity, getBlockPos(), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS,
                    0.5f, 2.6f + (getWorld().random.nextFloat() - getWorld().random.nextFloat()) * 0.8f);
            for (int i = 0; i < 8; ++i) {
                getWorld().addParticle(ParticleTypes.LARGE_SMOKE, x + Math.random(), y + Math.random(), z + Math.random(), 0.0, 0.0, 0.0);
            }
        }
    }

    @Unique
    private void damageStack(ItemStack stack, float temperature, Hand hand) {
        final LivingEntity entity = (LivingEntity) (Object) this;

        if (isEnchantedIceTool(stack)) {
            int damageAmount = (int) (random.nextInt(3) * temperature);
            stack.damage(damageAmount, entity, e -> e.sendToolBreakStatus(hand));
        }
    }

    @Unique
    private static boolean isEnchantedIceTool(ItemStack stack) {
        return stack.getItem() instanceof ToolItem tool && tool.getMaterial() == ModToolMaterials.ENCHANTED_ICE;
    }

    @Unique
    private boolean isAffectedByDaylight() {
        if (getWorld().isDay() && !getWorld().isClient()) {
            boolean isInPowderSnow = inPowderSnow || wasInPowderSnow;
            // I don't give a damn
            // noinspection deprecation
            float brightness = getBrightnessAtEyes();
            BlockPos blockPos = BlockPos.ofFloored(getX(), getEyeY(), getZ());
            return brightness > 0.5f && random.nextFloat() * 30.0f < (brightness - 0.4f) * 2.0f && !isInPowderSnow && getWorld().isSkyVisible(blockPos);
        }
        return false;
    }
}
