package com.raven_cze.projt2.common.event;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.world.PT2WorldGen;
import com.raven_cze.projt2.common.content.world.aura.AuraHandler;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=ProjectT2.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class WorldEvents{
    //public static WorldEvents INSTANCE=new WorldEvents();

    @SubscribeEvent
    public static void worldLoad(WorldEvent.Load event){
        if(!(event.getWorld()).isClientSide()){
            Level lvl=(Level)event.getWorld();
            AuraHandler.addAuraWorld(lvl.dimension());
        }

    }

    @SubscribeEvent
    public static void worldSave(WorldEvent.Save event){
        //  Ale píču
        (event.getWorld()).isClientSide();
    }

    @SubscribeEvent
    public static void worldUnload(WorldEvent.Unload event){
        if(!(event.getWorld()).isClientSide())return;
        Level lvl= (Level)event.getWorld();
        AuraHandler.removeAuraWorld(lvl.dimension());
    }

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event){
        PT2WorldGen.doSomeMagic(event);
    }
}
