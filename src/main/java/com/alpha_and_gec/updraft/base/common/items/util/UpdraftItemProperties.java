package com.alpha_and_gec.updraft.base.common.items.util;


import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.registry.UpdraftItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class UpdraftItemProperties {

    /*public static void registerItemProperties() {
        for (RegistryObject<Item> item : UpdraftItems.ITEMS.getEntries()) {
            if (item.get() instanceof ItemModFishBucket) {
                registerVariantProperties(item.get());
            }
        }
    }

    public static void registerVariantProperties(Item item) {
        ItemProperties.register(item, new ResourceLocation(Updraft.MOD_ID, "variant"), (stack, world, player, i) -> stack.hasTag() ? stack.getOrCreateTag().getInt("BucketVariantTag") : 0);
    }*/
}
