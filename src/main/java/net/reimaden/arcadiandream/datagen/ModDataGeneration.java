/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.world.feature.ModConfiguredFeatures;
import net.reimaden.arcadiandream.world.feature.ModPlacedFeatures;
import org.jetbrains.annotations.Nullable;

public class ModDataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModRecipeGenerator::new);
        pack.addProvider(ModAdvancementGenerator::new);
        pack.addProvider(ModWorldGenerator::new);
        pack.addProvider(ModLootTableGenerator.BlockLoot::new);
        pack.addProvider(ModLootTableGenerator.EntityLoot::new);
        pack.addProvider(ModTagGenerator.BlockTags::new);
        pack.addProvider(ModTagGenerator.ItemTags::new);
        pack.addProvider(ModModelGenerator::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return ArcadianDream.MOD_ID;
    }
}
