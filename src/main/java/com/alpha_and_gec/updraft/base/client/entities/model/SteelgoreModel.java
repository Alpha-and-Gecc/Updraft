package com.alpha_and_gec.updraft.base.client.entities.model;// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.client.entities.animations.SteelgoreAnimations;
import com.alpha_and_gec.updraft.base.common.entities.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.util.MathHelpers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
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

		//CoreGeoBone neck = this.getAnimationProcessor().getBone("neck");
		//CoreGeoBone head = this.getAnimationProcessor().getBone("head");
	}

}