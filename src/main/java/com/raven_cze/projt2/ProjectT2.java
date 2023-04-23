package com.raven_cze.projt2;

import com.raven_cze.projt2.client.ClientHandler;
import com.raven_cze.projt2.client.TickHandler;
import com.raven_cze.projt2.client.gui.screen.DiscoveryTomeScreen;
import com.raven_cze.projt2.client.gui.screen.GeneratorScreen;
import com.raven_cze.projt2.client.gui.screen.VoidChestScreen;
import com.raven_cze.projt2.common.PT2Config;
import com.raven_cze.projt2.common.PT2SaveData;
import com.raven_cze.projt2.common.content.*;
import com.raven_cze.projt2.common.content.world.feature.PT2FeatureConfig;
import com.raven_cze.projt2.common.content.world.feature.PT2TreePlacements;
import com.raven_cze.projt2.common.content.world.feature.PT2Vegetation;
import com.raven_cze.projt2.common.content.world.feature.PT2VegetationPlacements;
import com.raven_cze.projt2.common.network.Network;
import com.raven_cze.projt2.common.network.commands.CommandHandler;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Random;

@Mod("projt2")
@Mod.EventBusSubscriber(modid=ProjectT2.MODID)
public class ProjectT2{
	public static HashMap<?,?>SpecialTileHM;
	public static int dawnInc=0;
	public static int dawnStep=0;
	public static long dawnDest;

	public static final Logger LOGGER=LogManager.getLogger("projt2");
	public static final String MODID="projt2";
	
	static ProjectT2 INSTANCE;
	
	public ProjectT2(){
		if(INSTANCE!=null)throw new IllegalStateException();
		INSTANCE=this;
		
		IEventBus eventBus=FMLJavaModLoadingContext.get().getModEventBus();

		if(PT2Blocks.REGISTRY.getEntries().size()>=1)PT2Blocks.register(eventBus);
		if(PT2Tiles.REGISTRY.getEntries().size()>=1)PT2Tiles.register(eventBus);
		if(PT2Items.REGISTRY.getEntries().size()>=1)PT2Items.register(eventBus);
		if(PT2Enchants.REGISTRY.getEntries().size()>=1)PT2Enchants.register(eventBus);
		if(PT2Sounds.REGISTRY.getEntries().size()>=1)PT2Sounds.register(eventBus);
		if(PT2Entities.REGISTRY.getEntries().size()>=1)PT2Entities.register(eventBus);
		if(PT2Particles.REGISTRY.getEntries().size()>=1)PT2Particles.register(eventBus);
		if(PT2Menus.REGISTRY.getEntries().size()>=1)PT2Menus.register(eventBus);
		if(PT2Features.REGISTRY.getEntries().size()>=1)PT2Features.register(eventBus);

		eventBus.addListener(this::setup);
		eventBus.addListener(this::setupClient);

		MinecraftForge.EVENT_BUS.addListener(this::setup);
		MinecraftForge.EVENT_BUS.addListener(this::setupClient);
		MinecraftForge.EVENT_BUS.register(new ClientHandler());

		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT,PT2Config.client);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,PT2Config.shared);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,PT2Config.server);

		eventBus.register(PT2Config.class);
	}
	private void setupClient(final FMLClientSetupEvent event){
		event.enqueueWork(()->{
			MenuScreens.register(PT2Menus.MENU_DISCOVERY_TOME.get(),DiscoveryTomeScreen::new);
			MenuScreens.register(PT2Menus.MENU_VOID_CHEST.get(),VoidChestScreen::new);
			MenuScreens.register(PT2Menus.MENU_GENERATOR.get(),GeneratorScreen::new);
		});
		IEventBus bus=MinecraftForge.EVENT_BUS;
		bus.addListener((TickEvent.ClientTickEvent e)->{
			if(e.phase == TickEvent.Phase.END)
				TickHandler.clientTickEnd(Minecraft.getInstance());
		});
		bus.addListener( (TickEvent.RenderTickEvent e)->{
			if(e.phase==TickEvent.Phase.START)TickHandler.renderTick(e.renderTickTime);
		} );
		bus.addListener((e)->PT2Blocks.prepareSpecialRender());
	}
	private void setup(final FMLCommonSetupEvent event){
		if(PT2Config.SHARED.debugMode.get())LOGGER.info("Pre-Init Phase");

		IEventBus bus=MinecraftForge.EVENT_BUS;
		bus.addListener(this::registerCommands);
		bus.addListener(this::serverStarted);

		PT2FeatureConfig.initialize();
		PT2TreePlacements.initialize();
		PT2Vegetation.initialize();
		PT2VegetationPlacements.initialize();

		Network.register();
	}

	public void serverStarted(ServerStartedEvent event){
		ServerLevel world=event.getServer().getLevel(Level.OVERWORLD);
		if(world!=null)
			if(!world.isClientSide){
				PT2SaveData data=world.getDataStorage().computeIfAbsent(PT2SaveData::new,PT2SaveData::new,PT2SaveData.dataName);
				PT2SaveData.setInstance(data);
			}
		//	if(world!=null)
	}
	public void registerCommands(RegisterCommandsEvent event){
		CommandHandler.registerServer(event.getDispatcher());
	}

	@OnlyIn(Dist.CLIENT)
	public static SpriteSet getSpriteSet(ResourceLocation name,boolean isAnimated){
		SpriteSet sprites=null;
		try{
			sprites=new SpriteSet(){
				@Override
				public @NotNull TextureAtlasSprite get(int age,int life){
					int index;
					index=Mth.clamp(Math.round((float)age/life),0,32);
					ResourceLocation resLoc;
					if(isAnimated)
						resLoc=new ResourceLocation(name.getNamespace(),name.getPath().replaceAll("[0-9]",String.valueOf(index)));
					else
						resLoc=name;
					System.out.print(Minecraft.getInstance().getTextureAtlas(resLoc));
					return Minecraft.getInstance().getTextureAtlas(resLoc).apply(resLoc).atlas().getSprite(resLoc);
				}

				@Override
				public @NotNull TextureAtlasSprite get(@NotNull Random r){
					TextureAtlasSprite atlas;
					//if(isAnimated){resLoc=new ResourceLocation(name.getNamespace(),name.getPath().replaceAll("[0-9]",String.valueOf(r.nextInt(0,32))));}
					atlas=Minecraft.getInstance().getTextureAtlas(name).apply(name);
					return atlas.atlas().getSprite(name);
				}
			};
		}catch(Exception e){
			Exception ex;
			ex=e;
			if(Utils.changed(ex)){e.printStackTrace();}
		}
		return sprites;
	}

	@SuppressWarnings("unused")
	public static class MARKERS{
		public static final Marker CORE=MarkerManager.getMarker("Core");
		public static final Marker REGISTRY=MarkerManager.getMarker("Registry");
		public static final Marker RESEARCH=MarkerManager.getMarker("Research");
		public static final Marker RENDER=MarkerManager.getMarker("Render");
		public static final Marker INFO=MarkerManager.getMarker("Info");
		public static final Marker DEBUG=MarkerManager.getMarker("Debug");
		public static final Marker WARN=MarkerManager.getMarker("Warn");
		public static final Marker ERROR=MarkerManager.getMarker("Error");
		public static final Marker FATAL=MarkerManager.getMarker("Fatal");
	}
}
