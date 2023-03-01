package com.raven_cze.projt2.common.content.world.feature.tree;

import com.raven_cze.projt2.common.content.world.feature.PT2FeatureConfig;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class TaintwoodGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration, ?> getConfiguredFeature(@NotNull Random random, boolean bees) {
        return PT2FeatureConfig.TAINTWOOD_000;
    }
}
