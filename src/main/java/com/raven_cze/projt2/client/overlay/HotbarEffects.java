package com.raven_cze.projt2.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class HotbarEffects{
    ResourceLocation TEXTURE=new ResourceLocation("projt2","textures/particles/particle.png");

    public void renderParticles1(int slot,int progress,int offset){
        Minecraft mc= Minecraft.getInstance();
        int width=mc.getWindow().getWidth();
        int height=mc.getWindow().getHeight();
        mc.getWindow().updateDisplay();

        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);


    }
    public void renderParticles2(int slot,int progress,int offset){

    }
}
