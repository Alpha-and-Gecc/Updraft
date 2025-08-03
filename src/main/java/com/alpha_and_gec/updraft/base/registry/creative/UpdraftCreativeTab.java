package com.alpha_and_gec.updraft.base.registry.creative;

import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.registry.UpdraftItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class UpdraftCreativeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Updraft.MOD_ID);

    public static final RegistryObject<CreativeModeTab> UPDRAFT_TAB = CREATIVE_MODE_TABS.register("updraft_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(UpdraftItems.WYRMBLOOD.get()))
                    .title(Component.translatable("creativetab.updraft_tab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(UpdraftItems.STEELGORE_SPAWN_EGG.get());
                        pOutput.accept(UpdraftItems.WYRMBLOOD.get());
                        pOutput.accept(UpdraftItems.WYRMWOUND_POTION.get());
                        pOutput.accept(UpdraftItems.WYRMSBANITE_INGOT.get());
                        pOutput.accept(UpdraftItems.RAW_WYRMSBANITE.get());
                        pOutput.accept(UpdraftItems.DRAKESCALE.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
