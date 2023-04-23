package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.PT2Config;
import com.raven_cze.projt2.common.content.entities.Singularity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PT2Entities{
    public static final DeferredRegister<EntityType<?>>REGISTRY =DeferredRegister.create(ForgeRegistries.ENTITIES,ProjectT2.MODID);
    public static final RegistryObject<EntityType<Singularity>>SINGULARITY=register(rs("singularity"),()->EntityType.Builder.of(Singularity::new,MobCategory.MISC));


    private static<T extends Entity>RegistryObject<EntityType<T>>register(ResourceLocation name,Supplier<EntityType.Builder<T>>supplier){
        if(PT2Config.SHARED.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering ENTITIEs");
        return REGISTRY.register(name.getPath(),()->((EntityType.Builder)supplier.get()).build(name.toString()) );
    }
    private static ResourceLocation rs(String name){return new ResourceLocation(ProjectT2.MODID,name);}
    public static void register(IEventBus eventBus){
        REGISTRY.register(eventBus);}
}
