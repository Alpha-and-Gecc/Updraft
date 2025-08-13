package com.alpha_and_gec.updraft.base.client.entities.renderer.layers;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import com.alpha_and_gec.updraft.base.events.ClientEvents;
import com.alpha_and_gec.updraft.base.util.MathHelpers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.*;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.joml.*;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;
import software.bernie.geckolib.util.RenderUtils;

import java.nio.ByteBuffer;
import java.util.Vector;

public class RiderLayer<T extends UpdraftDragon> extends GeoRenderLayer<T> {
    //gracious thanks to Wyrmroost for having a public github lmao

    protected GeoModel model;

    public RiderLayer(GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
        model = this.getGeoModel();

    }

    @Override
    public void render(PoseStack matrixStackIn, T creature, BakedGeoModel bakedModel, RenderType renderType,
                       MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
                       int packedLight, int packedOverlay) {

        if (creature.isBaby() || model == null){
            return;
        }


        if (!creature.getPassengers().isEmpty()) {
            for (Entity passenger : creature.getPassengers()) {

                matrixStackIn.pushPose();

                GeoBone root = (GeoBone) model.getBone("root").get();
                GeoBone rider = (GeoBone) model.getBone("rider1").get();
                Vector3d modelPos = rider.getPositionVector();

                modelPos.mul(0.0625f);
                //downscale factor
                matrixStackIn.translate(modelPos.x, modelPos.y + 2, modelPos.z);

                matrixStackIn.mulPoseMatrix(rider.getModelRotationMatrix());

                renderEntity(passenger, partialTick, matrixStackIn, bufferSource, packedLight);

                matrixStackIn.popPose();
            }
        }
    }




    private <E extends Entity> void renderEntity(E entityIn, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
        EntityRenderer<? super E> render;
        EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();

        render = manager.getRenderer(entityIn);
        matrixStack.pushPose();

        try {
            render.render(entityIn, entityIn.yRotO, partialTicks, matrixStack, bufferIn, packedLight);
        } catch (Throwable throwable1) {
            throw new ReportedException(CrashReport.forThrowable(throwable1, "Error rendering entity in world"));
        }

        matrixStack.popPose();
    }
}
