package raven_cze.projt2.common.block;

import net.minecraft.util.IStringSerializable;
import raven_cze.projt2.api.BlockBase;

import java.util.Locale;

public enum BlockTypes_Hidden implements IStringSerializable, BlockBase.IBlockEnum {
    TileVoidHole,
    TileMonolith,
    TileVoidCube,
    TileVoidLock;

    @Override
    public String getName(){return toString().toLowerCase(Locale.ENGLISH);}
    @Override
    public int getMeta() {return ordinal();}
    @Override
    public boolean listForCreative(){return true;}
}
