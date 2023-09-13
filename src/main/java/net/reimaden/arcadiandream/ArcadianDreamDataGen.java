/*
 * Copyright (c) 2022-2023 Maxmani and contributors.
 * Licensed under the EUPL-1.2 or later.
 */

package net.reimaden.arcadiandream;

import dhyces.trimmed.api.data.TrimDatagenSuite;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;
import net.reimaden.arcadiandream.damage.ModDamageSources;
import net.reimaden.arcadiandream.datagen.*;
import net.reimaden.arcadiandream.item.ModArmorMaterials;
import net.reimaden.arcadiandream.item.ModArmorTrimMaterials;
import net.reimaden.arcadiandream.item.ModItems;
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
        FabricTagProvider.BlockTagProvider blockTagProvider = pack.addProvider(ModTagGenerator.BlockTags::new);
        pack.addProvider((output, registries) -> new ModTagGenerator.ItemTags(output, registries, blockTagProvider));
        pack.addProvider(ModTagGenerator.EntityTypeTags::new);
        pack.addProvider(ModTagGenerator.EnchantmentTypeTags::new);
        pack.addProvider(ModTagGenerator.DamageTypeTags::new);
        pack.addProvider(ModTagGenerator.StatusEffectTags::new);
        pack.addProvider(ModTagGenerator.PaintingVariantTags::new);
        pack.addProvider(ModTagGenerator.PointOfInterestTypeTags::new);
        pack.addProvider(ModTagGenerator.BiomeTags::new);
        pack.addProvider(ModModelGenerator::new);
        pack.addProvider(ModDamageTypeGenerator::new);
        pack.addProvider((FabricDataGenerator.Pack.Factory<ModItemOverrideGenerator>) ModItemOverrideGenerator::new);

        // Trimmed API
        TrimDatagenSuite trimDatagenSuite = TrimDatagenSuite.create(pack, ArcadianDream.MOD_ID);

        trimDatagenSuite.makeMaterial(ModArmorTrimMaterials.MAKAITE, ModItems.MAKAITE_INGOT, 0xDB6432,
                materialConfig -> materialConfig.armorOverride(ModArmorMaterials.MAKAITE, ArcadianDream.MOD_ID + "-makaite_darker"));
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
