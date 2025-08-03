package com.alpha_and_gec.updraft.base.registry;

import com.alpha_and_gec.updraft.base.Updraft;
import com.alpha_and_gec.updraft.base.common.entities.SteelgoreEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class UpdraftEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Updraft.MOD_ID);

    public static final RegistryObject<EntityType<SteelgoreEntity>> STEELGORE =
            ENTITY_TYPES.register("steelgore",
                    () -> EntityType.Builder.of(SteelgoreEntity::new, MobCategory.CREATURE)
                            .sized(3f, 3f)
                            .clientTrackingRange(10)
                            .build(new ResourceLocation(Updraft.MOD_ID, "steelgore").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
