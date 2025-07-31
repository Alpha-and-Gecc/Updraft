package com.alpha_and_gec.updraft.base.common.entities.base;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

public class UpdraftDragon extends Animal {

    protected UpdraftDragon(EntityType<? extends Animal> p_30341_, Level p_30342_) {
        super(p_30341_, p_30342_);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public int getVariant() {
        return 1;
    }
}
