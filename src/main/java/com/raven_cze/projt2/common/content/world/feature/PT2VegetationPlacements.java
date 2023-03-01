package com.raven_cze.projt2.common.content.world.feature;

import com.google.common.collect.ImmutableList;
import com.raven_cze.projt2.common.content.PT2Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public class PT2VegetationPlacements{
    public static void initialize(){}

    public static final PlacedFeature GREATWOOD=registrar("greatwood",PT2Vegetation.GREATWOOD_VEGETATION.placed(treeModifier(PlacementUtils.countExtra(6,0.1F,1),PT2Blocks.GREATWOOD_SAPLING.get())));

    public static List<PlacementModifier>treeModifier(PlacementModifier modifier,Block block){
        return ImmutableList.of(modifier,RarityFilter.onAverageOnceEvery(1),BlockPredicateFilter.forPredicate(BlockPredicate.matchesBlock(block,BlockPos.ZERO)),RarityFilter.onAverageOnceEvery(2),CountPlacement.of(1));
    }
    public static PlacedFeature registrar(String unlocalized,@NotNull PlacedFeature feature){
        return PlacementUtils.register("projt2:"+Objects.requireNonNull(unlocalized),Objects.requireNonNull(feature));
    }
}
