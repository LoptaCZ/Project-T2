package com.raven_cze.projt2.common.event;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.world.aura.AuraThread;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid=ProjectT2.MODID,bus=Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents{
    public static ConcurrentHashMap<ResourceKey<Level>,AuraThread>auraThreads=new ConcurrentHashMap<>();

    @SubscribeEvent
    public static void serverTick(TickEvent.WorldTickEvent event){
        ServerLevel level=(ServerLevel)event.world;
        if(ProjectT2.dawnInc>0){
            if(ProjectT2.dawnInc==999)
                ProjectT2.dawnStep=50;
            ProjectT2.dawnStep--;
            ProjectT2.dawnInc=(int)Math.max(1L, (ProjectT2.dawnDest-level.random.nextLong())/(100L *ProjectT2.dawnStep) );
            level.setDayTime(level.getDayTime()+ProjectT2.dawnInc);
            if(level.getDayTime()>=ProjectT2.dawnDest){
                level.setDayTime((long)Utils.Math.lerp(level.getDayTime(),level.getDayTime()+ProjectT2.dawnDest,0.1));
                ProjectT2.dawnInc=0;

            }
        }
    }
}
