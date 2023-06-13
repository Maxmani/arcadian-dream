/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.networking;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.networking.packet.ItemStackSyncS2CPacket;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.networking.packet.RoukankenS2CPacket;
import net.reimaden.arcadiandream.networking.packet.StaminaC2SPacket;
import net.reimaden.arcadiandream.networking.packet.StaminaSyncS2CPacket;

public class ModMessages {

    // S2C
    public static final Identifier ITEM_SYNC = new Identifier(ArcadianDream.MOD_ID, "item_sync");
    public static final Identifier STAMINA = new Identifier(ArcadianDream.MOD_ID, "stamina");
    public static final Identifier STAMINA_SYNC = new Identifier(ArcadianDream.MOD_ID, "stamina_sync");

    public static final Identifier ROUKANKEN_DASH = new Identifier(ArcadianDream.MOD_ID, "roukanken_dash");

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(STAMINA_SYNC, StaminaSyncS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ROUKANKEN_DASH, RoukankenS2CPacket::receive);
    }
    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(STAMINA, StaminaC2SPacket::receive);
    }
}
