package com.alpha_and_gec.updraft.base.client.entities.renderer;

import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.client.entities.model.SteelgoreModel;
import com.alpha_and_gec.updraft.base.common.entities.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.registry.UpdraftLayers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Fox;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

@OnlyIn(Dist.CLIENT)
public class SteelgoreRenderer extends MobRenderer<SteelgoreEntity, SteelgoreModel<SteelgoreEntity>> {

    private static final ResourceLocation STEELGORE_BARIOTH = new ResourceLocation(Updraft.MOD_ID,"textures/entity/goby/steelgore_barioth.png");
    private static final ResourceLocation STEELGORE_SILVERASH = new ResourceLocation(Updraft.MOD_ID, "textures/entity/goby/steelgore_silverash.png");
    private static final ResourceLocation STEELGORE_ELDERFROST = new ResourceLocation(Updraft.MOD_ID, "textures/entity/goby/steelgore_elderfrost.png");
    private static final ResourceLocation STEELGORE_OPALINE = new ResourceLocation(Updraft.MOD_ID, "textures/entity/goby/steelgore_opaline.png");
    private static final ResourceLocation STEELGORE_OKAPI = new ResourceLocation(Updraft.MOD_ID, "textures/entity/goby/steelgore_okapi.png");
    private static final ResourceLocation STEELGORE_TAPIR = new ResourceLocation(Updraft.MOD_ID, "textures/entity/goby/steelgore_tapir.png");
    private static final ResourceLocation STEELGORE_WILDEBEEST = new ResourceLocation(Updraft.MOD_ID, "textures/entity/goby/steelgore_wildebeest.png");
    private static final ResourceLocation STEELGORE_BISON = new ResourceLocation(Updraft.MOD_ID, "textures/entity/goby/steelgore_bison.png");
    private static final ResourceLocation STEELGORE_ALBINO = new ResourceLocation(Updraft.MOD_ID, "textures/entity/goby/steelgore_albino.png");
    private static final ResourceLocation STEELGORE_MELANISTIC = new ResourceLocation(Updraft.MOD_ID, "textures/entity/goby/steelgore_melanistic.png");


    private static final ResourceLocation TEXTURE = new ResourceLocation(Updraft.MOD_ID, "textures/entity/steelgore/steelgore.png");

    public SteelgoreRenderer(EntityRendererProvider.Context context) {
        super(context, new SteelgoreModel<>(context.bakeLayer(UpdraftLayers.STEELGORE_LAYER)), 0.4F);
    }
    protected void scale(SteelgoreEntity entitylivingbaseIn, PoseStack matrixStackIn, float partialTickTime) {
    }

    protected void setupRotations(SteelgoreEntity p_114738_, PoseStack p_114739_, float p_114740_, float p_114741_, float p_114742_) {
        super.setupRotations(p_114738_, p_114739_, p_114740_, p_114741_, p_114742_);

    }

    @Override
    protected @Nullable RenderType getRenderType(SteelgoreEntity entity, boolean bodyVisible, boolean translucent, boolean glowing) {
        return RenderType.entityCutoutNoCull(TEXTURE);
    }

    public ResourceLocation getTextureLocation(SteelgoreEntity entity) {
        return switch (entity.getVariant()) {
            case 1 -> STEELGORE_BARIOTH;
            case 2 -> STEELGORE_SILVERASH;
            case 3 -> STEELGORE_ELDERFROST;
            case 4 -> STEELGORE_OPALINE;
            case 5 -> STEELGORE_OKAPI;
            case 6 -> STEELGORE_TAPIR;
            case 7 -> STEELGORE_WILDEBEEST;
            case 8 -> STEELGORE_BISON;
            case 9 -> STEELGORE_ALBINO;
            case 10 -> STEELGORE_MELANISTIC;

            default -> STEELGORE_OKAPI;
        };
    }
}