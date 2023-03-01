package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.raven_cze.projt2.common.content.tiles.TileConduit;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import org.jetbrains.annotations.NotNull;

public class ConduitRenderer implements BlockEntityRenderer<TileConduit>{
    public ConduitRenderer(BlockEntityRendererProvider.Context ignoredContext){}
    @Override
    public void render(@NotNull TileConduit be,float pPartialTick,@NotNull PoseStack pose,@NotNull MultiBufferSource bufferS,int light,int overlay){
        
    }
}
