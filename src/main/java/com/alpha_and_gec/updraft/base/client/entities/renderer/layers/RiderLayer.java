package com.alpha_and_gec.updraft.base.client.entities.renderer.layers;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import com.alpha_and_gec.updraft.base.events.ClientEvents;
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
import net.minecraft.world.entity.Entity;
import org.joml.*;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class RiderLayer<T extends UpdraftDragon> extends GeoRenderLayer<T> {
    //gracious thanks to Wyrmroost for having a public github lmao

    protected GeoModel model;

    public RiderLayer(GeoRenderer<T> entityRendererIn) {
        super(entityRendererIn);
        model = this.getGeoModel();

    }

    // The "real" player is hidden in ClientEvents#hidePlayerWhenOnDragon

    @Override
    public void render(PoseStack matrixStackIn, T dragon, BakedGeoModel bakedModel, RenderType renderType,
                       MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick,
                       int packedLight, int packedOverlay) {

        if (dragon.isBaby() || model == null){
            return;
        }

        matrixStackIn.pushPose();

        if (!dragon.getPassengers().isEmpty()) {
            int passengerIndex = 1;
            // Go through all the passengers.
            for (Entity passenger : dragon.getPassengers()) {

                ClientEvents.dragonRiders.remove(passenger.getUUID());

                matrixStackIn.pushPose();
                translateToBody(matrixStackIn, model, passengerIndex, dragon, passenger); // TODO maybe make this only activate on needed frames? EDIT: Probably not, each animation is different and it wouldn't be worth it
                //matrixStackIn.translate(0.0, -0.6f, 0.0);

                matrixStackIn.mulPose(new Quaternionf().fromAxisAngleDeg(new Vector3f(0, 1, 0), dragon.getYRot()));

                renderEntity(passenger, partialTick, matrixStackIn, bufferSource, packedLight);
                matrixStackIn.popPose();
                passengerIndex++;
                ClientEvents.dragonRiders.add(passenger.getUUID());
            }
        }
        matrixStackIn.popPose();
    }

    //ToDo: Verify rider2 bones on each dragon
    protected void translateToBody(PoseStack stack, GeoModel model, int passengerIndex, T dragon, Entity passenger) {
        if (model.getBone("rider" + passengerIndex).isEmpty()) {
            throw new ReportedException(CrashReport.forThrowable(new Throwable(), "Dragon should have a bone named 'rider" + passengerIndex + "' to have a rider layer!"));
        }

        // Get the rider bone, which should be present if the passenger is able to get to this spot.
        GeoBone bone = (GeoBone) model.getBone("rider" + passengerIndex).get();

        Vector3d modelPos = bone.getModelPosition();
        // Scale by 1/16 to get from block bench coordinates to minecraft coordinates.
        modelPos.mul(0.0625f);


        // Translate the player accordingly
        stack.translate(0, 0, 0);


        Matrix4f rot = bone.getModelRotationMatrix();
        // Get the bone's rotation and scale accordingly.

        stack.mulPoseMatrix(rot);

        stack.mulPose(new Quaternionf().fromAxisAngleDeg(new Vector3f(0, 1, 0), dragon.getYRot()));
    }




    private <E extends Entity> void renderEntity(E entityIn, float partialTicks, PoseStack matrixStack, MultiBufferSource bufferIn, int packedLight) {
        EntityRenderer<? super E> render;
        EntityRenderDispatcher manager = Minecraft.getInstance().getEntityRenderDispatcher();

        render = manager.getRenderer(entityIn);
        matrixStack.pushPose();
        try {
            render.render(entityIn, entityIn.yRotO, partialTicks, matrixStack, bufferIn, packedLight);
        } catch (Throwable throwable1) {
            throw new ReportedException(CrashReport.forThrowable(throwable1, "Rendering entity in world"));
        }

        matrixStack.popPose();
    }
}
