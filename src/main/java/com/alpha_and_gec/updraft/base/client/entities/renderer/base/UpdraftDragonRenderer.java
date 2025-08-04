package com.alpha_and_gec.updraft.base.client.entities.renderer.base;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Pose;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class UpdraftDragonRenderer<T extends Mob, M extends EntityModel<T>> extends MobRenderer<T, M> implements RenderLayerParent<T, M> {
    //this bullshit solely exists so that dragons don't roll over when sleeping or dead
    //they still flip if they're named dinnerbone tho

    public UpdraftDragonRenderer(EntityRendererProvider.Context pContext, M pModel, float pShadowRadius) {
        super(pContext, pModel, pShadowRadius);
    }

    @Override
    public void render(T pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        //This function pulls up stuff all the way back to EntityRenderer!!!

        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Pre<T, M>(pEntity, this, pPartialTicks, pPoseStack, pBuffer, pPackedLight))) return;
        pPoseStack.pushPose();
        this.model.attackTime = this.getAttackAnim(pEntity, pPartialTicks);

        boolean shouldSit = pEntity.isPassenger() && (pEntity.getVehicle() != null && pEntity.getVehicle().shouldRiderSit());
        this.model.riding = shouldSit;
        this.model.young = pEntity.isBaby();
        float f = Mth.rotLerp(pPartialTicks, pEntity.yBodyRotO, pEntity.yBodyRot);
        float f1 = Mth.rotLerp(pPartialTicks, pEntity.yHeadRotO, pEntity.yHeadRot);
        float f2 = f1 - f;
        if (shouldSit && pEntity.getVehicle() instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)pEntity.getVehicle();
            f = Mth.rotLerp(pPartialTicks, livingentity.yBodyRotO, livingentity.yBodyRot);
            f2 = f1 - f;
            float f3 = Mth.wrapDegrees(f2);
            if (f3 < -85.0F) {
                f3 = -85.0F;
            }

            if (f3 >= 85.0F) {
                f3 = 85.0F;
            }

            f = f1 - f3;
            if (f3 * f3 > 2500.0F) {
                f += f3 * 0.2F;
            }

            f2 = f1 - f;
        }

        float f6 = Mth.lerp(pPartialTicks, pEntity.xRotO, pEntity.getXRot());
        if (isEntityUpsideDown(pEntity)) {
            f6 *= -1.0F;
            f2 *= -1.0F;
        }

        if (pEntity.hasPose(Pose.SLEEPING)) {
            Direction direction = pEntity.getBedOrientation();
            if (direction != null) {
                float f4 = pEntity.getEyeHeight(Pose.STANDING) - 0.1F;
                pPoseStack.translate((float)(-direction.getStepX()) * f4, 0.0F, (float)(-direction.getStepZ()) * f4);
            }
        }

        float f7 = this.getBob(pEntity, pPartialTicks);
        this.setupRotations(pEntity, pPoseStack, f7, f, pPartialTicks);
        pPoseStack.scale(-1.0F, -1.0F, 1.0F);
        this.scale(pEntity, pPoseStack, pPartialTicks);
        pPoseStack.translate(0.0F, -1.501F, 0.0F);
        float f8 = 0.0F;
        float f5 = 0.0F;
        if (!shouldSit && pEntity.isAlive()) {
            f8 = pEntity.walkAnimation.speed(pPartialTicks);
            f5 = pEntity.walkAnimation.position(pPartialTicks);
            if (pEntity.isBaby()) {
                f5 *= 3.0F;
            }

            if (f8 > 1.0F) {
                f8 = 1.0F;
            }
        }

        this.model.prepareMobModel(pEntity, f5, f8, pPartialTicks);
        this.model.setupAnim(pEntity, f5, f8, f7, f2, f6);
        Minecraft minecraft = Minecraft.getInstance();
        boolean flag = this.isBodyVisible(pEntity);
        boolean flag1 = !flag && !pEntity.isInvisibleTo(minecraft.player);
        boolean flag2 = minecraft.shouldEntityAppearGlowing(pEntity);
        RenderType rendertype = this.getRenderType(pEntity, flag, flag1, flag2);
        if (rendertype != null) {
            VertexConsumer vertexconsumer = pBuffer.getBuffer(rendertype);
            int i = getOverlayCoordsDragon(pEntity, this.getWhiteOverlayProgress(pEntity, pPartialTicks));
            System.out.println(i);
            this.model.renderToBuffer(pPoseStack, vertexconsumer, pPackedLight, i, 1.0F, 1.0F, 1.0F, flag1 ? 0.15F : 1.0F);
        }

        if (!pEntity.isSpectator()) {
            for(RenderLayer<T, M> renderlayer : this.layers) {
                renderlayer.render(pPoseStack, pBuffer, pPackedLight, pEntity, f5, f8, pPartialTicks, f7, f2, f6);
            }
        }

        pPoseStack.popPose();

        Entity entity = pEntity.getLeashHolder();
        if (entity != null) {
            this.renderLeash(pEntity, pPartialTicks, pPoseStack, pBuffer, entity);
        }

        var renderNameTagEvent = new net.minecraftforge.client.event.RenderNameTagEvent(pEntity, pEntity.getDisplayName(), this, pPoseStack, pBuffer, pPackedLight, pPartialTicks);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(renderNameTagEvent);
        if (renderNameTagEvent.getResult() != net.minecraftforge.eventbus.api.Event.Result.DENY && (renderNameTagEvent.getResult() == net.minecraftforge.eventbus.api.Event.Result.ALLOW || this.shouldShowName(pEntity))) {
            this.renderNameTag(pEntity, renderNameTagEvent.getContent(), pPoseStack, pBuffer, pPackedLight);
        }
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.RenderLivingEvent.Post<T, M>(pEntity, this, pPartialTicks, pPoseStack, pBuffer, pPackedLight));
    }


    @Override
    protected void setupRotations(T pEntityLiving, PoseStack pPoseStack, float pAgeInTicks, float pRotationYaw, float pPartialTicks) {
        if (this.isShaking(pEntityLiving)) {
            pRotationYaw += (float)(Math.cos((double)pEntityLiving.tickCount * 3.25D) * Math.PI * (double)0.4F);
        }

        if (!pEntityLiving.hasPose(Pose.SLEEPING)) {
            pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pRotationYaw));
        }

        if (pEntityLiving.isAutoSpinAttack()) {
            pPoseStack.mulPose(Axis.XP.rotationDegrees(-90.0F - pEntityLiving.getXRot()));
            pPoseStack.mulPose(Axis.YP.rotationDegrees(((float)pEntityLiving.tickCount + pPartialTicks) * -75.0F));
        }else if (isEntityUpsideDown(pEntityLiving)) {
            pPoseStack.translate(0.0F, pEntityLiving.getBbHeight() + 0.1F, 0.0F);
            pPoseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }

    }

    public static int getOverlayCoordsDragon(LivingEntity pLivingEntity, float pU) {
        return OverlayTexture.pack(OverlayTexture.u(pU), OverlayTexture.v(pLivingEntity.hurtTime > 0));
    }


}
