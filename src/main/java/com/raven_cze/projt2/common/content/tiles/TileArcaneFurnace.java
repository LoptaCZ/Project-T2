package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.client.gui.container.ArcaneFurnaceMenu;

import com.raven_cze.projt2.common.content.PT2Tiles;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileArcaneFurnace extends BlockEntity implements MenuProvider{
	public boolean boost;
	public float bellows;

	private final ItemStackHandler handler=new ItemStackHandler(6){
		@Override
		protected void onContentsChanged(int slot){setChanged();}
	};
	public boolean isBurning;
    private LazyOptional<IItemHandler>lazyHandler=LazyOptional.empty();
	protected final ContainerData data;
	private int progress;
	private int maxProgress;

	public TileArcaneFurnace(BlockPos pos,BlockState state) {
		super(PT2Tiles.TILE_FURNACE.get(),pos,state);

		this.data=new ContainerData(){
			@Override
			public int get(int pIndex){
				return switch (pIndex) {
					case 0 -> TileArcaneFurnace.this.progress;
					case 1 -> TileArcaneFurnace.this.maxProgress;
					default -> 0;
				};

			}
			@Override
			public void set(int pIndex,int pValue){
				switch(pIndex){
					case 0->TileArcaneFurnace.this.progress=pValue;
					case 1->TileArcaneFurnace.this.maxProgress=pValue;
				}
			}
			@Override
			public int getCount(){
				return 2;
			}
		};
	}

	@Override
	public @NotNull Component getDisplayName(){return new TextComponent(I18n.get("block.projt2.furnace"));}

	@Nullable
	@Override
	public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory, @NotNull Player pPlayer) {
		return new ArcaneFurnaceMenu(pContainerId,pInventory,this,this.data);
	}
}
