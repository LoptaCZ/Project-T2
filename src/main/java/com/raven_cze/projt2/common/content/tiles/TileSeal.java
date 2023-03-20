package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.ProjectT2;
import com.raven_cze.projt2.api.ITickableBlockEntity;
import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.blocks.BlockApparatusStone;
import com.raven_cze.projt2.common.content.entities.TravelingTrunk;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

@SuppressWarnings("unused")
public class TileSeal extends BlockEntity implements ITickableBlockEntity {
    public byte[]runes=new byte[]{-1,-1,-1};
    public int delay=0;
    private int soundDelay=0;
    public int portalWindow=0;
    public boolean portalOpen=false;
    public float portalSize=0.0F;
    private int portalDelay =0;
    public boolean worked;
    public Direction orientation=this.getBlockState().getValue(BlockApparatusStone.DIRECTION);

    protected boolean gettingPower(){
        int returner=0;
        if(this.level!=null)
            for(Direction dir:Direction.values())
                returner=this.getBlockState().getDirectSignal(this.level,this.worldPosition,dir);

        return returner>0;
    }

    @Override
    public void tick(){
        this.portalSize=Mth.clamp(this.portalSize,0.0F,1.4F);
        if(this.delay<=0){
            int oldPower=0;
            this.soundDelay--;
            if(!gettingPower() || this.runes[0]==0 && this.runes[1]==4){
                switch(this.runes[0]){
                    case 0->magicSeal();
                    case 1->airSeal();
                    case 2->waterSeal();
                    case 3->earthSeal();
                    case 4->fireSeal();
                    case 5->darkSeal();
                }
            }
        }if(this.delay>0)this.delay--;
        if(this.portalOpen && this.portalSize<1.4D)this.portalSize+=0.15F;
        if( (!this.portalOpen && this.portalSize>0.0F)||(this.delay>0&&this.portalSize>0.0F) )this.portalSize-=0.25F;
    }

    public TileSeal(BlockPos worldPos,BlockState state){
        super(PT2Tiles.TILE_SEAL.get(),worldPos,state);
        if(this.level!=null)
            this.orientation=this.level.getBlockState(worldPos).getValue(BlockApparatusStone.DIRECTION);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void magicSeal(){
        if(this.level!=null)
            switch(this.runes[1]){
            case -1->{
                magicBoost();
                this.delay=20;
            }
            case 0->{
                switch(this.runes[2]){
                    case -1->{//        Magic | Magic | null
                        magicBoost();
                        this.delay=15;
                    }
                    case 0->{//         Magic | Magic | Magic
                        magicBoost();
                        this.delay=10;
                    }
                }
            }
            case 1->{//                  Magic | Air | null
                handlePortals();
            }
            case 2->{
                switch(this.runes[2]){

                }
            }
            case 3->{
                switch(this.runes[2]){
                    case -1,3->{
                        double x=worldPosition.getX(),y=worldPosition.getY(),z=worldPosition.getZ();
                        level.addParticle(ParticleTypes.BUBBLE_POP,x,y,z,0,0,0);//FXSparkle
                        this.delay=5;
                    }
                }
            }
            case 4->{
                switch(this.runes[2]){
                    case -1->{scan(3,true,true,true,true);}
                    case 0->{scan(6,false,true,false,false);}
                    case 1->{scan(9,true,true,true,true);}
                    //case 2->{scan(3,true,true,true,true);}
                    case 3->{scan(6,false,true,false,true);}
                    case 4->{scan(6,true,false,false,false);}
                    case 5->{scan(6,false,false,false,true);}
                }
                this.delay=5;
            }
            case 5->{//                  Magic | Dark | null

            }
        }
    }
    private void airSeal(){
        List<Entity>list;
        List<Entity>list2;
        if(level!=null) {
            switch (this.runes[1]) {
                case -1 -> {
                    pushEntity(false, true, true, 3, 0.03F);
                    this.delay = 2;
                }
                case 0 -> {
                    switch (this.runes[2]) {
                    }
                }
                case 1 -> {
                    switch (this.runes[2]) {
                        case -1 -> pushEntity(false, true, true, 5, 0.06F);
                        case 0 -> pushEntity(false, true, false, 4, 0.08F);
                        case 1 -> pushEntity(false, true, true, 7, 0.08F);
                        case 3 -> pushEntity(false, false, true, 7, 0.08F);
                    }
                }
                case 2 -> {
                    switch (this.runes[2]) {
                    }
                }
                case 3 -> {
                    switch (this.runes[2]) {
                    }
                }
                case 4 -> {
                    shock();
                    this.delay = 8 + this.level.random.nextInt(3) - runeAmount(1)* 2;
                }
                case 5 -> {
                    switch (this.runes[2]) {
                        case -1 ->pushEntity(true,true,true,5,0.06F);
                        case 0 ->pushEntity(true,true,false,7,0.08F);
                        case 1 ->pushEntity(true,true,true,7,0.08F);
                        case 3 ->pushEntity(true,false,true,7,0.08F);
                        case 4 -> {
                            pushEntity(true,true,true,6,0.07F);
                            list=level.getEntitiesOfClass(Entity.class,new AABB(0,0,0,1,1,1));
                            for(Entity ent:list){
                                if(ent instanceof LivingEntity)
                                    if(!(ent instanceof Player) && !(ent instanceof TravelingTrunk)){
                                        ent.hurt(DamageSource.GENERIC,1);
                                        //  Poof Particle
                                        this.level.addParticle(ParticleTypes.POOF,worldPosition.getX(),worldPosition.getY(),worldPosition.getZ(),0,0,0);
                                        this.level.playSound(null,this.worldPosition,SoundEvents.GENERIC_BURN,SoundSource.BLOCKS,0.5F,2.0F+this.level.random.nextFloat()*0.4F);
                                    }
                            }
                        }
                        case 5 -> {
                            pushEntity(true, true, true, 6, 0.04F);
                            list=level.getEntitiesOfClass(Entity.class,new AABB(0,0,0,1,1,1));
                            for(Entity ent:list){
                                if(ent instanceof ItemEntity item){
                                    attemptItemPickup(item);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private void waterSeal(){}
    private void earthSeal(){}
    private void fireSeal(){}
    private void darkSeal(){}

    private void nullifyAura(){}
    private void preventSpawn(int range){}
    private boolean scan(int range,boolean items,boolean mobs,boolean animals,boolean pvp){
        return false;
    }
    private boolean freeze(int range,boolean mobs,boolean animals,boolean pvp){return false;}
    private boolean fertilize(int range){return false;}
    private boolean harvest(int range){return false;}
    private boolean hydrate(int range){return false;}
    private boolean replant(int range){return false;}
    private boolean fetchSeed(){return false;}
    private boolean till(int range){return false;}
    private void magicBoost(){}
    private void handlePortals(){
        //  Magic       =>  0
        //  Air         =>  1
        //  < Any >     =>  CHANNEL 0 1 2 3 4 5
        Level level=this.level;
        if(level!=null){
            BlockPos maxOff=this.getBlockPos().relative(Direction.NORTH,2).relative(Direction.EAST,2).relative(Direction.SOUTH,2).relative(Direction.WEST,2).relative(Direction.UP,2).relative(Direction.DOWN,2);
            BlockPos minOff=this.getBlockPos().relative(Direction.NORTH,-2).relative(Direction.EAST,-2).relative(Direction.SOUTH,-2).relative(Direction.WEST,-2).relative(Direction.UP,-2).relative(Direction.DOWN,-2);
            List<?>list=level.getEntitiesOfClass(LivingEntity.class,new AABB(minOff,maxOff));
            boolean plyNear=false;
            if(list.size()>0){
                Collection<?>specialTiles=ProjectT2.SpecialTileHM.values();
                boolean fs=false;
                for(Object objEntity:list){
                    if(objEntity instanceof LivingEntity ent){
                        //Do shit
                        if(!this.portalOpen && plyNear)
                            level.playSound(null,this.getBlockPos(),PT2Sounds.PortalOpen.get(),SoundSource.BLOCKS,0.4F,1.0F+level.random.nextFloat()*0.2F);
                        if(this.portalDelay<=0 && plyNear){
                            renderTeleportDest();
                            this.portalDelay=3;
                        }
                        this.portalDelay--;
                        this.portalOpen=true;
                        fs=true;
                    //  Moving comment
                    }
                }if(!fs && this.portalOpen)this.portalOpen=false;
            }else{
                if(this.portalOpen){
                    list=level.getEntitiesOfClass(Player.class,new AABB(minOff,maxOff));
                    for(Object objEntity:list){
                        if(objEntity instanceof Player ply){
                            level.playSound(ply,this.getBlockPos(),PT2Sounds.PortalClose.get(),SoundSource.BLOCKS,0.4F,1.0F+level.random.nextFloat()*0.2F);
                            break;
                        }
                    }
                }
                this.portalOpen=false;
            }
            teleport();
        }
    }
    private void renderTeleportDest(){}
    private void teleport(){}
    private void beam(int range,int damage,boolean push,boolean homing,boolean motes,boolean core,float speed1,float speed2){}
    private void heal(int range,boolean mobs,boolean animals,boolean player,boolean buff){}
    private void scorch(int range,boolean mobs,boolean animals,boolean players,boolean buff){}
    private void shock(){}
    private void pushEntity(boolean pull,boolean creatures,boolean items,int range,float strength){}
    private void attemptItemPickup(ItemEntity item){
        ItemStack items=item.getItem();

        label48:for(int x=-2;x<=2;x++){
            for(int y=-2;y<=2;y++){
                for(int z=-2;z<=2;z++){
                    if((x!=0||y!=0||z!=0)&&this.worldPosition.getY()+y>=0){
                        BlockEntity block=this.level.getBlockEntity(this.worldPosition.offset(x,y,z));
                        if(block instanceof ChestBlockEntity chest){
                            for(int slot=0;slot<chest.getContainerSize();slot++){
                                if(chest.getItem(slot)==ItemStack.EMPTY){
                                    chest.setItem(slot,items);
                                    item.kill();
                                    this.level.playSound(null,this.worldPosition,SoundEvents.ITEM_PICKUP,SoundSource.BLOCKS,0.15F,2.0F+this.level.random.nextFloat()*0.45F);
                                    for(int idk=0;idk<5;idk++){
                                        //TODO Replace ParticleTypes.SPLASH with FXSparkle
                                        //this.level | x | y | z | x+((level.rand.nextFloat()-level.rand.nextFloat())*0.5F | y+((level.rand.nextFloat()-level.rand.nextFloat())*0.5F | z+((level.rand.nextFloat()-level.rand.nextFloat())*0.5F | 2.0F | 1 | 3
                                        //this.level | x | y | z | x+((level.rand.nextFloat()-level.rand.nextFloat())*0.2F | y+((level.rand.nextFloat()-level.rand.nextFloat())*0.2F | z+((level.rand.nextFloat()-level.rand.nextFloat())*0.2F | block.X | block.Y | block.Z | 1.0F | 1 | 3
                                        level.addParticle(ParticleTypes.SPLASH,0,0,0,0,0,0);
                                        level.addParticle(ParticleTypes.SPLASH,0,0,0,0,0,0);
                                    }
                                    break label48;
                                }
                                if(chest.getItem(slot).equals(items) && (chest.getItem(slot)).getCount()+items.getCount()<=items.getMaxStackSize() ){
                                    for(int idk=0;idk<5;idk++){
                                        level.addParticle(ParticleTypes.SPLASH,0,0,0,0,0,0);
                                        level.addParticle(ParticleTypes.SPLASH,0,0,0,0,0,0);
                                    }
                                    break label48;
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public int runeAmount(int type){
        int count=0;
        if(this.runes[1]==type)count++;
        if(this.runes[2]==type)count++;
        return count;
    }
    public int runeCount(){
        int count=0;
        if(this.runes[0]!=-1)count++;
        if(this.runes[1]!=-1)count++;
        if(this.runes[2]!=-1)count++;
        return count;
    }

    @Override
    public void load(@NotNull CompoundTag tag){
        super.load(tag);
        this.orientation=Direction.byName(tag.getString("orientation"));
        this.runes=tag.getByteArray("runes");
        this.portalWindow=tag.getInt("portalWindow");
    }

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag){
        super.saveAdditional(tag);
        tag.putString("orientation",this.orientation.getName());
        tag.putByteArray("runes",this.runes);
        tag.putInt("portalWindow",this.portalWindow);
    }
}
