package com.alpha_and_gec.updraft.base.events;

import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.common.entities.Steelgore.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.networking.UpdraftPacketHandler;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = Updraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event) {
        //register ATTRIBUTES here
        event.put(UpdraftEntities.STEELGORE.get(), SteelgoreEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent entity) {
        //register SPAWNS here
        SpawnPlacements.register(UpdraftEntities.STEELGORE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SteelgoreEntity::checkSteelgoreSpawnRules);
    }

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            UpdraftPacketHandler.register();
        });
    }

}
