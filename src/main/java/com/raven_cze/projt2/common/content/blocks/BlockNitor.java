package com.raven_cze.projt2.common.content.blocks;

import com.raven_cze.projt2.common.content.tiles.TileNitor;
import com.raven_cze.projt2.common.content.blocks.references.PT2Block;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.util.ForgeSoundType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;


public class BlockNitor extends PT2Block implements EntityBlock{
    public BlockNitor(Properties properties){
        super(properties);
        Supplier<SoundEvent>SND=()->SoundEvents.WOOL_BREAK;
        properties.strength(0.1F);
        properties.sound(new ForgeSoundType(1.0F,1.0F,SND,SND,SND,SND,SND));
        properties.dynamicShape();
        properties.lightLevel( (state)->state.hasBlockEntity()?15:0 );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pPos,@NotNull BlockState pState){
        return new TileNitor(pPos,pState);
    }

    //hasTileEntity(BlockState) -[>ripped from TC-1.12.2-6.1.BETA26<]

    //getMapColor(BlockState,BlockAccess,BlockPos) -[>ripped from TC-1.12.2-6.1.BETA26<]

    //getFaceShape(BlockAccess,BlockState,BlockPos,Direction) -[>ripped from TC-1.12.2-6.1.BETA26<]

    @Override   //AxisAlignedBB func_185496_a(BlockState,BlockAccess,BlockPos)
    public @NotNull VoxelShape getShape(@NotNull BlockState pState,@NotNull BlockGetter pLevel,@NotNull BlockPos pPos,@NotNull CollisionContext pContext){
        return Block.box(0.33D,0.33D,0.33D,0.66D,0.66D,0.66D);
    }

    @Override   //EnumBlockRenderType getRenderType(BlockState)-[>ripped from TC-1.12.2-6.1.BETA26<]
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState){
        return RenderShape.INVISIBLE;
    }
}
