package raven_cze.projt2.common;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import raven_cze.projt2.ProjectT2;
import raven_cze.projt2.util.api.ItemPT2Base;
import raven_cze.projt2.util.api.ItemToolBase;

import java.util.ArrayList;

@Mod.EventBusSubscriber
public class PT2Content {
    public static ArrayList<Item> registeredItems;
    public static ArrayList<Block> registeredBlocks;

    public static ItemPT2Base itemCrystal;
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

    public static Fluid fluidVis=setupFluid((new Fluid("vis",new ResourceLocation("projt2:blocks/fluid/tcubeanim"),new ResourceLocation("projt2:blocks/fluid/tcubeanim"))).setDensity(4000).setViscosity(4000));

    static {
        registeredItems = new ArrayList();
        registeredBlocks = new ArrayList();

        itemCrystal = new ItemPT2Base("crystal", 64, "air", "earth", "fire", "water", "taint", "vis", "empty");
    }

    private static Fluid setupFluid(Fluid fluid){
        FluidRegistry.addBucketForFluid(fluid);
        if(!FluidRegistry.registerFluid(fluid))
            return FluidRegistry.getFluid(fluid.getName());
        return fluid;
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event){
        for(Block block:registeredBlocks){
            event.getRegistry().register(block.setRegistryName(block.getTranslationKey()));
            ProjectT2.ProjectT2Core.PT2Logger.info("Registering BLOCK {}, unlocalized: {}, registry: {}, subtypes: {}",new Object[]{block,block.getTranslationKey(),block.getRegistryName(),null});
        }
    }
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event){
        for(Item item:registeredItems){
            event.getRegistry().register(item.setRegistryName(item.getTranslationKey().replace("item.","")));
            ProjectT2.ProjectT2Core.PT2Logger.info("Registering ITEM {}, unlocalized: {}, registry: {}, subtypes: {}",new Object[]{item,item.getTranslationKey(),item.getRegistryName(),item.getHasSubtypes()});
        }
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event){
        for(Item item:registeredItems){
            if(item.getHasSubtypes()){
                for(int meta = 0; meta<new ItemStack(item).getMaxDamage(); meta++){
                    ModelLoader.setCustomModelResourceLocation(item,meta,new ModelResourceLocation(new ResourceLocation(ProjectT2.MOD_ID,item.getTranslationKey()),""));
                    ProjectT2.ProjectT2Core.PT2Logger.info("ModelLoader: {},{},{},{}",new Object[]{item,meta,new ResourceLocation(ProjectT2.MOD_ID,item.getTranslationKey()),null});
                }
            }else{
                ModelLoader.setCustomModelResourceLocation(item,0,new ModelResourceLocation(new ResourceLocation(ProjectT2.MOD_ID,item.getTranslationKey()),""));

            }
        }
    }

}
