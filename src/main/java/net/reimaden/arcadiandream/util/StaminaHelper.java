package net.reimaden.arcadiandream.util;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.networking.ModMessages;



public class StaminaHelper {

    private static final int BaseMaxStamina = 100;

    public static void changeStamina(IEntityDataSaver player, int amount){
        NbtCompound nbt = player.getPersistentData();
        int playerStamina = nbt.getInt(ArcadianDream.MOD_ID + "_stamina");
        int total = playerStamina + amount;
        nbt.putInt((ArcadianDream.MOD_ID + "_stamina"), total);
        syncStamina(total, (ServerPlayerEntity) player);
    }

    public static int getStamina(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt(ArcadianDream.MOD_ID + "_stamina");
    }

    public static int getMaxStamina(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        //Might be useful later down the road
        return BaseMaxStamina;
    }

    public static void setMaxStamina(IEntityDataSaver player, int Amount){
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(ArcadianDream.MOD_ID + "_maxstamina", Amount);
    }

public static void syncStamina(int stamina, ServerPlayerEntity player){
    PacketByteBuf buffer = PacketByteBufs.create();
    buffer.writeInt(stamina);
    ServerPlayNetworking.send(player, ModMessages.STAMINA_SYNC, buffer);
    }
}
