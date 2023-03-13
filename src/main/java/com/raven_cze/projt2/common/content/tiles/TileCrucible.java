package com.raven_cze.projt2.common.content.tiles;

import com.raven_cze.projt2.api.IConnection;
import com.raven_cze.projt2.api.ITickableBlockEntity;
import com.raven_cze.projt2.common.content.PT2Sounds;
import com.raven_cze.projt2.common.content.PT2Tiles;
import com.raven_cze.projt2.common.content.entities.SkeletonAlly;
import com.raven_cze.projt2.common.content.entities.TravelingTrunk;
import com.raven_cze.projt2.common.content.particles.FXWisp;
import com.raven_cze.projt2.common.recipes.RecipesCrucible;
import com.raven_cze.projt2.common.util.Utils;
import com.raven_cze.projt2.common.content.world.aura.AuraChunk;
import com.raven_cze.projt2.common.content.world.aura.AuraWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.SnowGolem;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

@SuppressWarnings({"unused"})
public class TileCrucible extends BlockEntity implements IConnection,ITickableBlockEntity{
	public int smeltDelay;
	public float pureVis;
	public float taintedVis;
	public float maxVis;
	public int face=3;
	private short type;
	public float conversion;
	public float speed;
	public int bellows;
	
	public int soundDelay=25;
	public float pPure;
	public float pTaint;
	public int wait;
	public boolean updateNextPeriod;
	public boolean isPowering;

	public TileCrucible(BlockPos pos,BlockState state){
		super(PT2Tiles.TILE_CRUCIBLE.get(),pos,state);
		this.isPowering=false;
		this.pureVis=0.0F;
		this.taintedVis=0.0F;
		this.bellows=0;
	}
	
	@Override
	public void load(@NotNull CompoundTag compound){
		super.load(compound);
		this.pureVis=compound.getFloat("pureVis");
		this.taintedVis=compound.getFloat("taintedVis");
		this.type=compound.getShort("type");
		setTier(this.type);
		this.bellows=compound.getInt("bellows");
	}
	
	@Override
	public void saveAdditional(@NotNull CompoundTag compound){
		super.saveAdditional(compound);
		compound.putFloat("pureVis",this.pureVis);
		compound.putFloat("taintedVis",this.taintedVis);
		compound.putShort("type",this.type);
		compound.putInt("bellows",this.bellows);
	}
	
	public void setTier(short t){
		t=(short)Mth.clamp(t,1,4);
		switch(t){
			case 1 -> {        //	Default
				this.maxVis = 500.0F;
				this.conversion = 0.5F;
				this.speed = 0.25F;
				this.type = 1;
			}
			case 2 -> {        //	Eye
				this.maxVis = 600.0F;
				this.conversion = 0.6F;
				this.speed = 0.5F;
				this.type = 2;
			}
			case 3 -> {        //	Thaumium
				this.maxVis = 750.0F;
				this.conversion = 0.7F;
				this.speed = 0.75F;
				this.type = 3;
			}
			case 4 -> {        //	Souls
				this.maxVis = 750.0F;
				this.conversion = 0.4F;
				this.speed = 0.75F;
				this.type = 4;
			}
		}
	}

	//TODO Tick Method
	@Override
	public void tick(){
		float totalVis=this.pureVis+this.taintedVis;
		this.smeltDelay--;
		this.wait--;

		if(this.pPure!=this.pureVis || this.pTaint!=this.taintedVis){
			this.pPure=this.pureVis;
			this.pTaint=this.taintedVis;
			this.updateNextPeriod=true;
		}
		if(this.wait<=0 && this.updateNextPeriod){
			this.setChanged();
			this.updateNextPeriod=false;
			this.wait=10;
		}
		this.soundDelay--;
		if(this.soundDelay<=0)if(this.level!=null)this.soundDelay=15+this.level.random.nextInt(15);

		if(totalVis>this.maxVis){
			float overflowSplit=Math.min( (this.pureVis+this.taintedVis-this.maxVis)/2.0F,1.0F);
			if(this.pureVis>=overflowSplit)this.pureVis-=overflowSplit;
			if(overflowSplit>=1.0F){
				AuraChunk ac=AuraWorld.getAuraChunkAt(this.worldPosition);
				if(ac!=null && ac.getTaint()>=1.0F){
					this.taintedVis--;
					ac.setTaint(ac.getTaint()+1);
					FXWisp ef=new FXWisp(this.level,this.worldPosition,0.5F,5);
					Minecraft.getInstance().particleEngine.add(ef);
				}
			}
			this.setChanged();
		}
		if( this.type==1 || this.type==2 ){
			boolean oldPower=this.isPowering;
			this.isPowering= totalVis >= this.maxVis * 0.9D;
			if(oldPower!=this.isPowering){
				//this.getBlockState().getSignal();
				//TODO setRedstoneOutput in 5 Directions { NOT TOP }
			}
		}
		if(this.smeltDelay<=0 && this.type!=3){
			this.smeltDelay=5;
			List<ItemEntity>list=getContents();
			if(list!=null && this.level!=null) {
				if (list.size() > 0) {
					ItemEntity ent = list.get(this.level.random.nextInt(list.size()));
					ItemStack item = ent.getItem();
					if (canCook(item)) {
						boolean aboveFurnace = false;
						boolean aboveBoostFurnace = false;
						if (this.level.getBlockEntity(this.worldPosition.below()) instanceof TileArcaneFurnace && ((TileArcaneFurnace) Objects.requireNonNull(this.level.getBlockEntity(this.worldPosition.below()))).isBurning) {
							aboveFurnace = true;
							if (((TileArcaneFurnace) Objects.requireNonNull(this.level.getBlockEntity(this.worldPosition.below()))).boost)
								aboveBoostFurnace = true;
						}
						float currentItemCookValue = RecipesCrucible.smelting().getSmeltingResult(item, true, false);
						float tconv = this.conversion;

						if (aboveFurnace) {
							tconv += 0.1F + ((TileArcaneFurnace) Objects.requireNonNull(this.level.getBlockEntity(this.worldPosition))).bellows * 0.025F;
							if (aboveBoostFurnace) tconv += 0.1F;
							tconv = Math.min(tconv, 1.0F);
						}
						float pureCook = currentItemCookValue * tconv;
						float taintCook = currentItemCookValue - pureCook;
						if (this.type != 2 || totalVis + currentItemCookValue <= this.maxVis) {
							this.pureVis += pureCook;
							this.taintedVis += taintCook;
							float tSpeed = this.speed + this.bellows * 0.1F;
							this.smeltDelay = 10 + Math.round(currentItemCookValue / 5.0F / tSpeed);

							if (aboveFurnace)
								this.smeltDelay = (int) (this.smeltDelay * (0.8F - ((TileArcaneFurnace) Objects.requireNonNull(this.level.getBlockEntity(this.worldPosition))).bellows * 0.025F));
							//add bad vibes to aura
							item.shrink(1);
							if (item.getCount() <= 0)
								ent.kill();
							this.level.setBlockAndUpdate(this.worldPosition, this.getBlockState());
							this.level.addParticle(ParticleTypes.LARGE_SMOKE, ent.xo, ent.yo, ent.zo, 0, 0, 0);
							this.level.playSound(null,this.worldPosition, PT2Sounds.Bubbling.get(), SoundSource.BLOCKS,0.25F,0.9F*this.level.random.nextFloat()*0.2F);
						}
					} else {
						ent.xo = ((this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F);
						ent.yo = (0.2F + this.level.random.nextFloat() * 0.3F);
						ent.zo = ((this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F);
						this.level.playSound(null,ent,SoundEvents.BUBBLE_COLUMN_BUBBLE_POP,SoundSource.BLOCKS,0.5F,2.0F+this.level.random.nextFloat()*0.45F);
						ent.flyDist = 10;
						ent.invulnerableTime = 0;
					}
				}//list.size()>0
			}//
		//code for other crucibles
		}else if( this.smeltDelay<=0 && this.type==3 && Math.round(totalVis+1.0F)<=this.maxVis ){
			this.smeltDelay=20-this.bellows*2;
			List<Entity>list=NonNullList.create();
			if(this.level!=null){
				list=this.level.getEntitiesOfClass(Entity.class,new AABB(x,y,z,x+1,y+1,z+1));
			}
			boolean sucked=false;
			for(Entity ent:list){
				if( !(ent instanceof Player) && !(ent instanceof Wolf) && !(ent instanceof Cat) && !(ent instanceof Parrot) && !(ent instanceof TravelingTrunk) && !(ent instanceof SkeletonAlly)  && !(ent instanceof ItemEntity)){//  && ((ItemEntity)ent).getAge()<=0
					if(ent instanceof SnowGolem){
						ent.animateHurt();
						ent.kill();
						// something else but IDK what
					}
					boolean haveFurnace=false;
					boolean haveBFurnace=false;

					if(this.level.getBlockEntity(this.worldPosition.below())instanceof TileArcaneFurnace && ((TileArcaneFurnace) Objects.requireNonNull(this.level.getBlockEntity(this.worldPosition.below()))).isBurning ){
						haveFurnace=true;
						if( ((TileArcaneFurnace) Objects.requireNonNull(this.level.getBlockEntity(this.worldPosition.below()))).boost )haveBFurnace=true;
					}
					float tconv=this.conversion;
					if(haveFurnace){
						tconv+=0.1F+((TileArcaneFurnace) Objects.requireNonNull(this.level.getBlockEntity(this.worldPosition))).bellows*0.025F;
						if(haveBFurnace) tconv+=0.1F;
						tconv=Math.min(tconv,0.1F);
					}
					float sa=1.0F;
					if( ((LivingEntity)ent).isBaby() )sa=0.5F;
					float pureCook=sa*tconv;
					float taintCook=sa-pureCook;
					ent.hurt(DamageSource.GENERIC,1.0F);
					((LivingEntity)ent).addEffect(new MobEffectInstance(MobEffects.HUNGER));
					sucked=true;
					for(int fx=0;fx<3;fx++){
						FXWisp ef=new FXWisp(level,worldPosition,0.3F,5);
						Minecraft.getInstance().particleEngine.add(ef);
					}
				}
			}//for
			if(sucked){
				AuraChunk ac=AuraWorld.getAuraChunkAt(this.worldPosition);
				if(ac!=null && ac.getTaint()>=1.0F){
					this.taintedVis--;
					ac.setTaint(ac.getTaint()+1);
					FXWisp ef=new FXWisp(this.level,this.worldPosition,0.5F,5);
					Minecraft.getInstance().particleEngine.add(ef);
				}
				this.face=0;
				this.level.playSound(null,this.worldPosition,PT2Sounds.Suck.get(),SoundSource.BLOCKS,0.1F,0.8F+this.level.random.nextFloat()*0.3F);
				this.level.setBlockAndUpdate(this.worldPosition,this.getBlockState());
			}else if(this.face<3){
				this.face++;
				this.level.setBlockAndUpdate(this.worldPosition,this.getBlockState());
			}
		}//code for thaumium crucible
	}//public void tick(){

	private List<ItemEntity>getContents(){
		double x=this.worldPosition.getX();
		double y=this.worldPosition.getY();
		double z=this.worldPosition.getZ();
		if(this.level!=null){
			return this.level.getEntitiesOfClass(ItemEntity.class,new AABB(x,y,z,x+1,y+1,z+1));
		}return null;
	}
	public InteractionResult ejectContents(Player player){
		return InteractionResult.FAIL;
	}
	private boolean canCook(ItemStack item){
		return false;
	}

	@Override
	public boolean isConnectable(Direction direction){
		return direction!=Direction.UP;
	}
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
	    if (this.taintedVis < taintAmount)
	      taintAmount = this.taintedVis; 
	    if (pureAmount < amount / 2.0F && taintAmount == amount / 2.0F) {
	      taintAmount = Math.min(amount - pureAmount, this.taintedVis);
	    } else if (taintAmount < amount / 2.0F && pureAmount == amount / 2.0F) {
	      pureAmount = Math.min(amount - taintAmount, this.pureVis);
	    } 
	    this.pureVis -= pureAmount;
	    this.taintedVis -= taintAmount;
	    result[0] = pureAmount;
	    result[1] = taintAmount;
	    return result;
	}
	@Override
	public float getPureVis(){return this.pureVis;}
	@Override
	public float getTaintedVis(){return this.taintedVis;}
	@Override
	public float getMaxVis(){return this.maxVis;}
	@Override
	public int getVisSuction(Direction dir){return 0;}
	@Override
	public int getTaintSuction(Direction dir){return 0;}
	@Override
	public int getSuction(Direction dir){return 0;}
	@Override
	public void setPureVis(float pureAmount){}
	@Override
	public void setTaintedVis(float taintAmount){}
	@Override
	public void setVisSuction(int suctionAmount){}

	@Override
	public void setTaintSuction(int suctionAmount){}

	@Override
	public void setSuction(int suctionAmount){}
}
