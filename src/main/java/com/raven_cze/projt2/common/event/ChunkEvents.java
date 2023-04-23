package com.raven_cze.projt2.common.event;

import com.raven_cze.projt2.ProjectT2;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=ProjectT2.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ChunkEvents{
    @SubscribeEvent
    public static void chunkSave(ChunkDataEvent.Save event){
        //  Saving Aura to chunk
    }//	chunkSave
    @SubscribeEvent
    public static void chunkLoad(ChunkDataEvent.Load event){//  ProjectT2.LOGGER.debug("");
        //  Loading Aura from chunk
    }//	chunkLoad
}