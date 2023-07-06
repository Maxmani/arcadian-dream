package net.reimaden.arcadiandream.networking.packet;


import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.util.IEntityDataSaver;


public class StaminaSyncS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        ((IEntityDataSaver) client.player).getPersistentData().putInt(ArcadianDream.MOD_ID + "_stamina", buf.readInt());

    }
}
