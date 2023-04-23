package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.PT2Config;
import com.raven_cze.projt2.common.content.tiles.TileSeal;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.client.renderer.LightTexture;
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

@SuppressWarnings({"unused"})
public class SealRenderer implements BlockEntityRenderer<TileSeal>{
    public SealRenderer(BlockEntityRendererProvider.Context ignoredContext){
        this.fuckOff=false;
        this.bob=0.0F;
        this.count=0;
    }
    public static final ResourceLocation tPortalOpen =new ResourceLocation("projt2","textures/misc/portal2.png");
    public static final ResourceLocation tPortalClosed =new ResourceLocation("projt2","textures/misc/portal.png");
    private float bob;
    private int count;
    private boolean fuckOff;

    private void translateFromDir(BlockPos pos,Direction dir,PoseStack pose){
        int x=pos.getX();
        int y=pos.getY();
        int z=pos.getZ();

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
        try{
            if(!fuckOff){
                boolean noPush=true;
                this.count=(int)MinecraftForgeClient.getPartialTick();
                this.bob=Mth.sin(count/10.0F)*0.025F+0.03F;
                //renderTileAt(seal,ticks,pose,buffers,light,OverlayTexture.NO_OVERLAY);
                {   //pose.pushPose();
                    int a=this.count%360;
                    translateFromDir(seal.getBlockPos(),seal.orientation,pose);
                    pose.translate(0.33F, 0.33F, 0.0F);

                    if(seal.runes[0] == 0 && seal.runes[1] == 1 && seal.portalSize > 0.0F){
                        noPush = false;
                    }
                    if(seal.runes[0] != -1){
                        pose.translate(0.5F, 0.5F, -0.015F);
                        drawSeal(180.0F, 0, seal.runes[0], pose, buffers);
                        //noPush = false;
                    }
                    if(seal.runes[1] != -1){
                        //pose.pushPose();
                        //pose.pushPose();
                        pose.translate(0.5F, 0.5F, -0.02F);
                        drawSeal(-a, 1, seal.runes[0], pose, buffers);
                        //noPush = false;
                    }
                    if(seal.runes[2] != -1){
                        //pose.pushPose();
                        //pose.pushPose();
                        pose.translate(0.5F, 0.5F, -0.02F-this.bob);
                        drawSeal(a, 2, seal.runes[0], pose, buffers);
                        //noPush = false;
                    }
                    //if(noPush){
                    //    pose.popPose();
                    //    pose.popPose();
                    //}
                    //pose.popPose()
                }
            }else{if(Utils.changed(fuckOff))ProjectT2.LOGGER.error(ProjectT2.MARKERS.RENDER,"Variable fuckOff have been triggered!");}
        }catch(Exception e){fuckOff=true;e.printStackTrace();}
    }
    /*
    public void renderTileAt(TileSeal seal,float ticks,PoseStack pose,MultiBufferSource buffers,int light,int overlay){
        int a=this.count%360;
        translateFromDir(seal.getBlockPos(),seal.orientation,pose);
        pose.translate(0.33F,0.33F,0.0F);
        //  Render the Seal?
        pose.pushPose();
        boolean noPush=true;
        if(seal.runes[0]!=-1){
            pose.translate(0.5F,0.5F,-0.015F);
            if(!fuckOff)drawSeal(180.0F,0,seal.runes[0],pose,buffers);
            noPush=false;
        }
        if(seal.runes[1]!=-1){
            //pose.pushPose();
            //.pushPose();
            pose.translate(0.5F,0.5F,-0.02F);
            if(!fuckOff)drawSeal(-a,1,seal.runes[0],pose,buffers);
            noPush=false;
        }
        if(seal.runes[2]!=-1){
            //pose.pushPose();
            //pose.pushPose();
            pose.translate(0.5F,0.5F,-0.02-this.bob);
            if(!fuckOff)drawSeal(a,2,seal.runes[0],pose,buffers);
            noPush=false;
        }
        if(seal.runes[0]==0 && seal.runes[1]==1 && seal.portalSize>0.0F){
            //pose.pushPose();
            //pose.pushPose();
            pose.translate(0.5F,0.5F,-seal.portalSize/5.0F);
            //drawPortal(seal,seal.getBlockPos(),(-a*4),pose);
            noPush=false;
        }
        if(noPush){
            //pose.popPose();
            //pose.popPose();
        }
        pose.popPose();
    }*/

    private void drawSeal(float angle,int level,int rune,PoseStack pose,MultiBufferSource buffs){
        try{
            RenderType layer;
            pose.mulPose(new Quaternion(Vector3f.XN, 90.0F, false));
            pose.mulPose(new Quaternion(Vector3f.YP, angle, false));
            pose.translate(-0.5F, 0.0F, -0.5F);
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            RenderSystem.blendFunc(770, 1);
            if (level != 2)
                layer = RenderType.entityTranslucentCull(new ResourceLocation("projt2","textures/misc/s_" + level + "_" + rune + ".png"));
            else
                layer = RenderType.entityTranslucentCull(new ResourceLocation("projt2","textures/misc/seal5.png"));
            VertexConsumer c = buffs.getBuffer(layer);
            //  Vertexes
            RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
            if(level==2)c.color(colors[rune]);
            Matrix4f m=pose.last().pose();
            try{
                c.vertex(m,0,1,0).color(colors[rune]).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(1,0,0).endVertex();
                c.vertex(m,1,1,0).color(colors[rune]).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(1,0,0).endVertex();
                c.vertex(m,1,0,0).color(colors[rune]).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(1,0,0).endVertex();
                c.vertex(m,0,0,0).color(colors[rune]).uv(0,0).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(1,0,0).endVertex();
            }catch(Exception r){fuckOff=true;r.printStackTrace();}
            //  Finishing shits
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
            //pose.popPose();
            //pose.popPose();
        }catch(Exception e){fuckOff=true;e.printStackTrace();}
    }

    private void drawPortal(TileSeal seal,BlockPos pos,float ticks,PoseStack pose){
        Direction facing=seal.orientation;
        pose.pushPose();
        if(!PT2Config.CLIENT.portalGFX.get())return;
        {
            //      Code goes here
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            //      disableLightning()
            RenderSystem.setShaderColor(1, 1, 1, 1);
            RenderSystem.bindTexture(GL11.GL_TEXTURE_2D);

            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            RenderSystem.texParameter(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            pose.translate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            pose.mulPose(new Quaternion(-90F * facing.ordinal() + 180F, 0, 1, false));
            //      GlStateManager.enableRescaleNormal()

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
            //      GlStateManager.disableRescaleNormal()
            RenderSystem.disableBlend();
            //      EnableLightning
        }
        pose.popPose();
    }

    private static final int[]colors=new int[]{13532671,16777088,8421631,8454016,16744576,4194368};
}
