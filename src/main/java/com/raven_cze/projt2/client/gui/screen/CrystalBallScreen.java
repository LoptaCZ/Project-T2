package com.raven_cze.projt2.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.raven_cze.projt2.client.gui.container.CrystalBallMenu;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class CrystalBallScreen extends AbstractContainerScreen<CrystalBallMenu>{
    private static final ResourceLocation texture=new ResourceLocation("projt2","textures/gui/guicrystalball.png");
    public CrystalBallScreen(CrystalBallMenu menu,Inventory plyInv,Component title){
        super(menu,plyInv,title);
        this.titleLabelX=171;
        this.titleLabelY=134;
    }

    @Override
    protected void renderBg(@NotNull PoseStack pose,float ticks,int mouseX,int mouseY){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0,texture);
        blit(pose,leftPos,topPos,0,0,imageWidth,imageHeight);
    }

    @Override
    protected void renderTooltip(@NotNull PoseStack pose,int pX,int pY){
        super.renderTooltip(pose,pX,pY);
    }
}
