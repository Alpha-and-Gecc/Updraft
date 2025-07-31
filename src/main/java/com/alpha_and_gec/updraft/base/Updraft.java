package com.alpha_and_gec.updraft.base;

import com.alpha_and_gec.updraft.base.client.entities.model.SteelgoreModel;
import com.alpha_and_gec.updraft.base.client.entities.renderer.SteelgoreRenderer;
import com.alpha_and_gec.updraft.base.common.entities.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import com.alpha_and_gec.updraft.base.registry.UpdraftItems;
import com.alpha_and_gec.updraft.base.registry.UpdraftLayers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Updraft.MOD_ID)
public class Updraft {
    public static final String MOD_ID = "updraft";

    public Updraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //UpdraftCreativeTabs.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        UpdraftItems.register(modEventBus);
        UpdraftEntities.register(modEventBus);
        //UpdraftBlocks.BLOCKS.register(modEventBus);
        //UpdraftSounds.REGISTER.register(modEventBus);
        //UpdraftLootModifiers.register(modEventBus);
        //UpdraftFeatures.FEATURES.register(modEventBus);
        //UpdraftPOI.DEF_REG.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        //GeckoLib.initialize();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            //register RENDERERS here
            EntityRenderers.register(UpdraftEntities.STEELGORE.get(), SteelgoreRenderer:: new);

        }
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        //register LAYERS here
        event.registerLayerDefinition(UpdraftLayers.STEELGORE_LAYER, SteelgoreModel::createBodyLayer);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        //register ATTRIBUTES here
        event.put(UpdraftEntities.STEELGORE.get(), SteelgoreEntity.createAttributes().build());
    }

    private void registerCommon(FMLCommonSetupEvent event) {
        //register SPAWNS here
        SpawnPlacements.register(UpdraftEntities.STEELGORE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING, SteelgoreEntity::checkSteelgoreSpawnRules);

        event.enqueueWork(() -> {
            //PUT THINGS IN TAGS IN HERE
            //ComposterBlock.COMPOSTABLES.put(AAItems.WORM.get().asItem(), 1.0F);
        });
    }
}
