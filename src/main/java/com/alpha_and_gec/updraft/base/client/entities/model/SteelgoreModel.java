package com.alpha_and_gec.updraft.base.client.entities.model;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.common.entities.Steelgore.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.util.MathHelpers;
import com.mojang.math.Axis;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;


public class SteelgoreModel extends GeoModel<SteelgoreEntity> {

	private static final ResourceLocation STEELGORE_BARIOTH = new ResourceLocation(Updraft.MOD_ID,"textures/entity/steelgore/steelgore_barioth.png");
	private static final ResourceLocation STEELGORE_SILVERASH = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore_silverash.png");
	private static final ResourceLocation STEELGORE_ELDERFROST = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore_elderfrost.png");
	private static final ResourceLocation STEELGORE_OPALINE = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore_opaline.png");
	private static final ResourceLocation STEELGORE_OKAPI = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore_okapi.png");
	private static final ResourceLocation STEELGORE_TAPIR = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore_tapir.png");
	private static final ResourceLocation STEELGORE_WILDEBEEST = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore_wildebeest.png");
	private static final ResourceLocation STEELGORE_BISON = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore_bison.png");
	private static final ResourceLocation STEELGORE_ALBINO = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore_albino.png");
	private static final ResourceLocation STEELGORE_MELANISTIC = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore_melanistic.png");


	@Override
	public ResourceLocation getModelResource(SteelgoreEntity object)
	{
		return new ResourceLocation(Updraft.MOD_ID, "geo/steelgore.geo.json");
	}

	public @NotNull ResourceLocation getTextureResource(SteelgoreEntity entity) {
		return switch (entity.getVariant()) {
			case 0 -> STEELGORE_BARIOTH;
			case 1 -> STEELGORE_SILVERASH;
			case 2 -> STEELGORE_ELDERFROST;
			case 3 -> STEELGORE_OPALINE;
			case 4 -> STEELGORE_OKAPI;
			case 5 -> STEELGORE_TAPIR;
			case 6 -> STEELGORE_WILDEBEEST;
			case 7 -> STEELGORE_BISON;
			case 8 -> STEELGORE_ALBINO;
			case 9 -> STEELGORE_MELANISTIC;

			default -> STEELGORE_OKAPI;
		};
	}

	@Override
	public ResourceLocation getAnimationResource(SteelgoreEntity object)
	{
		return new ResourceLocation(Updraft.MOD_ID, "animations/steelgore.animation.json");
	}

	@Override
	public void setCustomAnimations(SteelgoreEntity animatable, long instanceId, AnimationState<SteelgoreEntity> animationState) {
		super.setCustomAnimations(animatable, instanceId, animationState);
		if (animationState == null) return;
		EntityModelData extraDataOfType = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

		GeoBone root = this.getBone("root").get();
		GeoBone geoCore = this.getBone("core").get();
		GeoBone torso = this.getBone("torso").get();
		GeoBone rider = this.getBone("rider1").get();

		CoreGeoBone core = this.getAnimationProcessor().getBone("core");
		CoreGeoBone tail1 = this.getAnimationProcessor().getBone("tail1");
		CoreGeoBone tail2 = this.getAnimationProcessor().getBone("tail2");
		CoreGeoBone tail3 = this.getAnimationProcessor().getBone("tail3");
		CoreGeoBone tail4 = this.getAnimationProcessor().getBone("tail4");
		CoreGeoBone tail5 = this.getAnimationProcessor().getBone("tail5");
		CoreGeoBone tail6 = this.getAnimationProcessor().getBone("tail6");

		CoreGeoBone t1roll = this.getAnimationProcessor().getBone("t1roll");
		CoreGeoBone t2roll = this.getAnimationProcessor().getBone("t2roll");
		CoreGeoBone t3roll = this.getAnimationProcessor().getBone("t3roll");
		CoreGeoBone t4roll = this.getAnimationProcessor().getBone("t4roll");
		CoreGeoBone t5roll = this.getAnimationProcessor().getBone("t5roll");
		CoreGeoBone t6roll = this.getAnimationProcessor().getBone("t6roll");

		float lerpSpeed = 0.005f;
		float moderation = 0.1f;

		if (animatable.isFlying()) {
			int rollmult = 10;

			core.setRotX(core.getRotX() + extraDataOfType.headPitch() * (Mth.DEG_TO_RAD));
			core.setRotZ(core.getRotZ() + Mth.lerp(animationState.getPartialTick(), -animatable.prevYawDiff * rollmult, -animatable.nowYawDiff * rollmult) * (Mth.DEG_TO_RAD));

			tail1.setRotZ(tail1.getRotZ() + Mth.lerp(animationState.getPartialTick(), -animatable.prevYawDiff * rollmult, -animatable.nowYawDiff * rollmult) * -(Mth.DEG_TO_RAD));

			t1roll.setRotZ(Mth.lerp(animationState.getPartialTick(), -animatable.prevYawDiff * rollmult, -animatable.nowYawDiff * rollmult) * (Mth.DEG_TO_RAD));
			t2roll.setRotZ(Mth.lerp(animationState.getPartialTick(), -animatable.prevYawDiff * rollmult, -animatable.nowYawDiff * rollmult) * (Mth.DEG_TO_RAD));
			t3roll.setRotZ(Mth.lerp(animationState.getPartialTick(), -animatable.prevYawDiff * rollmult, -animatable.nowYawDiff * rollmult) * (Mth.DEG_TO_RAD));
			t4roll.setRotZ(Mth.lerp(animationState.getPartialTick(), -animatable.prevYawDiff * rollmult, -animatable.nowYawDiff * rollmult) * (Mth.DEG_TO_RAD));
			t5roll.setRotZ(Mth.lerp(animationState.getPartialTick(), -animatable.prevYawDiff * rollmult, -animatable.nowYawDiff * rollmult) * (Mth.DEG_TO_RAD));
			t6roll.setRotZ(Mth.lerp(animationState.getPartialTick(), -animatable.prevYawDiff * rollmult, -animatable.nowYawDiff * rollmult) * (Mth.DEG_TO_RAD));
		}

		//System.out.println(extraDataOfType.netHeadYaw());

		tail1.setRotY(tail1.getRotY() + MathHelpers.WrappedLerp(lerpSpeed, animatable.tailPrevYaw * moderation, 0) * Mth.DEG_TO_RAD);
		tail2.setRotY(tail2.getRotY() + MathHelpers.WrappedLerp(lerpSpeed, animatable.tailPrevYaw * moderation, 0) * Mth.DEG_TO_RAD);
		tail3.setRotY(tail3.getRotY() + MathHelpers.WrappedLerp(lerpSpeed, animatable.tailPrevYaw * moderation, 0) * Mth.DEG_TO_RAD);
		tail4.setRotY(tail4.getRotY() + MathHelpers.WrappedLerp(lerpSpeed, animatable.tailPrevYaw * moderation, 0) * Mth.DEG_TO_RAD);
		tail5.setRotY(tail5.getRotY() + MathHelpers.WrappedLerp(lerpSpeed, animatable.tailPrevYaw * moderation, 0) * Mth.DEG_TO_RAD);
		tail6.setRotY(tail6.getRotY() + MathHelpers.WrappedLerp(lerpSpeed, animatable.tailPrevYaw * moderation, 0) * Mth.DEG_TO_RAD);
		//basically lerp from the last angle of the tail relative to the world axis to the current heading of the creature (0)
		//you HAVE to use geckolib's lerpYaw() function(I ported it to MathHelpers so that I can use the same argument order as my own lerp stuff)

		//System.out.println(MathHelpers.WrappedLerp(lerpSpeed, animatable.tailPrevYaw * moderation, 0));

		animatable.tailPrevYaw = MathHelpers.WrappedLerp(0.01f, animatable.tailPrevYaw, 0);
		//return to center
		//must be faster/have higher weight than the following line

		animatable.tailPrevYaw += MathHelpers.WrappedLerp(0.005f, animatable.prevYawDiff, animatable.nowYawDiff);
		//adds deltarotation

		if (animatable.tailPrevYaw < -160 ) {
			animatable.tailPrevYaw = -160;
		} else if (animatable.tailPrevYaw > 160) {
			animatable.tailPrevYaw = 160;
		}
		//clamps the tail rotations

		if (animatable.hasControllingPassenger()) {
			Matrix4f faaaa = torso.getModelSpaceMatrix();
			animatable.riderRotMatrix = faaaa;
		}
	}


}