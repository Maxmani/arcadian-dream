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

@SuppressWarnings({"ConstantConditions", "unused"})
public class ItemStackSyncS2CPacket {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender packetSender) {
        int size = buf.readInt();
        DefaultedList<ItemStack> list = DefaultedList.ofSize(size, ItemStack.EMPTY);
        for (int i = 0; i < size; i++) {
            list.set(i, buf.readItemStack());
        }
        BlockPos pos = buf.readBlockPos();

        if (client.world.getBlockEntity(pos) instanceof OnbashiraBlockEntity blockEntity) {
            blockEntity.setInventory(list);
        }
        if (client.world.getBlockEntity(pos) instanceof RitualShrineBlockEntity blockEntity) {
            blockEntity.setInventory(list);
        }
    }
}
