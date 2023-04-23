package com.raven_cze.projt2.client;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.items.ItemDiscoveryTome;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value={Dist.CLIENT},modid=ProjectT2.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class TickHandler{
    public static int ticksWithTomeOpen=0;
    public static int pageFlipTicks=0;
    public static int ticksInGame=0;
    public static float partialTicks=0.0F;

    public static float total(){return ticksInGame+partialTicks;}
    public static void renderTick(float renderTickTime){partialTicks=renderTickTime;}

    public static void clientTickEnd(Minecraft mc){
        if(!mc.isDemo()){
            ticksInGame++;
            partialTicks=0.0F;

            //	Rendering something only with some item active
        }
        int ticksToOpen=10;
        if(ItemDiscoveryTome.isOpen()){
            if(ticksWithTomeOpen<0)ticksWithTomeOpen=0;
            if(ticksWithTomeOpen<ticksToOpen)ticksWithTomeOpen++;
            if(pageFlipTicks>0)pageFlipTicks--;
        }else{
            pageFlipTicks=0;
            if(ticksWithTomeOpen>0){
                if(ticksWithTomeOpen>ticksToOpen)ticksWithTomeOpen=ticksToOpen;
                ticksWithTomeOpen--;
            }
        }
    }
    public static void notifyPageChange(){if(pageFlipTicks==0)pageFlipTicks=5;}
}
