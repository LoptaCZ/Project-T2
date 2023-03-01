package com.raven_cze.projt2.common.content.world.feature;

import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.WeightedPlacedFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomFeatureConfiguration;

import java.util.List;

public class PT2Vegetation{
    public static void initialize(){}

    public static final ConfiguredFeature<RandomFeatureConfiguration,?>GREATWOOD_VEGETATION=FeatureUtils.register("greatwood_vegetation",Feature.RANDOM_SELECTOR.configured(new RandomFeatureConfiguration(List.of(new WeightedPlacedFeature(PT2TreePlacements.GREATWOOD_000,0.5F)),PT2TreePlacements.GREATWOOD_000)));
}
