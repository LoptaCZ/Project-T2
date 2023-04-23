package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.api.IConnection;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class PT2Capabilities{
    public static final Capability<IConnection>CONNECTABLE_CAP= CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public static void initCaps(RegisterCapabilitiesEvent event){
        event.register(IConnection.class);
    }
}
