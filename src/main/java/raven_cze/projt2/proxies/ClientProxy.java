package raven_cze.projt2.proxies;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import raven_cze.projt2.ProjectT2;
import raven_cze.projt2.api.BlockInterfaces;
import raven_cze.projt2.api.CustomStateMapper;
import raven_cze.projt2.common.PT2Content;
import raven_cze.projt2.api.ItemBase;

@Mod.EventBusSubscriber(Side.CLIENT)
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

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event){
        for(Block block:PT2Content.registeredBlocks){
            final ResourceLocation loc = (ResourceLocation)Block.REGISTRY.getNameForObject(block);
            Item itemBlock=Item.getItemFromBlock(block);
            if(itemBlock==null) throw new RuntimeException("ITEMBLOCK FOR "+loc+" : "+block+" IS NULL");
            if(block instanceof BlockInterfaces.IMetaBlock){
                BlockInterfaces.IMetaBlock metaBlock=(BlockInterfaces.IMetaBlock)block;
                if(metaBlock.useCustomStateMapper()){
                    ModelLoader.setCustomStateMapper(block, CustomStateMapper.getStateMapper(metaBlock));
                    ModelLoader.setCustomMeshDefinition(itemBlock, new ItemMeshDefinition() {
                        @Override
                        public ModelResourceLocation getModelLocation(ItemStack stack) {
                            return new ModelResourceLocation(loc,"inventory");
                        }
                    });
                }
                for(int meta=0;meta<metaBlock.getMetaEnums().length;meta++){

                }
            }
        }
        for(Item item:PT2Content.registeredItems){
            if(item instanceof net.minecraft.item.ItemBlock) continue;
            if(item instanceof ItemBase){
                ItemBase metaItem =(ItemBase) item;
                if(metaItem.registerSubModels && metaItem.getSubNames()!=null&&metaItem.getSubNames().length>0){
                    for(int meta=0;meta<metaItem.getSubNames().length;meta++){
                        final ResourceLocation loc = new ResourceLocation(ProjectT2.MOD_ID,metaItem.itemName+"/"+metaItem.getSubNames()[meta]);
                        ModelBakery.registerItemVariants(metaItem,new ResourceLocation[]{loc});
                        ModelLoader.setCustomModelResourceLocation(metaItem,meta,new ModelResourceLocation(loc,"inventory"));

                    }
                    continue;
                }
                final ResourceLocation loc = new ResourceLocation(ProjectT2.MOD_ID,metaItem.itemName);
                ModelBakery.registerItemVariants(metaItem,new ResourceLocation[]{loc});
                ModelLoader.setCustomMeshDefinition(metaItem, new ItemMeshDefinition() {
                    @Override
                    public ModelResourceLocation getModelLocation(ItemStack stack) {
                        return new ModelResourceLocation(loc,"inventory");
                    }
                });
                continue;
            }
            final ResourceLocation loc = (ResourceLocation) Item.REGISTRY.getNameForObject(item);
            ModelBakery.registerItemVariants(item,new ResourceLocation[] {loc});
            ModelLoader.setCustomMeshDefinition(item, new ItemMeshDefinition() {
                @Override
                public ModelResourceLocation getModelLocation(ItemStack stack) {
                    return new ModelResourceLocation(loc,"inventory");
                }
            });
        }
    }
}
