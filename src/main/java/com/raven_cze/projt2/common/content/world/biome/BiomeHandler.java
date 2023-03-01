package com.raven_cze.projt2.common.content.world.biome;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class BiomeHandler {
    //public static Biome TAINTED;
    public static HashMap<BiomeDictionary.Type,List<?>>biomeInfo=new HashMap<>();
    public static HashMap<ResourceKey<Level>,Boolean>dimBlacklist=new HashMap<>();
    public static HashMap<Biome,Boolean>biomeBlacklist=new HashMap<>();
    public static float getBiomeAuraModifier(Biome biome){
        try{
            Set<BiomeDictionary.Type>types=BiomeDictionary.getTypes(null);
            float average=0.0F;
            int count=0;
            for(BiomeDictionary.Type type:types){
                average += (Float)(biomeInfo.get(type)).get(0);
                count++;
            }
            return average/count;
        }catch(Exception exception){return 0.5F;}
    }
    public static float getBiomeSupportGreatwood(ResourceKey<Biome> biome){
        try{
            Set<BiomeDictionary.Type>types=BiomeDictionary.getTypes(biome);
            for(BiomeDictionary.Type type:types){
                if ((Boolean)((biomeInfo.get(type).get(2)))){
                    return (Float) (biomeInfo.get(type)).get(3);
                }
            }
        }catch(Exception ignored){}
        return 0.0F;
    }
    public static void addDimBlacklist(ResourceKey<Level>dim){
        dimBlacklist.put(dim,true);
    }
    public static boolean getDimBlacklist(ResourceKey<Level>dim){
        if (!dimBlacklist.containsKey(dim))return false;
        return dimBlacklist.get(dim);
    }
    public static void addBiomeBlacklist(Biome obj){
        biomeBlacklist.put(obj,true);
    }
    public static boolean getBiomeBlacklist(Biome biome){
        if (!biomeBlacklist.containsKey(biome))return false;
        return biomeBlacklist.get(biome);
    }
}
