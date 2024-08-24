package net.reimaden.arcadiandream.mixin;

import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.Bootstrap;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.reimaden.arcadiandream.loot.ModEarlyLootTableModifiers.LEAVES_LOOT_TABLES;

@Mixin(Bootstrap.class)
public class BootstrapMixin {
    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/Registries;bootstrap()V"))
    private static void initialize(CallbackInfo ci) {
        RegistryEntryAddedCallback.event(Registries.BLOCK).register((rawId, id, object) -> {
            if (id.toString().endsWith("leaves") && !id.getNamespace().equals("minecraft")) {
                LEAVES_LOOT_TABLES.add(object.getLootTableId());
            }
        });
    }
}
