package com.raven_cze.projt2.common.event;

import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
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