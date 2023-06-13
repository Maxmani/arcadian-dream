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
    private static final int BaseStaminaRegen = 1;

    public static void changeStamina(IEntityDataSaver player, int amount){
        NbtCompound nbt = player.getPersistentData();
        int playerStamina = nbt.getInt(ArcadianDream.MOD_ID + "_stamina");
        int total = playerStamina + amount;
        if (total > getMaxStamina(player)) {total = getMaxStamina(player);}
        nbt.putInt((ArcadianDream.MOD_ID + "_stamina"), total);
        syncStamina(total, (ServerPlayerEntity) player);
    }

    public static int getStamina(IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        return nbt.getInt(ArcadianDream.MOD_ID + "_stamina");
    }

    public static int getMaxStamina(IEntityDataSaver player){
        return BaseMaxStamina;
    }

    public static void setMaxStamina(IEntityDataSaver player, int Amount){
        NbtCompound nbt = player.getPersistentData();
        nbt.putInt(ArcadianDream.MOD_ID + "_maxstamina", Amount);
    }

    public static void changeStaminaRegenerationFactor (IEntityDataSaver player, int Amount){
        NbtCompound nbt = player.getPersistentData();
        int factor = nbt.getInt(ArcadianDream.MOD_ID + "_regenstamina") + Amount;
        nbt.putInt(ArcadianDream.MOD_ID + "_regenstamina", factor);
    }

    public static int getStaminaRegenerationFactor (IEntityDataSaver player){
        NbtCompound nbt = player.getPersistentData();
        return BaseStaminaRegen + nbt.getInt(ArcadianDream.MOD_ID + "_regenstamina");
    }


public static void syncStamina(int stamina, ServerPlayerEntity player){
    PacketByteBuf buffer = PacketByteBufs.create();
    buffer.writeInt(stamina);
    ServerPlayNetworking.send(player, ModMessages.STAMINA_SYNC, buffer);
    }
}
