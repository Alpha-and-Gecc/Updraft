package com.alpha_and_gec.updraft.base.registry.creative;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;

public class UpdraftCreativeTab {

    public static final CreativeModeTab ITEM_GROUP = register("item_group", FabricItemGroup.builder().icon(SPAWN::getDefaultInstance).title(Component.translatable("spawn.item_group")).displayItems((featureFlagSet, output) -> {
                // angler fish
                output.accept(ANGLER_FISH_SPAWN_EGG);
                output.accept(ANGLER_FISH_BUCKET);
                output.accept(ANGLER_FISH);

                // tuna
                output.accept(TUNA_SPAWN_EGG);
                output.accept(TUNA_CHUNK);
                output.accept(COOKED_TUNA_CHUNK);
                output.accept(TUNA_SANDWICH);
                output.accept(TUNA_EGG_BUCKET);

                // seahorse
                output.accept(SEAHORSE_SPAWN_EGG);
                output.accept(SEAHORSE_BUCKET);

                // snail
                output.accept(SNAIL_SPAWN_EGG);
                output.accept(SNAIL_SHELL);
                output.accept(ESCARGOT);
                output.accept(POTTED_SWEET_BERRIES);
                output.accept(BIG_SNAIL_SHELL);
                output.accept(SNAIL_SHELL_TILES);
                output.accept(SNAIL_SHELL_TILE_STAIRS);
                output.accept(SNAIL_SHELL_TILE_SLAB);
                output.accept(SNAIL_EGGS);
                output.accept(MUCUS);
                output.accept(MUCUS_BLOCK);
                output.accept(GHOSTLY_MUCUS_BLOCK);

                // hamster
                output.accept(HAMSTER_SPAWN_EGG);
                output.accept(SUNFLOWER);
                output.accept(SUNFLOWER_SEEDS);
                output.accept(ROASTED_SUNFLOWER_SEEDS);

                // ant
                output.accept(ANT_SPAWN_EGG);
                output.accept(ANT_MOUND);
                output.accept(ANTHILL);
                output.accept(ROTTEN_LOG_ANTHILL);
                output.accept(ANT_FARM);
                output.accept(ROTTEN_LOG);
                output.accept(ROTTEN_WOOD);
                output.accept(STRIPPED_ROTTEN_LOG);
                output.accept(STRIPPED_ROTTEN_WOOD);
                output.accept(ROTTEN_PLANKS);
                output.accept(CRACKED_ROTTEN_PLANKS);
                output.accept(ROTTEN_STAIRS);
                output.accept(ROTTEN_SLAB);
                output.accept(ROTTEN_FENCE);
                output.accept(ROTTEN_FENCE_GATE);
                output.accept(ROTTEN_DOOR);
                output.accept(ROTTEN_TRAPDOOR);
                output.accept(FALLEN_LEAVES);
                output.accept(ANT_PUPA);
                output.accept(MUSIC_DISC_ROT);
                output.accept(CROWN_POTTERY_SHERD);
                output.accept(SPADE_POTTERY_SHERD);
            }).build()
    );

    private static CreativeModeTab register(String id, CreativeModeTab tab) {
        return Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(Spawn.MOD_ID, id), tab);
    }
}
