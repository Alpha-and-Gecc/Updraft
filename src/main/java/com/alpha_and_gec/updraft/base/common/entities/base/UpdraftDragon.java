package com.alpha_and_gec.updraft.base.common.entities.base;

import com.alpha_and_gec.updraft.base.util.IKSolver;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.jetbrains.annotations.Nullable;

public class UpdraftDragon extends Animal {

    public IKSolver tailKinematics;

    public boolean flightState = false;
    public int attackState = 0;
    //state determining which animation this beast should play

    public boolean contactDmg = false;

    public float meleeRadius = 3.5F;

    public int wetness = 0;
    public int wetlim = 100;
    //how many ticks does it take to dry

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

        this.tailKinematics.TakePerTickAction(this);
        this.tickCDs();

        if (this.isInWaterRainOrBubble()) {
            wetness = Math.min(wetlim, wetness + 1);
        } else {
            wetness = Math.max(0, wetness - 1);
        }

        if (this.level().isClientSide()){
            this.checkAnimationState();
        }
    }

    public void tickCDs() {
        //method ran per - tick to tick down cooldowns
    }

    public void checkAnimationState() {
        //method ran per - tick to assert which animation a dragon ought to play
    }


    public boolean justGotOutOfWater() {
        //check when was the last time the dragon touched water
        return this.wetness > 0;
    }

    public boolean isDiving() {
        //check if the dragon is at the correct angle to play the diving animation

        if (this.isFallFlying() && this.flightState == true && this.getXRot() > 75) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEating() {
        //check if the dragon is eating smthing
        return false;
    }

    public void setAttackState(int state) {
        this.attackState = state;
    }

    public int getAttackState() {
        return this.attackState;
    }

    public void setCanContactDamage(boolean state) {
        this.contactDmg = state;
    }

    public boolean canContactDamage() {
        return this.contactDmg;
    }

    public void setMeleeRadius(float state) {
        this.meleeRadius = state;
    }

    public float getMeleeRadius() {
        return this.meleeRadius;
    }
}
