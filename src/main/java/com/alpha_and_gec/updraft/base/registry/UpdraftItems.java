package com.alpha_and_gec.updraft.base.registry;

import com.alpha_and_gec.updraft.base.Updraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
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
    public static final RegistryObject<Item> DRAKESCALE = ITEMS.register("drakescale", () -> new Item(new Item.Properties()));

    //Ores and ingots
    public static final RegistryObject<Item> WYRMSBANITE_INGOT = ITEMS.register("wyrmsbanite_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_WYRMSBANITE = ITEMS.register("raw_wyrmsbanite", () -> new Item(new Item.Properties()));

    //Potions
    public static final RegistryObject<Item> WYRMWOUND_POTION = ITEMS.register("wyrmwound_potion", () -> new PotionItem(new Item.Properties().stacksTo(16)));

    //Spawn eggs
    public static final RegistryObject<Item> STEELGORE_SPAWN_EGG = ITEMS.register("steelgore_spawn_egg",
            () -> new ForgeSpawnEggItem(UpdraftEntities.STEELGORE, 0x42170c, 0xaf9e95, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
