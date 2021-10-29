package raven_cze.projt2;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
//import raven_cze.projt2.common.config.ModConfig;
import raven_cze.projt2.proxies.IProxy;

@Mod(
        modid = ProjectT2.MOD_ID,
        name = ProjectT2.MOD_NAME,
        version = ProjectT2.VERSION,
        acceptedMinecraftVersions = ProjectT2.ACCEPTED_VERSIONS,
        updateJSON = ProjectT2.UPDATE,
        useMetadata = true
)
public class ProjectT2 {
    public static final String MOD_NAME = "Project T2";
    public static final String MOD_ID = "projt2";
    public static final String VERSION = "1.0.0";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final String UPDATE = "https://google.com/";
    @Mod.Instance(MOD_ID)
    public static ProjectT2 instance;
    @SidedProxy(clientSide = "raven_cze.projt2.proxies.ClientProxy",serverSide = "raven_cze.projt2.proxies.ServerProxy")
    public static IProxy proxy;
    public static final SimpleNetworkWrapper packetHandler = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
    public static CreativeTabs creativeTab;

    static{
        FluidRegistry.enableUniversalBucket();

        creativeTab=new CreativeTabs(MOD_ID){
            //@Override
            public ItemStack createIcon(){return new ItemStack(Items.CAKE);}
            /*
            @Override
            public ItemStack getTabIconItem(){
                return new ItemStack(Items.CAKE);
            }
            */
        }.setBackgroundImageName(MOD_NAME.toLowerCase().replace(" ","")+".png");
    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event){
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event){
        proxy.postInit(event);
    }
    /*
    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Blocks{
    }

    @GameRegistry.ObjectHolder(MOD_ID)
    public static class Items{
    }

    @Mod.EventBusSubscriber
    public static class ObjectRegistryHandler{
        @SubscribeEvent
        public static void addItems(RegistryEvent.Register<Item> event){
        }

        @SubscribeEvent
        public static void addBlocks(RegistryEvent.Register<Block> event){
        }
    }

     */
}
