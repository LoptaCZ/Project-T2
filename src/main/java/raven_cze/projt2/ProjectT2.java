package raven_cze.projt2;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.jetbrains.annotations.NotNull;
import raven_cze.projt2.proxies.IProxy;
import raven_cze.projt2.util.world.WorldGenMonolith;

import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

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
    public static final String UPDATE = "https://raw.githubusercontent.com/LoptaCZ/Project-T2/main/src/main/resources/META-INF/update.json";
    @Mod.Instance(MOD_ID)
    public static ProjectT2 instance;
    @SidedProxy(clientSide = "raven_cze.projt2.proxies.ClientProxy",serverSide = "raven_cze.projt2.proxies.ServerProxy")
    public static IProxy proxy;
    public static final SimpleNetworkWrapper packetHandler = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);
    private static HashMap SpecialTileHM;
    public static CreativeTabs creativeTab = new CreativeTabs(MOD_ID){
        @MethodsReturnNonnullByDefault
        public @NotNull ItemStack createIcon(){return new ItemStack(Items.CAKE);}

        @Override
        public @NotNull CreativeTabs setBackgroundImageName(@NotNull String texture) {
            return super.setBackgroundImageName(MOD_NAME.toLowerCase().replace(" ","")+".png");
        }
    };

    static{
        FluidRegistry.enableUniversalBucket();
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

    public static class ProjectT2Core {
        public static void GenerateAura(){

        }
        public static void GenerateTaintedArea(){

        }
        private static byte GetDimension(World world){
            try{
                return (byte)(Minecraft.getMinecraft()).player.dimension;
            }catch(Exception e){
                return (byte)world.provider.getDimension();
            }
        }
        public static boolean GenerateMonolith(World world,Random random,int coordX,int coordZ){
            int x=coordX+8;
            int z=coordZ+8;
            int y=world.getHeight(x,z)-1;
            if(y<20)return false;
            if(random.nextInt(50)==0 && DistanceToNearestMonolith(world,x,y,z)>300.0D){
                return (new WorldGenMonolith()).generate(world,random,x,y,z);
            }
            return false;
        }
        public static double DistanceToNearestMonolith(World world,int x,int y,int z){
            Collection SpecialTiles;
            SpecialTiles = ProjectT2.SpecialTileHM.values();
            double distance = Double.MAX_VALUE;
            //try{
                /*
                for (SISpecialTile tile : SpecialTiles) {
                    if (tile.type != 2 || tile.dimension != GetDimension(world)) continue;
                    double var7 = tile.x - x;
                    double var9 = tile.y - y;
                    double var11 = tile.z - z;
                    double d = var7 * var7 + var9 * var9 + var11 * var11;
                    if (d < distance) distance = d;
                }
                */
            //}catch(Exception e){
                //PT2Logger.log(Level.ERROR,"Error while executing DistanceToNearestMonolith(World,x,y,z) function",e);}
            return Math.sqrt(distance);
        }

        public static boolean GenerateSilverwood(World world,Random random,int coordX,int coordZ){
            return false;
        }
        public static boolean GenerateGreatwood(World world,Random random,int coordX,int coordZ){
            return false;
        }
    }
}
