package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.raven_cze.projt2.common.PT2Config;
import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.blocks.BlockVoidCube;
import com.raven_cze.projt2.common.content.tiles.TileVoidCube;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.EmptyModelData;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused"})
public class VoidCubeRenderer implements BlockEntityRenderer<TileVoidCube>{//TODO This is levitating shit in middle of Monument
    public static final ResourceLocation field=new ResourceLocation("projt2","textures/particlefield.png");
    public static final ResourceLocation tunnel=new ResourceLocation("projt2","textures/tunnel.png");
    public VoidCubeRenderer(BlockEntityRendererProvider.Context ignoredContext){
    }

    @Override
    public void render(@NotNull TileVoidCube tvc,float ticks,@NotNull PoseStack stack,@NotNull MultiBufferSource buffer,int overlay,int light) {
        boolean logged=false;
        float speed = 0.125F;
        float count = 0+ticks;
        if(Minecraft.getInstance().level!=null)
            count=(Minecraft.getInstance()).level.getGameTime() + ticks;
        float bob;
        bob = (float) Math.sin(count / 10.0F) * speed;
        BlockRenderDispatcher BRD=Minecraft.getInstance().getBlockRenderer();

        stack.pushPose();
        stack.translate(0.0F,bob,0.0F);
        if(tvc.getLevel().getBlockState(tvc.getBlockPos()).getBlock()instanceof BlockVoidCube block){
            BlockState state=tvc.getBlockState();
            int type;
            if(tvc.getLevel().getBlockState(tvc.getBlockPos().below()).getBlock()instanceof BlockVoidCube){
                type=0;
                if(!tvc.getLevel().getBlockState(tvc.getBlockPos().above()).getBlock().equals(PT2Blocks.VOID_CUBE.get()) )type=1;
            }
            else type=2;
            BRD.renderSingleBlock(state.setValue(BlockVoidCube.CUBE_TYPE,type),stack,buffer,15728880,OverlayTexture.NO_OVERLAY,EmptyModelData.INSTANCE);
        }

        stack.scale(1.0F,1.0F,1.0F);

        Matrix4f matrix = stack.last().pose();
        //  This does render that interdimensional "galaxy"
        this.renderCube(tvc, matrix, buffer.getBuffer(PT2RenderTypes.VOID_CUBE));
        if(PT2Config.CLIENT.lowFX.get())this.renderCube(tvc, matrix, buffer.getBuffer(PT2RenderTypes.VOID_CUBE_32));// Lower res
        stack.popPose();
        stack.scale(1.0F,1.0F,1.0F);
    }

    private void renderCube(TileVoidCube te,Matrix4f matrix,VertexConsumer consumer){
        //  Zs = NORTH & SOUTH
        //  Xs = EAST & SOUTH
        //  Ys = UP & DOWN
        this.renderFace(te,matrix,consumer,0.0F,1.0F,1.0F,0.0F,0.01F,0.01F,0.01F,0.01F,Direction.NORTH);

        this.renderFace(te,matrix,consumer,0.99F,0.99F,1.0F,0.0F,0F,1.0F,1.0F,0.0F,Direction.EAST);

        this.renderFace(te,matrix,consumer,0.0F,1.0F,0.0F,1.0F,0.99F,0.99F,0.99F,0.99F,Direction.SOUTH);

        this.renderFace(te,matrix,consumer,0.01F,0.01F,0.0F,1.0F,0.0F,1.0F,1.0F,0.0F,Direction.WEST);

        this.renderFace(te,matrix,consumer,0.0F,1.0F,0.01F,0.01F,0.0F,0.0F,1.0F,1.0F,Direction.DOWN);
        this.renderFace(te,matrix,consumer,0.0F,0.99F,0.99F,0.99F,0.99F,0.99F,0.0F,0.0F,Direction.UP);
    }
    private void renderFace(TileVoidCube te,Matrix4f m,VertexConsumer c,float x0,float x1,float y0,float y1,float z0,float z1,float z2,float z3,Direction dir){
        if(te.shouldRenderFace(dir)){
            c.vertex(m,x0,y0,z0).endVertex();
            c.vertex(m,x1,y0,z1).endVertex();
            c.vertex(m,x1,y1,z2).endVertex();
            c.vertex(m,x0,y1,z3).endVertex();
        }
    }
}
