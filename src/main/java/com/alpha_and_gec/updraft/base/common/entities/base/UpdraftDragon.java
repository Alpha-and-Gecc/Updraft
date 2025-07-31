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

    public void tick() {
        super.tick();

        if (this.level().isClientSide()){
            this.checkAnimationState();
        }
    }


    public void checkAnimationState() {
        //method ran per - tick to assert which animation a dragon ought to play
    }

    public boolean justGotOutOfWater() {
        //check when was the last time the dragon touched water
        return false;
    }

    public boolean isDiving() {
        //check if the dragon is at the correct angle to play the diving animation
        return false;
    }
}
