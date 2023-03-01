package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.raven_cze.projt2.common.content.PT2Items;
import com.raven_cze.projt2.common.content.blocks.BlockVoidLock;
import com.raven_cze.projt2.common.content.tiles.TileVoidLock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class VoidLockRenderer implements BlockEntityRenderer<TileVoidLock>{
    //ResourceLocation texture=new ResourceLocation("projt2","textures/tunnel.png");
    public VoidLockRenderer(BlockEntityRendererProvider.Context ignoredContext){}
    @Override
    public void render(@NotNull TileVoidLock tvl,float tick,@NotNull PoseStack stack,@NotNull MultiBufferSource buff,int light,int overlay) {
        Matrix4f matrix=stack.last().pose();
        //this.renderCube(tvl,matrix,buff.getBuffer(this.renderType()));
        this.renderFace(tvl,matrix,buff.getBuffer(this.renderType()));

        String[]crystals=new String[]{"vis","air","water","earth","fire","taint"};

        BlockState stater=tvl.getBlockState();

        if(Minecraft.getInstance().player!=null)
            if(Minecraft.getInstance().player.getInventory().armor.get(0).getItem().equals(PT2Items.GOGGLES.get())){
                int north=Arrays.stream(crystals).toList().indexOf(tvl.runes[0]);
                int east=Arrays.stream(crystals).toList().indexOf(tvl.runes[1]);
                int south=Arrays.stream(crystals).toList().indexOf(tvl.runes[2]);
                int west=Arrays.stream(crystals).toList().indexOf(tvl.runes[3]);

                stater.setValue(BlockVoidLock.NORTH_RUNE,north);
                stater.setValue(BlockVoidLock.EAST_RUNE,east);
                stater.setValue(BlockVoidLock.WEST_RUNE,south);
                stater.setValue(BlockVoidLock.SOUTH_RUNE,west);
        }else{
                stater.setValue(BlockVoidLock.NORTH_RUNE,0);
                stater.setValue(BlockVoidLock.EAST_RUNE,0);
                stater.setValue(BlockVoidLock.SOUTH_RUNE,0);
                stater.setValue(BlockVoidLock.WEST_RUNE,0);
            }
    }

    private void renderFace(TileVoidLock te,Matrix4f m,VertexConsumer c){
        if(te.shouldRenderFace(Direction.UP)){
            c.vertex(m, (float) 0.0, (float) 0.99, (float) 0.99).endVertex();
            c.vertex(m, (float) 0.99, (float) 0.99, (float) 0.99).endVertex();
            c.vertex(m, (float) 0.99, (float) 0.99, (float) 0.0).endVertex();
            c.vertex(m, (float) 0.0, (float) 0.99, (float) 0.0).endVertex();
        }
    }
    protected RenderType renderType(){return PT2RenderTypes.VOID_CUBE;}
}
