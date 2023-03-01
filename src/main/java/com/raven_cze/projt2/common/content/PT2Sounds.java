package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.config.CommonCFG;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PT2Sounds{
    protected static final DeferredRegister<SoundEvent>REGISTRY=DeferredRegister.create(ForgeRegistries.SOUND_EVENTS,ProjectT2.MODID);

    public static final RegistryObject<SoundEvent>Attach=register("attach");
    public static final RegistryObject<SoundEvent>Bubbling=register("bubbling");
    public static final RegistryObject<SoundEvent>Creaking=register("creaking");
    public static final RegistryObject<SoundEvent>Gore=register("gore");
    public static final RegistryObject<SoundEvent>Infuser=register("infuser");
    public static final RegistryObject<SoundEvent>Infuser_Dark=register("infuser_dark");
    public static final RegistryObject<SoundEvent>Monolith=register("monolith");
    public static final RegistryObject<SoundEvent>MonolithFound=register("monolith_found");
    public static final RegistryObject<SoundEvent>PortalClose=register("pclose");
    public static final RegistryObject<SoundEvent>Place=register("place");
    public static final RegistryObject<SoundEvent>PodBurst=register("podburst");
    public static final RegistryObject<SoundEvent>PortalOpen=register("popen");
    public static final RegistryObject<SoundEvent>Roots=register("roots");
    public static final RegistryObject<SoundEvent>Rumble=register("rumble");
    public static final RegistryObject<SoundEvent>RuneSet=register("rune_set");
    public static final RegistryObject<SoundEvent>StoneClose=register("stoneclose");
    public static final RegistryObject<SoundEvent>StoneOpen=register("stoneopen");
    public static final RegistryObject<SoundEvent>Upgrade=register("upgrade");

    public static final RegistryObject<SoundEvent>BeamLoop=register("beamloop");
    public static final RegistryObject<SoundEvent>ElectricLoop=register("elecloop");
    public static final RegistryObject<SoundEvent>FireLoop=register("fireloop");
    public static final RegistryObject<SoundEvent>Heal=register("heal");
    public static final RegistryObject<SoundEvent>Learn=register("learn");
    public static final RegistryObject<SoundEvent>Page=register("page");
    public static final RegistryObject<SoundEvent>Recover=register("recover");
    public static final RegistryObject<SoundEvent>Scribble=register("scribble");
    public static final RegistryObject<SoundEvent>Shock=register("shock");
    public static final RegistryObject<SoundEvent>Stomp=register("stomp");
    public static final RegistryObject<SoundEvent>Suck=register("suck");
    public static final RegistryObject<SoundEvent>Swing=register("swing");
    public static final RegistryObject<SoundEvent>Tinkering=register("tinkering");
    public static final RegistryObject<SoundEvent>Tool=register("tool");
    public static final RegistryObject<SoundEvent>Whisper=register("whisper");
    public static final RegistryObject<SoundEvent>Wind=register("wind");
    public static final RegistryObject<SoundEvent>Zap=register("zap");

    public static final RegistryObject<SoundEvent>Singularity=register("singularity");

    private static RegistryObject<SoundEvent>register(String name){return REGISTRY.register(name,()->new SoundEvent(new ResourceLocation(ProjectT2.MODID,name)));}
    public static void register(IEventBus eventBus){
        if(CommonCFG.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering SOUNDs");
        REGISTRY.register(eventBus);
    }
}
