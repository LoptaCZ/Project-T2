package com.raven_cze.projt2.api.internal;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

public class InternalEnergyStorage extends EnergyStorage{
    public InternalEnergyStorage(int capacity){super(capacity,capacity,capacity,0);}

    public CompoundTag write(CompoundTag tag){tag.putInt("energy",energy);return tag;}
    public CompoundTag write(CompoundTag tag,String name){tag.putInt("energy_"+name,energy);return tag;}
    public void read(CompoundTag tag){setEnergy(tag.getInt("energy"));}
    public void read(CompoundTag tag,String name){setEnergy(tag.getInt("energy_"+name));}
    public int getSpace(){return Math.max(getMaxEnergyStored()-getEnergyStored(),0);}
    public void setEnergy(int energy){this.energy=energy;}
    public void setCapacity(int capacity){this.capacity=capacity;}
}
