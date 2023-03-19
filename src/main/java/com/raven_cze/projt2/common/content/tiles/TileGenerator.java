package com.raven_cze.projt2.common.content.tiles;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.raven_cze.projt2.api.internal.InternalEnergyStorage;
import com.raven_cze.projt2.api.IUpgradable;
import com.raven_cze.projt2.api.TileVisUser;
import com.raven_cze.projt2.common.config.ClientCFG;
import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.world.inventory.DiscoveryTomeMenu;
import com.raven_cze.projt2.common.content.world.inventory.GeneratorMenu;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TileGenerator extends TileVisUser implements IUpgradable,MenuProvider,IEnergyStorage{
    public TileGenerator(BlockPos pos,BlockState state){
        super(PT2Tiles.TILE_GENERATOR.get(),pos,state);
        energy=new InternalEnergyStorage(energyMax);
        lazyEnergy=LazyOptional.of(()->energy);
    }
    public float rotation=0.0F;
    public int storedEnergy=0;
    public int energyMax=5000;
    private int genLoop;
    private byte[]upgrades=new byte[]{-1,-1};
    protected final InternalEnergyStorage energy;
    private final LazyOptional<IEnergyStorage>lazyEnergy;

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id,@NotNull Inventory inv,@NotNull Player ply){
        FriendlyByteBuf packetBuff=new FriendlyByteBuf(Unpooled.buffer());
        packetBuff.writeBlockPos(this.worldPosition);
        return new GeneratorMenu(id,inv,packetBuff);
    }

    //  Tick Function
    @Nullable
    @Override
    public Packet<ClientGamePacketListener>getUpdatePacket(){
        return super.getUpdatePacket();
    }
    @Override
    public void handleUpdateTag(CompoundTag tag){super.handleUpdateTag(tag);}
    @Override
    public void onDataPacket(Connection net,ClientboundBlockEntityDataPacket pkt){
        super.onDataPacket(net,pkt);
        if(this.level.isClientSide)return;
        if(this.rotation==-1.0F)this.rotation=this.level.random.nextInt(360);
        this.rotation++;
        if(hasUpgrade((byte)5))this.energyMax=10000;
        boolean emitPower=false;
        if(!gettingPower()){
            if(this.storedEnergy<this.energyMax){
                float moon=(2+Math.abs(this.level.getTimeOfDay(5.0F)-4))*0.2F;
                if(hasUpgrade((byte)0))moon+=0.2F;
                float mod=hasUpgrade((byte)1)?0.8F:1.0F;
                float visPerUnit=6.6666666E-4F*mod;
                float suck=visPerUnit*Math.min(75.0F*moon,(this.energyMax-this.storedEnergy));
                if(suck>0.006666667F){
                    float s=suck;
                    if(getExactPureVis(s)){
                        float add=suck*150.0F;
                        this.storedEnergy+=Math.round(add);
                    }
                }
            }
            if(this.storedEnergy>this.energyMax)this.storedEnergy=this.energyMax;
            int emit=2;
            for(Direction dir:Direction.values()){
                BlockEntity te=this.level.getBlockEntity(this.worldPosition.relative(dir));
                if(te instanceof IEnergyStorage ies){
                    int energyUsed=Math.min(Math.min(Math.min(ies.getMaxEnergyStored(),this.storedEnergy/3),ies.getMaxEnergyStored()-ies.getEnergyStored()),emit);
                    this.storedEnergy-=energyUsed*3;
                    emitPower=true;
                    int arcs=energyUsed/2;
                    if(!ClientCFG.lowFX.get())arcs=energyUsed/3;
                    if(this.level.random.nextInt(6)<arcs && energyUsed>0){
                        //Create LightningBolt from CodeChicken API
                        //  With type 0
                    }
                }break;
            }//     End Of for()
            //
        }
        if(this.genLoop==0 && emitPower){
            this.level.playSound(null,this.worldPosition,PT2Sounds.ElectricLoop.get(),SoundSource.BLOCKS,0.05F,1.0F);
            //Add Bad Vibes to Aura Chunk
        }
        this.genLoop++;
        if(this.genLoop>=70)this.genLoop=0;
    }
    //  I guess this is all tick functions

    @Override
    public void load(@NotNull CompoundTag tag){
        super.load(tag);
        this.storedEnergy=tag.getInt("energy");
        this.upgrades=tag.getByteArray("upgrades");
        energy.read(tag);
    }
    @Override
    protected void saveAdditional(@NotNull CompoundTag tag){
        super.saveAdditional(tag);
        tag.putInt("energy",this.storedEnergy);
        tag.putByteArray("upgrades",this.upgrades);
        energy.write(tag);
    }

    @NotNull
    @Override
    public <T>LazyOptional<T>getCapability(@NotNull Capability<T>cap,Direction side){
        if(cap==CapabilityEnergy.ENERGY)return lazyEnergy.cast();
        return super.getCapability(cap);
    }

    @Override
    public boolean canAcceptUpgrade(byte upgradeID){
        if(upgradeID!=0 && upgradeID!=1 && upgradeID!=5)return false;
        return !hasUpgrade(upgradeID);
    }

    @Override
    public boolean hasUpgrade(byte upgradeID){
        if(this.upgrades.length<1)return false;
        for(int upgrade=0;upgrade<getUpgradeLimit();){
            if(this.upgrades[upgrade]==upgradeID)return true;
            upgrade++;
        }
        return false;
    }

    @Override
    public boolean setUpgrade(byte upgradeID){
        for(int u=0;u<getUpgradeLimit();u++){
            if(this.upgrades[u]<0&&canAcceptUpgrade(upgradeID)){
                this.upgrades[u]=upgradeID;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean clearUpgrade(byte index){
        if(this.upgrades[index]>=0){
            this.upgrades[index]=-1;
            return true;
        }
        return false;
    }

    @Override
    public int getUpgradeLimit(){return 2;}

    @Override
    public byte[] getUpgrades(){return this.upgrades;}

    @Override
    public @NotNull Component getDisplayName(){return new TextComponent("block.projt2.generator");}

    @Override
    public int receiveEnergy(int maxReceive,boolean simulate){return 0;}

    @Override
    public int extractEnergy(int maxExtract,boolean simulate){return 0;}

    @Override
    public int getEnergyStored(){return this.storedEnergy;}

    @Override
    public int getMaxEnergyStored(){return this.energyMax;}

    @Override
    public boolean canExtract(){return true;}

    @Override
    public boolean canReceive(){return false;}


}
