package com.raven_cze.projt2.common.content.world.feature.tree;


import com.raven_cze.projt2.common.content.world.feature.PT2FeatureConfig;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SilverwoodGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ConfiguredFeature<TreeConfiguration,?> getConfiguredFeature(@NotNull Random random, boolean bool) {
        return PT2FeatureConfig.SILVERWOOD_000;
    }
}