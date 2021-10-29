package raven_cze.projt2.proxies;

import net.minecraft.item.ItemBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.*;

public interface IProxy {
	void preInit(FMLPreInitializationEvent paramFMLPreInitializationEvent);
	void init(FMLInitializationEvent paramFMLInitializationEvent);
	void postInit(FMLPostInitializationEvent paramFMLPostInitializationEvent);
	World getClientWorld();
	boolean getSingleplayer();
	World getWorld(int paramInt);
	boolean isShiftKeyDown();
	void registerModel(ItemBlock paramItemBlock);
	void checkInterModComs(FMLInterModComms.IMCEvent paramIMCEvent);
}
