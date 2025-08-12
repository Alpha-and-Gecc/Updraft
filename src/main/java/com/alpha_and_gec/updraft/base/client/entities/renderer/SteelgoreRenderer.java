package com.alpha_and_gec.updraft.base.client.entities.renderer;

import com.alpha_and_gec.updraft.base.client.entities.renderer.base.UpdraftDragonRenderer;
import com.alpha_and_gec.updraft.base.client.entities.renderer.layers.RiderLayer;
import com.alpha_and_gec.updraft.base.common.entities.Steelgore.SteelgoreEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
public class SteelgoreRenderer<T extends SteelgoreEntity> extends UpdraftDragonRenderer<T> {


    public SteelgoreRenderer(EntityRendererProvider.Context renderManager, GeoModel<T> modelProvider) {
        super(renderManager, modelProvider);
        this.addRenderLayer(new RiderLayer<>(this));
    }

    @Override
    public RenderType getRenderType(T animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public void preRender(PoseStack stackIn, T animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        super.preRender(stackIn, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);

        if (animatable.isBaby()) {
            stackIn.scale(0.5F, 0.5F, 0.5F);
        }
    }

}