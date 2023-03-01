package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.config.CommonCFG;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PT2Particles{
    public static final DeferredRegister<ParticleType<?>>REGISTRY=DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,ProjectT2.MODID);
    public static final RegistryObject<SimpleParticleType>FX_WISP=REGISTRY.register("fxwisp",()->new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        if(CommonCFG.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering PARTICLEs");
        REGISTRY.register(eventBus);
    }
}
