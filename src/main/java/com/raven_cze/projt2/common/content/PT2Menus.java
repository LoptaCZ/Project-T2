package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.PT2Config;
import com.raven_cze.projt2.common.content.world.inventory.*;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PT2Menus{
    public static final DeferredRegister<MenuType<?>>REGISTRY=DeferredRegister.create(ForgeRegistries.CONTAINERS,ProjectT2.MODID);

    public static final RegistryObject<MenuType<DiscoveryTomeMenu>>MENU_DISCOVERY_TOME=register(DiscoveryTomeMenu::new,"discovery_tome");
    public static final RegistryObject<MenuType<VoidChestMenu>>MENU_VOID_CHEST=register(VoidChestMenu::create,"void_chest");
    public static final RegistryObject<MenuType<VoidChestMenu>>MENU_VOID_CHEST_NI=register(VoidChestMenu::createNoInterface,"void_chest_ni");
    public static final RegistryObject<MenuType<ArcaneFurnaceMenu>>MENU_FURNACE=register(ArcaneFurnaceMenu::new,"furnace");
    public static final RegistryObject<MenuType<CrystalBallMenu>>MENU_CRYSTALBALL=register(CrystalBallMenu::new,"crystal_ball");
    public static final RegistryObject<MenuType<GeneratorMenu>>MENU_GENERATOR=register(GeneratorMenu::new,"generator");

    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>>register(IContainerFactory<T> factory, String name){
        return REGISTRY.register(name,()-> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus){
        if(PT2Config.SHARED.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering MENUs");
        REGISTRY.register(eventBus);
    }
}
