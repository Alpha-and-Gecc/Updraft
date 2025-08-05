package com.alpha_and_gec.updraft.base.client.entities.model;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alpha_and_gec.updraft.base.client.entities.animations.SteelgoreAnimations;
import com.alpha_and_gec.updraft.base.common.entities.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.util.MathHelpers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SteelgoreModel<T extends SteelgoreEntity> extends HierarchicalModel<T> {

	private final ModelPart root;
	private final ModelPart core;
	private final ModelPart legscore;
	private final ModelPart leftlegfrontyaw;
	private final ModelPart leftlegfront;
	private final ModelPart leftlegfront2;
	private final ModelPart leftclawfront;
	private final ModelPart rightlegfrontyaw;
	private final ModelPart rightlegfront;
	private final ModelPart rightlegfront2;
	private final ModelPart rightclawfront;
	private final ModelPart leftlegfrontyaw2;
	private final ModelPart leftlegfront3;
	private final ModelPart leftlegfront4;
	private final ModelPart leftclawfront2;
	private final ModelPart rightlegfrontyaw2;
	private final ModelPart rightlegfront3;
	private final ModelPart rightlegfront4;
	private final ModelPart rightclawfront2;
	private final ModelPart torso;
	private final ModelPart elytrae;
	private final ModelPart leftelytra;
	private final ModelPart rightelytra;
	private final ModelPart wingscore;
	private final ModelPart leftwing3;
	private final ModelPart Lmembrane8;
	private final ModelPart Lmembrane;
	private final ModelPart leftwing4;
	private final ModelPart Lmembrane9;
	private final ModelPart Lmembrane10;
	private final ModelPart lefthand2;
	private final ModelPart leftfinger6;
	private final ModelPart Lmembrane12;
	private final ModelPart leftfinger7;
	private final ModelPart Lmembrane13;
	private final ModelPart leftfinger8;
	private final ModelPart Lmembrane14;
	private final ModelPart rightwing3;
	private final ModelPart Lmembrane2;
	private final ModelPart Lmembrane3;
	private final ModelPart rightwing4;
	private final ModelPart Lmembrane4;
	private final ModelPart Lmembrane5;
	private final ModelPart righthand2;
	private final ModelPart rightfinger6;
	private final ModelPart Lmembrane6;
	private final ModelPart rightfinger7;
	private final ModelPart Lmembrane7;
	private final ModelPart rightfinger8;
	private final ModelPart Lmembrane11;
	private final ModelPart neck;
	private final ModelPart frill;
	private final ModelPart leftfrill;
	private final ModelPart lefthorn;
	private final ModelPart lefthorn2;
	private final ModelPart rightfrill;
	private final ModelPart righthorn;
	private final ModelPart righthorn2;
	private final ModelPart head;
	private final ModelPart jaw;
	private final ModelPart leftjaw;
	private final ModelPart rightjaw;
	private final ModelPart tail;
	private final ModelPart t1roll;
	private final ModelPart tail2;
	private final ModelPart t2roll;
	private final ModelPart tail3;
	private final ModelPart t3roll;
	private final ModelPart tail4;
	private final ModelPart t4roll;
	private final ModelPart tail5;
	private final ModelPart t5roll;
	private final ModelPart tail6;
	private final ModelPart t6roll;
	private final ModelPart leftarm;
	private final ModelPart leftclaw;
	private final ModelPart rightarm;
	private final ModelPart rightclaw;

	public SteelgoreModel(ModelPart root) {
		this.root = root.getChild("root");
		this.core = this.root.getChild("core");
		this.legscore = this.core.getChild("legscore");
		this.leftlegfrontyaw = this.legscore.getChild("leftlegfrontyaw");
		this.leftlegfront = this.leftlegfrontyaw.getChild("leftlegfront");
		this.leftlegfront2 = this.leftlegfront.getChild("leftlegfront2");
		this.leftclawfront = this.leftlegfront2.getChild("leftclawfront");
		this.rightlegfrontyaw = this.legscore.getChild("rightlegfrontyaw");
		this.rightlegfront = this.rightlegfrontyaw.getChild("rightlegfront");
		this.rightlegfront2 = this.rightlegfront.getChild("rightlegfront2");
		this.rightclawfront = this.rightlegfront2.getChild("rightclawfront");
		this.leftlegfrontyaw2 = this.legscore.getChild("leftlegfrontyaw2");
		this.leftlegfront3 = this.leftlegfrontyaw2.getChild("leftlegfront3");
		this.leftlegfront4 = this.leftlegfront3.getChild("leftlegfront4");
		this.leftclawfront2 = this.leftlegfront4.getChild("leftclawfront2");
		this.rightlegfrontyaw2 = this.legscore.getChild("rightlegfrontyaw2");
		this.rightlegfront3 = this.rightlegfrontyaw2.getChild("rightlegfront3");
		this.rightlegfront4 = this.rightlegfront3.getChild("rightlegfront4");
		this.rightclawfront2 = this.rightlegfront4.getChild("rightclawfront2");
		this.torso = this.core.getChild("torso");
		this.elytrae = this.torso.getChild("elytrae");
		this.leftelytra = this.elytrae.getChild("leftelytra");
		this.rightelytra = this.elytrae.getChild("rightelytra");
		this.wingscore = this.torso.getChild("wingscore");
		this.leftwing3 = this.wingscore.getChild("leftwing3");
		this.Lmembrane8 = this.leftwing3.getChild("Lmembrane8");
		this.Lmembrane = this.Lmembrane8.getChild("Lmembrane");
		this.leftwing4 = this.leftwing3.getChild("leftwing4");
		this.Lmembrane9 = this.leftwing4.getChild("Lmembrane9");
		this.Lmembrane10 = this.Lmembrane9.getChild("Lmembrane10");
		this.lefthand2 = this.leftwing4.getChild("lefthand2");
		this.leftfinger6 = this.lefthand2.getChild("leftfinger6");
		this.Lmembrane12 = this.leftfinger6.getChild("Lmembrane12");
		this.leftfinger7 = this.lefthand2.getChild("leftfinger7");
		this.Lmembrane13 = this.leftfinger7.getChild("Lmembrane13");
		this.leftfinger8 = this.lefthand2.getChild("leftfinger8");
		this.Lmembrane14 = this.leftfinger8.getChild("Lmembrane14");
		this.rightwing3 = this.wingscore.getChild("rightwing3");
		this.Lmembrane2 = this.rightwing3.getChild("Lmembrane2");
		this.Lmembrane3 = this.Lmembrane2.getChild("Lmembrane3");
		this.rightwing4 = this.rightwing3.getChild("rightwing4");
		this.Lmembrane4 = this.rightwing4.getChild("Lmembrane4");
		this.Lmembrane5 = this.Lmembrane4.getChild("Lmembrane5");
		this.righthand2 = this.rightwing4.getChild("righthand2");
		this.rightfinger6 = this.righthand2.getChild("rightfinger6");
		this.Lmembrane6 = this.rightfinger6.getChild("Lmembrane6");
		this.rightfinger7 = this.righthand2.getChild("rightfinger7");
		this.Lmembrane7 = this.rightfinger7.getChild("Lmembrane7");
		this.rightfinger8 = this.righthand2.getChild("rightfinger8");
		this.Lmembrane11 = this.rightfinger8.getChild("Lmembrane11");
		this.neck = this.torso.getChild("neck");
		this.frill = this.neck.getChild("frill");
		this.leftfrill = this.frill.getChild("leftfrill");
		this.lefthorn = this.leftfrill.getChild("lefthorn");
		this.lefthorn2 = this.leftfrill.getChild("lefthorn2");
		this.rightfrill = this.frill.getChild("rightfrill");
		this.righthorn = this.rightfrill.getChild("righthorn");
		this.righthorn2 = this.rightfrill.getChild("righthorn2");
		this.head = this.neck.getChild("head");
		this.jaw = this.head.getChild("jaw");
		this.leftjaw = this.jaw.getChild("leftjaw");
		this.rightjaw = this.jaw.getChild("rightjaw");
		this.tail = this.torso.getChild("tail");
		this.t1roll = this.tail.getChild("t1roll");
		this.tail2 = this.tail.getChild("tail2");
		this.t2roll = this.tail2.getChild("t2roll");
		this.tail3 = this.tail2.getChild("tail3");
		this.t3roll = this.tail3.getChild("t3roll");
		this.tail4 = this.tail3.getChild("tail4");
		this.t4roll = this.tail4.getChild("t4roll");
		this.tail5 = this.tail4.getChild("tail5");
		this.t5roll = this.tail5.getChild("t5roll");
		this.tail6 = this.tail5.getChild("tail6");
		this.t6roll = this.tail6.getChild("t6roll");
		this.leftarm = this.torso.getChild("leftarm");
		this.leftclaw = this.leftarm.getChild("leftclaw");
		this.rightarm = this.torso.getChild("rightarm");
		this.rightclaw = this.rightarm.getChild("rightclaw");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 3.0F, 0.0F));

		PartDefinition core = root.addOrReplaceChild("core", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 8.0F));

		PartDefinition legscore = core.addOrReplaceChild("legscore", CubeListBuilder.create(), PartPose.offset(0.0F, 14.0F, 13.0F));

		PartDefinition leftlegfrontyaw = legscore.addOrReplaceChild("leftlegfrontyaw", CubeListBuilder.create(), PartPose.offset(6.0F, -1.0F, -10.0F));

		PartDefinition leftlegfront = leftlegfrontyaw.addOrReplaceChild("leftlegfront", CubeListBuilder.create().texOffs(246, 399).addBox(-2.0F, -4.0F, -4.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftlegfront2 = leftlegfront.addOrReplaceChild("leftlegfront2", CubeListBuilder.create().texOffs(404, 400).addBox(-2.0F, -13.0F, -3.0F, 8.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -1.0F, 0.0F));

		PartDefinition leftclawfront = leftlegfront2.addOrReplaceChild("leftclawfront", CubeListBuilder.create().texOffs(0, 387).addBox(-4.0F, -12.0F, -5.0F, 10.0F, 28.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(328, 288).addBox(-14.0F, -12.0F, -5.0F, 16.0F, 38.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(302, 361).addBox(1.0F, 5.0F, -2.0F, 7.0F, 16.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(398, 262).addBox(-2.0F, 18.0F, -2.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -9.0F, 0.0F));

		PartDefinition rightlegfrontyaw = legscore.addOrReplaceChild("rightlegfrontyaw", CubeListBuilder.create(), PartPose.offset(-6.0F, -1.0F, -10.0F));

		PartDefinition rightlegfront = rightlegfrontyaw.addOrReplaceChild("rightlegfront", CubeListBuilder.create().texOffs(246, 399).mirror().addBox(-8.0F, -4.0F, -4.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightlegfront2 = rightlegfront.addOrReplaceChild("rightlegfront2", CubeListBuilder.create().texOffs(404, 400).mirror().addBox(-6.0F, -13.0F, -3.0F, 8.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -1.0F, 0.0F));

		PartDefinition rightclawfront = rightlegfront2.addOrReplaceChild("rightclawfront", CubeListBuilder.create().texOffs(0, 387).mirror().addBox(-6.0F, -12.0F, -5.0F, 10.0F, 28.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(334, 191).addBox(-2.0F, -12.0F, -5.0F, 16.0F, 38.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(302, 361).mirror().addBox(-8.0F, 5.0F, -2.0F, 7.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(398, 262).mirror().addBox(-1.0F, 18.0F, -2.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -9.0F, 0.0F));

		PartDefinition leftlegfrontyaw2 = legscore.addOrReplaceChild("leftlegfrontyaw2", CubeListBuilder.create(), PartPose.offset(6.0F, -1.0F, 5.0F));

		PartDefinition leftlegfront3 = leftlegfrontyaw2.addOrReplaceChild("leftlegfront3", CubeListBuilder.create().texOffs(246, 399).addBox(-2.0F, -4.0F, -4.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftlegfront4 = leftlegfront3.addOrReplaceChild("leftlegfront4", CubeListBuilder.create().texOffs(404, 400).addBox(-2.0F, -13.0F, -3.0F, 8.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -1.0F, 0.0F));

		PartDefinition leftclawfront2 = leftlegfront4.addOrReplaceChild("leftclawfront2", CubeListBuilder.create().texOffs(0, 387).addBox(-4.0F, -12.0F, -5.0F, 10.0F, 28.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(328, 337).addBox(-14.0F, -12.0F, -5.0F, 16.0F, 38.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(302, 361).addBox(1.0F, 5.0F, -2.0F, 7.0F, 16.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(398, 262).addBox(-2.0F, 18.0F, -2.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, -9.0F, 0.0F));

		PartDefinition rightlegfrontyaw2 = legscore.addOrReplaceChild("rightlegfrontyaw2", CubeListBuilder.create(), PartPose.offset(-6.0F, -1.0F, 5.0F));

		PartDefinition rightlegfront3 = rightlegfrontyaw2.addOrReplaceChild("rightlegfront3", CubeListBuilder.create().texOffs(246, 399).mirror().addBox(-8.0F, -4.0F, -4.0F, 10.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition rightlegfront4 = rightlegfront3.addOrReplaceChild("rightlegfront4", CubeListBuilder.create().texOffs(404, 400).mirror().addBox(-6.0F, -13.0F, -3.0F, 8.0F, 17.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -1.0F, 0.0F));

		PartDefinition rightclawfront2 = rightlegfront4.addOrReplaceChild("rightclawfront2", CubeListBuilder.create().texOffs(0, 387).mirror().addBox(-6.0F, -12.0F, -5.0F, 10.0F, 28.0F, 11.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(192, 361).addBox(-2.0F, -12.0F, -5.0F, 16.0F, 38.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(302, 361).mirror().addBox(-8.0F, 5.0F, -2.0F, 7.0F, 16.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(398, 262).mirror().addBox(-1.0F, 18.0F, -2.0F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, -9.0F, 0.0F));

		PartDefinition torso = core.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(112, 175).addBox(-9.0F, -8.0F, -27.0F, 18.0F, 22.0F, 36.0F, new CubeDeformation(0.0F))
		.texOffs(192, 288).addBox(0.0F, -46.0F, -21.0F, 0.0F, 39.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 11.0F));

		PartDefinition elytrae = torso.addOrReplaceChild("elytrae", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -16.0F));

		PartDefinition leftelytra = elytrae.addOrReplaceChild("leftelytra", CubeListBuilder.create().texOffs(238, 233).addBox(-4.0F, -1.0F, -1.0F, 10.0F, 17.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, -2.0F, -11.0F));

		PartDefinition rightelytra = elytrae.addOrReplaceChild("rightelytra", CubeListBuilder.create().texOffs(0, 239).addBox(-6.0F, -1.0F, -1.0F, 10.0F, 17.0F, 38.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -2.0F, -11.0F));

		PartDefinition wingscore = torso.addOrReplaceChild("wingscore", CubeListBuilder.create(), PartPose.offset(0.0F, -6.0F, -21.0F));

		PartDefinition leftwing3 = wingscore.addOrReplaceChild("leftwing3", CubeListBuilder.create().texOffs(328, 175).addBox(0.0F, -3.0F, -7.0F, 34.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 1.0F, 8.0F));

		PartDefinition Lmembrane8 = leftwing3.addOrReplaceChild("Lmembrane8", CubeListBuilder.create().texOffs(322, 48).addBox(-34.0F, 0.5F, 0.0F, 34.0F, 0.0F, 21.0F, new CubeDeformation(0.0F)), PartPose.offset(34.0F, 0.0F, 3.0F));

		PartDefinition Lmembrane = Lmembrane8.addOrReplaceChild("Lmembrane", CubeListBuilder.create().texOffs(298, 70).addBox(0.0F, -0.5F, -9.0F, 13.0F, 0.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition leftwing4 = leftwing3.addOrReplaceChild("leftwing4", CubeListBuilder.create().texOffs(262, 144).addBox(-3.0F, -5.0F, -4.0F, 44.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(31.0F, 0.0F, -2.0F));

		PartDefinition Lmembrane9 = leftwing4.addOrReplaceChild("Lmembrane9", CubeListBuilder.create().texOffs(112, 144).addBox(-45.0F, 0.3F, -5.0F, 44.0F, 0.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(42.0F, 0.0F, 10.0F));

		PartDefinition Lmembrane10 = Lmembrane9.addOrReplaceChild("Lmembrane10", CubeListBuilder.create(), PartPose.offset(-45.0F, 0.0F, -13.0F));

		PartDefinition lefthand2 = leftwing4.addOrReplaceChild("lefthand2", CubeListBuilder.create().texOffs(0, 369).addBox(-2.0F, -3.0F, -3.0F, 21.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(41.0F, 0.0F, -1.0F));

		PartDefinition leftfinger6 = lefthand2.addOrReplaceChild("leftfinger6", CubeListBuilder.create(), PartPose.offsetAndRotation(19.0F, 0.0F, 2.0F, 0.0F, -0.1309F, 0.0F));

		PartDefinition cube_r1 = leftfinger6.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 144).addBox(-5.0F, -4.0F, -6.0F, 38.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 2.0F, 4.0F, 0.0F, 0.0436F, 0.0F));

		PartDefinition cube_r2 = leftfinger6.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 171).addBox(-5.0F, -3.0F, -5.0F, 38.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(40.0F, 2.0F, 2.0F, 0.0F, -0.0873F, 0.0F));

		PartDefinition Lmembrane12 = leftfinger6.addOrReplaceChild("Lmembrane12", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, 0.0F, 0.0F, 114.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition leftfinger7 = lefthand2.addOrReplaceChild("leftfinger7", CubeListBuilder.create(), PartPose.offsetAndRotation(15.0F, 0.0F, 9.0F, 0.0F, -0.9599F, 0.0F));

		PartDefinition cube_r3 = leftfinger7.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(0, 171).addBox(-5.0F, -3.0F, -5.0F, 38.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(34.0F, 2.0F, 2.0F, 0.0F, -0.0873F, 0.0F));

		PartDefinition cube_r4 = leftfinger7.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 153).addBox(-5.0F, -4.0F, -6.0F, 35.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 4.0F, 0.0F, 0.0873F, 0.0F));

		PartDefinition Lmembrane13 = leftfinger7.addOrReplaceChild("Lmembrane13", CubeListBuilder.create().texOffs(0, 48).addBox(-7.0F, 0.1F, 0.0F, 113.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition leftfinger8 = lefthand2.addOrReplaceChild("leftfinger8", CubeListBuilder.create(), PartPose.offsetAndRotation(8.0F, 0.0F, 11.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r5 = leftfinger8.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 171).addBox(-5.0F, -3.0F, -5.0F, 38.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(28.0F, 2.0F, 2.0F, 0.0F, -0.0873F, 0.0F));

		PartDefinition cube_r6 = leftfinger8.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 162).addBox(-5.0F, -4.0F, -6.0F, 28.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.0F, 4.0F, 0.0F, 0.0873F, 0.0F));

		PartDefinition Lmembrane14 = leftfinger8.addOrReplaceChild("Lmembrane14", CubeListBuilder.create().texOffs(0, 96).addBox(-2.0F, 0.2F, 0.0F, 113.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition rightwing3 = wingscore.addOrReplaceChild("rightwing3", CubeListBuilder.create().texOffs(328, 175).mirror().addBox(-34.0F, -3.0F, -7.0F, 34.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 1.0F, 8.0F));

		PartDefinition Lmembrane2 = rightwing3.addOrReplaceChild("Lmembrane2", CubeListBuilder.create().texOffs(322, 48).mirror().addBox(0.0F, 0.5F, 0.0F, 34.0F, 0.0F, 21.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-34.0F, 0.0F, 3.0F));

		PartDefinition Lmembrane3 = Lmembrane2.addOrReplaceChild("Lmembrane3", CubeListBuilder.create().texOffs(298, 70).mirror().addBox(-13.0F, -0.5F, -9.0F, 13.0F, 0.0F, 30.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition rightwing4 = rightwing3.addOrReplaceChild("rightwing4", CubeListBuilder.create().texOffs(262, 144).mirror().addBox(-41.0F, -5.0F, -4.0F, 44.0F, 10.0F, 14.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-31.0F, 0.0F, -2.0F));

		PartDefinition Lmembrane4 = rightwing4.addOrReplaceChild("Lmembrane4", CubeListBuilder.create().texOffs(112, 144).mirror().addBox(1.0F, 0.3F, -5.0F, 44.0F, 0.0F, 31.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-42.0F, 0.0F, 10.0F));

		PartDefinition Lmembrane5 = Lmembrane4.addOrReplaceChild("Lmembrane5", CubeListBuilder.create(), PartPose.offset(45.0F, 0.0F, -13.0F));

		PartDefinition righthand2 = rightwing4.addOrReplaceChild("righthand2", CubeListBuilder.create().texOffs(0, 369).mirror().addBox(-19.0F, -3.0F, -3.0F, 21.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-41.0F, 0.0F, -1.0F));

		PartDefinition rightfinger6 = righthand2.addOrReplaceChild("rightfinger6", CubeListBuilder.create(), PartPose.offsetAndRotation(-19.0F, 0.0F, 2.0F, 0.0F, 0.1309F, 0.0F));

		PartDefinition cube_r7 = rightfinger6.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 144).mirror().addBox(-33.0F, -4.0F, -6.0F, 38.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, 2.0F, 4.0F, 0.0F, -0.0436F, 0.0F));

		PartDefinition cube_r8 = rightfinger6.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 171).mirror().addBox(-33.0F, -3.0F, -5.0F, 38.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-40.0F, 2.0F, 2.0F, 0.0F, 0.0873F, 0.0F));

		PartDefinition Lmembrane6 = rightfinger6.addOrReplaceChild("Lmembrane6", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-108.0F, 0.0F, 0.0F, 114.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition rightfinger7 = righthand2.addOrReplaceChild("rightfinger7", CubeListBuilder.create(), PartPose.offsetAndRotation(-15.0F, 0.0F, 9.0F, 0.0F, 0.9599F, 0.0F));

		PartDefinition cube_r9 = rightfinger7.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 171).mirror().addBox(-33.0F, -3.0F, -5.0F, 38.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-34.0F, 2.0F, 2.0F, 0.0F, 0.0873F, 0.0F));

		PartDefinition cube_r10 = rightfinger7.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 153).mirror().addBox(-30.0F, -4.0F, -6.0F, 35.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 2.0F, 4.0F, 0.0F, -0.0873F, 0.0F));

		PartDefinition Lmembrane7 = rightfinger7.addOrReplaceChild("Lmembrane7", CubeListBuilder.create().texOffs(0, 48).mirror().addBox(-106.0F, 0.1F, 0.0F, 113.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition rightfinger8 = righthand2.addOrReplaceChild("rightfinger8", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.0F, 0.0F, 11.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r11 = rightfinger8.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 171).mirror().addBox(-33.0F, -3.0F, -5.0F, 38.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-28.0F, 2.0F, 2.0F, 0.0F, 0.0873F, 0.0F));

		PartDefinition cube_r12 = rightfinger8.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 162).mirror().addBox(-23.0F, -4.0F, -6.0F, 28.0F, 4.0F, 5.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.0F, 2.0F, 4.0F, 0.0F, -0.0873F, 0.0F));

		PartDefinition Lmembrane11 = rightfinger8.addOrReplaceChild("Lmembrane11", CubeListBuilder.create().texOffs(0, 96).mirror().addBox(-111.0F, 0.2F, 0.0F, 113.0F, 0.0F, 48.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition neck = torso.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(246, 361).addBox(-3.0F, -6.0F, -15.0F, 6.0F, 16.0F, 22.0F, new CubeDeformation(0.0F))
		.texOffs(382, 367).addBox(0.0F, 7.0F, -15.0F, 0.0F, 8.0F, 25.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 9.0F, -23.0F));

		PartDefinition frill = neck.addOrReplaceChild("frill", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -7.0F, -15.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition leftfrill = frill.addOrReplaceChild("leftfrill", CubeListBuilder.create().texOffs(384, 141).addBox(0.0F, -15.0F, 0.0F, 11.0F, 25.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(382, 273).addBox(0.0F, -31.0F, 0.0F, 18.0F, 47.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -1.0F, 0.0F, -0.4363F, 0.0F));

		PartDefinition lefthorn = leftfrill.addOrReplaceChild("lefthorn", CubeListBuilder.create(), PartPose.offsetAndRotation(10.0F, -13.0F, 0.0F, 0.2921F, 0.0905F, 0.2315F));

		PartDefinition cube_r13 = lefthorn.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(68, 317).addBox(-5.0F, -24.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r14 = lefthorn.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(96, 239).addBox(-3.0F, -24.0F, 0.0F, 4.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition lefthorn2 = leftfrill.addOrReplaceChild("lefthorn2", CubeListBuilder.create(), PartPose.offsetAndRotation(11.0F, 2.0F, 2.0F, 0.0873F, 0.0F, 1.1345F));

		PartDefinition cube_r15 = lefthorn2.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(82, 317).addBox(-5.0F, -24.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 11.0F, -2.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r16 = lefthorn2.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(362, 273).addBox(-3.0F, -10.0F, 0.0F, 4.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -3.0F, -2.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition rightfrill = frill.addOrReplaceChild("rightfrill", CubeListBuilder.create().texOffs(344, 386).addBox(-11.0F, -15.0F, 0.0F, 11.0F, 25.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(382, 320).addBox(-18.0F, -31.0F, 0.0F, 18.0F, 47.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.0F, -1.0F, 0.0F, 0.4363F, 0.0F));

		PartDefinition righthorn = rightfrill.addOrReplaceChild("righthorn", CubeListBuilder.create(), PartPose.offsetAndRotation(-10.0F, -13.0F, 0.0F, 0.2921F, -0.0905F, -0.2315F));

		PartDefinition cube_r17 = righthorn.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(302, 382).addBox(0.0F, -24.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r18 = righthorn.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(282, 399).addBox(-1.0F, -24.0F, 0.0F, 4.0F, 27.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition righthorn2 = rightfrill.addOrReplaceChild("righthorn2", CubeListBuilder.create(), PartPose.offsetAndRotation(-11.0F, 2.0F, 2.0F, 0.0873F, 0.0F, -1.1345F));

		PartDefinition cube_r19 = righthorn2.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(82, 317).mirror().addBox(0.0F, -24.0F, 0.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.0F, 11.0F, -2.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r20 = righthorn2.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(362, 273).mirror().addBox(-1.0F, -10.0F, 0.0F, 4.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(1.0F, -3.0F, -2.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(192, 271).addBox(-4.0F, -2.0F, -9.0F, 8.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(192, 288).addBox(-4.0F, 3.0F, -9.0F, 8.0F, 5.0F, 12.0F, new CubeDeformation(0.0F))
		.texOffs(128, 415).addBox(-1.0F, -15.0F, -12.0F, 2.0F, 17.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(242, 415).addBox(-1.0F, -18.0F, -12.0F, 2.0F, 3.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.0F, -13.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 2.0F));

		PartDefinition leftjaw = jaw.addOrReplaceChild("leftjaw", CubeListBuilder.create().texOffs(404, 23).addBox(-3.5F, 2.0F, -12.0F, 4.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(370, 0).addBox(-3.5F, -3.0F, -12.0F, 4.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 0.0F, 0.0F));

		PartDefinition rightjaw = jaw.addOrReplaceChild("rightjaw", CubeListBuilder.create().texOffs(406, 87).addBox(-0.5F, 2.0F, -12.0F, 4.0F, 5.0F, 11.0F, new CubeDeformation(0.0F))
		.texOffs(372, 87).addBox(-0.5F, -3.0F, -12.0F, 4.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, 0.0F));

		PartDefinition tail = torso.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 9.0F));

		PartDefinition t1roll = tail.addOrReplaceChild("t1roll", CubeListBuilder.create().texOffs(220, 175).addBox(-6.0F, -8.0F, -21.0F, 12.0F, 16.0F, 42.0F, new CubeDeformation(0.0F))
		.texOffs(260, 288).addBox(0.0F, -23.0F, -13.0F, 0.0F, 39.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 17.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 38.0F));

		PartDefinition t2roll = tail2.addOrReplaceChild("t2roll", CubeListBuilder.create().texOffs(96, 271).addBox(-5.0F, -4.75F, -20.0F, 10.0F, 12.0F, 38.0F, new CubeDeformation(0.0F))
		.texOffs(0, 294).addBox(0.0F, -20.75F, -16.0F, 0.0F, 39.0F, 34.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.75F, 16.0F));

		PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 34.0F));

		PartDefinition t3roll = tail3.addOrReplaceChild("t3roll", CubeListBuilder.create().texOffs(322, 87).addBox(-4.0F, -5.0F, -17.0F, 8.0F, 10.0F, 34.0F, new CubeDeformation(0.0F))
		.texOffs(68, 321).addBox(0.0F, -21.0F, -14.0F, 0.0F, 39.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 14.0F));

		PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 31.0F));

		PartDefinition t4roll = tail4.addOrReplaceChild("t4roll", CubeListBuilder.create().texOffs(324, 0).addBox(-3.0F, -3.5F, -17.0F, 6.0F, 7.0F, 34.0F, new CubeDeformation(0.0F))
		.texOffs(130, 321).addBox(0.0F, -20.5F, -14.0F, 0.0F, 39.0F, 31.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 14.0F));

		PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 32.0F));

		PartDefinition t5roll = tail5.addOrReplaceChild("t5roll", CubeListBuilder.create().texOffs(334, 240).addBox(-2.0F, -2.5F, -14.0F, 4.0F, 5.0F, 28.0F, new CubeDeformation(0.0F))
		.texOffs(82, 129).addBox(0.0F, -20.5F, -14.0F, 0.0F, 39.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.5F, 13.0F));

		PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 27.0F));

		PartDefinition t6roll = tail6.addOrReplaceChild("t6roll", CubeListBuilder.create().texOffs(0, 172).addBox(0.0F, -21.0F, 0.0F, 0.0F, 39.0F, 28.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

		PartDefinition leftarm = torso.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(334, 273).addBox(-3.0F, 3.0F, -12.0F, 5.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(220, 410).addBox(-3.0F, -3.0F, -3.0F, 5.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, 12.0F, -21.0F));

		PartDefinition leftclaw = leftarm.addOrReplaceChild("leftclaw", CubeListBuilder.create().texOffs(84, 391).addBox(-5.0F, -5.0F, -5.0F, 7.0F, 16.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(412, 124).addBox(-3.0F, 11.0F, -5.0F, 3.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 6.0F, -10.0F));

		PartDefinition rightarm = torso.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(192, 410).addBox(-2.0F, 3.0F, -12.0F, 5.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(84, 415).addBox(-2.0F, -3.0F, -3.0F, 5.0F, 12.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 12.0F, -21.0F));

		PartDefinition rightclaw = rightarm.addOrReplaceChild("rightclaw", CubeListBuilder.create().texOffs(84, 391).addBox(-2.0F, -5.0F, -5.0F, 7.0F, 16.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(412, 124).addBox(0.0F, 11.0F, -5.0F, 3.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 6.0F, -10.0F));

		return LayerDefinition.create(meshdefinition, 512, 512);
	}

	@Override
	public void setupAnim(SteelgoreEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root().getAllParts().forEach(ModelPart::resetPose);

		this.neck.xRot = (headPitch * (Mth.DEG_TO_RAD)) / 2;
		this.head.xRot = (headPitch * (Mth.DEG_TO_RAD)) / 2;

		this.neck.yRot = (netHeadYaw * (Mth.DEG_TO_RAD)) / 2;
		this.head.yRot = (netHeadYaw * (Mth.DEG_TO_RAD)) / 2;

		this.animate(entity.idleAnimationState, SteelgoreAnimations.IDLE, ageInTicks, (float) (0.5 + limbSwingAmount * 4.0f));

		double rate = 0.025;

		if (!entity.isDeadOrDying()) {
		}
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		root.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart root() {
		return this.root;
	}
}