package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.raven_cze.projt2.common.content.tiles.TileVoidChest;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class VoidChestRenderer<T extends TileVoidChest> implements BlockEntityRenderer<TileVoidChest>{
    public static final ResourceLocation field=new ResourceLocation("projt2","textures/particlefield.png");
    //public static final ResourceLocation field32=new ResourceLocation("projt2","textures/particlefield32.png");
    public static final ResourceLocation tunnel=new ResourceLocation("projt2","textures/tunnel.png");

    public VoidChestRenderer(BlockEntityRendererProvider.Context ignoredContext){}

    @Override
    public void render(@NotNull TileVoidChest tvc,float ticks,@NotNull PoseStack stack,@NotNull MultiBufferSource buffer,int overlay,int light){
        Matrix4f matrix=stack.last().pose();
        this.renderCube(tvc,matrix,buffer.getBuffer(this.renderType()));
    }

    private void renderCube(TileVoidChest te,Matrix4f matrix,VertexConsumer consumer){
        //  Zs = NORTH & SOUTH
        //  Xs = EAST & SOUTH
        //  Ys = UP & DOWN
        this.renderFace(te,matrix,consumer,0.0F,1.0F,1.0F,0.0F,0.01F,0.01F,0.01F,0.01F,Direction.NORTH);

        this.renderFace(te,matrix,consumer,0.99F,0.99F,1.0F,0.0F,0F,1.0F,1.0F,0.0F,Direction.EAST);

        this.renderFace(te,matrix,consumer,0.0F,1.0F,0.0F,1.0F,0.99F,0.99F,0.99F,0.99F,Direction.SOUTH);

        this.renderFace(te,matrix,consumer,0.01F,0.01F,0.0F,1.0F,0.0F,1.0F,1.0F,0.0F,Direction.WEST);

        //this.renderFace(te,matrix,consumer,0.0F,1.0F,0.01F,0.01F,0.0F,0.0F,1.0F,1.0F,Direction.DOWN);
        //this.renderFace(te,matrix,consumer,0.0F,0.99F,0.99F,0.99F,0.99F,0.99F,0.0F,0.0F,Direction.UP);
    }
    private void renderFace(TileVoidChest te,Matrix4f m,VertexConsumer c,float x0,float x1,float y0,float y1,float z0,float z1,float z2,float z3,Direction dir){
        //if(te.shouldRenderFace(dir)){
            c.vertex(m,x0,y0,z0).endVertex();
            c.vertex(m,x1,y0,z1).endVertex();
            c.vertex(m,x1,y1,z2).endVertex();
            c.vertex(m,x0,y1,z3).endVertex();
        //}
    }

    protected RenderType renderType(){
        return PT2RenderTypes.VOID_CUBE;
    }
}
