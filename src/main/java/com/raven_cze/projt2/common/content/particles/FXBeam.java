package com.raven_cze.projt2.common.content.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class FXBeam extends TextureSheetParticle {
    protected FXBeam(ClientLevel level,double x,double y,double z,SpriteSet sprites){
        super(level,x,y,z);
        this.setSpriteFromAge(sprites);
    }
    public FXBeam(Level level,double x,double y,double z,double dirX,double dirY,double dirZ,SpriteSet sprites){
        this((ClientLevel)level,x,y,z,sprites);
        this.setParticleSpeed(dirX,dirY,dirZ);
    }

    @Override
    public @NotNull ParticleRenderType getRenderType(){return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;}

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet set){this.sprites=set;}
        public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y, double z, double dx, double dy, double dz){
            return new FXBeam(level,x,y,z,dx,dy,dz,sprites);
        }
    }
}
