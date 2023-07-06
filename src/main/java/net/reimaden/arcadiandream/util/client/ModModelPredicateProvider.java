/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.util.client;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.ModItems;

public class ModModelPredicateProvider {

    public static void register() {
        registerMochiMallet();
    }

    private static void registerMochiMallet() {
        ModelPredicateProviderRegistry.register(ModItems.MOCHI_MALLET, new Identifier(ArcadianDream.MOD_ID, "kills"), (stack, world, entity, seed) -> {
            if (!ArcadianDream.CONFIG.mochiMalletOptions.lowViolence()) {
                if (stack.getNbt() != null) {
                    return stack.hasNbt() ? MathHelper.clamp((float) stack.getNbt().getInt("kill_count"), 0.0f, 1000.0f) / 1000 : 0.0f;
                }
            }
            return 0.0f;
        });
    }
}
