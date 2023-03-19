package com.raven_cze.projt2.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.raven_cze.projt2.common.content.world.inventory.GeneratorMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class GeneratorScreen extends AbstractContainerScreen<GeneratorMenu>{
    private static final ResourceLocation BASE=new ResourceLocation("projt2","textures/gui/container/guigenerator.png");
    protected static GeneratorMenu MENU;
    public GeneratorScreen(GeneratorMenu menu,Inventory inv,Component title){
        super(menu,inv,title);
        this.imageHeight=176;
        this.imageWidth=82;

        MENU=menu;
    }
    @Override
    public void render(@NotNull PoseStack poseStack, int mouseX, int mouseY, float tick){
        this.renderBackground(poseStack);
        super.render(poseStack,mouseX,mouseY,tick);
        this.renderTooltip(poseStack,mouseX,mouseY);

        if(MENU!=null){
            Map<Integer, Slot> items=MENU.get();/*
            for(Slot s:items.values()){

            }*/

        }
    }

    @Override
    public @NotNull Component getTitle(){return new TextComponent("block.projt2.generator");}

    @Override
    protected void renderBg(@NotNull PoseStack poseStack,float tick,int mouseX,int mouseY){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        RenderSystem.setShaderTexture(0,BASE);
        int x=(width-imageWidth)/2;
        int y=(height-imageHeight)/2;
        this.blit(poseStack,x,y,0,0,imageWidth,imageHeight);
        RenderSystem.disableBlend();
    }
    @Override
    public boolean keyPressed(int key,int code,int modifiers){
        if(key==256){
            if(this.minecraft!=null)
                if(this.minecraft.player!=null)
                    this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key,code,modifiers);
    }
    @Override
    protected void containerTick(){super.containerTick();}
    @Override
    public void onClose(){
        super.onClose();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }
    @Override
    protected void init() {
        super.init();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(true);
    }
}
