package com.raven_cze.projt2.common.content;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.config.CommonCFG;
import com.raven_cze.projt2.common.content.tiles.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class PT2Tiles{
    protected static final DeferredRegister<BlockEntityType<?>>REGISTRY=DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES,ProjectT2.MODID);

    public static final RegistryObject<BlockEntityType<TileCrucible>>TILE_CRUCIBLE=register("crucible",TileCrucible::new,()->new Block[]{PT2Blocks.CRUCIBLE_BASIC.get(),PT2Blocks.CRUCIBLE_EYES.get(),PT2Blocks.CRUCIBLE_SOULS.get(),PT2Blocks.CRUCIBLE_THAUMIUM.get()});
    public static final RegistryObject<BlockEntityType<TileArcaneFurnace>>TILE_FURNACE=register("arcane_furnace",TileArcaneFurnace::new,()->new Block[]{});
    public static final RegistryObject<BlockEntityType<TileHole>>TILE_HOLE=register("hole",TileHole::new,()->new Block[]{PT2Blocks.HOLE.get()});
    public static final RegistryObject<BlockEntityType<TileConduit>>TILE_CONDUIT=register("conduit",TileConduit::new,()->new Block[]{PT2Blocks.CONDUIT.get()});
    public static final RegistryObject<BlockEntityType<TileTotem>>TOTEM=register("totem",TileTotem::new,()->new Block[]{PT2Blocks.TOTEM_GOOD.get(), PT2Blocks.TOTEM_BAD.get()});

    public static final RegistryObject<BlockEntityType<TileVoidCube>>TILE_VOID_CUBE=register("void_cube",TileVoidCube::new,()->new Block[]{PT2Blocks.VOID_CUBE.get()});
    public static final RegistryObject<BlockEntityType<TileVoidLock>>TILE_VOID_LOCK=register("void_lock",TileVoidLock::new,()->new Block[]{PT2Blocks.VOID_LOCK.get()});
    public static final RegistryObject<BlockEntityType<TileVoidKeyhole>>TILE_VOID_KEYHOLE=register("void_keyhole",TileVoidKeyhole::new,()->new Block[]{PT2Blocks.VOID_KEYHOLE.get()});
    public static final RegistryObject<BlockEntityType<TileNitor>>TILE_NITOR=register("nitor",TileNitor::new,()->new Block[]{PT2Blocks.NITOR.get()});
    public static final RegistryObject<BlockEntityType<TileVoidInterface>>TILE_VOID_INTERFACE=register("void_interface",TileVoidInterface::new,()->new Block[]{PT2Blocks.VOID_INTERFACE.get()});
    public static final RegistryObject<BlockEntityType<TileVoidChest>>TILE_VOID_CHEST=register("void_chest",TileVoidChest::new,()->new Block[]{PT2Blocks.VOID_CHEST.get()});
    public static final RegistryObject<BlockEntityType<TileCrystalOre>>TILE_CRYSTAL=register("crystal_ore",TileCrystalOre::new,()->new Block[]{PT2Blocks.CRYSTAL_ORE_AIR.get(),PT2Blocks.CRYSTAL_ORE_VIS.get(),PT2Blocks.CRYSTAL_ORE_EARTH.get(),PT2Blocks.CRYSTAL_ORE_FIRE.get(),PT2Blocks.CRYSTAL_ORE_TAINT.get(),PT2Blocks.CRYSTAL_ORE_WATER.get()});
    public static final RegistryObject<BlockEntityType<TileSeal>>TILE_SEAL=register("seal",TileSeal::new,()->new Block[]{PT2Blocks.SEAL.get()});
    public static final RegistryObject<BlockEntityType<TileGenerator>>TILE_GENERATOR=register("generator",TileGenerator::new,()->new Block[]{PT2Blocks.GENERATOR.get()});

    @SuppressWarnings("DataFlowIssue")
    private static<T extends BlockEntity>RegistryObject<BlockEntityType<T>>register(String name, BlockEntityType.BlockEntitySupplier<T>supplier,Supplier<Block[]>blocks){
        return REGISTRY.register(name,()->BlockEntityType.Builder.of(supplier,blocks.get()).build(null));
    }
    public static void register(IEventBus eventBus){
        if(CommonCFG.debugMode.get())ProjectT2.LOGGER.debug(ProjectT2.MARKERS.REGISTRY,"Registering TILEs");
        REGISTRY.register(eventBus);
    }
}
