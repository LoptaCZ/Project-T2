package com.raven_cze.projt2.client.gui.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PT2Slot extends Slot{
    public PT2Slot(Container container,int id,int x,int y){
        super(container,id,x,y);

    }

    @Override
    public boolean hasItem(){
        return super.hasItem();
    }

    @Override
    public void set(@NotNull ItemStack stack) {
        super.set(stack);
    }
}
