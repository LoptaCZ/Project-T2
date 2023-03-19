package com.raven_cze.projt2.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.raven_cze.projt2.client.model.ModelGenCore;
import com.raven_cze.projt2.common.content.tiles.TileGenerator;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class GeneratorRenderer implements BlockEntityRenderer<TileGenerator>{
    private final ModelGenCore model;
    public static final ModelLayerLocation CORE=new ModelLayerLocation(new ResourceLocation("projt2","core"),"main");
    public GeneratorRenderer(BlockEntityRendererProvider.Context ignoredContext){
        this.model = new ModelGenCore(ignoredContext.bakeLayer(CORE));
    }
    @Override
    public void render(@NotNull TileGenerator generator,float ticks,@NotNull PoseStack stack,@NotNull MultiBufferSource buffer,int light,int overlay){
        float rotation= generator.rotation+ticks;
        stack.pushPose();
        stack.translate(0.5F,0.5F,0.5F);
        float rot2= Mth.abs(rotation*0.2F)/2.0F+0.5F;
        rot2=rot2*rot2+rot2;

        stack.mulPose(Vector3f.XP.rotation(0.0F));
        stack.mulPose(Vector3f.YP.rotation(rotation));
        stack.mulPose(Vector3f.ZP.rotation(rot2*0.01F));

        this.model.renderToBuffer(stack,buffer.getBuffer(RenderType.translucent()),light,overlay,1.0F,1.0F,1.0F,1.0F);
        stack.popPose();
    }
}
