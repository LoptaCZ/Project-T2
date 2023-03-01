package com.raven_cze.projt2.common.content.world.aura;

import com.raven_cze.projt2.common.network.Network;
import com.raven_cze.projt2.common.network.PacketAuraToClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AuraManager extends SavedData{
    private final Map<ChunkPos,AuraChunk>auraMap=new HashMap<>();
    private final Random random=new Random();
    private int counter=0;

    public static AuraManager get(Level level){
        if(level.isClientSide)throw new RuntimeException("Don't access this client-side!");
        DimensionDataStorage storage=((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(AuraManager::new,AuraManager::new,"aura_manager");
    }

    public void tick(Level level){
        counter--;
        if(counter<=0){
            counter=10;
            level.players().forEach(player->{
                if(player instanceof ServerPlayer sPlayer){
                    AuraChunk ac=new AuraChunk(new ChunkPos(sPlayer.blockPosition()),(short)random.nextInt(),random.nextFloat(),random.nextFloat());

                    Network.sendToPlayer(new PacketAuraToClient(ac),sPlayer);
                }
            });
            //  Possible aura regen place?
        }
    }
    public AuraManager(){}
    public AuraManager(CompoundTag tag){
        ListTag list=tag.getList("aura",Tag.TAG_COMPOUND);
        for(Tag t:list){
            CompoundTag auraTag=(CompoundTag)t;
            ChunkPos pos=new ChunkPos(auraTag.getInt("x"),auraTag.getInt("y"));
            AuraChunk au=new AuraChunk(pos,auraTag.getShort("base"),auraTag.getFloat("vis"),auraTag.getFloat("taint"));
            auraMap.put(pos,au);
        }
    }

    @Override
    public @NotNull CompoundTag save(@NotNull CompoundTag tag){
        return tag;
    }
}
