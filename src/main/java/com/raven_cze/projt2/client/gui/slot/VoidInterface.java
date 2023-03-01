package com.raven_cze.projt2.client.gui.slot;

import com.raven_cze.projt2.common.content.tiles.TileVoidInterface;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class VoidInterface extends PT2Slot{
    private final int slotIndex;

    public VoidInterface(Container container,int index,int x,int y){
        super(container,index,x,y);
        this.slotIndex=index;
    }

    public ItemStack getItemInSlot(){
        return((TileVoidInterface)this.container).getStackInSlot(this.slotIndex);
    }
    public ItemStack shrinkItemInSlot(int count){
        return ((TileVoidInterface)this.container).shrinkStackInSlot(this.slotIndex,count);
    }
}
