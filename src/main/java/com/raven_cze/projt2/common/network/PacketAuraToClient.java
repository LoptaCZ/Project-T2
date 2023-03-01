package com.raven_cze.projt2.common.network;

import com.raven_cze.projt2.client.overlay.VisDetector;
import com.raven_cze.projt2.common.content.world.aura.AuraChunk;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketAuraToClient{
    short base;
    float vis;
    float taint;
    BlockPos pos;

    public PacketAuraToClient(FriendlyByteBuf dat){
        this.base=dat.readShort();
        this.vis=dat.readFloat();
        this.taint=dat.readFloat();
        this.pos=dat.readBlockPos();
    }
    public PacketAuraToClient(AuraChunk dat){
        this.base=dat.getBase();
        this.vis=dat.getVis();
        this.taint=dat.getTaint();
        this.pos=dat.getLoc().getWorldPosition();
    }

    public void encode(ByteBuf dat) {
        dat.writeShort(this.base);
        dat.writeFloat(this.vis);
        dat.writeFloat(this.taint);

        dat.writeInt(this.pos.getX());
        dat.writeInt(this.pos.getY());
        dat.writeInt(this.pos.getZ());
    }
    public static PacketAuraToClient decode(FriendlyByteBuf dat){
        return new PacketAuraToClient(dat);
    }

    public void handle(Supplier<NetworkEvent.Context>supplier){
        NetworkEvent.Context ctx=supplier.get();
        ctx.enqueueWork(()->{
            //TODO handling packet
            VisDetector.aura.setBase(this.base);
            VisDetector.aura.setVis(this.vis);
            VisDetector.aura.setTaint(this.taint);
        });
        LevelChunk chunk=null;
        if(Minecraft.getInstance().level!=null)chunk=Minecraft.getInstance().level.getChunkAt(this.pos);
        LevelChunk finalChunk=chunk;
        Minecraft.getInstance().execute(()->VisDetector.aura=new AuraChunk(finalChunk,this.base,this.vis,this.taint));
        supplier.get().setPacketHandled(true);
    }
}
