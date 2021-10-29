package raven_cze.projt2.proxies;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import raven_cze.projt2.ProjectT2;
import raven_cze.projt2.common.config.ModConfig;

import javax.annotation.Nullable;
import java.io.File;

@Mod.EventBusSubscriber
public class CommonProxy implements IProxy, IGuiHandler{
    public static Configuration config;
    ProxyGUI proxyGUI = new ProxyGUI();
    public void preInit(FMLPreInitializationEvent event){
        File directory=event.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(),ProjectT2.MOD_ID+".cfg"));
        ModConfig.readConfig();
    }
    public void init(FMLInitializationEvent event){
        NetworkRegistry.INSTANCE.registerGuiHandler(ProjectT2.instance,this);
        ConfigManager.sync(ProjectT2.MOD_ID,Config.Type.INSTANCE);
    }
    public void postInit(FMLPostInitializationEvent event){
        if(config.hasChanged()){config.save();}
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){

    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){

    }

    @Override
    public World getClientWorld() {
        return null;
    }

    public World getWorld(int dim){return null;}

    @Override
    public boolean isShiftKeyDown() {
        return false;
    }

    @Override
    public void registerModel(ItemBlock paramItemBlock){}

    @Override
    public void checkInterModComs(FMLInterModComms.IMCEvent paramIMCEvent){}

    public boolean getSingleplayer(){return false;}

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }
}
