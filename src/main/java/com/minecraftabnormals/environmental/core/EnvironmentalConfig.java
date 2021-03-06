package com.minecraftabnormals.environmental.core;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.registries.ForgeRegistries;

@EventBusSubscriber(modid = Environmental.MODID)
public class EnvironmentalConfig {

    public static class Common {
        public final ConfigValue<Boolean> generateExtraWisterias;
        public final ConfigValue<Boolean> generateGiantMushroomsInSwamps;
        public final ConfigValue<Boolean> generateGiantTallGrass;

        public final ConfigValue<Integer> marshWeight;
        public final ConfigValue<Integer> mushroomMarshWeight;
        
        public final ConfigValue<Integer> blossomWoodsWeight;
        public final ConfigValue<Integer> blossomHillsWeight;
        public final ConfigValue<Integer> blossomHighlandsWeight;
        public final ConfigValue<Integer> blossomValleysWeight;
        
        public final ConfigValue<Boolean> limitFarmAnimalSpawns;
        public final ConfigValue<Boolean> biomeVariantsAlwaysSpawn;

        public final ConfigValue<Boolean> customFogs;
        public final ConfigValue<Integer> desertFog;
        public final ConfigValue<Integer> jungleFog;
        public final ConfigValue<Integer> snowyFog;
        public final ConfigValue<Integer> swampFog;

        Common(ForgeConfigSpec.Builder builder) {
        	builder.push("worldgen");
            	builder.push("biomes");
            		builder.push("marsh");
            			marshWeight = builder.define("Marsh weight", 6);
            			mushroomMarshWeight = builder.define("Mushroom Marsh weight", 0);
            		builder.pop();
            		builder.push("blossom");
            			blossomWoodsWeight = builder.define("Blossom Woods weight", 3);
            			blossomHillsWeight = builder.define("Blossom Hills weight", 0);
            			blossomHighlandsWeight = builder.define("Blossom Highlands weight", 1);
            			blossomValleysWeight = builder.define("Blossom Valleys weight", 0);
            			builder.pop();
            		builder.pop();
            	builder.push("features");
            		generateExtraWisterias = builder.define("Wisteria Tree generation out of Flower Forests", false);
            		generateGiantMushroomsInSwamps = builder.define("Giant Mushroom generation in Swamps", true);
            		generateGiantTallGrass = builder.define("Giant Tall Grass generation", true);
            	builder.pop();
            	builder.push("fog");
            		customFogs = builder.define("Enable Custom Fogs", true);
            		builder.push("values");
            			desertFog = builder.define("Desert Fog decimal value", 14539186);
            			jungleFog = builder.define("Jungle Fog decimal value", 11591080);
            			snowyFog = builder.define("Snowy Fog decimal value", 16777215);
            			swampFog = builder.define("Swamp Fog decimal value", 11595468);
            		builder.pop();
            	builder.pop();
            builder.pop();
            
            builder.push("entities");
            limitFarmAnimalSpawns = builder.comment("Make farm animals spawn in less biomes to allow new mobs to take their place and diversify biome spawns").define("Limit farm animal spawns", true);
            biomeVariantsAlwaysSpawn = builder.comment("Make biome variants of mobs like Husk always spawn in place of their original in their biomes").define("Biome variants always spawn", true);
            builder.pop();
        }
    }
    
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();
    }
    
    public static void onConfigReload(final ModConfig.ModConfigEvent event) {
		for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
			Biome.Category category = biome.getCategory();
			
			if(category == Biome.Category.DESERT) replaceFogValue(biome, EnvironmentalConfig.COMMON.desertFog);
			if(category == Biome.Category.JUNGLE) replaceFogValue(biome, EnvironmentalConfig.COMMON.jungleFog);
			if(category == Biome.Category.ICY) replaceFogValue(biome, EnvironmentalConfig.COMMON.snowyFog);
			if(category == Biome.Category.SWAMP) replaceFogValue(biome, EnvironmentalConfig.COMMON.swampFog);
		}
	}
    
    private static void replaceFogValue(Biome biome, ConfigValue<Integer> config) {
    	if (EnvironmentalConfig.COMMON.customFogs.get()) {
    		biome.field_235052_p_.fogColor = config.get();
    	} else {
    		biome.field_235052_p_.fogColor = 12638463;
    	}
    }
}