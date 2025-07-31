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
            create("steelgore",
                    EntityType.Builder.of(SteelgoreEntity::new, MobCategory.CREATURE)
                            .sized(0.4f, 0.35f));

    private static <T extends Entity> RegistryObject<EntityType<T>> create(String name, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(name, () -> builder.build(Updraft.MOD_ID + "." + name));
    }

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
