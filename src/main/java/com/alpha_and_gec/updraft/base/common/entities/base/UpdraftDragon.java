package com.alpha_and_gec.updraft.base.common.entities.base;

import com.alpha_and_gec.updraft.base.common.entities.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.util.IKSolver;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public abstract class UpdraftDragon extends TamableAnimal {
    public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.INT);
    //states determining which animation this beast should play

    private static final EntityDataAccessor<Integer> BEHAVIOUR_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.INT);
    //0 = wander
    //1 = sit
    //2 = follow

    private boolean selfFriendly = false;
    //^do NOT edit this variable outside of the constructor

    public IKSolver tailKinematics;

    public boolean flightState = false;

    public boolean contactDmg = false;

    public float meleeRadius = 3.5F;

    public int wetness = 0;
    public int wetlim = 100;
    //how many ticks does it take to dry

    protected UpdraftDragon(EntityType<? extends TamableAnimal> p_30341_, Level p_30342_) {
        super(p_30341_, p_30342_);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 1);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(BEHAVIOUR_STATE, 0);
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
        } else {
            this.switchAnimationState();
        }
    }

    public void travel(Vec3 travelVector) {

        if (this.isOrderedToSit()) {
            this.getNavigation().stop();
            this.setTarget(null);
            System.out.println("sitte");
        } else {
            super.travel(travelVector);
        }

    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        return super.mobInteract(pPlayer, pHand);


    }

    public void tickCDs() {
        //method ran per - tick to tick down cooldowns
    }

    public void checkAnimationState() {
        //method ran per - tick to assert which animation a dragon ought to play
    }

    public void switchAnimationState() {
        //method ran per - tick to assert which animation a dragon ought to play
        //serverside only, ran to guarantee checkAnimationState works
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
        this.entityData.set(ATTACK_STATE, state);
    }

    public int getAttackState() {
        return this.entityData.get(ATTACK_STATE);
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

    public boolean hasTarget() {
        return this.getTarget() != null;
    }


    public boolean hasOwner() {
        return this.getOwner() != null;
    }

    public void setBehaviour(int state) {
        this.entityData.set(BEHAVIOUR_STATE, state);
    }

    public void cycleBehaviour() {
        switch (this.entityData.get(BEHAVIOUR_STATE)) {
            case 0 -> this.sit();
            case 1 -> this.follow();
            case 2 -> this.wander();
        }
    }

    public void wander() {
        this.setOrderedToSit(false);
        this.setInSittingPose(false);
        this.setBehaviour(0);
    }

    public void sit() {
        this.setOrderedToSit(true);
        //ordered to sit tells goals that tghe entity is sitting
        this.setInSittingPose(true);
        this.setBehaviour(1);
    }

    public void follow() {
        this.setOrderedToSit(false);
        this.setInSittingPose(false);
        this.setBehaviour(2);
    }

    public int getBehaviour() {
        return this.entityData.get(BEHAVIOUR_STATE);
    }

    public boolean isWandering() {
        return this.getBehaviour() == 0;
    }

    public boolean isFollowing() {
        return this.getBehaviour() == 2;
    }


    public void setSelfFriendly(boolean state) {
        this.selfFriendly = state;
    }

    public boolean isSelfFriendly() {
        return this.selfFriendly;
    }
}
