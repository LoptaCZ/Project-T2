package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.raven_cze.projt2.common.config.ClientCFG;
import com.raven_cze.projt2.common.content.tiles.TileSeal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.MinecraftForgeClient;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.NoSuchElementException;

@SuppressWarnings({"unused"})
public class SealRenderer implements BlockEntityRenderer<TileSeal>{
    public SealRenderer(BlockEntityRendererProvider.Context ignoredContext){}
    public static final ResourceLocation texPortalOpen=new ResourceLocation("projt2","textures/misc/portal2.png");
    public static final ResourceLocation texPortalClosed=new ResourceLocation("projt2","textures/misc/portal.png");
    private float bob=0.0F;
    private int count=0;
    private boolean fuckOff=false;

    private void translateFromDir(BlockPos pos,Direction dir,PoseStack pose){
        int x= pos.getX();
        int y= pos.getY();
        int z= pos.getZ();

        if(dir.equals(Direction.NORTH)){
            pose.translate(0.5F,0.5F,1.3F);
            pose.mulPose(Vector3f.XP.rotationDegrees(90.0F));
        }else if(dir.equals(Direction.EAST)){
            pose.translate(-0.3F,0.5F,0.5F);
            pose.mulPose(Vector3f.ZP.rotationDegrees(90.0F));
        }else if(dir.equals(Direction.SOUTH)){
            pose.translate(0.5F,0.5F,-0.3F);
            pose.mulPose(Vector3f.XP.rotationDegrees(-90.0F));
        }else if(dir.equals(Direction.WEST)){
            pose.translate(1.3F,0.5F,0.5F);
            pose.mulPose(Vector3f.ZP.rotationDegrees(-90.0F));
        }else if(dir.equals(Direction.UP)){
            pose.translate(0.5F,-0.3F,0.5F);
            pose.mulPose(Vector3f.ZP.rotationDegrees(180.0F));
        }else if(dir.equals(Direction.DOWN)){
            pose.translate(0.5F,1.3F,0.5F);
            //pose.mulPose(Vector3f.ZP.rotationDegrees(0.0F));
        }
    }

    @Override
    public void render(@NotNull TileSeal seal,float ticks,@NotNull PoseStack pose,@NotNull MultiBufferSource buffers,int light,int overlay){
        Minecraft mc=Minecraft.getInstance();
        this.count=(int)MinecraftForgeClient.getPartialTick();
        this.bob=Mth.sin(count/10.0F)*0.025F+0.03F;

        try{ if(!fuckOff)renderTileAt(seal,ticks,pose,buffers,light,OverlayTexture.NO_OVERLAY);
        }catch(NoSuchElementException e){fuckOff=true;e.printStackTrace();}
    }

    public void renderTileAt(TileSeal seal,float ticks,PoseStack pose,MultiBufferSource buffers,int light,int overlay){
        int a=this.count%360;
        translateFromDir(seal.getBlockPos(),seal.orientation,pose);
        pose.translate(0.33F,0.33F,0.0F);
        //  Render the Seal?
        pose.pushPose();
        boolean noPush=true;
        if(seal.runes[0]!=-1){
            pose.translate(0.5F,0.5F,-0.015F);
            try{drawSeal(180.0F,0,seal.runes[0],pose,buffers);}catch(Exception ignored){}
            noPush=false;
        }
        if(seal.runes[1]!=-1){
            pose.pushPose();
            pose.pushPose();
            pose.translate(0.5F,0.5F,-0.02F);
        try{drawSeal(-a,1,seal.runes[0],pose,buffers);}catch(Exception ignored){}
            noPush=false;
        }
        if(seal.runes[2]!=-1){
            pose.pushPose();
            pose.pushPose();
            pose.translate(0.5F,0.5F,-0.02-this.bob);
            try{drawSeal(a,2,seal.runes[0],pose,buffers);}catch(Exception ignored){}
            noPush=false;
        }
        if(seal.runes[0]==0 && seal.runes[1]==1 && seal.portalSize>0.0F){
            pose.pushPose();
            pose.pushPose();
            pose.translate(0.5F,0.5F,-seal.portalSize/5.0F);
            //drawPortal(seal,seal.getBlockPos(),(-a*4),pose);
            //noPush=false;
        }
        if(noPush){
            pose.popPose();
            pose.popPose();
        }
        pose.popPose();
    }

    private void drawSeal(float angle,int level,int rune,PoseStack pose,MultiBufferSource buffs){
        Quaternion angularus=new Quaternion(Vector3f.XN,90.0F,false);
        angularus.mul(new Quaternion(Vector3f.YP,angle,false));
        pose.mulPose(angularus);
        pose.translate(-0.5F,0.0F,-0.5F);
        RenderSystem.depthMask(false);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770,1);
        ResourceLocation texture;
        if(level!=2){
            texture=new ResourceLocation("projt2","textures/misc/s_"+level+"_"+rune+".png");
            //bind texture to 's_<level>_<rune>.png'
        }else{
            texture=new ResourceLocation("projt2","textures/misc/seal5.png");
            //bind texture to 'seal5.png'
        }
        RenderType layer=RenderType.entityTranslucent(texture);
        VertexConsumer tess=buffs.getBuffer(layer);
        GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
        {
            Matrix4f matrix=pose.last().pose();
            //tess.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.BLOCK);
            if(level==2)tess.color(colors[rune]);
            tess.vertex(matrix,0.0F,0.0F,1.0F).endVertex();
            tess.vertex(matrix,1.0F,0.0F,1.0F).endVertex();
            tess.vertex(matrix,1.0F,0.0F,0.0F).endVertex();
            tess.vertex(matrix,0.0F,0.0F,0.0F).endVertex();
            //tess.end();

            RenderSystem.disableBlend();
            RenderSystem.depthMask(true);
            GL11.glPopMatrix();
            GL11.glPopMatrix();
        }
    }

    private void drawPortal(TileSeal seal,BlockPos pos,float ticks,PoseStack pose){
        Direction facing=seal.orientation;
        if(!ClientCFG.portalGFX.get())return;

        {//Code goes here
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            //      disableLightning()
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.bindTexture(GL11.GL_TEXTURE_2D);

            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            pose.translate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            pose.mulPose(new Quaternion(-90F * facing.ordinal() + 180F, 0, 1, false));
            //GlStateManager.enableRescaleNormal()

            GL11.glBegin(GL11.GL_QUADS);
            {
                GL11.glTexCoord2d(1,0);
                GL11.glVertex3d(0.0625,0.0625,0);
                GL11.glTexCoord2d(0,0);
                GL11.glVertex3d(0.9375,0.0625,0);
                GL11.glTexCoord2d(0,1);
                GL11.glVertex3d(0.9375,0.9375,0);
                GL11.glTexCoord2d(1,1);
                GL11.glVertex3d(0.0625,0.9375,0);
            }
            GL11.glEnd();
            //GlStateManager.disableRescaleNormal()
            RenderSystem.disableBlend();
            //EnableLightning
        }
        pose.popPose();
    }

    private void drawPlane(double multi){
        Tesselator tesselator=Tesselator.getInstance();
        BufferBuilder builder=tesselator.getBuilder();
        builder.begin(VertexFormat.Mode.QUADS,DefaultVertexFormat.BLOCK);
            builder.vertex(-0.5D*multi,-0.5D*multi,0.0D).uv(0.0F,0.0F).endVertex();
            builder.vertex(0.5D*multi,-0.5D*multi,0.0D).uv(1.0F,0.0F).endVertex();
            builder.vertex(0.5D*multi,0.5D*multi,0.0D).uv(1.0F,1.0F).endVertex();
            builder.vertex(-0.5D*multi,0.5D*multi,0.0D).uv(0.0F,1.0F).endVertex();
        builder.end();
    }

    private static final int[]colors=new int[]{13532671,16777088,8421631,8454016,16744576,4194368};
}
