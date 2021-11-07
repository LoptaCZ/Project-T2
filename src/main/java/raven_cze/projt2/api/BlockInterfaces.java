package raven_cze.projt2.api;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockInterfaces {
    public static interface IMetaBlock{
        String getBlockName();
        IProperty getMetaProperty();
        Enum[] getMetaEnums();
        IBlockState getInventoryState(int paramInt);
        boolean useCustomStateMapper();
        String getCustomStateMapping(int paramInt,boolean paramBoolean);
        @SideOnly(Side.CLIENT)
        StateMapperBase getCustomMapper();
        boolean appendPropertiesToState();
    }
}
