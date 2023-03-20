package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.config.CommonCFG;
import com.raven_cze.projt2.common.content.blocks.*;
import com.raven_cze.projt2.common.content.blocks.apparatus.BlockApparatusMetal;
import com.raven_cze.projt2.common.content.blocks.apparatus.BlockApparatusStone;
import com.raven_cze.projt2.common.content.blocks.references.BlockCustomWood;
import com.raven_cze.projt2.common.content.blocks.references.PT2Leaves;
import com.raven_cze.projt2.common.content.blocks.references.PT2SaplingBlock;
import com.raven_cze.projt2.common.content.world.feature.tree.GreatwoodGrower;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.function.Supplier;

public class PT2Blocks{
    protected static final DeferredRegister<Block>REGISTRY=DeferredRegister.create(ForgeRegistries.BLOCKS, ProjectT2.MODID);

    public static Boolean no(BlockState state, BlockGetter getter, BlockPos pos){return false;}
    public static Boolean yes(BlockState ignoredState, BlockGetter ignoredGetter, BlockPos ignoredPos){return true;}
    public static Boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entity){
        return entity == EntityType.OCELOT || entity == EntityType.PARROT;
    }

    protected static final BlockBehaviour.Properties PROPERTIES=net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.DIRT).isViewBlocking(PT2Blocks::yes);
    protected static final BlockBehaviour.Properties FRAGILE=net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.GLASS).isViewBlocking(PT2Blocks::no);
    protected static final BlockBehaviour.Properties METAL=net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.METAL).isViewBlocking(PT2Blocks::yes);
    protected static final BlockBehaviour.Properties STONE=net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.STONE).isViewBlocking(PT2Blocks::yes);
    protected static final BlockBehaviour.Properties WOOD=net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD).isViewBlocking(PT2Blocks::yes);
    protected static final BlockBehaviour.Properties WOOD_LOG=net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD).isViewBlocking(PT2Blocks::yes);
    protected static final BlockBehaviour.Properties WOOD_LOG_STRIPPED=net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD).isViewBlocking(PT2Blocks::yes);
    protected static final BlockBehaviour.Properties LEAVES=net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion().isValidSpawn(PT2Blocks::ocelotOrParrot).isSuffocating(PT2Blocks::no).isViewBlocking(PT2Blocks::no);
    protected static final BlockBehaviour.Properties PLANT=net.minecraft.world.level.block.state.BlockBehaviour.Properties.of(Material.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).isViewBlocking(PT2Blocks::no);
    protected static final Item.Properties ItemNormal=new Item.Properties().tab(PT2Items.TABULATOR).rarity(Rarity.COMMON).stacksTo(64);







    public static RegistryObject<Block>CRYSTAL_ORE_VIS=register("crystal_ore_vis",()->new BlockCrystalOre(FRAGILE.lightLevel((light)->5).noOcclusion().isViewBlocking(PT2Blocks::no)).setType("vis"));
    public static RegistryObject<Block>CRYSTAL_ORE_WATER=register("crystal_ore_water",()->new BlockCrystalOre(FRAGILE.lightLevel((light)->5).noOcclusion().isViewBlocking(PT2Blocks::no)).setType("water"));
    public static RegistryObject<Block>CRYSTAL_ORE_EARTH=register("crystal_ore_earth",()->new BlockCrystalOre(FRAGILE.lightLevel((light)->5).noOcclusion().isViewBlocking(PT2Blocks::no)).setType("earth"));
    public static RegistryObject<Block>CRYSTAL_ORE_FIRE=register("crystal_ore_fire",()->new BlockCrystalOre(FRAGILE.lightLevel((light)->5).noOcclusion().isViewBlocking(PT2Blocks::no)).setType("fire"));
    public static RegistryObject<Block>CRYSTAL_ORE_TAINT=register("crystal_ore_taint",()->new BlockCrystalOre(FRAGILE.lightLevel((light)->5).noOcclusion().isViewBlocking(PT2Blocks::no)).setType("taint"));
    public static RegistryObject<Block>CRYSTAL_ORE_AIR=register("crystal_ore_air",()->new BlockCrystalOre(FRAGILE.lightLevel((light)->5).noOcclusion().isViewBlocking(PT2Blocks::no)).setType("air"));
    public static RegistryObject<Block>CINNABAR=register("cinnabar_ore",()->new Block(STONE));
    public static RegistryObject<Block>GLOW_TAINTWEED=register("taintweed_glow",()->new Block(PLANT));
    public static RegistryObject<Block>GREATWOOD_LEAVES=register("greatwood_leaves",()->new PT2Leaves(LEAVES.color(MaterialColor.COLOR_GREEN)));
    public static RegistryObject<Block>GREATWOOD_LOG=register("greatwood_log",()->new BlockCustomWood(WOOD_LOG));
    public static RegistryObject<Block>STRIPPED_GREATWOOD_LOG=register("stripped_greatwood_log",()->new BlockCustomWood(WOOD_LOG_STRIPPED));
    public static RegistryObject<Block>GREATWOOD_SAPLING=register("greatwood_sapling",()->new PT2SaplingBlock(new GreatwoodGrower(),PLANT));
    public static RegistryObject<Block>NITOR=register("nitor",()->new BlockNitor(PROPERTIES.noCollission()));
    public static RegistryObject<Block>PETRIFIED_LOG=register("petrified_log",()->new BlockCustomWood(WOOD_LOG));
    public static RegistryObject<Block>STRIPPED_PETRIFIED_LOG=register("stripped_petrified_log",()->new BlockCustomWood(WOOD_LOG_STRIPPED));
    public static RegistryObject<Block>SILVERWOOD_LEAVES=register("silverwood_leaves",()->new PT2Leaves(LEAVES.color(MaterialColor.COLOR_GREEN)));
    public static RegistryObject<Block>SILVERWOOD_LOG=register("silverwood_log",()->new BlockCustomWood(WOOD_LOG));
    public static RegistryObject<Block>STRIPPED_SILVERWOOD_LOG=register("stripped_silverwood_log",()->new BlockCustomWood(WOOD_LOG_STRIPPED));
    public static RegistryObject<Block>TAINT_LEAVES=register("taint_leaves",()->new PT2Leaves(LEAVES.color(MaterialColor.COLOR_PURPLE)));
    public static RegistryObject<Block>TAINT_LOG=register("taint_log",()->new BlockCustomWood(WOOD_LOG));
    public static RegistryObject<Block>STRIPPED_TAINT_LOG=register("stripped_taint_log",()->new BlockCustomWood(WOOD_LOG_STRIPPED));
    public static RegistryObject<Block>CRUCIBLE_BASIC=register("crucible_basic",()->new BlockApparatusMetal(METAL));
    public static RegistryObject<Block>CRUCIBLE_EYES=register("crucible_eyes",()->new BlockApparatusMetal(METAL));
    public static RegistryObject<Block>CRUCIBLE_SOULS=register("crucible_souls",()->new BlockApparatusMetal(METAL));
    public static RegistryObject<Block>CRUCIBLE_THAUMIUM=register("crucible_thaumium",()->new BlockApparatusMetal(METAL));
    public static RegistryObject<Block>CONDUIT=register("conduit",()->new BlockConduit(FRAGILE));
    public static RegistryObject<Block>FURNACE=register("furnace",()->new BlockApparatusMetal(METAL));
    public static RegistryObject<Block>GENERATOR=register("generator",()->new BlockApparatusMetal(METAL));
    public static RegistryObject<Block>TOTEM_BAD=register("totem_taint",()->new BlockTotem(WOOD,true));
    public static RegistryObject<Block>TOTEM_GOOD=register("totem_vis",()->new BlockTotem(WOOD,false));
    public static RegistryObject<Block>ELDRITCH=register("eldritch",()->new BlockEldritch(STONE,false));
    public static RegistryObject<Block>ELDRITCH_MONOLITH=registerNoItem("eldritch_monolith",()->new BlockEldritch(STONE,true));
    public static RegistryObject<Block>VOID_CHEST=register("void_chest",()->new BlockApparatusMetal(METAL));
    public static RegistryObject<Block>VOID_INTERFACE=register("void_interface",()->new BlockVoidInterface(METAL));
    public static RegistryObject<Block>SEAL=register("arcane_seal",()->new BlockApparatusStone(METAL));
    public static RegistryObject<Block>VOID_LOCK=registerNoItem("void_lock",()->new BlockVoidLock(STONE));
    public static RegistryObject<Block>VOID_KEYHOLE=registerNoItem("void_key",()->new BlockVoidKeyhole(STONE));
    public static RegistryObject<Block>HOLE=registerNoItem("hole",()->new BlockHole(PROPERTIES));
    public static RegistryObject<Block>VOID_CUBE=registerNoItem("void_cube",()->new BlockVoidCube(PROPERTIES));

    private static<T extends Block> RegistryObject<T>register(String name,Supplier<T> block){
        RegistryObject<T>toReturn=REGISTRY.register(name,block);
        registerItem(name,toReturn);
        return toReturn;
    }
    private static<T extends Block>RegistryObject<T>registerNoItem(String name,Supplier<T>block){return REGISTRY.register(name,block);}
    private static<T extends Block>void registerItem(String name,RegistryObject<T>block){PT2Items.REGISTRY.register(name,()->new BlockItem(block.get(),ItemNormal));}

    public static void register(IEventBus eventBus){
        if(CommonCFG.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering BLOCKs");
        REGISTRY.register(eventBus);
    }
    @SubscribeEvent
    public static void blockColorLoad(ColorHandlerEvent.Block event){PT2Leaves.blockColorLoad(event);}
    @SubscribeEvent
    public static void itemColorLoad(ColorHandlerEvent.Item event){PT2Leaves.itemColorLoad(event);}
    @OnlyIn(Dist.CLIENT)
    public static void prepareSpecialRender(){
        Collection<RegistryObject<Block>>blocks=REGISTRY.getEntries();
        for(RegistryObject<Block>blockRegistryObject:blocks){
            Block block=blockRegistryObject.get();
            if(block.getRegistryName()!=null){
                String regName=block.getRegistryName().getPath();
                     if(regName.contains("leaves"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("sapling"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("void"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("crystal"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.translucent());
                else if(regName.contains("hole"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("furnace"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("conduit"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("taintweed"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("quicksilver"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("shimmerleaf"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("seal"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.cutout());
                else if(regName.contains("generator"))ItemBlockRenderTypes.setRenderLayer(block,RenderType.translucent());
            }
        }
    }
}
