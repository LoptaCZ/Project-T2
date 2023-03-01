package com.raven_cze.projt2.api;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
import com.raven_cze.projt2.api.internal.DummyInternalMethodHandler;
import com.raven_cze.projt2.api.internal.IInternalMethodHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class PT2Api {
	public static IInternalMethodHandler internalMethods=(IInternalMethodHandler)new DummyInternalMethodHandler();


	public static void renderLikeItem(ResourceLocation tex){
		int texID=(Minecraft.getInstance()).textureManager.getTexture(tex).getId();
		GL11.glBindTexture(3553, texID );

		int size=16;
		float size_minus0_01=size-0.01F;
		float texNudge=1.0F/size*size*2.0F;
		float reciprocal=1.0F/size;


		Tesselator tesselator=Tesselator.getInstance();
		BufferBuilder tess=tesselator.getBuilder();

		GL11.glEnable(32826);
		GL11.glScalef(8,8,8);
		GL11.glEnable(3042);
		GL11.glBlendFunc(770,1);
		GL11.glColor3f(1.0F,1.0F,1.0F);
		// Some rendering

		//	Yet again some TESSELATION
		//	That nobody knows how to


		//	Shutting down GL11
		GL11.glDisable(3042);
		GL11.glDisable(32826);
		GL11.glPopMatrix();
		GL11.glPopMatrix();
	}
}
