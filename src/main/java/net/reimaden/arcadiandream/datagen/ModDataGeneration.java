/*
 * Copyright (c) 2022 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.reimaden.arcadiandream.ArcadianDream;
import net.reimaden.arcadiandream.datagen.providers.ModWorldGenProvider;
import org.jetbrains.annotations.Nullable;

public class ModDataGeneration implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModRecipeGenerator::new);
        pack.addProvider(ModAdvancementGenerator::new);
        pack.addProvider(ModWorldGenProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModWorldGenGenerator::configuredFeatures);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModWorldGenGenerator::placedFeatures);
    }

    @Override
    public @Nullable String getEffectiveModId() {
        return ArcadianDream.MOD_ID;
    }
}
