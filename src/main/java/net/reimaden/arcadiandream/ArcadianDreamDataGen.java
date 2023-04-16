/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.reimaden.arcadiandream.damage.ModDamageSources;
import net.reimaden.arcadiandream.datagen.*;
import net.reimaden.arcadiandream.world.feature.ModConfiguredFeatures;
import net.reimaden.arcadiandream.world.feature.ModPlacedFeatures;
import net.reimaden.arcadiandream.world.structure.ModStructurePools;
import net.reimaden.arcadiandream.world.structure.ModStructureProcessorLists;
import net.reimaden.arcadiandream.world.structure.ModStructureSets;
import net.reimaden.arcadiandream.world.structure.ModStructures;
import org.jetbrains.annotations.Nullable;

public class ArcadianDreamDataGen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModRecipeGenerator::new);
        pack.addProvider(ModAdvancementGenerator::new);
        pack.addProvider(ModWorldGenerator::new);
        pack.addProvider(ModLootTableGenerator.BlockLoot::new);
        pack.addProvider(ModLootTableGenerator.EntityLoot::new);
        pack.addProvider(ModLootTableGenerator.ChestLoot::new);
        pack.addProvider(ModTagGenerator.BlockTags::new);
        pack.addProvider(ModTagGenerator.ItemTags::new);
        pack.addProvider(ModTagGenerator.DamageTypeTags::new);
        pack.addProvider(ModTagGenerator.PaintingVariantTags::new);
        pack.addProvider(ModTagGenerator.PointOfInterestTypeTags::new);
        pack.addProvider(ModTagGenerator.BiomeTags::new);
        pack.addProvider(ModModelGenerator::new);
        pack.addProvider(ModDamageTypeGenerator::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.DAMAGE_TYPE, ModDamageSources::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.STRUCTURE, ModStructures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.STRUCTURE_SET, ModStructureSets::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.TEMPLATE_POOL, ModStructurePools::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PROCESSOR_LIST, ModStructureProcessorLists::bootstrap);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return ArcadianDream.MOD_ID;
    }
}
