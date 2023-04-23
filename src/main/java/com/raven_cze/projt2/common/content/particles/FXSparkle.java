package com.raven_cze.projt2.common.content.particles;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class FXSparkle extends SimpleAnimatedParticle{
    public int multiplier;
    public boolean shrink;
    public int particle;
    public boolean tinkle;
    public int blendmode;
    private final float initialQuadSize;
    int type;
    @ParametersAreNonnullByDefault
    public FXSparkle(Level level,double x,double y,double z,SpriteSet sprites,float gravity){
        super((ClientLevel)level,x,y,z,sprites,gravity);
        this.multiplier=2;
        this.shrink=false;
        this.particle=16;
        this.tinkle=false;
        this.blendmode=1;
        //this.ao=f1
        //this.ap=f2
        //this.aq=f3
        this.friction=0.0F;
        this.xo=this.yo=this.zo=0.0D;
        this.lifetime*=this.age;
        this.age=3*this.age;
        this.hasPhysics=false;
        this.initialQuadSize=quadSize;
        //this.setSpriteFromAge(sprites);
    }
    public FXSparkle(Level l,double x,double y,double z,SpriteSet sprites,int particleType){
        this(l,x,y,z,sprites,0.0F);
        setType(particleType);
        setGravity(0.0F);
    }
    public void setType(int type){
        this.type=type;
        float rr;
        switch(type){
            //	VIS
            case 0->setColor(
                    0.75F+this.level.random.nextFloat()*0.25F,
                    0.25F+this.level.random.nextFloat()*0.25F,
                    0.75F+this.level.random.nextFloat()*0.25F
            );
            //	VAPOROUS
            case 1->setColor(
                    0.5F+this.level.random.nextFloat()*0.3F,
                    0.5F+this.level.random.nextFloat()*0.3F,
                    0.2F+this.level.random.nextFloat()*0.3F
            );
            //	AQUEOUS
            case 2->setColor(
                    0.2F,
                    0.2F,
                    0.7F+this.level.random.nextFloat()*0.3F
            );
            //	EARTHEN
            case 3->setColor(
                    0.2F,
                    0.7F+this.level.random.nextFloat()*0.3F,
                    0.2F
            );
            //	FIERY
            case 4->setColor(
                    0.7F+this.level.random.nextFloat()*0.3F,
                    0.2F,
                    0.2F
            );
            //	TAINT
            case 5->{this.blendmode=771;
                setColor(
                        this.level.random.nextFloat()*0.1F,
                        this.level.random.nextFloat()*0.1F,
                        this.level.random.nextFloat()*0.1F
                );}
            //	[ LIGHT GRAY ]
            case 6->setColor(
                    0.8F+this.level.random.nextFloat()*0.2F,
                    0.8F+this.level.random.nextFloat()*0.2F,
                    0.8F+this.level.random.nextFloat()*0.2F
            );
            //	[ DARK GRAY ]
            case 7->{rr=level.random.nextFloat();
                setColor(
                        0.2F+rr*0.3F,
                        0.2F+rr*0.3F,
                        0.7F+this.level.random.nextFloat()*0.3F
                );}
        }
    }
    @Override
    public @NotNull ParticleRenderType getRenderType(){
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }
    @Override
    public void render(@NotNull VertexConsumer buff,@NotNull Camera cam,float tick){
        if(this.sprite==null){
            if(Utils.changed(this.sprite))ProjectT2.LOGGER.error(ProjectT2.MARKERS.RENDER,"Variable this.sprite is null");
            return;
        }
        try{
            PoseStack pose=new PoseStack();

            pose.pushPose();
            {
                RenderSystem.depthMask(false);
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.SRC_ALPHA);
                //  Binding Texture
                buff.color(1.0F, 1.0F, 1.0F, 0.75F);

                super.render(buff,cam,tick);

                RenderSystem.disableBlend();
                RenderSystem.depthMask(true);
            }
            pose.popPose();
        }catch(Exception e){
            ProjectT2.LOGGER.error(ProjectT2.MARKERS.RENDER,"Exception caught while using custom rendering for particle. Defaulting to normal rendering.");
            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            super.render(buff,cam,tick);
        }
    }

    @Override
    public void tick(){
        if(this.shrink)this.scale(Mth.lerp(this.lifetime,this.initialQuadSize,0.0F));
        if(this.tinkle){
            if(this.age==0 && this.level.random.nextInt(10)==0){
                this.level.playSound(null,this.x,this.y,this.z,SoundEvents.EXPERIENCE_ORB_PICKUP,SoundSource.AMBIENT,0.02F,0.7F*((this.level.random.nextFloat()-this.level.random.nextFloat())*0.6F+2.0F) );
            }
            if(this.age++>=this.lifetime)this.remove();
            move(this.x,this.y,this.z);
            this.x*=0.9800000190734863D;
            this.y*=0.9800000190734863D;
            this.z*=0.9800000190734863D;
            if(this.hasPhysics){
                this.x*=0.699999988079071D;
                this.z*=0.699999988079071D;
            }
        }
        super.tick();
    }

    public void setGravity(float value){this.gravity=value;}

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;
        public Provider(SpriteSet set){this.sprites=set;}
        public Particle createParticle(@NotNull SimpleParticleType type,@NotNull ClientLevel level,double x,double y,double z,double dx,double dy,double dz){
            FXSparkle particle=new FXSparkle(level,x,y,z,sprites,0);
            particle.setParticleSpeed(dx,dy,dz);
            return particle;
        }
    }
}
