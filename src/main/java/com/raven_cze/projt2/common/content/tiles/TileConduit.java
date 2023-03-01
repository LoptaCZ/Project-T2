package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.api.DirectionalPosition;
import com.raven_cze.projt2.api.IConnection;
import com.raven_cze.projt2.api.ITickableBlockEntity;
import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.blocks.BlockConduit;
import com.raven_cze.projt2.common.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

public class TileConduit extends BlockEntity implements IConnection,ITickableBlockEntity{
    public Direction facing;
    public float pureVis;
    public float taintedVis;
    public float maxVis;
    float fillAmount=4.0F;
    public float displayPure;
    public float displayTaint;
    public float displayPurePrev;
    public float displayTaintPrev;
    public int visSuction;
    public int taintSuction;

    private final boolean[]disconnectedSides;
    @Nullable
    protected List<Connection>connectionCache;

    public TileConduit(BlockPos pos,BlockState state){
        super(PT2Tiles.TILE_CONDUIT.get(),pos,state);
        this.visSuction=0;
        this.taintSuction=0;
        this.pureVis=0.0F;
        this.taintedVis=0.0F;
        this.maxVis=4.0F;

        this.disconnectedSides=new boolean[Direction.values().length];
    }

    @Override
    public void tick(){
        if(Objects.requireNonNull(this.level).isClientSide())return;
        if(this.displayPurePrev!=this.displayPure || this.displayTaintPrev!=this.displayTaint){
            this.level.setBlockAndUpdate(this.worldPosition,this.getBlockState());
            this.displayPurePrev=this.displayPure;
            this.displayTaintPrev=this.displayTaint;
        }
        calculateSuction();
        if(getSuction(null)>0)equalizeWithNeighbours();
        this.displayPure=Math.max(this.displayPure,Utils.Math.clamp(this.pureVis,0.0F,this.maxVis));
        this.displayTaint=Math.max(this.displayTaint,Utils.Math.clamp(this.taintedVis,0.0F,this.maxVis));
    }

    protected void calculateSuction(){
        setSuction(0);
        for(Direction dir:Direction.values()){
            BlockEntity te=getConnectableTile(Objects.requireNonNull(this.level),dir);
            if(te instanceof IConnection ic){
                if(getVisSuction(null)<ic.getVisSuction(dir)-1)setVisSuction(ic.getVisSuction(dir)-1);
                if(getTaintSuction(null)<ic.getTaintSuction(dir)-1)setTaintSuction(ic.getTaintSuction(dir)-1);
            }
        }
    }
    protected void equalizeWithNeighbours(){
        for(Direction dir:Direction.values()){
            if(isConnectable(dir)){
                BlockEntity te=getConnectableTile(Objects.requireNonNull(this.level),dir);
                if(te!=null){
                    IConnection ent=(IConnection)te;
                    if(this.pureVis+this.taintedVis<this.maxVis){
                        float[]results;
                        float qq=Math.min( (ent.getPureVis()+ ent.getTaintedVis())/4.0F,this.fillAmount);
                        results=ent.subtractVis(Math.min(qq,this.maxVis-this.pureVis+this.taintedVis));
                        if(getVisSuction(null)>ent.getVisSuction(this.facing))this.pureVis+=results[0];
                        else ent.setPureVis(results[0]+ent.getPureVis());
                        if(getTaintSuction(null)>ent.getTaintSuction(this.facing))this.pureVis+=results[0];
                        else ent.setTaintedVis(results[0]+ent.getTaintedVis());
                    }
                }//TE NOT NULL
            }//IS_CONNECTABLE
        }//FOR
        this.pureVis=Utils.Math.clamp(this.pureVis,0.0F,this.maxVis);
        this.taintedVis=Utils.Math.clamp(this.taintedVis,0.0F,this.maxVis);
    }//EQUALIZE
    @Override
    public void load(@NotNull CompoundTag compound){
        super.load(compound);
        this.pureVis=compound.getFloat("pureVis");
        this.taintedVis=compound.getFloat("pureVis");
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putFloat("pureVis",this.pureVis);
        compound.putFloat("taintedVis",this.taintedVis);
    }
    @Override
    public boolean isConnectable(Direction direction){return true;}
    @Override
    public boolean isVisSource(){return false;}
    @Override
    public boolean isVisConduit(){return true;}
    @Override
    public float[] subtractVis(float amount){
        float pureAmount=amount/2.0F;
        float taintAmount=amount/2.0F;
        float[]result={0.0F,0.0F};

        if(amount<0.001F)return result;

        if(this.pureVis<pureAmount)pureAmount=this.pureVis;
        if(this.taintedVis<taintAmount)taintAmount=this.taintedVis;
        if(pureAmount<amount/2.0F && taintAmount==amount/2.0F){
            taintAmount=Math.min(amount-pureAmount,this.taintedVis);
        }else if(taintAmount<amount/2.0F && pureAmount==amount/2.0F){
            pureAmount=Math.min(amount-taintAmount,this.pureVis);
        }

        this.pureVis-=pureAmount;
        this.taintedVis-=taintAmount;
        result[0]=pureAmount;
        result[1]=taintAmount;
        return result;
    }
    @Override
    public float getPureVis(){return this.pureVis;}
    @Override
    public float getTaintedVis(){return this.taintedVis;}
    @Override
    public float getMaxVis(){return this.maxVis;}
    @Override
    public int getVisSuction(Direction dir){return this.visSuction;}
    @Override
    public int getTaintSuction(Direction dir){return this.taintSuction;}
    @Override
    public int getSuction(Direction dir){return Math.max(this.visSuction,this.taintSuction);}
    @Override
    public void setPureVis(float pureAmount){this.pureVis=pureAmount;}
    @Override
    public void setTaintedVis(float taintAmount){this.taintedVis=taintAmount;}
    @Override
    public void setVisSuction(int suctionAmount){this.visSuction=suctionAmount;}
    @Override
    public void setTaintSuction(int suctionAmount){this.taintSuction=suctionAmount;}
    @Override
    public void setSuction(int suctionAmount){this.taintSuction=suctionAmount;}

    @Override
    public BlockEntity getConnectableTile(Level level,Direction direction){return IConnection.super.getConnectableTile(level, direction);}

    public boolean isDisconnected(Direction dir){
        return disconnectedSides[dir.get3DDataValue()];
    }

    public void setDisconnected(Direction side, boolean disconnected){
        disconnectedSides[side.get3DDataValue()]=disconnected;
        setChanged();
    }

    public List<Connection> getConnections() {
        if (level == null) {
            return new ArrayList<>();
        }
        if (connectionCache == null) {
            updateCache();
            if (connectionCache == null) {
                return new ArrayList<>();
            }
        }
        return connectionCache;
    }

    public static void markPipesDirty(Level level,BlockPos pos){
        List<BlockPos> travelPositions = new ArrayList<>();
        LinkedList<BlockPos> queue = new LinkedList<>();
        Block block = level.getBlockState(pos).getBlock();
        if (!(block instanceof BlockConduit pipeBlock)) {
            return;
        }

        travelPositions.add(pos);
        addToDirtyList(level, pos, pipeBlock, travelPositions, queue);
        while (queue.size() > 0) {
            BlockPos blockPos = queue.removeFirst();
            block = level.getBlockState(blockPos).getBlock();
            if (block instanceof BlockConduit) {
                addToDirtyList(level,blockPos,(BlockConduit)block,travelPositions,queue);
            }
        }
        for (BlockPos p : travelPositions) {
            BlockEntity te = level.getBlockEntity(p);
            if (!(te instanceof TileConduit pipe)) {
                continue;
            }
            pipe.connectionCache = null;
        }
    }
    private static void addToDirtyList(Level world,BlockPos pos,BlockConduit pipeBlock,List<BlockPos>travelPositions,LinkedList<BlockPos>queue){
        for (Direction direction : Direction.values()) {
            if (pipeBlock.isConnected(world, pos, direction)) {
                BlockPos p = pos.relative(direction);
                if (!travelPositions.contains(p) && !queue.contains(p)) {
                    travelPositions.add(p);
                    queue.add(p);
                }
            }
        }
    }

    private void updateCache() {
        BlockState blockState = getBlockState();
        if (!(blockState.getBlock() instanceof BlockConduit)) {
            connectionCache = null;
            return;
        }

        Map<DirectionalPosition, Integer> connections = new HashMap<>();

        Map<BlockPos, Integer> queue = new HashMap<>();
        List<BlockPos> travelPositions = new ArrayList<>();

        if (level != null) {
            addToQueue(level, worldPosition, queue, travelPositions, connections, 1);
        }

        while (queue.size() > 0) {
            Map.Entry<BlockPos, Integer> blockPosIntegerEntry = queue.entrySet().stream().findAny().get();
            addToQueue(level, blockPosIntegerEntry.getKey(), queue, travelPositions, connections, blockPosIntegerEntry.getValue());
            travelPositions.add(blockPosIntegerEntry.getKey());
            queue.remove(blockPosIntegerEntry.getKey());
        }

        connectionCache = connections.entrySet().stream().map(entry -> new Connection(entry.getKey().getPos(), entry.getKey().getDirection(), entry.getValue())).collect(Collectors.toList());
    }

    public void addToQueue(Level world, BlockPos position, Map<BlockPos, Integer> queue, List<BlockPos> travelPositions, Map<DirectionalPosition, Integer> insertPositions, int distance) {
        Block block = world.getBlockState(position).getBlock();
        if (!(block instanceof BlockConduit pipeBlock)) {
            return;
        }
        for (Direction direction : Direction.values()) {
            if (pipeBlock.isConnected(world, position, direction)) {
                BlockPos p = position.relative(direction);
                DirectionalPosition dp = new DirectionalPosition(p, direction.getOpposite());
                if (canInsert(position, direction)) {
                    if (!insertPositions.containsKey(dp)) {
                        insertPositions.put(dp, distance);
                    } else {
                        if (insertPositions.get(dp) > distance) {
                            insertPositions.put(dp, distance);
                        }
                    }
                } else {
                    if (!travelPositions.contains(p) && !queue.containsKey(p)) {
                        queue.put(p, distance + 1);
                    }
                }
            }
        }
    }

    public boolean canInsert(BlockPos pos, Direction direction) {
        BlockEntity tileEntity = level.getBlockEntity(pos.relative(direction));
        if (tileEntity == null) {
            return false;
        }
        if (tileEntity instanceof TileConduit) {
            return false;
        }
        return canInsert(tileEntity, direction.getOpposite());
    }

    public boolean canInsert(BlockEntity tileEntity, Direction direction){return true;};

    public static class Connection {
        private final BlockPos pos;
        private final Direction direction;
        private final int distance;

        public Connection(BlockPos pos, Direction direction, int distance) {
            this.pos = pos;
            this.direction = direction;
            this.distance = distance;
        }

        public BlockPos getPos() {
            return pos;
        }

        public Direction getDirection() {
            return direction;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public String toString() {
            return "Connection{" +
                    "pos=" + pos +
                    ", direction=" + direction +
                    ", distance=" + distance +
                    '}';
        }
    }
}
