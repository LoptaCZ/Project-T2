package com.raven_cze.projt2.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.raven_cze.projt2.client.RenderDiscoveryTome;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({net.minecraft.client.renderer.ItemInHandRenderer.class})
public class ItemInHandRenderer{
    @Inject( method={"renderItem"}, at={@At(("HEAD"))}, cancellable=true)
    private void renderFirstPersonItem(LivingEntity entity,ItemStack item,ItemTransforms.TransformType transform,boolean leftHand,PoseStack pose,MultiBufferSource buffer,int light,CallbackInfo ci){
        if(RenderDiscoveryTome.renderHand(item,transform,leftHand,pose,buffer,light))ci.cancel();
    }
}
