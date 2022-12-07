package net.reimaden.arcadiandream.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static BlockEntityType<OnbashiraBlockEntity> ONBASHIRA;
    public static BlockEntityType<RitualShrineBlockEntity> RITUAL_SHRINE;

    public static void register() {
        ONBASHIRA = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ArcadianDream.MOD_ID, "onbashira"),
                FabricBlockEntityTypeBuilder.create(OnbashiraBlockEntity::new,
                        ModBlocks.ONBASHIRA).build(null));
        RITUAL_SHRINE = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(ArcadianDream.MOD_ID, "ritual_shrine"),
                FabricBlockEntityTypeBuilder.create(RitualShrineBlockEntity::new,
                        ModBlocks.RITUAL_SHRINE).build(null));
    }
}
