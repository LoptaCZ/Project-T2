package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.tiles.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockApparatusMetal extends BlockApparatus implements EntityBlock{
	private static final VoxelShape INSIDE = box(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
	protected static final VoxelShape SHAPE = Shapes.join(Shapes.block(), Shapes.or(box(0.0D, 0.0D, 4.0D, 16.0D, 3.0D, 12.0D), box(4.0D, 0.0D, 0.0D, 12.0D, 3.0D, 16.0D), box(2.0D, 0.0D, 2.0D, 14.0D, 3.0D, 14.0D), INSIDE), BooleanOp.ONLY_FIRST);

	public static final BooleanProperty ACTIVE=BooleanProperty.create("active");
	public static final IntegerProperty FREQUENCY=IntegerProperty.create("void_frequency",0,6);
	public BlockApparatusMetal(Properties properties){
		super(properties);

		registerDefaultState(getStateDefinition().any().setValue(ACTIVE,false));//,""
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState>b){b.add(ACTIVE,FREQUENCY);}
	//	All Tile Entities | Block Entities
	//	0	Crucible			[ TileCrucible ]
	//	1	Crucible of Eyes	[ TileCrucible ]
	//	2	Crucible Thaumium	[ TileCrucible ]
	//	3	Crucible of Souls	[ TileCrucible ]
	//	4	Arcane Furnace		[ TileArcaneFurnace ]
	//	5	Generator			[ TileGenerator ]
	//	6	Crystalizer			[ TileCrystalizer ]
	//	7	Bore				[ TileBore ]
	//	8	Void Chest			[ TileVoidChest ]
	//	9	Void Interface		[ TileVoidInterface ]
	//	10	Conduit Tank		[ TileConduitTank ]
	//	11	Soul Brazier		[ TileSoulBrazier ]

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){
		BlockEntity tile=null;
		if(this.getRegistryName()!=null){
			String name=this.getRegistryName().getPath();
			switch(name){
				case"crucible_basic"->{
					tile=new TileCrucible(pos,state);
					((TileCrucible)tile).setTier((short)1);
				}
				case"crucible_eyes"->{
					tile=new TileCrucible(pos,state);
					((TileCrucible)tile).setTier((short)2);
				}
				case"crucible_souls"->{
					tile=new TileCrucible(pos,state);
					((TileCrucible)tile).setTier((short)4);
				}
				case"crucible_thaumium"->{
					tile=new TileCrucible(pos,state);
					((TileCrucible)tile).setTier((short)3);
				}
				case"furnace"->tile=new TileArcaneFurnace(pos,state);
				case"generator"->tile=new TileGenerator(pos,state);
				case"crystalizer"->tile=new TileCrystalizer(pos,state);
				case"bore"->tile=new TileBore(pos,state);
				case"void_chest"->tile=new TileVoidChest(pos,state);
				case"void_interface"->{
					tile=new TileVoidInterface(pos,state);
					((TileVoidInterface)tile).network=1;
				}
				case"conduit_tank"->tile=new TileConduitTank(pos,state);
				case"brazier"->tile=new TileSoulBrazier(pos,state);
			}
		}
		return tile;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context){
		Block block=context.getLevel().getBlockState(context.getClickedPos()).getBlock();
		if(context.getClickedFace().equals(Direction.UP))
			block=context.getLevel().getBlockState(context.getClickedPos().below()).getBlock();

		if(block instanceof BlockApparatusMetal bam){
			if(bam.defaultBlockState().getBlock().getRegistryName()!=null && bam.defaultBlockState().getBlock().getRegistryName().equals(new ResourceLocation("projt2","void_chest"))) {
				TileVoidChest chest=(TileVoidChest) context.getLevel().getBlockEntity(context.getClickedPos().below());
				if(chest!=null){chest.hasInterface = true;}
				context.getLevel().playSound(null, context.getClickedPos(), PT2Sounds.Attach.get(), SoundSource.BLOCKS, 1.0F, 1.0F);
				return this.defaultBlockState();
			}else{return null;}
		}
		return super.getStateForPlacement(context);
	}
	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state,@NotNull BlockGetter getter,@NotNull BlockPos pos,@NotNull CollisionContext context){
		if(state.getBlock().getRegistryName()!=null)
			switch(state.getBlock().getRegistryName().getPath()){
				case"crucible_basic","crucible_eyes","crucible_thaumium"->{return SHAPE;}
				case"crucible_souls"->{return Shapes.box(0.0F,0.0F,0.0F,1.0F,1.0F,1.0F);}
				case"void_interface"->{return Shapes.or(Shapes.box(0.25F,0.435F,0.25F,0.75F,0.75F,0.75F),Shapes.box(0.0F,0.0F,0.0F,1.0F,0.435F,1.0F));}
			//	PLACEHOLDER
			}

		return super.getShape(state,getter,pos,context);
	}

	@SuppressWarnings("deprecation")
	@Override
	public @NotNull VoxelShape getInteractionShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos){
		return super.getInteractionShape(state,level,pos);
	}
}
