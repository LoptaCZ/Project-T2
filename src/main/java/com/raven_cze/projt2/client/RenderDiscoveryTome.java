package com.raven_cze.projt2.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.PT2Config;
import com.raven_cze.projt2.common.content.PT2Items;
import com.raven_cze.projt2.common.content.items.ItemDiscoveryTome;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.BookModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class RenderDiscoveryTome{
    private static BookModel model=null;
    public static final ResourceLocation TEXTURE=new ResourceLocation("projt2","textures/models/book.png");

    private static BookModel getModel(){
        if(model==null)model=new BookModel(Minecraft.getInstance().getEntityModels().bakeLayer(ModelLayers.BOOK));
        return model;
    }
    public static boolean renderHand(ItemStack stack,ItemTransforms.TransformType type,boolean leftHand,PoseStack pose,MultiBufferSource buffer,int light){
        if( PT2Config.CLIENT.lowFX.get() || !type.firstPerson() || !stack.is(PT2Items.BOOK.get()) )return false;
        try{
            doRender(leftHand,pose,buffer,light,TickHandler.partialTicks);
            return true;
        }catch(Throwable throwable){
            if(Utils.changed(throwable))
                ProjectT2.LOGGER.warn("Failed to render a Discovery Tome",throwable);
            return false;
        }
    }

    public static void doRender(boolean leftHand,PoseStack pose,MultiBufferSource buffers,int light,float tick){
        pose.pushPose();
        float ticks=TickHandler.ticksWithTomeOpen;
        if(ticks>0.0F &&ticks<10.0F)
            if(ItemDiscoveryTome.isOpen()){ticks+=tick;}else{ticks-=tick;}

        if(ItemDiscoveryTome.isOpen()){//   Thaumonomicon Open
            if(leftHand){//                 Left Hand
                pose.translate( 0.164F-0.2F,0.128F,-0.5F);
                pose.mulPose(Vector3f.XP.rotation(-0.2F));
                pose.mulPose(Vector3f.YN.rotation(1.5F));
            }else{//                        Right Hand
                pose.translate( 0.064F,0.128F,-0.5F);
                pose.mulPose(Vector3f.XP.rotation(-0.2F));
                pose.mulPose(Vector3f.YN.rotation(1.5F));
            }
        }else{//                            Thaumonomicon Closed
            if(leftHand){//                 Left Hand
                pose.translate(-0.500F,0.25F,-0.5F);
                pose.mulPose(Vector3f.XP.rotation(-0.25F));
            }else{//                        Right Hand
                pose.translate(0.128F,0.25F,-0.5F);
                pose.mulPose(Vector3f.XP.rotation(-0.25F));
            }
        }
        float opening=Mth.clamp(ticks/12.0F,0.0F,1.0F);
        float pageFlipTicks=TickHandler.pageFlipTicks;
        if(pageFlipTicks>0.0F)pageFlipTicks-=TickHandler.partialTicks;
        float pageFlip=pageFlipTicks/5.0F;
        float leftPageAngle =Mth.abs(pageFlip+0.25F)*1.6F-0.3F;
        float rightPageAngle=Mth.abs(pageFlip+0.75F)*1.6F-0.3F;
        BookModel model=getModel();
        model.setupAnim(TickHandler.total(),Mth.clamp(leftPageAngle,0.0F,1.0F),Mth.clamp(rightPageAngle,0.0F,1.0F),opening);
        RenderType layer=RenderType.entityCutout(TEXTURE);
        model.renderToBuffer(pose,buffers.getBuffer(layer),light,OverlayTexture.NO_OVERLAY,1.0F,1.0F,1.0F,1.0F);

        //  No fancy text till i know how to use this
        /*
        if(ticks<3.0F){
            Font font=Minecraft.getInstance().font;
            pose.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
            pose.translate(0.0F,0.0F,-1.0F);
            pose.scale(0.0030F,0.0030F,-0.0030F);
            font.drawInBatch(font.plainSubstrByWidth(new TranslatableComponent("item.projt2.discovery_tome").getContents(),80),0,0,16711680,false,pose.last().pose(),buffers,false,0,light);
        }*/
        pose.popPose();
    }
}
