package com.alpha_and_gec.updraft.base;

import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import com.alpha_and_gec.updraft.base.registry.UpdraftItems;
import com.alpha_and_gec.updraft.base.registry.creative.UpdraftCreativeTab;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

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
}
