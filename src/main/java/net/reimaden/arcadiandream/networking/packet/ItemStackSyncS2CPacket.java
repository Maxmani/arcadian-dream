/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.reimaden.arcadiandream.block.entity.OnbashiraBlockEntity;
import net.reimaden.arcadiandream.block.entity.RitualShrineBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class ItemStackSyncS2CPacket {

    @SuppressWarnings({"unused", "DataFlowIssue"})
    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender packetSender) {
        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            list.set(i, buf.readItemStack());
        }
        BlockPos pos = buf.readBlockPos();

        if (client.world.getBlockEntity(pos) instanceof OnbashiraBlockEntity blockEntity) {
            blockEntity.setInventory(list);
        } else if (client.world.getBlockEntity(pos) instanceof RitualShrineBlockEntity blockEntity) {
            blockEntity.setInventory(list);
        }
    }
}
