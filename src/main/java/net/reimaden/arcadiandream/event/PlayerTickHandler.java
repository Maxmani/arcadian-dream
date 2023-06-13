package net.reimaden.arcadiandream.event;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.reimaden.arcadiandream.networking.ModMessages;
import net.reimaden.arcadiandream.util.IEntityDataSaver;
import net.reimaden.arcadiandream.util.StaminaHelper;

public class PlayerTickHandler implements ServerTickEvents.StartTick {

    @Override
    public void onStartTick(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            IEntityDataSaver dataPlayer = ((IEntityDataSaver) player);
            dataPlayer.getPersistentData();
            if (StaminaHelper.getStamina((IEntityDataSaver) dataPlayer) < StaminaHelper.getMaxStamina((IEntityDataSaver) dataPlayer)){
                //This prevents a crash when trying to relog.
                //I knew this was a dumb way to do this.
                    try {
                        ClientPlayNetworking.send(ModMessages.STAMINA, PacketByteBufs.create());
                    }
                    catch(Exception e) {
                        return;
                    }
                }



        }
    }
}