package com.alpha_and_gec.updraft.base.events;

import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.client.entities.model.SteelgoreModel;
import com.alpha_and_gec.updraft.base.client.entities.renderer.SteelgoreRenderer;
import com.alpha_and_gec.updraft.base.client.entities.renderer.base.UpdraftDragonRenderer;
import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import com.alpha_and_gec.updraft.base.networking.UpdraftPacketHandler;
import com.alpha_and_gec.updraft.base.networking.packets.C2SrequestDragonAttack;
import com.alpha_and_gec.updraft.base.networking.packets.C2SrequestDragonPower;
import com.alpha_and_gec.updraft.base.networking.packets.C2SrequestDragonTakeoff;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import com.alpha_and_gec.updraft.base.registry.UpdraftKeybindings;
import com.alpha_and_gec.updraft.base.util.MathHelpers;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import net.minecraft.CrashReport;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Matrix4f;
import org.joml.Vector3d;
import org.joml.Vector3f;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Updraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientForgeEvents {

    //Action events
    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event) {
        Minecraft game = Minecraft.getInstance();

        if(UpdraftKeybindings.INSTANCE.dragonMelee.consumeClick() && game.player != null) {
            UpdraftPacketHandler.sendToServer(new C2SrequestDragonAttack((byte) 1));
        }

        if(UpdraftKeybindings.INSTANCE.dragonRanged.consumeClick() && game.player != null) {
            UpdraftPacketHandler.sendToServer(new C2SrequestDragonAttack((byte) 2));
        }

        if(UpdraftKeybindings.INSTANCE.dragonSpecial.consumeClick() && game.player != null) {
            UpdraftPacketHandler.sendToServer(new C2SrequestDragonAttack((byte) 3));
        }

        if(UpdraftKeybindings.INSTANCE.dragonRoar.consumeClick() && game.player != null) {
            UpdraftPacketHandler.sendToServer(new C2SrequestDragonAttack((byte) 4));
        }

        if(UpdraftKeybindings.INSTANCE.dragonTakeoff.consumeClick() && game.player != null && game.player.getVehicle() != null) {
            UpdraftPacketHandler.sendToServer(new C2SrequestDragonTakeoff());
        }

        if(UpdraftKeybindings.INSTANCE.dragonPower.isDown() && game.player != null && game.player.getVehicle() != null) {
            UpdraftPacketHandler.sendToServer(new C2SrequestDragonPower());
        }

    }

    @SubscribeEvent
    public static void renderPassengers(RenderLivingEvent.Pre event) {

        if (event.getEntity().isPassenger() && event.getEntity().getVehicle() instanceof UpdraftDragon) {
            UpdraftDragon dragon = (UpdraftDragon) event.getEntity().getVehicle();
            Matrix4f matrix = dragon.riderRotMatrix;

            Vec3 V = MathHelpers.makeUpVector3d(dragon);
            Transformation t = new Transformation(matrix.rotateLocal((dragon.getYRot() + 180) * Mth.DEG_TO_RAD, (float) V.x(), (float) V.y(), (float) V.z())
                    .rotateY((dragon.getYRot() - 180) * Mth.DEG_TO_RAD)
                    .translateLocal(0f, -2f, 0f)
            );

            pushTransformation(t, event.getPoseStack());
        }

    }

    public static void pushTransformation(Transformation transformation, PoseStack self)
    {

        Vector3f trans = transformation.getTranslation();
        self.translate(trans.x(), trans.y(), trans.z());

        self.mulPose(transformation.getLeftRotation());

        Vector3f scale = transformation.getScale();
        self.scale(scale.x(), scale.y(), scale.z());

        self.mulPose(transformation.getRightRotation());
    }
}