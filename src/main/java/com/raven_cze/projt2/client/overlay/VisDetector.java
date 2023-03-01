package com.raven_cze.projt2.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.api.IConnection;
import com.raven_cze.projt2.common.content.PT2Items;
import com.raven_cze.projt2.common.util.Utils;
import com.raven_cze.projt2.common.content.world.aura.AuraChunk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.GuiUtils;
import net.minecraftforge.client.gui.IIngameOverlay;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
public class VisDetector{
	public static final IIngameOverlay OVERLAY=VisDetector::renderOverlay;
	private static final ResourceLocation HUD=new ResourceLocation(ProjectT2.MODID,"textures/gui/guidetector.png");
	public static int hoverTicks=0;
	public static BlockPos lastHovered=BlockPos.ZERO;
	public static AuraChunk aura=new AuraChunk(new ChunkPos(lastHovered),(short)0,0.0F,0.0F);
	public static AuraChunk last;
	public static void renderOverlay(ForgeIngameGui gui,PoseStack pose,float partialTicks,int w,int h){
		HitResult obj=Minecraft.getInstance().hitResult;
		if(!(obj instanceof BlockHitResult result)){
			lastHovered=null;
			hoverTicks=0;
			return;
		}
		Minecraft mc=Minecraft.getInstance();
		ClientLevel level=mc.level;
		BlockPos pos=result.getBlockPos();
		Player player=mc.player;
		ItemStack headSlot=Objects.requireNonNull(player).getItemBySlot(EquipmentSlot.HEAD);
		BlockEntity be=Objects.requireNonNull(level).getBlockEntity(pos);

		if(lastHovered==null || lastHovered.equals(pos))hoverTicks++;else hoverTicks=0;

		lastHovered=pos;

		if(headSlot.getItem().equals(PT2Items.GOGGLES.get())){
			int visLevel=(int)Mth.clamp(aura.getVis(),0.0F,56.0F);
			int taintLevel=(int)Mth.clamp(aura.getTaint(),0.0F,56.0F);

			RenderSystem.setShaderColor(1F,1F,1F,1F);

			String vis=visLevel+" V";//aura.goodVibes+V
			String taint=taintLevel+" T";//aura.badVibes+T

			GuiComponent.drawString(new PoseStack(),mc.font, FormattedCharSequence.forward(vis,Style.EMPTY.withColor(TextColor.fromRgb(15650030))),(int)(w/1.91),(int)(h/1.1),0);
			GuiComponent.drawString(new PoseStack(),mc.font, FormattedCharSequence.forward(taint,Style.EMPTY.withColor(TextColor.fromRgb(10057625))),(int)(w/1.91),(int)(h/1.15),0);

			gui.setupOverlayRenderState(true,false,HUD);
			//			Vis Gauge
			GuiUtils.drawTexturedModalRect(new PoseStack(), (int)(w/1.05),(int)(h/1.19),24,0,10,56,1);
			//		Vis Level
			GuiUtils.drawTexturedModalRect(new PoseStack(), (int)(w/1.05),(int)(h/1.18),0,0,9,visLevel,0);
			//			Taint Gauge
			GuiUtils.drawTexturedModalRect(new PoseStack(), (int)(w/1.025),(int)(h/1.19),40,0,10,56,0);

			//			Aura Charges
			//		Vis Charge
			GuiUtils.drawTexturedModalRect(new PoseStack(), (int)(w/1.05),(int)(h/1.05),24,56,10,10,0);
			//		Taint Charge
			GuiUtils.drawTexturedModalRect(new PoseStack(), (int)(w/1.025),(int)(h/1.05),40,56,10,10,0);

			if(be instanceof IConnection te){
				int capPure=Math.round(Math.round(te.getPureVis())/te.getMaxVis()*100.0F);
				int capTaint=Math.round(Math.round(te.getTaintedVis())/te.getMaxVis()*100.0F);
				vis=Math.round(te.getPureVis())+" V ("+capPure+"%)";
				taint=Math.round(te.getTaintedVis())+" T ("+capTaint+"%)";

				GuiComponent.drawString(new PoseStack(),mc.font, FormattedCharSequence.forward(vis,Style.EMPTY.withColor(TextColor.fromRgb(15650030))),(int)(w/1.9),(int)(h/1.75),0);
				GuiComponent.drawString(new PoseStack(),mc.font, FormattedCharSequence.forward(taint,Style.EMPTY.withColor(TextColor.fromRgb(10057625))),(int)(w/1.9),(int)(h/1.7),0);

				if(te.getSuction(null)>0.01){
					String pressure=te.getVisSuction(null)+" Vis TCB, "+te.getTaintSuction(null)+" Taint TCB";
					GuiComponent.drawString(new PoseStack(),mc.font, FormattedCharSequence.forward(pressure,Style.EMPTY.withColor(TextColor.fromRgb(8947848))),(int)(w/1.9),(int)(h/1.65),0);
				}
			}
		}else if(player.getMainHandItem().getItem().equals(PT2Items.VIS_DETECTOR.get()) || player.getOffhandItem().getItem().equals(PT2Items.VIS_DETECTOR.get())){
			//			VIS DETECTOR
			int visLevel=(int)Utils.Math.clamp(aura.getVis(),0.0F,56.0F);
			//			Vis Gauge
			GuiUtils.drawTexturedModalRect(new PoseStack(), (int)(w/1.05),(int)(h/1.19),24,0,10,56,1);
			//		Vis Level
			GuiUtils.drawTexturedModalRect(new PoseStack(), (int)(w/1.05),(int)(h/1.18),0,0,9,visLevel,0);
			//			Aura Charges
			//		Vis Charge
			GuiUtils.drawTexturedModalRect(new PoseStack(), (int)(w/1.05),(int)(h/1.05),24,56,10,10,0);
		}else if(player.getMainHandItem().getItem().equals(PT2Items.TAINT_DETECTOR.get()) || player.getOffhandItem().getItem().equals(PT2Items.TAINT_DETECTOR.get())){
			//TAINT DETECTOR
			int taintLevel=(int)Utils.Math.clamp(aura.getTaint(),0.0F,56.0F);
		}else if(player.getMainHandItem().getItem().equals(PT2Items.THAUMOMETER.get()) || player.getOffhandItem().getItem().equals(PT2Items.THAUMOMETER.get())){
			//THAUMOMETER
			if(last.getTaint()<aura.getTaint())
				GuiUtils.drawTexturedModalRect(new PoseStack(),(int)(w/1.01),(int)(h/1.01),72,0,8,8,0);

		}
		last=aura;
	}
}
