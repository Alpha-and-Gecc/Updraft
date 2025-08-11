package com.alpha_and_gec.updraft.base.events;

import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.client.entities.model.SteelgoreModel;
import com.alpha_and_gec.updraft.base.client.entities.renderer.SteelgoreRenderer;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import com.alpha_and_gec.updraft.base.registry.UpdraftKeybindings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Updraft.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    //@SubscribeEvent
    //public static void init(final FMLClientSetupEvent event) {
    //    event.enqueueWork(UpdraftItemProperties::registerItemProperties);
    //}

    //Registries
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(UpdraftEntities.STEELGORE.get(), e -> new SteelgoreRenderer<>(e, new SteelgoreModel()));

    }

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(UpdraftKeybindings.INSTANCE.dragonMelee);
        event.register(UpdraftKeybindings.INSTANCE.dragonRanged);
        event.register(UpdraftKeybindings.INSTANCE.dragonSpecial);
        event.register(UpdraftKeybindings.INSTANCE.dragonRoar);
    }
}