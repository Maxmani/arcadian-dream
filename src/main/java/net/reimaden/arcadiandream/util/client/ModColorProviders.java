package net.reimaden.arcadiandream.util.client;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.reimaden.arcadiandream.item.ModItems;
import net.minecraft.item.DyeableItem;

public class ModColorProviders {

    public static void register() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex == 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), ModItems.ORB_BULLET);
    }
}
