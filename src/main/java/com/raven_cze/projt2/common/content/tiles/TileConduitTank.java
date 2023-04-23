package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.api.IConnection;
import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.PT2Tiles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class TileConduitTank extends BlockEntity implements IConnection{
    public float pureVis;
    public float taintVis;
    float fillAmount=1.0F;
    int wait;
    public int visSuction;
    public int taintSuction;

    public TileConduitTank(BlockPos pWorldPosition,BlockState pBlockState){
        super(PT2Tiles.TILE_VOID_CUBE.get(),pWorldPosition,pBlockState);
        this.visSuction=10;
        this.taintSuction=10;
        this.pureVis=0.0F;
        this.taintVis=0.0F;
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener>getUpdatePacket(){
        if(this.level!=null){
            if(this.level.isClientSide)return null;
            this.wait--;
            if(this.wait<=0){
                this.level.blockUpdated(this.worldPosition,this.getBlockState().getBlock());
                this.wait=10;
                calculateSuction();
                int breakChance=999;
                if (!this.getBlockState().getValue(null).equals(3))breakChance=3333;
                if (this.taintVis > getMaxVis() * 0.9F)
                    if (this.getBlockState().getValue(null).equals(3) && this.level.random.nextInt(breakChance) == 123) {
                        //TaintExplosion
                        this.level.explode(null, this.worldPosition.getX(), this.worldPosition.getY(), this.worldPosition.getZ(), 0.0F, Explosion.BlockInteraction.NONE);
                    } else if (this.level.random.nextInt(breakChance / 8) == 42) {
                        this.level.playSound(null, this.worldPosition, PT2Sounds.Creaking.get(), SoundSource.BLOCKS, 0.75F, 1.0F);
                    }
            }
            equalizeWithNeighbours();
        }
        return super.getUpdatePacket();
    }

    protected void equalizeWithNeighbours(){
        if(this.level!=null){
            float stackpureVis = this.pureVis;
            float stacktaintedVis = this.taintVis;
            float stackmaxVis = getMaxVis();
            int count = 1;
            TileConduitTank ts;
            while (this.level.getBlockEntity(this.worldPosition.above(count)) instanceof TileConduitTank st) {
                ts = st;
                stackpureVis += ts.pureVis;
                stacktaintedVis += ts.taintVis;
                stackmaxVis += ts.getMaxVis();
                count++;
            }
            for (int dir = 0; dir < 6; dir++) {
                if (isConnectable(null)) {
                    BlockEntity te = this.isConnectable(null) ? this : null;
                    if (te instanceof IConnection ent) {
                        if (!(te instanceof TileConduitTank) && stackpureVis + stacktaintedVis < stackmaxVis && (getVisSuction(null) > ent.getVisSuction(null) || getTaintSuction(null) > ent.getTaintSuction(null))) {
                            float[] results;
                            results = ent.subtractVis(Math.min(this.fillAmount, stackmaxVis - stackpureVis + stacktaintedVis));
                            if (getVisSuction(null) > ent.getVisSuction(null)) {
                                stackpureVis += results[0];
                            } else {
                                ent.setPureVis(results[0] + ent.getPureVis());
                            }
                            if (getTaintSuction(null) > ent.getTaintSuction(null)) {
                                stacktaintedVis += results[1];
                            } else {
                                ent.setTaintedVis(results[1] + ent.getTaintedVis());
                            }
                        }
                    }
                }
            }
            float total = stackpureVis + stacktaintedVis;
            if (Math.round(total) >= stackmaxVis)
                setSuction(0);
            float pratio = stackpureVis / total;
            float tratio = stacktaintedVis / total;
            count = 0;
            boolean clearrest = false;
            while (this.level.getBlockEntity(this.worldPosition.above(count)) instanceof TileConduitTank st) {
                ts = st;
                if (clearrest) {
                    ts.pureVis = 0.0F;
                    ts.taintVis = 0.0F;
                } else if (total <= ts.getMaxVis()) {
                    ts.pureVis = stackpureVis;
                    ts.taintVis = stacktaintedVis;
                    clearrest = true;
                } else {
                    ts.pureVis = ts.getMaxVis() * pratio;
                    ts.taintVis = ts.getMaxVis() * tratio;
                    stackpureVis -= ts.pureVis;
                    stacktaintedVis -= ts.taintVis;
                }
                total = stackpureVis + stacktaintedVis;
                count++;
            }
        }
    }
    public void calculateSuction(){
        setSuction(10);
        if(this.level!=null){
            for (int dir = 0; dir < 4; dir++) {
                if (isConnectable(Direction.values()[dir])) {
                    BlockEntity te = getConnectableTile(this.level, Direction.values()[dir]);
                    if (te instanceof TileBellows && ((TileBellows) te).isBoosting(this))
                        setSuction(getSuction(null) + 10);
                }
            }
        }
    }

    @Override
    public boolean isConnectable(Direction direction){return true;}

    @Override
    public boolean isVisSource(){return true;}

    @Override
    public boolean isVisConduit(){return false;}

    @Override
    public float[] subtractVis(float amount){
        float pureAmount = amount / 2.0F;
        float taintAmount = amount / 2.0F;
        float[] result = { 0.0F, 0.0F };
        if (amount < 0.001F)
            return result;
        if (this.pureVis < pureAmount)
            pureAmount = this.pureVis;
        if (this.taintVis < taintAmount)
            taintAmount = this.taintVis;
        if (pureAmount < amount / 2.0F && taintAmount == amount / 2.0F) {
            taintAmount = Math.min(amount - pureAmount, this.taintVis);
        } else if (taintAmount < amount / 2.0F && pureAmount == amount / 2.0F) {
            pureAmount = Math.min(amount - taintAmount, this.pureVis);
        }
        this.pureVis -= pureAmount;
        this.taintVis -= taintAmount;
        result[0] = pureAmount;
        result[1] = taintAmount;
        return result;
    }

    @Override
    public float getPureVis(){return this.pureVis;}

    @Override
    public float getTaintedVis(){return this.taintVis;}

    @Override
    public float getMaxVis(){
        return (this.getBlockState().getValue(null))?1000.0F:500.0F;
    }
    @Override
    public int getVisSuction(Direction dir){return this.visSuction;}
    @Override
    public int getTaintSuction(Direction dir){return this.taintSuction;}
    @Override
    public int getSuction(Direction dir){return Math.max(this.visSuction,this.taintSuction);}
    @Override
    public void setPureVis(float pureAmount){this.pureVis=pureAmount;}
    @Override
    public void setTaintedVis(float taintAmount){this.taintVis=taintAmount;}
    @Override
    public void setVisSuction(int suctionAmount){this.visSuction=suctionAmount;}
    @Override
    public void setTaintSuction(int suctionAmount){this.taintSuction=suctionAmount;}
    @Override
    public void setSuction(int suctionAmount){this.visSuction=this.taintSuction=suctionAmount;}
}
