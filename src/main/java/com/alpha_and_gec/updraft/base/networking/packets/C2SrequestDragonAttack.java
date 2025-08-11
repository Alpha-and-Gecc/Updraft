package com.alpha_and_gec.updraft.base.networking.packets;

import com.alpha_and_gec.updraft.base.common.entities.Steelgore.SteelgoreEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class C2SrequestDragonAttack {
    private final byte attackStateRequest;

    //Sends a sum of 8 bits


    public C2SrequestDragonAttack(byte attackStateRequest) {
        this.attackStateRequest = attackStateRequest;
    }

    public C2SrequestDragonAttack(FriendlyByteBuf buffer) {
        this(buffer.readByte());
    }

    public void encode(FriendlyByteBuf buffer) {
        buffer.writeByte(attackStateRequest);
    }

    public void handle(Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();

        ServerPlayer player = context.getSender();

        if (player == null) {
            return;
        }

        if (player.isPassenger() && player.getVehicle() instanceof SteelgoreEntity beast && beast.getAttackState() == 0) {
            //normally there would also be beast.getAttackState() == 0 but I removed that temporarily for debugging
            beast.setAttackState(this.attackStateRequest);
            System.out.println("fuck yeah pack et send " + this.attackStateRequest);
        }
        context.setPacketHandled(true);
    }
}
