package com.example.crimsongold.client;

import com.example.crimsongold.entity.CountDuckulaEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class CountDuckulaCustomModel extends EntityModel<CountDuckulaEntity> {
        public final ModelPart head;
        public final ModelPart bill;
        public final ModelPart body;
        public final ModelPart left_wing;
        public final ModelPart right_wing;
        public final ModelPart left_leg;
        public final ModelPart right_leg;
        public final ModelPart left_vampire;
        public final ModelPart right_vampire;

        public CountDuckulaCustomModel(ModelPart root) {
                this.head = root.getChild("head");
                this.bill = this.head.getChild("bill");
                this.body = root.getChild("body");
                this.left_wing = root.getChild("left_wing");
                this.right_wing = root.getChild("right_wing");
                this.left_leg = root.getChild("left_leg");
                this.right_leg = root.getChild("right_leg");
                this.left_vampire = this.head.getChild("left_vampire");
                this.right_vampire = this.head.getChild("right_vampire");
        }

        public static LayerDefinition createBodyLayer() {
                MeshDefinition meshdefinition = new MeshDefinition();
                PartDefinition partdefinition = meshdefinition.getRoot();

                // Head Pivot: (0, 15, -4)
                PartDefinition head = partdefinition
                                .addOrReplaceChild("head",
                                                CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -6.0F, -2.0F, 4.0F,
                                                                6.0F, 3.0F, new CubeDeformation(0.0F)),
                                                PartPose.offset(0.0F, 15.0F, -4.0F));

                // Bill: Same position as head pivot in original (0, 15, -4) -> Offset (0, 0, 0)
                // relative to head
                head.addOrReplaceChild("bill", CubeListBuilder.create().texOffs(14, 0).addBox(-2.0F, -4.0F, -4.0F, 4.0F,
                                2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

                // Left Vampire: Original (0, 24, 0). Head (0, 15, -4). Diff: (0, 9, 4)
                head.addOrReplaceChild(
                                "left_vampire", CubeListBuilder.create().texOffs(27, 7).addBox(-2.0F, -11.0F, -7.0F,
                                                1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                                PartPose.offset(0.0F, 9.0F, 4.0F));

                // Right Vampire: Original (0, 24, 0). Head (0, 15, -4). Diff: (0, 9, 4)
                head.addOrReplaceChild(
                                "right_vampire", CubeListBuilder.create().texOffs(28, 7).addBox(1.0F, -11.0F, -7.0F,
                                                1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                                PartPose.offset(0.0F, 9.0F, 4.0F));

                PartDefinition body = partdefinition.addOrReplaceChild("body",
                                CubeListBuilder.create().texOffs(0, 9).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F,
                                                new CubeDeformation(0.0F)),
                                PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

                PartDefinition left_wing = partdefinition.addOrReplaceChild(
                                "left_wing", CubeListBuilder.create().texOffs(24, 13).addBox(-1.0F, 0.0F, -3.0F, 1.0F,
                                                4.0F, 6.0F, new CubeDeformation(0.0F)),
                                PartPose.offset(4.0F, 13.0F, 0.0F));

                PartDefinition right_wing = partdefinition.addOrReplaceChild(
                                "right_wing", CubeListBuilder.create().texOffs(24, 13).addBox(0.0F, 0.0F, -3.0F, 1.0F,
                                                4.0F, 6.0F, new CubeDeformation(0.0F)),
                                PartPose.offset(-4.0F, 13.0F, 0.0F));

                PartDefinition left_leg = partdefinition.addOrReplaceChild(
                                "left_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F,
                                                5.0F, 3.0F, new CubeDeformation(0.0F)),
                                PartPose.offset(1.0F, 19.0F, 1.0F));

                PartDefinition right_leg = partdefinition.addOrReplaceChild(
                                "right_leg", CubeListBuilder.create().texOffs(26, 0).addBox(-1.0F, 0.0F, -3.0F, 3.0F,
                                                5.0F, 3.0F, new CubeDeformation(0.0F)),
                                PartPose.offset(-2.0F, 19.0F, 1.0F));

                return LayerDefinition.create(meshdefinition, 64, 32);
        }

        @Override
        public void setupAnim(CountDuckulaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                        float netHeadYaw, float headPitch) {
                // Head rotation
                float headXRot = headPitch * ((float) Math.PI / 180F);
                float headYRot = netHeadYaw * ((float) Math.PI / 180F);

                this.head.xRot = headXRot;
                this.head.yRot = headYRot;

                // Visual Sitting Logic
                if (entity.isInSittingPose()) {
                        // Hide Legs
                        this.left_leg.visible = false;
                        this.right_leg.visible = false;
                        // Lower Body Parts (+6 pixels down)
                        this.body.y = 22.0F;
                        this.head.y = 21.0F;
                        this.left_wing.y = 19.0F;
                        this.right_wing.y = 19.0F;
                } else {
                        // Stand
                        this.left_leg.visible = true;
                        this.right_leg.visible = true;
                        // Reset Body Parts
                        this.body.y = 16.0F;
                        this.head.y = 15.0F;
                        this.left_wing.y = 13.0F;
                        this.right_wing.y = 13.0F;
                }

                // Leg animation (only effective if visible)
                this.right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
                this.left_leg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;

                // Realistic flapping animation (0 to 90 degrees, Outward only)
                float flapSpeed = 0.2F;
                float maxFlapAngle = (float) Math.PI / 2.0F; // 90 degrees max
                float flapCycle = (Mth.cos(ageInTicks * flapSpeed) + 1.0F) * 0.5F; // 0.0 to 1.0

                if (!entity.isInSittingPose()) {
                        this.right_wing.zRot = flapCycle * maxFlapAngle;
                        this.left_wing.zRot = -flapCycle * maxFlapAngle;
                } else {
                        // Stop wings
                        this.right_wing.zRot = 0.0F;
                        this.left_wing.zRot = 0.0F;
                }
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight,
                        int packedOverlay, int color) {
                head.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
                // bill/vampires rendered as children of head automatically
                body.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
                left_wing.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
                right_wing.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
                left_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
                right_leg.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
        }
}
