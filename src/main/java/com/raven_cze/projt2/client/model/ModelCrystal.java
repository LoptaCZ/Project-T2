package com.raven_cze.projt2.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import org.jetbrains.annotations.NotNull;

public class ModelCrystal extends Model{
	private final ModelPart crystal;
	public ModelCrystal(ModelPart root){
		super(RenderType::entityTranslucent);
		this.crystal=root.getChild("crystal");
	}
	public static MeshDefinition createMesh(){
		MeshDefinition mesh=new MeshDefinition();
		PartDefinition root=mesh.getRoot();
		root.addOrReplaceChild("crystal",CubeListBuilder.create().addBox(-16,-16,0,16,16,16,CubeDeformation.NONE),PartPose.rotation(0.7071F,0.0F,0.7071F));
		root.getChild("crystal").bake(64,32);
		return mesh;
	}
	@Override
	public void renderToBuffer(@NotNull PoseStack stack,@NotNull VertexConsumer buffer,int light,int overlay,float red,float green,float blue,float alpha){
		this.crystal.render(stack,buffer,light,overlay,red,green,blue,alpha);
	}
}
