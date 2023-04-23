package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.PT2Config;
import com.raven_cze.projt2.common.content.items.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class PT2Items{
    public static final DeferredRegister<Item>REGISTRY=DeferredRegister.create(ForgeRegistries.ITEMS,ProjectT2.MODID);

    static EquipmentSlot[]MAIN=new EquipmentSlot[]{EquipmentSlot.MAINHAND};
    static EquipmentSlot[]OFF=new EquipmentSlot[]{EquipmentSlot.OFFHAND};
    static EquipmentSlot[]BOTH=new EquipmentSlot[]{EquipmentSlot.MAINHAND,EquipmentSlot.OFFHAND};
    static EquipmentSlot[]ARMOR=new EquipmentSlot[]{EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET};
    static EquipmentSlot[]EVERYTHING=EquipmentSlot.values();

    public static final CreativeModeTab TABULATOR = new CreativeModeTab("projt2"){
        @Override
        public @NotNull ItemStack makeIcon(){return new ItemStack(PT2Items.BOOK.get());}
        @Override
        public @NotNull ResourceLocation getTabsImage(){return new ResourceLocation(ProjectT2.MODID,"textures/gui/container/creative_inventory/tabs.png");}
        @Override
        public @NotNull ResourceLocation getBackgroundImage(){return new ResourceLocation(ProjectT2.MODID,"textures/gui/container/creative_inventory/items.png");}
    };

    protected static final Item.Properties ItemNormal=new Item.Properties().tab(TABULATOR).rarity(Rarity.COMMON).stacksTo(64);
    protected static final Item.Properties ItemArmor=new Item.Properties().tab(TABULATOR).stacksTo(1);
    protected static final Item.Properties ItemShiny=new Item.Properties().tab(TABULATOR).rarity(Rarity.UNCOMMON).stacksTo(64);
    protected static final Item.Properties ItemDMG=new Item.Properties().tab(TABULATOR).stacksTo(1);

    public static final RegistryObject<Item>CRYSTAL_VIS=register("crystal_vis",()->new ItemCrystal(ItemShiny).setEffect(true));//          Vis Crystal
    public static final RegistryObject<Item>CRYSTAL_WATER=register("crystal_water",()->new ItemCrystal(ItemShiny).setEffect(true));//          Aqueous Crystal
    public static final RegistryObject<Item>CRYSTAL_EARTH=register("crystal_earth",()->new ItemCrystal(ItemShiny).setEffect(true));//          Earthen Crystal
    public static final RegistryObject<Item>CRYSTAL_FIRE=register("crystal_fire",()->new ItemCrystal(ItemShiny).setEffect(true));//          Fiery Crystal
    public static final RegistryObject<Item>CRYSTAL_TAINT=register("crystal_taint",()->new ItemCrystal(ItemShiny).setEffect(true));//          Tainted Crystal
    public static final RegistryObject<Item>CRYSTAL_AIR=register("crystal_air",()->new ItemCrystal(ItemShiny).setEffect(true));//          Vaporous Crystal
    public static final RegistryObject<Item>CRYSTAL_EMPTY=register("crystal_empty",()->new ItemCrystal(ItemNormal));//          Depleted Crystal
    public static final RegistryObject<Item>ALUMENTUM=register("alumentum",()->new PT2Item(ItemNormal));
    public static final RegistryObject<Item>ANIM_PISTON=register("animated_piston",()->new PT2Item(ItemNormal));
    public static final RegistryObject<Item>SINGULARITY=register("singularity",()->new ItemSingularity(ItemShiny).setEffect(true));
    public static final RegistryObject<Item>CINDERPEARL=register("cinderpearl_pod",()->new PT2Item(ItemNormal));
    public static final RegistryObject<Item>CONGEALED_TAINT=register("congealed_taint",()->new PT2Item(ItemNormal));
    public static final RegistryObject<Item>ENCH_FABRIC=register("enchanted_fabric",()->new PT2Item(ItemShiny).setEffect(true));
    public static final RegistryObject<Item>ENCH_SILVERWOOD=register("enchanted_silverwood",()->new PT2Item(ItemShiny).setEffect(true));
    public static final RegistryObject<Item>ENCH_WOOD=register("enchanted_wood",()->new PT2Item(ItemShiny).setEffect(true));
    public static final RegistryObject<Item>TAINT_DETECTOR=register("taint_detector",()->new ItemDetector(ItemNormal).setType(0));//          Taint Detector
    public static final RegistryObject<Item>VIS_DETECTOR=register("vis_detector",()->new ItemDetector(ItemNormal).setType(0));//          Vis Detector
    public static final RegistryObject<Item>THAUMOMETER=register("thaumometer",()->new ItemDetector(ItemNormal).setType(0));//          Thaumometer
    public static final RegistryObject<Item>FRAGMENTS=register("fragment",()->new ItemKnowledgeFragment(ItemNormal));//Knowledge Fragments
    public static final RegistryObject<Item>THEORY=register("theory",()->new ItemTheory(ItemNormal));//Theories
    public static final RegistryObject<Item>DISCOVERY=register("discovery",()->new ItemDiscovery(ItemNormal));//Discoveries
    public static final RegistryObject<Item>VOID_COMPASS=register("void_compass",()->new ItemVoidCompass(ItemNormal));//          Void Compass
    public static final RegistryObject<Item>RUNIC_AIR=register("runic_air",()->new ItemRunicEssence(ItemNormal));
    public static final RegistryObject<Item>RUNIC_DARK=register("runic_dark",()->new ItemRunicEssence(ItemNormal));
    public static final RegistryObject<Item>RUNIC_EARTH=register("runic_earth",()->new ItemRunicEssence(ItemNormal));
    public static final RegistryObject<Item>RUNIC_FIRE=register("runic_fire",()->new ItemRunicEssence(ItemNormal));
    public static final RegistryObject<Item>RUNIC_MAGIC=register("runic_magic",()->new ItemRunicEssence(ItemNormal));
    public static final RegistryObject<Item>RUNIC_WATER=register("runic_water",()->new ItemRunicEssence(ItemNormal));
    public static final RegistryObject<Item>CRYSTAL_BALL=register("crystalball",()->new ItemCrystalBall(ItemNormal));
    public static final RegistryObject<Item>DAWN_STONE=register("dawnstone",()->new ItemDawnStone(ItemNormal));
    public static final RegistryObject<Item>BOOK=register("discovery_tome",()->new ItemDiscoveryTome(ItemNormal));
    public static final RegistryObject<Item>DEBUG_ITEM=register("debug_item",()->new ItemDebug(ItemNormal));





    public static final RegistryObject<Item>GOGGLES=register("goggles",()->new ItemVisGoggles(ItemArmor));
    public static final RegistryObject<Item>THAUMIUM_HELMET=register("helmetthaumium",()->new PT2ArmorItem(PT2ArmorItem.PT2ArmorMats.THAUMIUM,EquipmentSlot.HEAD,ItemArmor));
    public static final RegistryObject<Item>THAUMIUM_CHEST=register("platethaumium",()->new PT2ArmorItem(PT2ArmorItem.PT2ArmorMats.THAUMIUM,EquipmentSlot.CHEST,ItemArmor));
    public static final RegistryObject<Item>THAUMIUM_LEGS=register("legsthaumium",()->new PT2ArmorItem(PT2ArmorItem.PT2ArmorMats.THAUMIUM,EquipmentSlot.LEGS,ItemArmor));
    public static final RegistryObject<Item>THAUMIUM_BOOTS=register("bootsthaumium",()->new PT2ArmorItem(PT2ArmorItem.PT2ArmorMats.THAUMIUM,EquipmentSlot.FEET,ItemArmor));
    public static final RegistryObject<Item>VOID_HELMET=register("helmetvoid",()->new PT2ArmorItem(PT2ArmorItem.PT2ArmorMats.VOID,EquipmentSlot.HEAD,ItemArmor));
    public static final RegistryObject<Item>VOID_PLATE=register("platevoid",()->new PT2ArmorItem(PT2ArmorItem.PT2ArmorMats.VOID,EquipmentSlot.CHEST,ItemArmor));
    public static final RegistryObject<Item>VOID_LEGS=register("legsvoid",()->new PT2ArmorItem(PT2ArmorItem.PT2ArmorMats.VOID,EquipmentSlot.LEGS,ItemArmor));
    public static final RegistryObject<Item>VOID_BOOTS=register("bootsvoid",()->new PT2ArmorItem(PT2ArmorItem.PT2ArmorMats.VOID,EquipmentSlot.FEET,ItemArmor));
    public static final RegistryObject<Item>BOOTS_STOMP=register("bootsstomp",()->new ItemStompBoots(ItemArmor));
    public static final RegistryObject<Item>BOOTS_SEVEN=register("bootsseven",()->new ItemSevenBoots(ItemArmor));
    public static final RegistryObject<Item>BOOTS_STRIDING=register("bootsstriding",()->new ItemStridingBoots(ItemArmor));
    public static final RegistryObject<Item>PORTABLE_HOLE=register("portable_hole",()->new ItemPortableHole(ItemDMG.durability(500)));
    public static final RegistryObject<Item>MASK_CRUELTY=register("mask",()->new ItemMaskCruelty(ItemArmor));

    private static<T extends Item>RegistryObject<T>register(String name,Supplier<T> item){return REGISTRY.register(name,item);}

    public static void register(IEventBus eventBus){
        if(PT2Config.SHARED.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering ITEMs");
        REGISTRY.register(eventBus);
    }
}
