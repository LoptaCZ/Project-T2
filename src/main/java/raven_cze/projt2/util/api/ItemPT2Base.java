package raven_cze.projt2.util.api;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import raven_cze.projt2.ProjectT2;
import raven_cze.projt2.common.PT2Content;

import javax.annotation.ParametersAreNonnullByDefault;

public class ItemPT2Base extends Item {

    private final String itemName;
    private final String[] subNames;

    public ItemPT2Base(String name, int stackSize, String... subNames){
        setTranslationKey(name);
        setHasSubtypes((subNames!=null && subNames.length>0));
        setCreativeTab(ProjectT2.creativeTab);
        setMaxStackSize(stackSize);

        this.itemName=name;
        this.subNames=(subNames!=null&&subNames.length>0)?subNames:null;

        PT2Content.registeredItems.add(this);
    }
    public String[] getSubNames(){return this.subNames;}

    @ParametersAreNonnullByDefault
    @SideOnly(Side.CLIENT)
    public void getSubItems(CreativeTabs tab,NonNullList<ItemStack> list){
        if(isInCreativeTab(tab)){
            if(getSubNames()!=null){
                for(int meta = 0; meta<getSubNames().length; meta++){
                    list.add(new ItemStack(this,1,meta));
                }
            }
        }else{
            list.add(new ItemStack(this));
        }
    }
    public String getUnlocalizedName(ItemStack stack){
        if(getSubNames()!=null){
            String subName=(stack.getItemDamage()<getSubNames().length)?getSubNames()[stack.getItemDamage()]:"";
            return getTranslationKey()+"_"+subName;
        }
        return getUnlocalizedName(stack);
    }
}
