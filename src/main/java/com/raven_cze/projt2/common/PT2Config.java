package com.raven_cze.projt2.common;

import com.raven_cze.projt2.ProjectT2;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

@Mod.EventBusSubscriber(modid=ProjectT2.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class PT2Config {
    public static class Client{
        public final ForgeConfigSpec.BooleanValue lowFX;
        public final ForgeConfigSpec.BooleanValue customItem;
        public final ForgeConfigSpec.BooleanValue pipeDrips;
        public final ForgeConfigSpec.BooleanValue portalGFX;
        public final ForgeConfigSpec.IntValue portalRes;
        Client(ForgeConfigSpec.Builder b){
            b.comment("config.projt2.client").push("Client");

            lowFX=b
                    .comment("Use low graphics settings.")
                    .translation("config.projt2.lowfx")
                    .define("LowFX",false);
            customItem=b
                    .comment("Use custom item name & description colors.")
                    .translation("config.projt2.items")
                    .define("CustomItem",false);
            pipeDrips=b
                    .comment("Make conduits leaky.")
                    .translation("config.projt2.pipes")
                    .define("PipeDrips",false);
            portalGFX=b
                    .comment("Allow portal RT rendering.")
                    .translation("config.projt2.portal")
                    .define("PortalGFX",true);
            portalRes=b
                    .comment("Custom resolution for portals.")
                    .translation("config.projt2.portal_res")
                    .defineInRange("PortalRes",512,128,4096);

            b.pop();
        }
    }
    public static class Shared{

        public final ForgeConfigSpec.BooleanValue debugMode;
        public final ForgeConfigSpec.BooleanValue versionCheck;
        public final ForgeConfigSpec.BooleanValue shiftTools;
        Shared(ForgeConfigSpec.Builder b){
            b.comment("config.projt2.common").push("Shared");

            debugMode=b
                    .comment("Allow debug mode")
                    .translation("config.projt2.debug")
                    .define("DebugMode",false);
            versionCheck=b
                    .comment("Enable version check")
                    .translation("config.projt2.version")
                    .define("VersionCheck",true);
            shiftTools=b
                    .comment("Switch elemental tools functions")
                    .translation("config.projt2.shift")
                    .define("ShiftTools",false);

            b.pop();
        }
    }
    public static class Server{
        public final ForgeConfigSpec.IntValue autoSave;
        public final ForgeConfigSpec.IntValue maxAura;
        public final ForgeConfigSpec.IntValue taintSpread;
        Server(ForgeConfigSpec.Builder b){
            b.comment("config.projt2.server").push("Server");

            autoSave=b
                    .comment("Autosave timer")
                    .translation("config.projt2.autosave")
                    .defineInRange("AutoSave",5,0,60);
            maxAura=b
                    .comment("Max aura in world")
                    .translation("config.projt2.aura")
                    .defineInRange("MaxAura",15000,5000,30000);
            taintSpread=b
                    .comment("Taint spreading")
                    .translation("config.projt2.taint")
                    .defineInRange("TaintSpread",1,0,2);

            b.pop();
        }
    }
    public static final ForgeConfigSpec client;
    public static final Client CLIENT;
    static{
        final Pair<Client,ForgeConfigSpec>specPair=new ForgeConfigSpec.Builder().configure(Client::new);
        client=specPair.getRight();
        CLIENT=specPair.getLeft();
    }
    public static final ForgeConfigSpec shared;
    public static final Shared SHARED;
    static{
        final Pair<Shared,ForgeConfigSpec>specPair=new ForgeConfigSpec.Builder().configure(Shared::new);
        shared=specPair.getRight();
        SHARED=specPair.getLeft();
    }
    public static final ForgeConfigSpec server;
    public static final Server SERVER;
    static{
        final Pair<Server,ForgeConfigSpec>specPair=new ForgeConfigSpec.Builder().configure(Server::new);
        server=specPair.getRight();
        SERVER=specPair.getLeft();
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event){

    }
    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event){

    }
}
