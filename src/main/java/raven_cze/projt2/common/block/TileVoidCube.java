package raven_cze.projt2.common.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileVoidCube extends TileEntity {
    public byte[] runes = {-1,-1,-1,-1};
    public byte placed = -1;

    public boolean canUpdate(){return false;}

    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        runes=compound.getByteArray("runes");
        placed=compound.getByte("placed");
    }
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setByteArray("runes",runes);
        compound.setByte("placed",placed);
        return compound;
    }
}
