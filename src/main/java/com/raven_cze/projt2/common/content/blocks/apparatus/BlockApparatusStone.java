package com.raven_cze.projt2.common.content.blocks.apparatus;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.tiles.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class BlockApparatusStone extends BlockApparatus implements EntityBlock{
	public static final DirectionProperty DIRECTION=DirectionProperty.create("direction");
	protected Direction orientation;
	public BlockApparatusStone(BlockBehaviour.Properties properties){
		super(properties);

		registerDefaultState(this.getStateDefinition().any().setValue(DIRECTION,Direction.NORTH));
		this.orientation=Direction.NORTH;
	}
	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block,BlockState>pBuilder){pBuilder.add(DIRECTION);}
	//	All Tile Entities | Block Entities
	//	0	Seal				[ TileSeal ]
	//	1	Infuser				[ TileInfuser ]
	//	2	Dark Infuser		[ TileInfuserDark ]
	//	3	Enchanter			[ TileEnchanter ]
	//	4	Researcher			[ TileResearcher ]
	//	5	Eldritch Stone		[ XXX ]
	//	6	Darkness Generator	[ TileDarknessGenerator ]
	//	7	Enchanter Advanced	[ TileEnchanterAdvanced ]
	//	8	Urn					[ TileUrn ]

	//	Enchanter			[ TileEnchanter ]

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext ctx){
		if(ctx.getLevel().getBlockState(ctx.getClickedPos().below()).getBlock().getRegistryName().getPath().equals("arcane_seal"))return null;

		return this.defaultBlockState().setValue(DIRECTION,ctx.getClickedFace());
	}

	@Override
	public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context){
		VoxelShape shape=Shapes.create(0.0F,0.0F,0.0F,1.0F,1.0F,1.0F);
		if(state.getBlock().getRegistryName()!=null){
			if(state.getBlock().getRegistryName().getPath().equals("arcane_seal")){
				switch(state.getValue(DIRECTION)){
					case UP -> shape = Shapes.create(0.25F, 0.0F, 0.25F, 0.75F, 0.0625, 0.75F);
					case DOWN -> shape = Shapes.create(0.25F, 0.9375F, 0.25F, 0.75F, 1.0F, 0.75F);
					case NORTH -> shape = Shapes.create(0.25F, 0.25F, 0.9375F, 0.75F, 0.75F, 1.0F);
					case EAST -> shape = Shapes.create(0.0F, 0.25F, 0.25F, 0.0625F, 0.75F, 0.75F);
					case WEST -> shape = Shapes.create(0.9375F, 0.25F, 0.25F, 1.0F, 0.75F, 0.75F);
					case SOUTH -> shape = Shapes.create(0.25F, 0.25F, 0.0F, 0.75F, 0.75F, 0.0625F);
				}
			}
		}
		return shape;
	}
	@Override
	public@NotNull VoxelShape getCollisionShape(@NotNull BlockState state,@NotNull BlockGetter level,@NotNull BlockPos pos,@NotNull CollisionContext context){return getShape(state,level,pos,context);}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){
		if(this.getRegistryName()!=null){
			String name=this.getRegistryName().getPath();
			BlockEntity tile;
			switch(name){
				case"arcane_seal"->{
					tile=new TileSeal(pos,state);
					TileSeal seal=(TileSeal)tile;
					seal.orientation=this.defaultBlockState().getValue(DIRECTION);
					return tile;
				}
				case"infuser"->{
					tile=new TileInfuser(pos,state);
					return tile;
				}
				case"infuser_dark"->{
					tile=new TileInfuserDark(pos,state);
					return tile;
				}
				case"enchanter"->{
					tile=new TileEnchanter(pos,state);
					return tile;
				}
				case"researcher"->{
					tile=new TileResearcher(pos,state);
					return tile;
				}
				case"darkness_gen"->{
					tile=new TileDarknessGenerator(pos,state);
					return tile;
				}
				case"enchanter_adv"->{
					tile=new TileEnchanterAdvanced(pos,state);
					return tile;
				}
				case"urn"->{
					tile=new TileUrn(pos,state);
					return tile;
				}
			}//switch
		}//if Valid RegistryName
		return null;
	}

	@Override
	public @NotNull InteractionResult use(@NotNull BlockState state,@NotNull Level level,@NotNull BlockPos pos,@NotNull Player player,@NotNull InteractionHand hand,@NotNull BlockHitResult hit){
		BlockEntity te=level.getBlockEntity(pos);
		if(te instanceof TileSeal && ((TileSeal) te).runes[0] == 0 && ((TileSeal) te).runes[1] == 1){
			((TileSeal)te).portalWindow++;
			level.playSound(player,pos,PT2Sounds.PortalClose.get(),SoundSource.BLOCKS,0.2F,1.0F+level.random.nextFloat()*0.2F);
			return InteractionResult.CONSUME;
		}

		if(te instanceof TileUrn urn){
			boolean filled=false;
			ItemStack item=player.getMainHandItem();
			ItemStack item2;
			if(item.is(Items.BUCKET)){
				item.shrink(1);
				item2=new ItemStack(Items.WATER_BUCKET);
				ItemEntity itemEnt=new ItemEntity(level,pos.getX(),pos.getY(),pos.getZ(),item2);
				level.addFreshEntity(itemEnt);
				filled=true;
			}else if(item.is(Items.GLASS_BOTTLE)){
				item.shrink(1);
				item2=new ItemStack(Items.POTION);
				ItemEntity itemEnt=new ItemEntity(level,pos.getX(),pos.getY(),pos.getZ(),item2);
				level.addFreshEntity(itemEnt);
				filled=true;
			}else{
				//TODO Filling Other fluid containers with water
			}
			if(filled){
				//	Add Bad Vibes to local Chunk
				//	ac.badVibes=(short)(ac.bagVibes+1);
				level.playSound(player,pos,PT2Sounds.Place.get(),SoundSource.BLOCKS,1.0F,1.0F);
			}
		}
		return InteractionResult.FAIL;
	}
}
