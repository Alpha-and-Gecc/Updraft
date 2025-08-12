package com.alpha_and_gec.updraft.base.events;

import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.client.entities.model.SteelgoreModel;
import com.alpha_and_gec.updraft.base.client.entities.renderer.SteelgoreRenderer;
import com.alpha_and_gec.updraft.base.networking.UpdraftPacketHandler;
import com.alpha_and_gec.updraft.base.networking.packets.C2SrequestDragonAttack;
import com.alpha_and_gec.updraft.base.networking.packets.C2SrequestDragonPower;
import com.alpha_and_gec.updraft.base.networking.packets.C2SrequestDragonTakeoff;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import com.alpha_and_gec.updraft.base.registry.UpdraftKeybindings;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
}