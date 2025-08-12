package com.alpha_and_gec.updraft.base.networking.packets;

import com.alpha_and_gec.updraft.base.common.entities.Steelgore.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SrequestDragonPower {

    public C2SrequestDragonPower() {
    }

    public C2SrequestDragonPower(FriendlyByteBuf buffer) {
    }

    public void encode(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        ServerPlayer player = context.getSender();

        if (player == null || player.getVehicle() == null) {
            return;
        }

        if (player.isPassenger() && player.getVehicle() instanceof UpdraftDragon beast && beast.getAttackState() == 0 && beast.onGround()) {
            //normally there would also be beast.getAttackState() == 0 but I removed that temporarily for debugging
            beast.setPowered(true);
        }

        context.setPacketHandled(true);
    }
}
