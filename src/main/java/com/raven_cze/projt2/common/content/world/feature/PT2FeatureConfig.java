package com.raven_cze.projt2.common.content.world.feature;

import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.PT2Features;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.FeatureSize;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;

public class PT2FeatureConfig{
    public static void initialize(){}
    public static final ConfiguredFeature<TreeConfiguration,?>GREATWOOD_000=FeatureUtils.register("greatwood",PT2Features.GREATWOOD_TREE.get().configured(getGreatwood().build()));
    public static final ConfiguredFeature<TreeConfiguration,?>SILVERWOOD_000=FeatureUtils.register("silverwood",PT2Features.SILVERWOOD_TREE.get().configured(getSilverwood().build()));
    public static final ConfiguredFeature<TreeConfiguration,?>TAINTWOOD_000=FeatureUtils.register("taintwood",PT2Features.TAINTWOOD_TREE.get().configured(getTaintwood().build()));

    private static TreeConfiguration.TreeConfigurationBuilder getGreatwood(){
        BlockStateProvider LOG=BlockStateProvider.simple(PT2Blocks.GREATWOOD_LOG.get());
        BlockStateProvider LEAVES=BlockStateProvider.simple(PT2Blocks.GREATWOOD_LEAVES.get());
        TrunkPlacer TRUNK=new StraightTrunkPlacer(4,2,0);
        FoliagePlacer FOLIAGE=new BlobFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),3);
        FeatureSize SIZE=new TwoLayersFeatureSize(1,0,1);

        return new TreeConfiguration.TreeConfigurationBuilder(LOG,TRUNK,LEAVES,FOLIAGE,SIZE).forceDirt();
    }
    private static TreeConfiguration.TreeConfigurationBuilder getSilverwood(){
        BlockStateProvider LOG=BlockStateProvider.simple(PT2Blocks.SILVERWOOD_LOG.get());
        BlockStateProvider LEAVES=BlockStateProvider.simple(PT2Blocks.SILVERWOOD_LEAVES.get());
        TrunkPlacer TRUNK=new StraightTrunkPlacer(4,2,0);
        FoliagePlacer FOLIAGE=new BlobFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),3);
        FeatureSize SIZE=new TwoLayersFeatureSize(1,0,1);

        return new TreeConfiguration.TreeConfigurationBuilder(LOG,TRUNK,LEAVES,FOLIAGE,SIZE).forceDirt();
    }
    private static TreeConfiguration.TreeConfigurationBuilder getTaintwood(){
        BlockStateProvider LOG=BlockStateProvider.simple(PT2Blocks.TAINT_LOG.get());
        BlockStateProvider LEAVES=BlockStateProvider.simple(PT2Blocks.GREATWOOD_LEAVES.get());
        TrunkPlacer TRUNK=new StraightTrunkPlacer(4,2,0);
        FoliagePlacer FOLIAGE=new BlobFoliagePlacer(ConstantInt.of(2),ConstantInt.of(0),3);
        FeatureSize SIZE=new TwoLayersFeatureSize(1,0,1);

        return new TreeConfiguration.TreeConfigurationBuilder(LOG,TRUNK,LEAVES,FOLIAGE,SIZE).forceDirt();
    }
}
