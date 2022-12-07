package net.reimaden.arcadiandream.item;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static final ItemGroup ITEMS = FabricItemGroupBuilder.build(
            new Identifier(ArcadianDream.MOD_ID, "items"), () -> new ItemStack(ModItems.POWER_ITEM));
    public static final ItemGroup BLOCKS = FabricItemGroupBuilder.build(
            new Identifier(ArcadianDream.MOD_ID, "blocks"), () -> new ItemStack(ModBlocks.ONBASHIRA));
    public static final ItemGroup DANMAKU = FabricItemGroupBuilder.build(
            new Identifier(ArcadianDream.MOD_ID, "danmaku"), () -> new ItemStack(ModItems.ORB_BULLET));
}
