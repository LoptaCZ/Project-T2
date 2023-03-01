package com.raven_cze.projt2.common.content.items;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ItemVoidCompass extends PT2Item{
    public ItemVoidCompass(Properties properties) {
        super(properties);

        if(Dist.CLIENT.isClient())
            ItemProperties.register(this,new ResourceLocation("angle"),new ClampedItemPropertyFunction(){
            private final ItemVoidCompass.CompassWobble wobble = new ItemVoidCompass.CompassWobble();

            @Override
            public float unclampedCall(@NotNull ItemStack stack,@Nullable ClientLevel level,@Nullable LivingEntity ent,int seed) {
                Entity entity = ent != null ? ent : stack.getEntityRepresentation();
                if (entity == null) {
                    return 0.0F;
                } else {
                    if (level == null && entity.level instanceof ClientLevel) {
                        level = (ClientLevel)entity.level;
                    }

                    BlockPos blockpos = this.getMonolithPos(level,stack.getOrCreateTag());
                    long i = level.getGameTime();
                    if (blockpos != null && !(entity.position().distanceToSqr((double)blockpos.getX() + 0.5D, entity.position().y(), (double)blockpos.getZ() + 0.5D) < (double)1.0E-5F)) {
                        boolean flag = ent instanceof Player && ((Player)ent).isLocalPlayer();
                        double d1 = 0.0D;
                        if (flag) {
                            d1 = ent.getYRot();
                        }else if (entity instanceof ItemEntity) {
                            d1 = 180.0F - ((ItemEntity)entity).getSpin(0.5F) / ((float)Math.PI * 2F) * 360.0F;
                        }else if (ent != null) {
                            d1 = ent.yBodyRot;
                        }

                        d1 = Mth.positiveModulo(d1 / 360.0D, 1.0D);
                        double d2 = this.getAngleTo(Vec3.atCenterOf(blockpos), entity) / (double)((float)Math.PI * 2F);
                        double d3;
                        if (flag) {
                            if (this.wobble.shouldUpdate(i)) {
                                this.wobble.update(i, 0.5D - (d1 - 0.25D));
                            }

                            d3 = d2 + this.wobble.rotation;
                        } else {
                            d3 = 0.5D - (d1 - 0.25D - d2);
                        }

                        return Mth.positiveModulo((float)d3, 1.0F);
                    } else {
                        return Mth.positiveModulo((float)this.hashCode(), 1.0F);
                    }
                }
            }

            private BlockPos getMonolithPos(Level level,CompoundTag tag){
                boolean bool1=tag.contains("MonolithPos");
                boolean bool2=tag.contains("MonolithDimension");
                if(bool1&&bool2){
                    Optional<ResourceKey<Level>> optional =Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE,tag.get("MonolithDimension")).result();
                    if (optional.isPresent() && level.dimension() == optional.get()) {
                        return NbtUtils.readBlockPos(tag.getCompound("MonolithPos"));
                    }
                }
                return BlockPos.ZERO;
            }
            private double getAngleTo(Vec3 pos, Entity ent){return Math.atan2(pos.z-ent.getZ(),pos.x-ent.getX());}
        });
    }

    public static boolean isTracking(ItemStack stack){
        CompoundTag tag=stack.getTag();
        return tag!=null && !tag.isEmpty() && tag.contains("MonolithPos") && tag.contains("MonolithDimension");
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack,@NotNull Level level,@NotNull Entity entity,int slot,boolean selected){
        if(!level.isClientSide()){
            if(isTracking(stack)){
                CompoundTag tag=stack.getOrCreateTag();

                Optional<ResourceKey<Level>>optional=Level.RESOURCE_KEY_CODEC.parse(NbtOps.INSTANCE,tag.get("MonolithDimension")).result();
                if(optional.isPresent() && tag.contains("MonolithPos")){
                    if(optional.get()==level.dimension()){
                        BlockPos pos=NbtUtils.readBlockPos(tag.getCompound("MonolithPos"));
                        if( !level.isInWorldBounds(pos) ){
                           tag.remove("MonolithPos");
                           requestNewMonolith(level,stack,entity);
                        }
                    }
                }
            }
        }
    }

    private void requestNewMonolith(Level lvl,ItemStack stack,Entity ent){
        CompoundTag tag=stack.getTag();


    }



    @OnlyIn(Dist.CLIENT)
    static class CompassWobble{
        double rotation;
        private double deltaRotation;
        private long lastUpdateTick;

        boolean shouldUpdate(long pGameTime) {
            return this.lastUpdateTick != pGameTime;
        }

        void update(long pGameTime, double pWobbleAmount) {
            this.lastUpdateTick = pGameTime;
            double d0 = pWobbleAmount - this.rotation;
            d0 = Mth.positiveModulo(d0 + 0.5D, 1.0D) - 0.5D;
            this.deltaRotation += d0 * 0.1D;
            this.deltaRotation *= 0.8D;
            this.rotation = Mth.positiveModulo(this.rotation + this.deltaRotation, 1.0D);
        }
    }
}
