package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.items.ItemPortableHole;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class TileHole extends BlockEntity{
    public static BlockState oldState;
    public static CompoundTag tileCompound;
    public static short countdown      = 0;
    public static short countdownMax   = 120;
    public static byte count           = 0;
    public static Direction direction  = null;
    public TileHole(BlockPos pos,BlockState state){
        super(PT2Tiles.TILE_HOLE.get(),pos,state);
        countdownMax=10;
        countdown=0;
        count=1;
        tileCompound=new CompoundTag();
    }

    public TileHole(BlockPos pos,BlockState state,short max,byte blockCount,BlockState altState){
        this(pos,state);
        countdownMax=max;
        countdown=0;
        count=!(blockCount>1)?1:blockCount;
        oldState=altState;
        if(this.level!=null && this.level.getBlockEntity(pos)!=null)
            tileCompound=this.level.getBlockEntity(pos).getTileData();
        //  SPACE
        ProjectT2.LOGGER.debug("Creating TileHole with: Countdown Max: {} | Countdown: {} | Count: {} | NBTTag: {} | OldState: {}",countdownMax,countdown,count,tileCompound,oldState);
    }

    public static void tick(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @NotNull BlockEntity ent){
        countdown=(short)(countdown+1);
        if(countdown>=countdownMax){
            level.removeBlockEntity(pos);
            recreate(level,pos,ent.getTileData());
        }
        if(level.getServer()!=null && !level.isClientSide()){
            if(countdown==0 && count>1 && direction!=null){
                int a;
                switch(direction.getAxis()){
                    case Y->{
                        for(a=0;a<9;){
                            if(a/3!=1 || a%3!=1)
                                ItemPortableHole.createHole(level,pos.offset(-1+a/3,0,-1+a%3),Direction.DOWN,(byte)2,countdownMax);
                            a++;
                        }
                    }
                    case Z->{
                        for(a=0;a<9;){
                            if(a/3!=1 || a%3!=1)
                                ItemPortableHole.createHole(level,pos.offset(-1+a/3,-1+a%3,0),Direction.NORTH,(byte)2,countdownMax);
                            a++;
                        }
                    }
                    case X->{
                        for(a=0;a<9;){
                            if(a/3!=1 || a%3!=1)
                                ItemPortableHole.createHole(level,pos.offset(0,-1+a/3,-1+a%3),Direction.EAST,(byte)2,countdownMax);
                            a++;
                        }
                    }
                }//SWITCH
                if(!ItemPortableHole.createHole(level,pos,direction,(byte)(count-1),countdownMax))count=0;
            }// COUNTDOWN
        }//if level.isClientSide() else
    }

    public static void surroundWithSparkles(){
        //ProjectT2.LOGGER.warn("Now I am not doing that!");
    }

    public static void recreate(Level level,BlockPos pos,CompoundTag nbt){
        BlockState state;
        if(nbt.contains("tileEntity"))
            recreateTileEntity(level,pos,nbt);
        else{
            if(level!=null && !level.isClientSide()){
                if(oldState!=null)state=oldState.getBlock().defaultBlockState();
                else{
                    state=Blocks.STONE.defaultBlockState();
                    ProjectT2.LOGGER.error("TileHole at {} has invalid previous BlockState. Defaulting to to minecraft:stone!",pos);
                }
                if(!level.isClientSide())System.out.println(oldState);
                level.setBlockAndUpdate(pos,state);
                level.removeBlockEntity(pos);
            }
        }
    }

    private static void recreateTileEntity(Level level,BlockPos pos,CompoundTag nbt){
        if(level!=null)
            if(nbt != null && level.getBlockEntity(pos) != null) {
                level.setBlockAndUpdate(pos,oldState);
                //this.i.c(j,k,l,oldMeta) this.level._method_IDFK(x,y,z,state)
                nbt.putIntArray("pos",new int[]{pos.getX(),pos.getY(),pos.getZ()});
                //Objects.requireNonNull(this.level.getBlockEntity(this.worldPosition)).getTileData().merge(this.tileCompound);

            }
    }

    @Override
    public void load(@NotNull CompoundTag compound){
        super.load(compound);
        countdown=compound.getShort("Countdown");
        countdownMax=compound.getShort("CountdownMax");
        count=compound.getByte("Count");

        oldState=Block.byItem(new ItemStack((ItemLike)this,1,tileCompound).getItem()).defaultBlockState();
        tileCompound=(CompoundTag)compound.get("tileCompound");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag compound){
        super.saveAdditional(compound);
        compound.putShort("Countdown", countdown);
        compound.putShort("CountdownMax", countdownMax);
        compound.putByte("Count", count);

        //  oldState
        if(tileCompound!=null)compound.put("tileCompound", tileCompound);
    }
}
