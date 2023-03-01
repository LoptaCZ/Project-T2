package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;
import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.tiles.TileVoidInterface;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class VoidInterfaceRenderer implements BlockEntityRenderer<TileVoidInterface>{
    private final ResourceLocation SYMBOLS=new ResourceLocation("projt2","textures/tunnel_4096.png");//  textures/particle/particles.png
    public VoidInterfaceRenderer(BlockEntityRendererProvider.Context ignoredContext){}

    @Override
    public void render(@NotNull TileVoidInterface tvc,float ticks,@NotNull PoseStack stack,@NotNull MultiBufferSource buffer,int overlay,int light){
        Matrix4f matrix=stack.last().pose();
        this.renderCube(tvc,matrix,buffer.getBuffer(PT2RenderTypes.VOID_CUBE));
        //this.renderCube(tvc,matrix,buffer.getBuffer(RenderType.endPortal()));

        for(int side=0;side<4;side++){
            float xx=0.0F;
            float zz=0.0F;
            switch(side){
                case 0->{
                    xx=0.375F;
                    zz=0.2F;
                }
                case 1->{
                    xx=-0.375F;
                    zz=0.2F;
                }
                case 2->{
                    xx=-0.375F;
                    zz=-0.2F;
                }
                case 3->{
                    xx=0.375F;
                    zz=-0.2F;
                }
            }

            stack.pushPose();
            stack.translate( (float)tvc.getBlockPos().getX()+((side==2||side==3)?1.0F:0.0F) , (float)tvc.getBlockPos().getY()+0.44F , (float)tvc.getBlockPos().getZ()+((side==1||side==2)?1.0F:0.0F) );
            stack.translate(xx,0.0F,zz);
            stack.mulPose(new Quaternion(90.0F*side,0.0F,1.0F,0.0F));
            stack.pushPose();

            try{
                this.renderAsItem(buffer,tvc);
            }catch(Exception e){
                if(Utils.changed(e)){
                    ProjectT2.LOGGER.warn(e);
                    e.printStackTrace();
                }
            }

            stack.popPose();
            stack.popPose();
        }
    }

    private void renderCube(TileVoidInterface te,Matrix4f matrix,VertexConsumer consumer){
        this.renderFace(te,matrix,consumer,0.05F,0.95F,0.25F,0.18F,0.05F,0.05F,0.05F,0.05F);
        this.renderFace(te,matrix,consumer,0.95F,0.95F,0.25F,0.18F,0.05F,0.95F,0.95F,0.05F);
        this.renderFace(te,matrix,consumer,0.05F,0.95F,0.18F,0.25F,0.95F,0.95F,0.95F,0.95F);
        this.renderFace(te,matrix,consumer,0.05F,0.05F,0.18F,0.25F,0.05F,0.95F,0.95F,0.05F);
    }
    @SuppressWarnings("unused")
    private void renderFace(TileVoidInterface te,Matrix4f m,VertexConsumer c,float x0,float x1,float y0,float y1,float z0,float z1,float z2,float z3){
        c.vertex(m,x0,y0,z0).endVertex();
        c.vertex(m,x1,y0,z1).endVertex();
        c.vertex(m,x1,y1,z2).endVertex();
        c.vertex(m,x0,y1,z3).endVertex();
    }

    private void renderAsItem(MultiBufferSource buffer,TileVoidInterface vi){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0,SYMBOLS);

        VertexConsumer c=buffer.getBuffer(RenderType.cutout());

        float texPos=vi.network*0.1F;

        //TODO vertex position
        c.vertex(0.0F,1.0F,0.0F)
                .color(1.0F,1.0F,1.0F,1.0F)
                .uv(0.0F,0.85F)
                .overlayCoords(0)
                .uv2(220)
                .normal(0.0F,0.0F,-1.0F);

        c.vertex(1.0F,1.0F,0.0F)
                .color(1.0F,1.0F,1.0F,1.0F)
                .uv(0.0F,0.85F)
                .overlayCoords(0)
                .uv2(220)
                .normal(0.0F,0.0F,-1.0F);

        c.vertex(1.0F,0.0F,0.0F)
                .color(1.0F,1.0F,1.0F,1.0F)
                .uv(0.0F+0.132F,0.85F)
                .overlayCoords(0)
                .uv2(220)
                .normal(0.0F,0.0F,-1.0F);

        c.vertex(0.0F,0.0F,0.0F)
                .color(1.0F,1.0F,1.0F,1.0F)
                .uv(0.0F+0.132F,0.85F)
                .overlayCoords(0)
                .uv2(220)
                .normal(0.0F,0.0F,-1.0F);
    }
}
