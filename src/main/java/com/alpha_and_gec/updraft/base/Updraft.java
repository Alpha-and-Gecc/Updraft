package com.alpha_and_gec.updraft.base;

import com.alpha_and_gec.updraft.base.client.entities.model.SteelgoreModel;
import com.alpha_and_gec.updraft.base.client.entities.renderer.SteelgoreRenderer;
import com.alpha_and_gec.updraft.base.common.entities.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import com.alpha_and_gec.updraft.base.registry.UpdraftItems;
import com.alpha_and_gec.updraft.base.registry.UpdraftLayers;
import com.alpha_and_gec.updraft.base.registry.creative.UpdraftCreativeTab;
import com.mojang.logging.LogUtils;
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
    public static final Logger LOGGER = LogUtils.getLogger();

    public Updraft() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);

        UpdraftEntities.register(modEventBus);
        UpdraftCreativeTab.register(modEventBus);
        UpdraftItems.register(modEventBus);
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
            EntityRenderers.register(UpdraftEntities.STEELGORE.get(), SteelgoreRenderer:: new);
        }
    }
}
