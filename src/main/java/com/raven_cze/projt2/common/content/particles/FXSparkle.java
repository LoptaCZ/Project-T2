package com.raven_cze.projt2.common.content.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import org.jetbrains.annotations.NotNull;

public class FXSparkle extends TextureSheetParticle {
    protected FXSparkle(ClientLevel cLevel,double x,double y,double z){
        super(cLevel,x,y,z);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
}
