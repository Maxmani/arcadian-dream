package net.reimaden.arcadiandream.mixin;

import com.google.common.collect.Lists;
import net.reimaden.arcadiandream.item.custom.ModSpearItem;
import net.reimaden.arcadiandream.util.ValidatingEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Inject(method = "generateEnchantments", at = @At(value = "INVOKE", target = "Ljava/util/List;isEmpty()Z", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private static void generateEnchantments(Random random, ItemStack stack, int level, boolean treasureAllowed, CallbackInfoReturnable<List<?>> cir, List<?> list, Item item, int i, float f, List<EnchantmentLevelEntry> list2) {
        List<EnchantmentLevelEntry> newEnchantments = new ArrayList<>();

        list2.forEach(enchantmentLevelEntry -> {
            if (enchantmentLevelEntry.enchantment instanceof ValidatingEnchantment) {
                if (enchantmentLevelEntry.enchantment.isAcceptableItem(stack)) {
                    newEnchantments.add(enchantmentLevelEntry);
                }
            } else {
                newEnchantments.add(enchantmentLevelEntry);
            }
        });

        list2.clear();
        list2.addAll(newEnchantments);
    }

    @Inject(method = "getPossibleEntries", at = @At("HEAD"), cancellable = true)
    private static void adjustPossibleEntries(int power, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> cir) {
        Item item = stack.getItem();

        if (item instanceof ModSpearItem) {
            List<EnchantmentLevelEntry> entries = Lists.newArrayList();

            Registry.ENCHANTMENT.forEach(enchantment -> {
                if (!enchantment.isAcceptableItem(stack)) {
                    return;
                }
                if (!enchantment.isAvailableForRandomSelection()) {
                    return;
                }
                if (enchantment.isTreasure() && !treasureAllowed) {
                    return;
                }
                for (int i = enchantment.getMaxLevel(); i > enchantment.getMinLevel() - 1; --i) {
                    if (power >= enchantment.getMinPower(i) && power <= enchantment.getMaxPower(i)) {
                        entries.add(new EnchantmentLevelEntry(enchantment, i));
                        break;
                    }
                }
            });

            cir.setReturnValue(entries);
        }
    }
}
