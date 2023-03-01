package com.raven_cze.projt2.common.content.items;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;

import com.raven_cze.projt2.api.TransformingVertexBuilder;
import com.raven_cze.projt2.client.renderer.PT2RenderTypes;
import com.raven_cze.projt2.common.content.PT2Items;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class PT2Item extends Item {

	//private static boolean noJeiLogged=false;
	private boolean glint;
	
	public PT2Item(Properties properties){
		super(properties.tab(PT2Items.TABULATOR));
		this.glint=false;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean hasEffect(@Nonnull ItemStack stack){return(this.glint||super.isEnchantable(stack));}
	
	public PT2Item setEffect(boolean glint){
		this.glint=glint;
		return this;
	}
	
	@Override
	public boolean isFoil(@NotNull ItemStack item){
		return this.glint;
	}
	
	@Override
	public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity){
		//ProjectT2.LOGGER.info(""+PT2Core.getDimension(p_41410_));
		
		return super.finishUsingItem(stack,level,entity);
	}

	public boolean isInSlot(EquipmentSlot slot){
		if( new ItemStack(this).getEquipmentSlot()==slot )return true;
		return false;
	}

	protected static void drawExternalGUI(@NonNull ItemStack item,PoseStack transform,ResourceLocation texture){
		MultiBufferSource.BufferSource buffer=MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
		VertexConsumer builder=buffer.getBuffer(PT2RenderTypes.getGUI(texture));
		drawRect(builder,transform,0.0F,1.0F,74.0F,74.0F,0.0F,0.2890625F,0.19921875F,0.48828125F);
		buffer.endBatch();
		ItemRenderer ir=Minecraft.getInstance().getItemRenderer();
		PoseStack modelView=RenderSystem.getModelViewStack();
		modelView.pushPose();
		if(modelView!=transform)modelView.mulPoseMatrix(transform.last().pose());
		modelView.translate(0,0,10);
		RenderSystem.applyModelViewMatrix();
		int x=29;int y=3;
		ir.renderAndDecorateItem(item,x,y);
		RenderSystem.applyModelViewMatrix();
	}

	private static void drawRect(VertexConsumer builder,PoseStack transform,float x,float y,float w,float h,float u0,float u1,float v0,float v1){
		TransformingVertexBuilder innerBuilder=new TransformingVertexBuilder(builder,transform,DefaultVertexFormat.BLIT_SCREEN);
		innerBuilder.color(1,1,1,1);
		innerBuilder.setLight(LightTexture.pack(15,15));
		innerBuilder.setOverlay(OverlayTexture.NO_OVERLAY);
		innerBuilder.setNormal(1.0F,1.0F,1.0F);
		innerBuilder.vertex(x,(y+h),0.0D).uv(u0,v1).endVertex();
		innerBuilder.vertex((x+w),(y+h),0.0D).uv(u1,v1).endVertex();
		innerBuilder.vertex((x+w),y,0.0D).uv(u1,v0).endVertex();
		innerBuilder.vertex(x,y,0.0D).uv(u0,v0).endVertex();
		innerBuilder.unsetDefaultColor();
	}
}
