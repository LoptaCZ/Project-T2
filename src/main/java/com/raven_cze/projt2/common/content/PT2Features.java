package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.PT2Config;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.TreeFeature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PT2Features{
    public static final DeferredRegister<Feature<?>>REGISTRY=DeferredRegister.create(ForgeRegistries.FEATURES,ProjectT2.MODID);

    public static final RegistryObject<Feature<TreeConfiguration>> GREATWOOD_TREE=REGISTRY.register("greatwood_tree",()->new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>>SILVERWOOD_TREE=REGISTRY.register("silverwood_tree",()->new TreeFeature(TreeConfiguration.CODEC));
    public static final RegistryObject<Feature<TreeConfiguration>>TAINTWOOD_TREE=REGISTRY.register("taintwood_tree",()->new TreeFeature(TreeConfiguration.CODEC));

    public static void register(IEventBus eventBus){
        if(PT2Config.SHARED.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering FEATUREs");
        REGISTRY.register(eventBus);
    }
}
