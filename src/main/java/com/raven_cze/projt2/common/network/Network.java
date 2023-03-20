package com.raven_cze.projt2.common.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Optional;

public class Network{
    private static SimpleChannel INSTANCE;
    private static int packetID=0;
    private static int id(){return packetID++;}

    public static void register(){
        INSTANCE=NetworkRegistry.newSimpleChannel(new ResourceLocation("projt2","network"),()->"1.0",s->true,s->true);
        INSTANCE.registerMessage(id(),PacketAuraToClient.class,PacketAuraToClient::encode,PacketAuraToClient::decode,PacketAuraToClient::handle,Optional.of(NetworkDirection.PLAY_TO_CLIENT));
    }

    //public static<MSG>void sendToServer(MSG message){INSTANCE.sendToServer(message);}
    public static<MSG>void sendToPlayer(MSG message,ServerPlayer player){INSTANCE.send(PacketDistributor.PLAYER.with(()->player),message);}
}