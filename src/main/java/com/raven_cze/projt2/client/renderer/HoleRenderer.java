package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.raven_cze.projt2.common.content.tiles.TileHole;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class HoleRenderer implements BlockEntityRenderer<TileHole>{
    public HoleRenderer(BlockEntityRendererProvider.Context ignoredContext){}

    @Override
    public void render(@NotNull TileHole hole,float tick,@NotNull PoseStack stack,@NotNull MultiBufferSource buffer,int overlay,int light){
        Matrix4f matrix=stack.last().pose();

        //CHECK FOR 1x2x1 block to use right render
        //---------------   Side
        //  #   #   #   |
        //  #   H   #   |   # = Any Block
        //  #   H   #   |   H = Hole
        //  #   #   #   |
        //---------------   Top / Bottom
        //  #   #   #   |
        //  #   H   #   |
        //  #   #   #   |
        //---------------
        Level lvl=hole.getLevel();

        stack.pushPose();
        stack.scale(1.0F,1.0F,1.0F);
        if(lvl!=null){
            VertexConsumer VC=buffer.getBuffer(PT2RenderTypes.VOID_CUBE);
            if( canRender(lvl,hole,Direction.UP) ){// 0 1 0 0 0 0 1 1
                VC.vertex(matrix,0.0F,0.99F,0.0F).endVertex();//     1 3 5
                VC.vertex(matrix,1.0F,0.99F,0.0F).endVertex();//     2 3 6
                VC.vertex(matrix,1.0F,0.99F,1.0F).endVertex();//     2 4 7
                VC.vertex(matrix,0.0F,0.99F,1.0F).endVertex();//     1 4 8
            }
            if( canRender(lvl,hole,Direction.DOWN) ){
                VC.vertex(matrix,0.0F,0.01F,1.0F).endVertex();
                VC.vertex(matrix,1.0F,0.01F,1.0F).endVertex();
                VC.vertex(matrix,1.0F,0.01F,0.0F).endVertex();
                VC.vertex(matrix,0.0F,0.01F,0.0F).endVertex();
            }
            if( canRender(lvl,hole,Direction.NORTH) ){
                VC.vertex(matrix,0.0F,1.0F,1.0F).endVertex();
                VC.vertex(matrix,1.0F,1.0F,1.0F).endVertex();
                VC.vertex(matrix,1.0F,0.0F,0.99F).endVertex();
                VC.vertex(matrix,0.0F,0.0F,0.99F).endVertex();
            }
            if( canRender(lvl,hole,Direction.EAST) ){// 0.99 0.99 1.0 0.0 0.0 1.0 1.0 0
                VC.vertex(matrix,0.01F,1.0F,0.0F).endVertex();
                VC.vertex(matrix,0.01F,1.0F,1.0F).endVertex();
                VC.vertex(matrix,0.01F,0.0F,1.0F).endVertex();
                VC.vertex(matrix,0.01F,0.0F,0.0F).endVertex();
            }
            if( canRender(lvl,hole,Direction.SOUTH) ){// 0 1 0 1 099 099 099 099
                VC.vertex(matrix,0.0F,0.0F,0.01F).endVertex();
                VC.vertex(matrix,1.0F,0.0F,0.01F).endVertex();
                VC.vertex(matrix,1.0F,1.0F,0.01F).endVertex();
                VC.vertex(matrix,0.0F,1.0F,0.01F).endVertex();
            }
            if( canRender(lvl,hole,Direction.WEST) ){// 0.1 0.1 0 1 0 1 1 0
                VC.vertex(matrix,0.99F,0.0F,0.0F).endVertex();
                VC.vertex(matrix,0.99F,0.0F,1.0F).endVertex();
                VC.vertex(matrix,0.99F,1.0F,1.0F).endVertex();
                VC.vertex(matrix,0.99F,1.0F,0.0F).endVertex();
            }
        }
        stack.popPose();
        stack.scale(1.0F,1.0F,1.0F);
    }

    private boolean canRender(Level lvl,TileHole tHole,Direction dir){
        boolean bool=false;
        BlockState air=Blocks.AIR.defaultBlockState();
        BlockState cave_air=Blocks.CAVE_AIR.defaultBlockState();
        BlockState void_air=Blocks.VOID_AIR.defaultBlockState();
        BlockPos pos=tHole.getBlockPos();

        Direction dur=dir.getOpposite();

        if(dir.equals(Direction.UP) || dir.equals(Direction.DOWN))dur=dir;


        if(lvl.isClientSide()){
            if(!tHole.isRemoved()){
                bool=!(lvl.getBlockState(pos.relative(dur)).equals(air) || lvl.getBlockState(pos.relative(dur)).equals(cave_air) || lvl.getBlockState(pos.relative(dur)).equals(void_air));
                //System.out.println("[ "+dir+" ] "+"> "+pos.relative(dir)+" | "+bool);
            }
        }
        return bool;
    }
}
