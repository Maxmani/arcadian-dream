package net.reimaden.arcadiandream.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.networking.packet.ItemStackSyncS2CPacket;
import net.minecraft.util.Identifier;

public class ModMessages {

    // S2C
    public static final Identifier ITEM_SYNC = new Identifier(ArcadianDream.MOD_ID, "item_sync");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
}
