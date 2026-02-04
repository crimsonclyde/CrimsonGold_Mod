package com.example.crimsongold.client;

import com.example.crimsongold.entity.FuchskyEntity;
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

public class FuchskyModel extends EntityModel<FuchskyEntity> {
        public final ModelPart head;
        public final ModelPart body;
        public final ModelPart leg0;
        public final ModelPart leg1;
        public final ModelPart leg2;
        public final ModelPart leg3;
        public final ModelPart tail;

        public FuchskyModel(ModelPart root) {
                this.head = root.getChild("head");
                this.body = root.getChild("body");
                this.leg0 = root.getChild("leg0");
                this.leg1 = root.getChild("leg1");
                this.leg2 = root.getChild("leg2");
                this.leg3 = root.getChild("leg3");
                this.tail = root.getChild("tail"); // Body child usually in Fox? Checking standard fox model
                // FoxModel structure: head, body, leg0, leg1, leg2, leg3, tail.
                // Wait, in FoxModel, tail is "tail".
        }

        public static LayerDefinition createBodyLayer() {
                MeshDefinition meshdefinition = new MeshDefinition();
                PartDefinition partdefinition = meshdefinition.getRoot();

                // Head (Vanilla: 8, 6, 6)
                PartDefinition head = partdefinition.addOrReplaceChild("head",
                                CubeListBuilder.create().texOffs(1, 5).addBox(-3.0F, -2.0F, -5.0F, 8.0F, 6.0F, 6.0F),
                                PartPose.offset(-1.0F, 16.5F, -3.0F));
                head.addOrReplaceChild("right_ear",
                                CubeListBuilder.create().texOffs(8, 1).addBox(-3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F),
                                PartPose.offset(0.0F, 0.0F, 0.0F));
                head.addOrReplaceChild("left_ear",
                                CubeListBuilder.create().texOffs(15, 1).addBox(3.0F, -4.0F, -4.0F, 2.0F, 2.0F, 1.0F),
                                PartPose.offset(0.0F, 0.0F, 0.0F));
                head.addOrReplaceChild("nose",
                                CubeListBuilder.create().texOffs(6, 18).addBox(-1.0F, 2.01F, -8.0F, 4.0F, 2.0F, 3.0F),
                                PartPose.offset(0.0F, 0.0F, 0.0F));

                // Body (Vanilla: 6, 11, 6) - Rotated PI/2 X, Offset(0, 16, -6)
                // Note: Vanilla body pivot is (0, 16, -6)
                PartDefinition body = partdefinition.addOrReplaceChild("body",
                                CubeListBuilder.create().texOffs(24, 15).addBox(-3.0F, 3.999F, -3.5F, 6.0F, 11.0F,
                                                6.0F),
                                PartPose.offsetAndRotation(0.0F, 16.0F, -6.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));

                // Legs
                // Leg0 (Right Hind): Vanilla offset (-5, 17.5, 7)
                CubeListBuilder legBuilder = CubeListBuilder.create().texOffs(13, 24).addBox(2.0F, 0.5F, -1.0F, 2.0F,
                                6.0F, 2.0F);
                partdefinition.addOrReplaceChild("leg0", legBuilder, PartPose.offset(-5.0F, 17.5F, 7.0F));

                // Leg1 (Left Hind): Vanilla offset (-1, 17.5, 7)
                partdefinition.addOrReplaceChild("leg1",
                                CubeListBuilder.create().texOffs(4, 24).addBox(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F),
                                PartPose.offset(-1.0F, 17.5F, 7.0F));

                // Leg2 (Right Front): Vanilla offset (-5, 17.5, 0)
                partdefinition.addOrReplaceChild("leg2", legBuilder, PartPose.offset(-5.0F, 17.5F, 0.0F));

                // Leg3 (Left Front): Vanilla offset (-1, 17.5, 0)
                partdefinition.addOrReplaceChild("leg3",
                                CubeListBuilder.create().texOffs(4, 24).addBox(2.0F, 0.5F, -1.0F, 2.0F, 6.0F, 2.0F),
                                PartPose.offset(-1.0F, 17.5F, 0.0F));

                // Tail (Vanilla: 4, 9, 5) - Offset (-4, 15, -1) Rotated PI/2 in some cases, but
                // Vanilla looks different here.
                // Standard Vanilla Tail: texOffs(30, 0).addBox(2.0F, 0.0F, -1.0F, 4.0F, 9.0F,
                // 5.0F)
                // Vanilla OFFSET: PartPose.offsetAndRotation(-4.0F, 15.0F, -1.0F,
                // ((float)Math.PI / 2F), 0.0F, 0.0F));
                // Wait, my previous manual fix had offset(0, 16, 5). Vanilla is (-4, 15, -1)
                // with Box (2, 0, -1).
                // Let's ensure this matches the User's request for "Exact Vanilla".

                // RE-VERIFYING VANILLA TAIL:
                // Vanilla FoxModel:
                // Tail definition:
                // addOrReplaceChild("tail", CubeListBuilder.create().texOffs(30,
                // 0).addBox(2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F),
                // PartPose.offsetAndRotation(-4.0F, 15.0F, -1.0F, ((float)Math.PI / 2F), 0.0F,
                // 0.0F));

                PartDefinition tail = partdefinition.addOrReplaceChild("tail",
                                CubeListBuilder.create().texOffs(30, 0).addBox(2.0F, 0.0F, -1.0F, 4.0F, 9.0F, 5.0F),
                                PartPose.offsetAndRotation(-4.0F, 15.0F, -1.0F, ((float) Math.PI / 2F), 0.0F, 0.0F));

                return LayerDefinition.create(meshdefinition, 48, 32);
        }

        @Override
        public void setupAnim(FuchskyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks,
                        float netHeadYaw, float headPitch) {
                this.head.xRot = headPitch * ((float) Math.PI / 180F);
                this.head.yRot = netHeadYaw * ((float) Math.PI / 180F);

                float legSpeed = 1.0F;
                if (entity.isInSittingPose()) {
                        // Sleeping Pose (User provided values)
                        this.body.zRot = -1.5707964F; // -PI/2 (Lie on side)
                        this.body.setPos(0.0F, 21.0F, -6.0F);

                        this.tail.xRot = -2.6179938F; // Curl tail
                        this.tail.zRot = 2.094395F;

                        this.tail.y = 21.0F;
                        this.tail.z = 8.0F; // Moved out to avoid clipping (was 2.0F)

                        this.head.xRot = 0.0F;
                        this.head.yRot = -2.094395F;
                        this.head.y = 21.0F;
                        this.head.z = -7.0F; // Moved out to avoid clipping (was -1.0F)

                        // Hide legs
                        this.leg0.visible = false;
                        this.leg1.visible = false;
                        this.leg2.visible = false;
                        this.leg3.visible = false;
                } else {
                        // Reset visibility
                        this.leg0.visible = true;
                        this.leg1.visible = true;
                        this.leg2.visible = true;
                        this.leg3.visible = true;

                        // Reset Body
                        this.body.zRot = 0.0F;
                        this.body.setPos(0.0F, 16.0F, -6.0F); // Reset pos!
                        // Wait, Standing definition: PartPose.offsetAndRotation(0.0F, 16.0F, -6.0F,
                        // ((float) Math.PI / 2F), 0.0F, 0.0F)
                        // So I must reset pos to (0, 16, -6).

                        this.head.z = -3.0F;
                        this.head.y = 16.5F;

                        this.tail.y = 16.0F;
                        this.tail.z = 9.0F;
                        this.tail.zRot = 0.0F;

                        this.leg0.y = 17.5F;
                        this.leg1.y = 17.5F;
                        this.leg2.y = 17.5F;
                        this.leg3.y = 17.5F;

                        // Standing/Walking
                        this.body.xRot = ((float) Math.PI / 2F);

                        this.leg0.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
                        this.leg1.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
                        this.leg2.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount;
                        this.leg3.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

                        // Proper tail wiggle (COS wave)
                        this.tail.xRot = ((float) Math.PI / 2F)
                                        - 0.05F * Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
                }
        }

        @Override
        public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
                        int color) {
                this.head.render(poseStack, buffer, packedLight, packedOverlay, color);
                this.body.render(poseStack, buffer, packedLight, packedOverlay, color);
                this.leg0.render(poseStack, buffer, packedLight, packedOverlay, color);
                this.leg1.render(poseStack, buffer, packedLight, packedOverlay, color);
                this.leg2.render(poseStack, buffer, packedLight, packedOverlay, color);
                this.leg3.render(poseStack, buffer, packedLight, packedOverlay, color);
                this.tail.render(poseStack, buffer, packedLight, packedOverlay, color);
        }
}
