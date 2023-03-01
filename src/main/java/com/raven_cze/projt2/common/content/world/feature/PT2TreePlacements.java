package com.raven_cze.projt2.common.content.world.feature;

import com.raven_cze.projt2.common.content.PT2Blocks;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PT2TreePlacements{
    public static void initialize(){}
    public static final PlacedFeature GREATWOOD_000=registrar("greatwood_000",PT2FeatureConfig.GREATWOOD_000.filteredByBlockSurvival(PT2Blocks.GREATWOOD_SAPLING.get()));
    public static PlacedFeature registrar(String unlocalized,@NotNull PlacedFeature feature){
        return PlacementUtils.register("projt2:"+Objects.requireNonNull(unlocalized),Objects.requireNonNull(feature));
    }
}
