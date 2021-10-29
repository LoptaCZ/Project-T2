package raven_cze.projt2.proxies;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import raven_cze.projt2.ProjectT2;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event){
        super.preInit(event);
        OBJLoader.INSTANCE.addDomain(ProjectT2.MOD_ID.toLowerCase());
    }
    @Override
    public void init(FMLInitializationEvent event){
        super.init(event);
    }
    @Override
    public void postInit(FMLPostInitializationEvent event){
        super.postInit(event);
    }
    public World getClientWorld(){return (FMLClientHandler.instance().getClient()).world;}
    public World getWorld(int dim){return getClientWorld();}

    public boolean getSingleplayer(){return Minecraft.getMinecraft().isSingleplayer();}

    public void registerModel(ItemBlock itemBlock){
        ModelLoader.setCustomModelResourceLocation(itemBlock,0,new ModelResourceLocation(itemBlock.getRegistryName(),"inventory"));
    }
}
