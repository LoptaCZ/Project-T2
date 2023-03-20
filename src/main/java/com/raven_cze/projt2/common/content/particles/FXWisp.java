package com.raven_cze.projt2.common.content.particles;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class FXWisp extends TextureSheetParticle{
	int type;
	public FXWisp(Level level,BlockPos pos,float size,int type){
		this(level,pos.getX(),pos.getY(),pos.getZ(),null,0,0,0);
		this.scale(size);
		this.type=type;
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

		float rr;
		switch(Mth.clamp(this.type,0,7)){
			//		Vis Color
			case 0->setColor(
					0.75F+level.random.nextFloat()*0.25F,
					0.25F+level.random.nextFloat()*0.25F,
					0.75F+level.random.nextFloat()*0.25F
			);
			//		Air Color
			case 1->setColor(
					0.5F+level.random.nextFloat()*0.3F,
					0.5F+level.random.nextFloat()*0.3F,
					0.2F);
			//		Water Color
			case 2->setColor(
					0.2F,
					0.2F,
					0.7F+level.random.nextFloat()*0.3F);
			//		Earth Color
			case 3->setColor(
					0.2F,
					0.7F+level.random.nextFloat()*0.3F,
					0.2F);
			//		Fire Color
			case 4->setColor(
					0.7F+level.random.nextFloat()*0.3F,
					0.2F,
					0.2F);
			//		Blending Black Particle?
			//		Probably Taint Color
			case 5->{
				//Set Blend Mode 771;
					setColor(
					level.random.nextFloat()*0.1F,
					level.random.nextFloat()*0.1F,
					level.random.nextFloat()*0.1F);
			}
			//		Gray Particle
			case 6->setColor(
					0.8F+level.random.nextFloat()*0.2F,
					0.8F+level.random.nextFloat()*0.2F,
					0.8F+level.random.nextFloat()*0.2F);
			//		Blue-ish Particle (Blue-Purple)
			case 7->{
				rr=level.random.nextFloat();
					setColor(
					0.2F+rr*0.3F,
					0.2F+rr*0.3F,
					0.7F+level.random.nextFloat()*0.3F);
			}
		}
		@SuppressWarnings("unused")
		Color test=new Color(0.2F+0.5F*0.3F,0.2F+0.5F*0.3F,0.7F+0.9F*0.3F);
		//this.rCol=this.gCol=this.bCol=1.0F;
	}
	@Override
	public void tick(){super.tick();fadeOut();}
	private void fadeOut(){this.alpha=(-(1/(float)lifetime)*age+1);}

	public void setType(int type){
		this.type=type;
	}
	@Override
	public @NotNull ParticleRenderType getRenderType(){return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;}
	
	@OnlyIn(Dist.CLIENT)
	public static class Provider implements ParticleProvider<SimpleParticleType>{
		private final SpriteSet sprites;
		public Provider(SpriteSet set){this.sprites=set;}

		public Particle createParticle(@NotNull SimpleParticleType type,@NotNull ClientLevel level,double x,double y,double z,double dx,double dy,double dz){
			return new FXWisp(level,x,y,z,this.sprites,dx,dy,dz);
		}
	}
}
