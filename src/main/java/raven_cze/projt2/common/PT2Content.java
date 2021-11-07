package raven_cze.projt2.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import raven_cze.projt2.ProjectT2;
import raven_cze.projt2.api.BlockBase;
import raven_cze.projt2.api.ItemBase;
import raven_cze.projt2.api.ItemBlockBase;
import raven_cze.projt2.common.block.BlockResearcher;
import raven_cze.projt2.common.block.BlockTypes_Hidden;
import raven_cze.projt2.common.item.ItemCrystal;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber
public class PT2Content{
    public static ArrayList<Block> registeredBlocks = new ArrayList();
    public static ArrayList<Item> registeredItems = new ArrayList();
    public static List<Class<?extends TileEntity>> registeredTiles = new ArrayList();

    public static ItemBase itemCrystal;
    /*
    public static ItemPT2Base itemPlant;
    //	thaumcraft.machine
    public static ItemPT2Base itemFocus;
    public static ItemPT2Base itemTool;
    public static ItemPT2Base itemUpgrade;
    //	thaumcraft.arcana
    public static ItemPT2Base itemCarpet;
    public static ItemPT2Base itemCharmCleansing;
    public static ItemPT2Base itemCharmLife;
    public static ItemPT2Base itemCharmNecro;
    public static ItemPT2Base itemCharmSouls;
    public static ItemPT2Base itemCharmVigor;
    public static ItemPT2Base itemComponents;
    public static ItemPT2Base itemCrystalBall;
    public static ItemPT2Base itemCrystallineBell;
    public static ItemPT2Base itemPotion;
    public static ItemPT2Base itemDawnStone;
    public static ItemPT2Base itemEldritchKeystone;
    public static ItemPT2Base itemPortableHole;
    public static ItemPT2Base itemRunicEssence;
    public static ItemPT2Base itemSeal;
    public static ItemPT2Base itemSingularity;
    public static ItemPT2Base itemVisDetector;
    public static ItemPT2Base itemVoidBracelet;
    public static ItemPT2Base itemVoidCompass;
    public static ItemPT2Base itemWand;
    //	Tools
    public static ItemToolBase itemElementalAxe;
    public static ItemToolBase itemElementalBow;
    public static ItemToolBase itemElementalCrusher;
    public static ItemToolBase itemElementalCutter;
    public static ItemToolBase itemElementalHoe;
    public static ItemToolBase itemElementalPick;
    public static ItemToolBase itemElementalShovel;
    public static ItemToolBase itemElementalSword;

    public static ItemToolBase itemThaumiumAxe;
    public static ItemToolBase itemThaumiumHoe;
    public static ItemToolBase itemThaumiumPick;
    public static ItemToolBase itemThaumiumShovel;
    public static ItemToolBase itemThaumiumSword;

    public static ItemToolBase itemVoidCrusher;
    public static ItemToolBase itemVoidCutter;
    //	Armor
    public static ItemPT2Base itemMaskCruelty;
    public static ItemPT2Base itemSevenBoots;
    public static ItemPT2Base itemStompBoots;
    public static ItemPT2Base itemStridingBoots;
    public static ItemPT2Base itemThaumiumArmor;
    public static ItemPT2Base itemVoidArmor;
    public static ItemPT2Base itemVisGoggles;
    //	thaumcraft.arcana.research
    public static ItemPT2Base itemArtifact;
    public static ItemPT2Base itemDiscovery;
    public static ItemPT2Base itemDiscoveryTome;
    public static ItemPT2Base itemKnowledgeFragment;
    public static ItemPT2Base itemTheory;
    */
    //  BLOCKS
    //public static BlockResearcher blockResearcher;
    public static BlockBase<BlockTypes_Hidden> blockVoidDevice;
    //  FLUID(s)
    public static Fluid fluidVis=setupFluid((new Fluid("vis",new ResourceLocation("projt2:blocks/fluid/tcubeanim"),new ResourceLocation("projt2:blocks/fluid/tcubeanim"))).setDensity(4000).setViscosity(4000));


    static{
        //      ITEMS
        itemCrystal = new ItemCrystal();
        //      BLOCKS
        blockVoidDevice=(BlockBase)(new BlockBase("void", Material.ROCK, PropertyEnum.create("type",BlockTypes_Hidden.class),ItemBlockBase.class,new Object[0])).setOpaque(true).setHasFlavour(new int[0]);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        if(!registeredBlocks.isEmpty()){
            for (Block block:registeredBlocks) {
                if (block!=null) {
                    ProjectT2.ProjectT2Core.PT2Logger.info("Registering BLOCK {}, unlocalized: {}, registry: {}, subtypes: {}", new Object[]{block, block.getTranslationKey(), block.getRegistryName(), null});
                    event.getRegistry().register(block.setRegistryName(createRegistryName(block.getTranslationKey())));
                }
            }
        }
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        if(!registeredItems.isEmpty()){
            for (Item item : registeredItems){
                if (item != null) {
                    ProjectT2.ProjectT2Core.PT2Logger.info("Registering ITEM {}, unlocalized: {}, registry: {}, subtypes: {}",new Object[]{item,item.getTranslationKey(),item.getRegistryName(),item.getHasSubtypes()});
                    event.getRegistry().register(item.setRegistryName(createRegistryName(item.getTranslationKey())));
                }
            }
        }
        //      ItemBlocks
        if(!registeredBlocks.isEmpty()){
            for(Block thing : registeredBlocks){
                ProjectT2.ProjectT2Core.PT2Logger.info("Registering ITEM_BLOCK {}, unlocalized: {}, registry: {}",new Object[]{thing,thing.getTranslationKey(),thing.getRegistryName()});
                event.getRegistry().register(Item.getItemFromBlock(thing.setRegistryName(createRegistryName(thing.getTranslationKey()))));
            }
        }
    }
    public static ResourceLocation createRegistryName(String unlocalized){
        try{
            unlocalized = unlocalized.substring(unlocalized.indexOf("projectt2"));
            unlocalized = unlocalized.replaceFirst("\\.", ":");
            return new ResourceLocation(unlocalized);
        }catch(Exception e){
            return new ResourceLocation(unlocalized);
        }
    }

    private static Fluid setupFluid(Fluid fluid){
        FluidRegistry.addBucketForFluid(fluid);
        if(!FluidRegistry.registerFluid(fluid))
            return FluidRegistry.getFluid(fluid.getName());
        return fluid;
    }
}
