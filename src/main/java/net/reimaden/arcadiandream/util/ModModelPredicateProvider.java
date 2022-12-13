package net.reimaden.arcadiandream.util;

import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.item.ModItems;

@SuppressWarnings("SameParameterValue")
public class ModModelPredicateProvider {

    public static void register() {
        registerMochiHammer(ModItems.MOCHI_HAMMER);
    }

    private static void registerMochiHammer(Item hammer) {
        ModelPredicateProviderRegistry.register(hammer, new Identifier(ArcadianDream.MOD_ID, "kills"), (stack, world, entity, seed) -> {
            if (!ArcadianDream.CONFIG.mochiHammerOptions.lowViolence()) {
                if (stack.getNbt() != null) {
                    return stack.hasNbt() ? MathHelper.clamp((float) stack.getNbt().getInt("kill_count"), 0.0f, 1000.0f) / 1000 : 0.0f;
                }
            }
            return 0.0f;
        });
    }
}
