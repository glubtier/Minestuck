package com.mraof.minestuck.world.biome;

import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;

public abstract class LandBiome
{
	public static final float DEFAULT_NORMAL_DEPTH = 0.125F, DEFAULT_NORMAL_SCALE = 0.05F;
	public static final float DEFAULT_ROUGH_DEPTH = 0.45F, DEFAULT_ROUGH_SCALE = 0.3F;
	public static final float DEFAULT_OCEAN_DEPTH = -1.0F, DEFAULT_OCEAN_SCALE = 0.1F;
	
	public static Biome createNormalBiome(Biome.Precipitation precipitation, float temperature, float downfall)
	{
		Biome.BiomeBuilder builder = createBiomeBase().precipitation(precipitation).biomeCategory(Biome.BiomeCategory.NONE)
				.temperature(temperature).downfall(downfall);
		
		return builder.build();
	}
	
	public static Biome createRoughBiome(Biome.Precipitation precipitation, float temperature, float downfall)
	{
		Biome.BiomeBuilder builder = createBiomeBase().precipitation(precipitation).biomeCategory(Biome.BiomeCategory.NONE)
				.temperature(temperature).downfall(downfall);
		
		return builder.build();
	}
	
	public static Biome createOceanBiome(Biome.Precipitation precipitation, float temperature, float downfall)
	{
		Biome.BiomeBuilder builder = createBiomeBase().precipitation(precipitation).biomeCategory(Biome.BiomeCategory.OCEAN)
				.temperature(temperature).downfall(downfall);
		
		return builder.build();
	}
	
	private static Biome.BiomeBuilder createBiomeBase()
	{
		Biome.BiomeBuilder builder = new Biome.BiomeBuilder();
		
		BiomeSpecialEffects.Builder effects = new BiomeSpecialEffects.Builder().waterColor(0x3F76E4).waterFogColor(0x050533)
				.fogColor(0xC0D8FF).skyColor(0x7AA4FF);
		
		BiomeGenerationSettings.Builder generation = new BiomeGenerationSettings.Builder()/*TODO look at surface rules
				.surfaceBuilder(() -> SurfaceBuilder.DEFAULT.configured(SurfaceBuilder.CONFIG_GRASS))*/;
		
		return builder.specialEffects(effects.build()).mobSpawnSettings(new MobSpawnSettings.Builder().build())
				.generationSettings(generation.build());
	}
}