package com.alpha_and_gec.updraft.base.networking;

import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.networking.packets.C2SrequestDragonAttack;
import com.alpha_and_gec.updraft.base.networking.packets.C2SrequestDragonPower;
import com.alpha_and_gec.updraft.base.networking.packets.C2SrequestDragonTakeoff;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.lwjgl.system.windows.MSG;

public class UpdraftPacketHandler {
    public static final SimpleChannel MAIN_CHANNEL = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Updraft.MOD_ID, "main"))
            .serverAcceptedVersions(a -> true)
            .clientAcceptedVersions(a -> true)
            .networkProtocolVersion(()-> NetworkConstants.NETVERSION)
            .simpleChannel();

    public static void register() {
        MAIN_CHANNEL.messageBuilder(C2SrequestDragonAttack.class, 19800, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SrequestDragonAttack::encode)
                .decoder(C2SrequestDragonAttack::new)
                .consumerMainThread(C2SrequestDragonAttack::handle)
                .add();

        MAIN_CHANNEL.messageBuilder(C2SrequestDragonTakeoff.class, 19801, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SrequestDragonTakeoff::encode)
                .decoder(C2SrequestDragonTakeoff::new)
                .consumerMainThread(C2SrequestDragonTakeoff::handle)
                .add();

        MAIN_CHANNEL.messageBuilder(C2SrequestDragonPower.class, 19802, NetworkDirection.PLAY_TO_SERVER)
                .encoder(C2SrequestDragonPower::encode)
                .decoder(C2SrequestDragonPower::new)
                .consumerMainThread(C2SrequestDragonPower::handle)
                .add();
        //^ sends a request to set dragon attack state to the given state
    }

    public static void sendToServer(Object packet) {
        MAIN_CHANNEL.send(PacketDistributor.SERVER.noArg(), packet);

    }

    public static void sendToAllClients(Object packet) {
        MAIN_CHANNEL.send(PacketDistributor.ALL.noArg(), packet);

    }

    public static void sendToPlayer(Object packet) {
        MAIN_CHANNEL.send(PacketDistributor.PLAYER.noArg(), packet);

    }

}
