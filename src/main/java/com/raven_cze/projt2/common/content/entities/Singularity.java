package com.raven_cze.projt2.common.content.entities;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.common.content.PT2Entities;
import com.raven_cze.projt2.common.content.PT2Items;
import com.raven_cze.projt2.common.content.PT2Particles;
import com.raven_cze.projt2.common.content.particles.FXWisp;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.HashSet;
import java.util.Set;

public class Singularity extends Entity{
    public float rotation;
    double bounceFactor;
    int fuse;
    int fuseMax;
    boolean exploded;
    public BlockPos explosion;
    public float explosionSize;
    public Set destroyedBlocks;
    private float currentVis;

    double x,y,z;

    public Singularity(EntityType<Singularity>entity,Level level){this(level);}
    public Singularity(Level lvl){
        super(PT2Entities.SINGULARITY.get(),lvl);
        this.bounceFactor=0.25D;
        this.exploded=false;
        this.destroyedBlocks=new HashSet();
        this.level=lvl;
    }
    public Singularity(Level lvl,LivingEntity ent){
        this(lvl);
        double xHeading=-Mth.sin(ent.yBodyRot*3.141593F/180.0F);
        double zHeading=Mth.cos(ent.yBodyRot*3.141593F/180.0F);
        //x
        //y
        //z
        //d(ent.o+xHeading*0.8D,ent.p,ent.q+zHeading*0.8D)
        this.xo=this.xOld;
        this.yo=this.yOld;
        this.zo=this.zOld;
        this.explosionSize=4.0F;
        this.fuse=60;
    }
    public Singularity(Level lvl,double x,double y,double z){
        this(lvl);
        this.setPos(x,y,z);
    }

    @Override
    public Packet<?>getAddEntityPacket(){return null;}
    @Override
    protected void defineSynchedData(){}
    @Override
    public void tick(){
        this.x=this.blockPosition().getX();
        this.y=this.blockPosition().getY();
        this.z=this.blockPosition().getZ();

        if(this.fuse--==0)explode();
        if(this.fuse>0){
            double prevVelX=this.getX();
            double prevVelY=this.getY();
            double prevVelZ=this.getZ();

            this.xo=this.xOld;
            this.yo=this.yOld;
            this.zo=this.zOld;

            moveTo(this.getX(),this.getY(),this.getZ());

            if(this.getX()!=prevVelX)this.x=-this.bounceFactor*prevVelX;
            if(this.getY()!=prevVelY)this.y=-this.bounceFactor*prevVelY;
            if(this.getZ()!=prevVelZ)this.z=-this.bounceFactor*prevVelZ;

            try{
                //  Add custom FXWisp Particle

                SpriteSet sprites=ProjectT2.getSpriteSet(new ResourceLocation(ProjectT2.MODID,"particles/wisp.json"),false);
                FXWisp ef=new FXWisp(this.level,this.x,this.y,this.z,sprites,0,0,0);
                ef.shrink=true;
                Minecraft.getInstance().particleEngine.add(ef);
            }catch(Exception e){}
        }else{
            this.rotation++;
            if(this.rotation>360.0F)this.rotation-=360.0F;
            doSuckage();
        }
        if(this.fuse<-170){
            doSpawnResult();
            super.tick();
        }
    }
    public void doExplosion(){}
    private void explode(){}
    private void doSuckage(){}
    private void doSpawnResult(){}
    @Override
    public boolean hurt(DamageSource dmg,float val){
        return super.hurt(dmg,val);
    }
    @Override
    public void playerTouch(Player pPlayer){super.playerTouch(pPlayer);}
    public boolean isDead(){return !this.isAlive();}
    @Override
    public float getPercentFrozen(){return 0.1F;}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag){
        this.fuse=tag.getInt("Fuse");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag){
        tag.putInt("Fuse",this.fuse);
    }
}
