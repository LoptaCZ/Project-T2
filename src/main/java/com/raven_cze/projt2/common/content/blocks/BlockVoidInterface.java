package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.blocks.apparatus.BlockApparatusMetal;
import com.raven_cze.projt2.common.content.tiles.TileVoidChest;
import com.raven_cze.projt2.common.content.tiles.TileVoidInterface;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BlockVoidInterface extends BlockApparatusMetal implements EntityBlock{
    public static final IntegerProperty FREQUENCY=IntegerProperty.create("void_frequency",0,6);
    public BlockVoidInterface(Properties properties){super(properties);}

    @Nullable
    @Override
    public BlockState getStateForPlacement(@NotNull BlockPlaceContext context){
        Block block=context.getLevel().getBlockState(context.getClickedPos()).getBlock();
        if(context.getClickedFace().equals(Direction.UP))
            block=context.getLevel().getBlockState(context.getClickedPos().below()).getBlock();

        if(block instanceof BlockVoidChest){
            TileVoidChest chest=(TileVoidChest)context.getLevel().getBlockEntity(context.getClickedPos().below());
            if(chest!=null){chest.hasInterface=true;}
            context.getLevel().playSound(null,context.getClickedPos(),PT2Sounds.Attach.get(),SoundSource.BLOCKS,1.0F,1.0F);
            return this.defaultBlockState();
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos,@NotNull BlockState state){
        TileVoidInterface vInterface=new TileVoidInterface(pos,state);
        vInterface.network=1;
        return vInterface;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block,BlockState>pBuilder){
        pBuilder.add(FREQUENCY);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape top=Shapes.box(0.25F,0.435F,0.25F,0.75F,0.75F,0.75F);
        VoxelShape bottom=Shapes.box(0.0F,0.0F,0.0F,1.0F,0.435F,1.0F);

        return Shapes.or(bottom,top);
    }
}
