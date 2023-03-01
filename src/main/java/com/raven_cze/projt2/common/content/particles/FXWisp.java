package com.raven_cze.projt2.common.content.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

public class FXWisp extends TextureSheetParticle{
	public FXWisp(Level level,BlockPos pos,float size,int type){
		this(level,pos.getX(),pos.getY(),pos.getZ(),null,0,0,0);
		this.scale(size);
	}
	
	protected FXWisp(Level level,double x,double y,double z,SpriteSet sprites,double dx,double dy,double dz){
		super((ClientLevel)level,x,y,z,dx,dy,dz);
		
		this.friction=0.8F;
		this.xd=dx;
		this.yd=dy;
		this.zd=dz;
		this.quadSize*=0.85F;
		this.lifetime=20;
		this.setSpriteFromAge(sprites);
		
		this.rCol=this.gCol=this.bCol=1.0F;
	}
	@Override
	public void tick(){super.tick();fadeOut();}
	private void fadeOut(){this.alpha=(-(1/(float)lifetime)*age+1);}
	
	@Override
	public @NotNull ParticleRenderType getRenderType(){return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;}
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType>{
		private final SpriteSet sprites;
		public Provider(SpriteSet set){this.sprites=set;}
		
		public Particle createParticle(@NotNull SimpleParticleType type, @NotNull ClientLevel level, double x, double y , double z, double dx, double dy, double dz){
			return new FXWisp(level,x,y,z,this.sprites,dx,dy,dz);
		}
	}
}
