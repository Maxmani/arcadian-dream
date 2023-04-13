/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.block.entity;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.block.ModBlocks;

public class ModBlockEntities {

    public static final BlockEntityType<OnbashiraBlockEntity> ONBASHIRA = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "onbashira"),
            FabricBlockEntityTypeBuilder.create(OnbashiraBlockEntity::new, ModBlocks.ONBASHIRA).build());
    public static final BlockEntityType<RitualShrineBlockEntity> RITUAL_SHRINE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "ritual_shrine"),
            FabricBlockEntityTypeBuilder.create(RitualShrineBlockEntity::new, ModBlocks.RITUAL_SHRINE).build());
    public static final BlockEntityType<DanmakuCraftingTableBlockEntity> DANMAKU_CRAFTING_TABLE = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "danmaku_crafting_table"),
            FabricBlockEntityTypeBuilder.create(DanmakuCraftingTableBlockEntity::new, ModBlocks.DANMAKU_CRAFTING_TABLE).build());
    public static final BlockEntityType<MysteriousSealBlockEntity> MYSTERIOUS_SEAL = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(ArcadianDream.MOD_ID, "mysterious_seal"),
            FabricBlockEntityTypeBuilder.create(MysteriousSealBlockEntity::new, ModBlocks.MYSTERIOUS_SEAL).build());

    public static void register() {
        ArcadianDream.LOGGER.debug("Registering block entities for " + ArcadianDream.MOD_ID);
    }
}
