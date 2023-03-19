package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.raven_cze.projt2.client.model.ModelCrystal;
import com.raven_cze.projt2.common.content.tiles.TileCrystalOre;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SuppressWarnings("unused")
public class CrystalOreRenderer implements BlockEntityRenderer<TileCrystalOre>{
    private final ModelCrystal model;
    public static final ModelLayerLocation CRYSTAL=new ModelLayerLocation(new ResourceLocation("projt2","crystal"),"main");
    private float shade=1.0F;

    public CrystalOreRenderer(BlockEntityRendererProvider.Context ignoredContext){
        this.model=new ModelCrystal(ignoredContext.bakeLayer(CRYSTAL));
    }

    @Override
    public void render(@NotNull TileCrystalOre tco,float ticks,@NotNull PoseStack pose,@NotNull MultiBufferSource buffer,int light,int overlay){
        Matrix4f matrix=pose.last().pose();
        ResourceLocation texture= new ResourceLocation("projt2","textures/models/crystal.png");
        switch(tco.rune){
            case"vis"->{texture = new ResourceLocation("projt2","textures/models/crystal.png");shade=1.0F;}
            case"water"->{texture=new ResourceLocation("projt2","textures/models/crystalb.png");shade=1.0F;}
            case"earth"->{texture=new ResourceLocation("projt2","textures/models/crystalg.png");shade=1.0F;}
            case"fire"->{texture=new ResourceLocation("projt2","textures/models/crystalr.png");shade=1.0F;}
            case"air"->{texture=new ResourceLocation("projt2","textures/models/crystaly.png");shade=1.0F;}
            case"taint"->{
                texture=new ResourceLocation("projt2","textures/models/crystal.png");
                shade=0.2F;
            }
            case"notch"->{texture=new ResourceLocation("minecraft","textures/block/lava_still.png");shade=1.0F;}
        }
        RenderType layer=RenderType.entityTranslucent(texture);

        Random rand=new Random(tco.crystals+tco.getBlockPos().getX()+tco.getBlockPos().getY()+tco.getBlockPos().getZ());
        drawCrystal(tco,pose,buffer.getBuffer(layer),rand,light,overlay);
        if(tco.crystals>1)
            for(int crystal=1;crystal<tco.crystals;crystal++){
                float angle1=( rand.nextFloat() - rand.nextFloat() ) * 5.0F;
                float angle2=( rand.nextFloat() - rand.nextFloat() ) * 5.0F;
                this.drawCrystal(tco,pose,buffer.getBuffer(layer),angle1,angle2,rand,light,overlay);
        }
    }

    private void translateFromDir(BlockPos pos,Direction dir,PoseStack pose){
        switch(dir){
            case NORTH->{
                pose.translate(0.5F,0.5F,1.3F);
                pose.mulPose(Vector3f.XP.rotationDegrees(90.0F));
            }
            case EAST->{
                pose.translate(-0.3F,0.5F,0.5F);
                pose.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
            }
            case SOUTH->{
                pose.translate(0.5F,0.5F,-0.3F);
                pose.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
            }
            case WEST->{
                pose.translate(1.3F,0.5F,0.5F);
                pose.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
            }
            case UP->{
                pose.translate(0.5F,-0.3F,0.5F);
                pose.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            }
            case DOWN->{
                pose.translate(0.5F,1.3F,0.5F);
                pose.mulPose(Vector3f.ZP.rotationDegrees(0.0F));
            }
        }
    }

    private void drawCrystal(TileCrystalOre tco,PoseStack pose,VertexConsumer consumer,Random rand,int light,int overlay){drawCrystal(tco,pose,consumer,0.0F,0.0F,rand,light,overlay);}
    private void drawCrystal(TileCrystalOre tco,PoseStack pose,VertexConsumer consumer,float Ang1,float Ang2,Random rand,int light,int overlay){
        try{
            Direction dir = tco.orientation;
            BlockPos pos = tco.getBlockPos();

            pose.pushPose();
            translateFromDir(pos,dir,pose);
            pose.pushPose();
            pose.mulPose(Vector3f.XP.rotation( Mth.clamp(Ang1,-0.5F,0.5F) ));
            pose.mulPose(Vector3f.ZP.rotation( Mth.clamp(Ang2,-0.5F,0.5F) ));
            pose.pushPose();
            pose.scale(0.15F+rand.nextFloat()*0.075F,0.5F+rand.nextFloat()*0.1F,0.15F+rand.nextFloat()*0.05F);// Set Scale
            model.renderToBuffer(pose,consumer,15728880,OverlayTexture.NO_OVERLAY,shade,shade,shade,1.0F);
            pose.scale(1.0F,1.0F,1.0F);
            pose.popPose();
            pose.popPose();
            pose.popPose();
        }catch(Exception e){if(Utils.changed(e))e.printStackTrace();}
    }
}
