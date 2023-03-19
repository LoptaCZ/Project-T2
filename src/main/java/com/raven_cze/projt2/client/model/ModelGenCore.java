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

public class ModelGenCore extends Model{
    private final ModelPart small;
    private final ModelPart middle;
    private final ModelPart large;
    public ModelGenCore(ModelPart root){
        super(RenderType::entityTranslucent);
        this.small=root.getChild("Cores").getChild("SmallCore");
        this.middle=root.getChild("Cores").getChild("MediumCore");
        this.large=root.getChild("Cores").getChild("LargeCore");
    }

    public static MeshDefinition createMesh(){
        MeshDefinition mesh=new MeshDefinition();
        PartDefinition root=mesh.getRoot().getChild("Cores");
        root.addOrReplaceChild("SmallCore",CubeListBuilder.create().addBox(-4,-4,-4,8,16,16, CubeDeformation.NONE), PartPose.ZERO);
        root.addOrReplaceChild("MediumCore",CubeListBuilder.create().addBox(-16,-16,0,16,16,16, CubeDeformation.NONE), PartPose.ZERO);
        root.addOrReplaceChild("LargeCore",CubeListBuilder.create().addBox(-16,-16,0,16,16,16, CubeDeformation.NONE), PartPose.ZERO);
        root.getChild("Cores").getChild("SmallCore").bake(64,32);
        root.getChild("Cores").getChild("MediumCore").bake(64,32);
        root.getChild("Cores").getChild("LargeCore").bake(64,32);
        return mesh;
    }
    @Override
    public void renderToBuffer(@NotNull PoseStack pose,@NotNull VertexConsumer vc,int light,int overlay,float red,float green,float blue,float alpha){
        this.small.render(pose,vc,light,overlay,red,green,blue,alpha);
        this.middle.render(pose,vc,light,overlay,red,green,blue,alpha);
        this.large.render(pose,vc,light,overlay,red,green,blue,alpha);
    }
}
