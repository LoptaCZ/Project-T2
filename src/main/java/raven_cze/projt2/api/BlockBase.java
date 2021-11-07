package raven_cze.projt2.api;

import com.google.common.collect.Sets;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import raven_cze.projt2.common.PT2Content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BlockBase<E extends Enum<E> & BlockBase.IBlockEnum> extends Block implements BlockInterfaces.IMetaBlock{
    protected static IProperty[] tempProperties;
    protected static IUnlistedProperty[] tempUnlistedProperties;
    public final String name;
    public final PropertyEnum<E> property;
    public final IProperty[] additionalProperties;
    public final IUnlistedProperty[] additionalUnlistedProperties;
    public final Enum[] enumValues;
    boolean[] hasFlavour;
    protected Set<BlockRenderLayer> renderLayers = Sets.newHashSet(new BlockRenderLayer[] { BlockRenderLayer.SOLID });
    protected Set<BlockRenderLayer>[] metaRenderLayers;
    protected Map<Integer, Integer> metaLightOpacities = new HashMap();
    protected Map<Integer, Float> metaHardness = new HashMap();
    protected Map<Integer, Integer> metaResistances = new HashMap();

    protected EnumPushReaction[] metaMobilityFlags;
    protected boolean[] canHammerHarvest;
    protected boolean[] metaNotNormalBlock;
    private boolean opaqueCube = false;

    public BlockBase(String name, Material material, PropertyEnum<E> mainProperty,Class<?extends ItemBlockBase> itemBlock,Object... additionalProperties){
        super(setTempProperties(material,mainProperty,additionalProperties));
        this.name=name;
        this.property=mainProperty;
        this.enumValues=(Enum[]) mainProperty.getValueClass().getEnumConstants();

        ArrayList<IProperty>propList=new ArrayList<IProperty>();
        ArrayList<IUnlistedProperty>unlistedPropList=new ArrayList<IUnlistedProperty>();
        for(Object o : additionalProperties){
            if(o instanceof IProperty) propList.add((IProperty)o);
            if(o instanceof IProperty[]) for(IProperty p:(IProperty[])o) propList.add(p);
            if(o instanceof IUnlistedProperty) propList.add((IProperty)o);
            if(o instanceof IUnlistedProperty[]) for(IUnlistedProperty p:(IUnlistedProperty[])o) propList.add((IProperty) p);
        }
        this.additionalProperties=(IProperty[])propList.toArray(new IProperty[propList.size()]);
        this.additionalUnlistedProperties=(IUnlistedProperty[])unlistedPropList.toArray(new IUnlistedProperty[unlistedPropList.size()]);

        String registryName = createRegistryName();

        PT2Content.registeredBlocks.add(this);
    }
    public String getBlockName(){return this.name;}
    public Enum[] getMetaEnums(){return this.enumValues;}
    public IBlockState getInventoryState(int meta) {return getInventoryState(meta);}
    public PropertyEnum<E> getMetaProperty(){return this.property;}
    public boolean useCustomStateMapper(){return false;}
    public String getCustomStateMapping(int meta,boolean itemBlock){return null;}
    @SideOnly(Side.CLIENT)
    public StateMapperBase getCustomMapper(){return null;}
    public boolean appendPropertiesToState(){return true;}
    protected static Material setTempProperties(Material material, PropertyEnum<?> property, Object... additionalProperties) {
        ArrayList<IProperty> propList = new ArrayList<IProperty>();
        ArrayList<IUnlistedProperty> unlistedPropList = new ArrayList<IUnlistedProperty>();
        propList.add(property);
        for (Object o : additionalProperties) {

            if (o instanceof IProperty)
                propList.add((IProperty)o);
            if (o instanceof IProperty[])
                for (IProperty p : (IProperty[])o)
                    propList.add(p);
            if (o instanceof IUnlistedProperty)
                unlistedPropList.add((IUnlistedProperty)o);
            if (o instanceof IUnlistedProperty[])
                for (IUnlistedProperty p : (IUnlistedProperty[])o)
                    unlistedPropList.add(p);
        }
        tempProperties = (IProperty[])propList.toArray(new IProperty[propList.size()]);
        tempUnlistedProperties = (IUnlistedProperty[])unlistedPropList.toArray(new IUnlistedProperty[unlistedPropList.size()]);
        return material;
    }
    protected static Object[] combineProperties(Object[] currentProperties, Object... addedProperties) {
        Object[] array = new Object[currentProperties.length + addedProperties.length];
        for (int i = 0; i < currentProperties.length; i++)
            array[i] = currentProperties[i];
        for (int i = 0; i < addedProperties.length; i++)
            array[currentProperties.length + i] = addedProperties[i];
        return array;
    }
    public BlockBase setHasFlavour(int... meta) {
        if (meta == null || meta.length < 1) {
            for (int i = 0; i < this.hasFlavour.length; i++)
                this.hasFlavour[i] = true;
        } else {
            for (int i : meta)
            { if (i >= 0 && i < this.hasFlavour.length)
                this.hasFlavour[i] = false;  }
        }  return this;
    }
    public boolean hasFlavour(ItemStack stack){return this.hasFlavour[Math.max(0,Math.min(stack.getItemDamage(),this.hasFlavour.length))];}

    public BlockBase<E> setBlockLayer(BlockRenderLayer... layer) {
        this.renderLayers = Sets.newHashSet(layer);
        return this;
    }
    public BlockBase<E> setMetaBlockLayer(int meta, BlockRenderLayer... layer) {
        this.metaRenderLayers[Math.max(0, Math.min(meta, this.metaRenderLayers.length - 1))] = Sets.newHashSet(layer);
        return this;
    }

    protected E fromMeta(int meta) {
        if (meta < 0 || meta >= this.enumValues.length)
            meta = 0;
        return (E)this.enumValues[meta];
    }

    public boolean isOpaqueCube(){return this.opaqueCube;}
    public BlockBase<E> setOpaque(boolean isOpaque){
        this.opaqueCube=isOpaque;
        return this;
    }
    public String createRegistryName(){return "projt2:"+this.name;}

    public interface IBlockEnum extends IStringSerializable{
        int getMeta();
        boolean listForCreative();
    }
}