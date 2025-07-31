package com.alpha_and_gec.updraft.base.registry;

import com.alpha_and_gec.updraft.base.Updraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UpdraftItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Updraft.MOD_ID);

    //Mob drops
    public static final RegistryObject<Item> WYRMBLOOD = ITEMS.register("wyrmblood", () -> new Item(new Item.Properties().stacksTo(16)));

    //Spawn eggs
    public static final Item STEELGORE_SPAWN_EGG = register("steelgore_spawn_egg", new SpawnEggItem(UpdraftEntities.STEELGORE.get(), 0x5D3F30, 0xF6DEA2, new Item.Properties()));

    private static Item register(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(Updraft.MOD_ID, id), item);
    }
}
