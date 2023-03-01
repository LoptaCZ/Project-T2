package com.raven_cze.projt2.client.tooltip;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.raven_cze.projt2.client.TheoryTextureManager;
import com.raven_cze.projt2.common.content.items.ItemTheory;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class TheoryTooltipComponent implements ClientTooltipComponent {
    private static final int SIZE=80;
    private final ResourceLocation texture;

    public TheoryTooltipComponent(ItemTheory.TheoryTooltip tooltip){
        this.texture=TheoryTextureManager.INSTANCE.getTheoryInstance(tooltip.packed()).getTextureLocation();
    }

    @Override
    public int getHeight(){return SIZE+8;}
    @Override
    public int getWidth(@NotNull Font pFont){return SIZE;}

    @Override
    public void renderImage(@NotNull Font font,int mX,int mY,@NotNull PoseStack stack,@NotNull ItemRenderer renderer,int offset){
        //ClientTooltipComponent.super.renderImage(pFont, pMouseX, pMouseY, pPoseStack, pItemRenderer, pBlitOffset);
        stack.pushPose();
            RenderSystem.setShaderColor(1,1,1,1);
            RenderSystem.setShaderTexture(0,texture);
            GuiComponent.blit(stack,mX,mY,offset,0,0,SIZE,SIZE,SIZE,SIZE);
        stack.popPose();
    }
}
