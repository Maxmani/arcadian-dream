package net.reimaden.arcadiandream.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;

public class RoukankenS2CPacket {
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender){
        ClientPlayerEntity user = client.player;
        user.setVelocity(
                user.getRotationVector().getX() * 1.5,
                user.getRotationVector().getY() * 1.05,
                user.getRotationVector().getZ() * 1.5);
    }
}
