package com.alpha_and_gec.updraft.base.registry;

import com.alpha_and_gec.updraft.base.Updraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UpdraftItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Updraft.MOD_ID);

    //Mob drops
    public static final RegistryObject<Item> WYRMBLOOD = ITEMS.register("wyrmblood", () -> new Item(new Item.Properties().stacksTo(16)));

    //Spawn eggs
    public static final RegistryObject<Item> STEELGORE_SPAWN_EGG = ITEMS.register("steelgore_spawn_egg",
            () -> new ForgeSpawnEggItem(UpdraftEntities.STEELGORE, 0xd6d126, 0x7a8e1e, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
