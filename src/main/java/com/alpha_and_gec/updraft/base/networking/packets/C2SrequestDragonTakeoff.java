package com.alpha_and_gec.updraft.base.networking.packets;

import com.alpha_and_gec.updraft.base.common.entities.Steelgore.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SrequestDragonTakeoff {

    public C2SrequestDragonTakeoff() {
    }

    public C2SrequestDragonTakeoff(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        ServerPlayer player = context.getSender();

        if (player == null || player.getVehicle() == null) {
            return;
        }

        if (player.isPassenger() && player.getVehicle() instanceof UpdraftDragon beast && beast.getAttackState() == 0) {

            if (!beast.isFlying()) {
                beast.takeOff();
            } else if (beast.onGround()) {
                beast.tryLand();
            }
        }

        context.setPacketHandled(true);
    }
}