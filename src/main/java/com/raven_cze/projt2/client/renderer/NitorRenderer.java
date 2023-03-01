package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.raven_cze.projt2.common.content.PT2Particles;
import com.raven_cze.projt2.common.content.tiles.TileNitor;
import com.raven_cze.projt2.common.content.particles.FXWisp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class NitorRenderer implements BlockEntityRenderer<TileNitor>{
    int count=0;
    public NitorRenderer(BlockEntityRendererProvider.Context ignoredContext){}

    @Override
    public void render(@NotNull TileNitor nitor,float tick,@NotNull PoseStack stack,@NotNull MultiBufferSource buff,int light,int overlay){
        double x=nitor.getBlockPos().getX();
        double y=nitor.getBlockPos().getY();
        double z=nitor.getBlockPos().getZ();

        if(Objects.requireNonNull(nitor.getLevel()).isClientSide()){
                this.drawFlame((x + 0.5F) + nitor.getLevel().random.nextGaussian() * 0.025D, (y + 0.45F) + nitor.getLevel().random.nextGaussian() * 0.025D, (z + 0.5F) + nitor.getLevel().random.nextGaussian() * 0.025D, nitor.getLevel().random
                                .nextGaussian() * 0.0025D, nitor.getLevel().random.nextFloat() * 0.06D, nitor.getLevel().random.nextGaussian() * 0.0025D,
                         0);
            if(this.count++%10==0)
                this.drawCore(x+0.5F,y+0.49F,z+0.5F,0.0D,0.0D,0.0D);
        }
    }
    private void drawCore(double x,double y,double z,double x2,double y2,double z2){
        FXWisp fb=new FXWisp(this.getWorld(),new BlockPos(x,y,z),1,0);
        fb.setLifetime(10);
        fb.setColor(1.0F,1.0F,1.0F);
        //setting alpha?
        //IDK
        //custom scale
        //layers?
        //random movement
        fb.setParticleSpeed(2.0E-4F,2.0E-4F,2.0E-4F);
        //Minecraft.getInstance().particleEngine.add(fb);
        this.getWorld().addParticle(PT2Particles.FX_WISP.get(),x,y,z,x2,y2,z2);
    }
    private void drawFlame(double x,double y,double z,double x2,double y2,double z2,int delay){
        int mDelay=(int)System.currentTimeMillis()+delay;
        if(mDelay<System.currentTimeMillis()){
            FXWisp fb=new FXWisp(this.getWorld(),new BlockPos(x,y,z),1,4);
            //idk
            //Color c=newColor(int color)
            //setting color
            Color c=new Color(245,158,34);
            fb.setColor(c.getRed(),c.getGreen(),c.getBlue());
            //setting alpha
            //setLoop(true)
            //setGridSize(64)
            //idk
            //custom scale
            fb.setParticleSpeed(0.0025F,0.0F,0.0025F);
            //Minecraft.getInstance().particleEngine.add(fb);
            this.getWorld().addParticle(PT2Particles.FX_WISP.get(),x,y,z,x2,y2,z2);
            fb=new FXWisp(getWorld(),new BlockPos(x,y,z),0.25F,1);
            //setGravity(0.05F)
            //Minecraft.getInstance().particleEngine.add(fb);
            this.getWorld().addParticle(PT2Particles.FX_WISP.get(),x,y,z,x2,y2,z2);
        }
    }
    private Level getWorld(){return Minecraft.getInstance().level;}
}
