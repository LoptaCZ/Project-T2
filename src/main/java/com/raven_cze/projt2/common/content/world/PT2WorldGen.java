package com.raven_cze.projt2.common.content.world;

import com.raven_cze.projt2.common.content.world.feature.PT2VegetationPlacements;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.world.BiomeGenerationSettingsBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;


public class PT2WorldGen{
    @SubscribeEvent(priority=EventPriority.HIGH)
    public static void doSomeMagic(BiomeLoadingEvent event){
        generateOres(event);
        generateEntities(event);
        generateTrees(event);
        generateFlowers(event);
    }
    private static void generateOres(final BiomeLoadingEvent event){
        //null
    }
    private static void generateTrees(final BiomeLoadingEvent event){
        //TODO This shit is empty ... YEEET
        BiomeGenerationSettingsBuilder builder=event.getGeneration();
        Biome.BiomeCategory category=event.getCategory();

        if(category!=Biome.BiomeCategory.DESERT){
            if(category!=Biome.BiomeCategory.NETHER){
                if(new Random().nextInt(80)==3)//   SILVERWOOD
                    builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PT2VegetationPlacements.GREATWOOD);
                if(new Random().nextInt(25)==7)//   GREATWOOD
                    builder.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, PT2VegetationPlacements.GREATWOOD);
            }
        }
    }

    private static void generateFlowers(final BiomeLoadingEvent event){
        //null
    }
    private static void generateEntities(final BiomeLoadingEvent event){
        //null
    }
}
