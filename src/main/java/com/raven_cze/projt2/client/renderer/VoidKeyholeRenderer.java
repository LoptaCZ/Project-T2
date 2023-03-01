package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.raven_cze.projt2.client.model.ModelCrystal;
import com.raven_cze.projt2.common.content.tiles.TileVoidKeyhole;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class VoidKeyholeRenderer implements BlockEntityRenderer<TileVoidKeyhole>{
    private final ModelCrystal model;
    public static final ModelLayerLocation CRYSTAL=new ModelLayerLocation(new ResourceLocation("projt2","crystal"),"main");
    public VoidKeyholeRenderer(BlockEntityRendererProvider.Context ignoredContext){
        this.model=new ModelCrystal(ignoredContext.bakeLayer(CRYSTAL));
    }
    private boolean darken=false;
    protected ResourceLocation texture;

    @Override
    public void render(@NotNull TileVoidKeyhole keyhole,float tick,@NotNull PoseStack stack,@NotNull MultiBufferSource buff,int light,int overlay){
        Matrix4f matrix=stack.last().pose();
        this.renderCube(keyhole,matrix,buff.getBuffer(PT2RenderTypes.VOID_CUBE));

        if(keyhole.placed){
            switch(keyhole.rune){
                default->{texture=new ResourceLocation("projt2","textures/models/crystal.png");
                darken=false;}
                case"vis"-> texture =new ResourceLocation("projt2","textures/models/crystal.png");
                case"water"-> texture =new ResourceLocation("projt2","textures/models/crystalb.png");
                case"earth"-> texture =new ResourceLocation("projt2","textures/models/crystalg.png");
                case"fire"-> texture =new ResourceLocation("projt2","textures/models/crystalr.png");
                case"air"-> texture =new ResourceLocation("projt2","textures/models/crystaly.png");
                case"taint"->{
                    texture =new ResourceLocation("projt2","textures/models/crystal.png");
                    darken=true;}
            }
            RenderType layer=RenderType.entityTranslucent(texture);

            stack.pushPose();

            stack.translate(0.5F,1.6F,0.5F);

            stack.scale(0.15F,0.45F,0.15F);

            stack.mulPose(new Quaternion(0.0F,0.0F,0.0F,true));

            this.model.renderToBuffer(stack,buff.getBuffer(layer),15728880,OverlayTexture.NO_OVERLAY,darken ? 0.3F : 1.0F,darken ? 0.3F : 1.0F,darken ? 0.3F : 1.0F,1.0F);

            stack.scale(1.0F,1.0F,1.0F);

            stack.popPose();
        }
    }

    //  Shits for rendering inside

    private void renderCube(TileVoidKeyhole te, Matrix4f matrix, VertexConsumer consumer){
        this.renderFace(te,matrix,consumer,0.0F,0.99F,0.0F,0.99F,0.99F,0.99F,0.99F,0.99F, Direction.SOUTH);
        this.renderFace(te,matrix,consumer,0.0F,0.99F,0.99F,0.0F,0.0F,0.0F,0.0F,0.0F,Direction.NORTH);
        this.renderFace(te,matrix,consumer,0.99F,0.99F,0.99F,0.0F,0.0F,0.99F,0.99F,0.0F,Direction.EAST);
        this.renderFace(te,matrix,consumer,0.0F,0.0F,0.0F,0.99F,0.0F,0.99F,0.99F,0.0F,Direction.WEST);
        this.renderFace(te,matrix,consumer,0.0F,0.99F,0.99F,0.99F,0.0F,0.0F,0.99F,0.99F,Direction.DOWN);
        this.renderFace(te,matrix,consumer,0.0F,0.99F,0.99F,0.99F,0.99F,0.99F,0.0F,0.0F,Direction.UP);
    }
    private void renderFace(TileVoidKeyhole te,Matrix4f m,VertexConsumer c,float x0,float x1,float y0,float y1,float z0,float z1,float z2,float z3,Direction dir){
        if(te.shouldRenderFace(dir)){
            c.vertex(m,x0,y0,z0).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
            c.vertex(m,x1,y0,z1).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
            c.vertex(m,x1,y1,z2).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
            c.vertex(m,x0,y1,z3).color(1.0F, 1.0F, 1.0F, 1.0F).endVertex();
        }
    }
}
