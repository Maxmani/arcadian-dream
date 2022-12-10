package net.reimaden.arcadiandream;

import net.fabricmc.api.ModInitializer;
import net.reimaden.arcadiandream.block.ModBlocks;
import net.reimaden.arcadiandream.block.entity.ModBlockEntities;
import net.reimaden.arcadiandream.config.ArcadianDreamConfig;
import net.reimaden.arcadiandream.entity.ModEntities;
import net.reimaden.arcadiandream.painting.ModPaintings;
import net.reimaden.arcadiandream.particle.ModParticles;
import net.reimaden.arcadiandream.recipe.ModRecipes;
import net.reimaden.arcadiandream.sound.ModSounds;
import net.reimaden.arcadiandream.statistic.ModStats;
import net.reimaden.arcadiandream.util.ModDispenserBehavior;
import net.reimaden.arcadiandream.world.feature.ModConfiguredFeatures;
import net.reimaden.arcadiandream.item.ModItems;
import net.reimaden.arcadiandream.util.ModLootTableModifiers;
import net.reimaden.arcadiandream.world.gen.ModOreGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArcadianDream implements ModInitializer {

	public static final String MOD_ID = "arcadiandream";
	public static final Logger LOGGER = LoggerFactory.getLogger(ArcadianDream.class);
	public static final ArcadianDreamConfig CONFIG = ArcadianDreamConfig.createAndLoad();

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing, please watch warmly until it is ready.");

		ModConfiguredFeatures.register();

		ModItems.register();
		ModBlocks.register();
		ModPaintings.register();
		ModEntities.register();
		ModBlockEntities.register();
		ModSounds.register();
		ModParticles.register();
		ModDispenserBehavior.register();
		ModRecipes.register();
		ModStats.register();

		ModLootTableModifiers.modify();
		ModOreGeneration.generate();
	}
}
