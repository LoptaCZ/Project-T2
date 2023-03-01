package com.raven_cze.projt2.client;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.client.model.ModelCrystal;
import com.raven_cze.projt2.client.overlay.VisDetector;
import com.raven_cze.projt2.client.renderer.*;
import com.raven_cze.projt2.common.config.ClientCFG;
import com.raven_cze.projt2.common.content.PT2Blocks;
import com.raven_cze.projt2.common.content.PT2Particles;
import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.particles.FXWisp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.FoliageColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value={Dist.CLIENT},modid=ProjectT2.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class ClientHandler{
	//	DO NOT TOUCH OR I WILL TOUCH YOU
	private static ShaderInstance rendertypeVoidCubeShader;
	//	DO NOT TOUCH OR I WILL TOUCH YOU
	@SubscribeEvent
	public static void clientInit(FMLClientSetupEvent event){
		registerOverlays();
	}
	
	@SubscribeEvent
	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
		//event.registerLayerDefinition(null, null);//ExampleModel.LOCATION,ExampleModel:createBodyLayer

		registerTELayers(event::registerLayerDefinition);
	}

	//@SubscribeEvent
	public static void registerTELayers(BiConsumer<ModelLayerLocation,Supplier<LayerDefinition>>consumer){
		consumer.accept( VoidKeyholeRenderer.CRYSTAL,()->LayerDefinition.create(ModelCrystal.createMesh(),64,32) );
		consumer.accept( CrystalOreRenderer.CRYSTAL,()->LayerDefinition.create(ModelCrystal.createMesh(),64,32) );
	}
	
	@SubscribeEvent
	public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event){
		event.registerBlockEntityRenderer(PT2Tiles.TILE_CRYSTAL.get(),CrystalOreRenderer::new);
		event.registerBlockEntityRenderer(PT2Tiles.TILE_HOLE.get(),HoleRenderer::new);
		event.registerBlockEntityRenderer(PT2Tiles.TILE_NITOR.get(),NitorRenderer::new);
		event.registerBlockEntityRenderer(PT2Tiles.TILE_VOID_CUBE.get(),VoidCubeRenderer::new);
		event.registerBlockEntityRenderer(PT2Tiles.TILE_VOID_KEYHOLE.get(),VoidKeyholeRenderer::new);
		event.registerBlockEntityRenderer(PT2Tiles.TILE_VOID_LOCK.get(),VoidLockRenderer::new);
		event.registerBlockEntityRenderer(PT2Tiles.TILE_VOID_CHEST.get(),VoidChestRenderer::new);
		event.registerBlockEntityRenderer(PT2Tiles.TILE_VOID_INTERFACE.get(),VoidInterfaceRenderer::new);
		event.registerBlockEntityRenderer(PT2Tiles.TILE_CONDUIT.get(),ConduitRenderer::new);
		event.registerBlockEntityRenderer(PT2Tiles.TILE_SEAL.get(),SealRenderer::new);
	}

	@SubscribeEvent
	public void registerBlockColors(ColorHandlerEvent.Block event){
		BlockColor leavesColor=(state,world,pos,tint)->(world!=null && pos!=null)? BiomeColors.getAverageFoliageColor(world,pos): FoliageColor.getDefaultColor();
		event.getBlockColors().register(leavesColor, PT2Blocks.GREATWOOD_LEAVES.get(),PT2Blocks.SILVERWOOD_LEAVES.get(),PT2Blocks.TAINT_LEAVES.get());
	}

	private static void registerOverlays(){
		OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HOTBAR_ELEMENT,"PT2 Vis Detector",VisDetector.OVERLAY);
	}

	@SubscribeEvent
	public static void registerParticleFactories(final ParticleFactoryRegisterEvent event){
		ProjectT2.LOGGER.debug("Registering FX Wisp Particle Factory");
		Minecraft.getInstance().particleEngine.register(PT2Particles.FX_WISP.get(),FXWisp.Provider::new);
	}

	@SubscribeEvent
	public static void registerShaders(RegisterShadersEvent event)throws IOException{
		event.registerShader(
				new ShaderInstance(
						event.getResourceManager(),
						new ResourceLocation("projt2","rendertype_void_cube"),
						DefaultVertexFormat.POSITION
				),shader->rendertypeVoidCubeShader=shader
		);

	}

	public static ShaderInstance getRenderTypeVoidCubeShader(){return ClientHandler.rendertypeVoidCubeShader;}

	public static boolean isVisibleTo(float fov, LivingEntity entity, BlockPos pos){
		double dist=entity.distanceToSqr(pos.getX(),pos.getY(),pos.getZ());
		if(dist<2.0D)return true;
		Minecraft mc=Minecraft.getInstance();
		double vT=(fov+mc.options.fov/2.0F);
		int j=Mth.clamp(64<<3-mc.options.renderDistance,-400,400);
		double rD=ClientCFG.lowFX.get()?(double)(j/2):j;
		float f1=Mth.cos(-entity.yHeadRot*0.01745329F-3.141593F);
		float f3=Mth.sin(-entity.yHeadRot*0.01745329F-3.141593F);
		float f5=-Mth.cos(-entity.yBodyRot*0.01745329F-3.141593F);
		float f7=Mth.sin(-entity.yBodyRot*0.01745329F-3.141593F);
		double lx=f3*f5;
		double lz=f1*f5;
		double dx=pos.getX()*0.5D-entity.xo;
		double dy=pos.getY()*0.5D-entity.yo;
		double dz=pos.getZ()*0.5D-entity.zo;
		double len=Math.sqrt(dx*dx+dy*dy+dz*dz);
		double dot=dx/len*lx+dy/len* (double) f7 +dz/len*lz;
		double angle=Math.acos(dot);
		return (( angle<vT && mc.options.renderDistance==0 && dist<rD ) || (mc.options.renderDistance>0 && dist<rD));
	}
}
