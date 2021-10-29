package raven_cze.projt2.proxies;

import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy extends CommonProxy{
    public void preInit(FMLPreInitializationEvent event){super.preInit(event);}
    public World getWorld(int dim){return DimensionManager.getWorld(dim);}
}
