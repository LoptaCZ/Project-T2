package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.PT2Config;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PT2Particles{
    public static final DeferredRegister<ParticleType<?>>REGISTRY=DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES,ProjectT2.MODID);
    //      ===============
    //      BASIC PARTICLES
    //      ===============
    public static final RegistryObject<SimpleParticleType>WISP=REGISTRY.register("wisp",()->new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType>SPARKLE=REGISTRY.register("sparkle",()->new SimpleParticleType(true));

    public static final RegistryObject<SimpleParticleType>BEAM=REGISTRY.register("beam",()->new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType>DRIP=REGISTRY.register("drip",()->new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType>FREEZE=REGISTRY.register("freeze",()->new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType>GUIDE_WISP=REGISTRY.register("guide_wisp",()->new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType>SCORCH=REGISTRY.register("scorch",()->new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType>WIND=REGISTRY.register("wind",()->new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        if(PT2Config.SHARED.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering PARTICLEs");
        REGISTRY.register(eventBus);
    }
}
