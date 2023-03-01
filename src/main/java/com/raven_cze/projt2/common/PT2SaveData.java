package com.raven_cze.projt2.common;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraftforge.common.MinecraftForge;
import org.jetbrains.annotations.NotNull;

public class PT2SaveData extends SavedData{
    private static PT2SaveData INSTANCE;
    public static final String dataName="ProjectT2-SaveData";
    public PT2SaveData(){}
    public PT2SaveData(CompoundTag tag){
        this();
        ListTag dimensionList=tag.getList("cock",10);
    }
    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag nbt){
        ListTag dimensionList=new ListTag();

        return nbt;
    }
    public static void markInstanceDirty(){if(INSTANCE!=null)INSTANCE.setDirty();}
    public static void setInstance(PT2SaveData in){INSTANCE=in;}
}
