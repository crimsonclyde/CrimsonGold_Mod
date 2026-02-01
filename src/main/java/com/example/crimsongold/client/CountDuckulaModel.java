package com.example.crimsongold.client;

import com.example.crimsongold.entity.CountDuckulaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class CountDuckulaModel extends EntityModel<CountDuckulaEntity> {
    public static final String RED_THING = "red_thing";
    public static final String BEAK = "beak";
    public static final String HEAD = "head";
    public static final String BODY = "body";
    public static final String RIGHT_LEG = "right_leg";
    public static final String LEFT_LEG = "left_leg";
    public static final String RIGHT_WING = "right_wing";
    public static final String LEFT_WING = "left_wing";
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart beak;
    private final ModelPart redThing;

    public CountDuckulaModel(ModelPart root) {
        this.head = root.getChild("head");
        this.beak = root.getChild("beak");
        this.redThing = root.getChild("red_thing");
        this.body = root.getChild("body");
        this.rightLeg = root.getChild("right_leg");
        this.leftLeg = root.getChild("left_leg");
        this.rightWing = root.getChild("right_wing");
        this.leftWing = root.getChild("left_wing");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("head",
                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F, 6.0F, 3.0F),
                PartPose.offset(0.0F, 15.0F, -4.0F));
        partdefinition.addOrReplaceChild("beak",
                CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 15.0F, -4.0F));
        partdefinition.addOrReplaceChild("red_thing",
                CubeListBuilder.create().texOffs(14, 4).addBox(-1.0F, -2.0F, -3.0F, 2.0F, 2.0F, 2.0F),
                PartPose.offset(0.0F, 15.0F, -4.0F));
        partdefinition.addOrReplaceChild("body",
                CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F),
                PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg",
                CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F),
                PartPose.offset(-2.0F, 19.0F, 1.0F));
        partdefinition.addOrReplaceChild("left_leg",
                CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F, 5.0F, 3.0F),
                PartPose.offset(1.0F, 19.0F, 1.0F));
        partdefinition.addOrReplaceChild("right_wing",
                CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F),
                PartPose.offset(-4.0F, 13.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_wing",
                CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 4.0F, 6.0F),
                PartPose.offset(4.0F, 13.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(CountDuckulaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
            float netHeadYaw, float headPitch) {
        this.head.xRot = headPitch * ((float) Math.PI / 180F);
        this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
        this.beak.xRot = this.head.xRot;
        this.beak.yRot = this.head.yRot;
        this.redThing.xRot = this.head.xRot;
        this.redThing.yRot = this.head.yRot;
        this.rightLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
        this.rightWing.zRot = ageInTicks;
        this.leftWing.zRot = -ageInTicks;

        if (entity.isInSittingPose()) {
            this.body.y = 19.0F;
            this.leftLeg.y = 19.0F;
            this.rightLeg.y = 19.0F;
            this.leftLeg.xRot = -1.57079633F;
            this.rightLeg.xRot = -1.57079633F;

            // Move wings with body
            this.rightWing.y = 19.0F - 3.0F; // Approx relative offset
            this.leftWing.y = 19.0F - 3.0F;
        } else {
            this.body.y = 16.0F;
            this.leftLeg.y = 19.0F;
            this.rightLeg.y = 19.0F;
            this.rightWing.y = 13.0F;
            this.leftWing.y = 13.0F;
        }
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
            int color) {
        this.head.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.beak.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.redThing.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.body.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.rightLeg.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.leftLeg.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.rightWing.render(poseStack, buffer, packedLight, packedOverlay, color);
        this.leftWing.render(poseStack, buffer, packedLight, packedOverlay, color);
    }
}
