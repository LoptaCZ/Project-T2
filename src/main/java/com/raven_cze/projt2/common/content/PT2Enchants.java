package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.PT2Config;
import com.raven_cze.projt2.common.content.enchantment.*;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PT2Enchants{
    public static final DeferredRegister<Enchantment>REGISTRY=DeferredRegister.create(ForgeRegistries.ENCHANTMENTS,ProjectT2.MODID);

    public static final RegistryObject<Enchantment> IceAspect=REGISTRY.register("ice_aspect",()->new PT2IceAspect(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE,PT2Items.BOTH));
    public static final RegistryObject<Enchantment>Potency=REGISTRY.register("potency",()->new PT2Potency(Enchantment.Rarity.RARE,EnchantmentCategory.BREAKABLE,null));
    public static final RegistryObject<Enchantment>RelicHunter=REGISTRY.register("relic_hunter",()->new PT2RelicHunter(Enchantment.Rarity.RARE,EnchantmentCategory.BREAKABLE,PT2Items.BOTH));
    public static final RegistryObject<Enchantment>Repair=REGISTRY.register("repair",()->new PT2Repair(Enchantment.Rarity.RARE,EnchantmentCategory.BREAKABLE,PT2Items.EVERYTHING));
    public static final RegistryObject<Enchantment>SoulSteal=REGISTRY.register("soul_steal",()->new PT2Soulstealer(Enchantment.Rarity.RARE,EnchantmentCategory.BREAKABLE,PT2Items.MAIN));
    public static final RegistryObject<Enchantment>Striding=REGISTRY.register("striding",()->new PT2Striding(Enchantment.Rarity.RARE,EnchantmentCategory.BREAKABLE,null));
    public static final RegistryObject<Enchantment>Vampiric=REGISTRY.register("vampiric",()->new PT2Vampiric(Enchantment.Rarity.RARE,EnchantmentCategory.BREAKABLE,PT2Items.BOTH));
    public static final RegistryObject<Enchantment>VenomAspect=REGISTRY.register("venom_aspect",()->new PT2VenomAspect(Enchantment.Rarity.RARE,EnchantmentCategory.BREAKABLE,null));

    public static void register(IEventBus eventBus){
        if(PT2Config.SHARED.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering ENCHANTs");
        REGISTRY.register(eventBus);
    }
}
