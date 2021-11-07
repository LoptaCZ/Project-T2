package raven_cze.projt2.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import raven_cze.projt2.ProjectT2;
import raven_cze.projt2.common.PT2Content;

public class BlockResearcher extends Block{
    public BlockResearcher(String name, Material material, int harvestLevel) {
        super(material);
        //setRegistryName(new ResourceLocation(ProjectT2.MOD_ID,name));
        setTranslationKey(ProjectT2.MOD_ID+"."+name);
        setHarvestLevel("pickaxe",harvestLevel);
        setCreativeTab(ProjectT2.creativeTab);


        PT2Content.registeredBlocks.add(this);
        initModel();
    }

    @SideOnly(Side.CLIENT)
    public void initModel(){
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),0,new ModelResourceLocation(ProjectT2.MOD_ID+"."+getRegistryName(),""));
    }
}
