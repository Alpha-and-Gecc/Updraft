package com.alpha_and_gec.updraft.base.common.entities;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;
import java.util.List;

public class SteelgoreEntity extends UpdraftDragon {

    public final net.minecraft.world.entity.AnimationState idleAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState walkAnimationState = new net.minecraft.world.entity.AnimationState();

    public final net.minecraft.world.entity.AnimationState waterIdleAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState swimAnimationState = new net.minecraft.world.entity.AnimationState();

    public final net.minecraft.world.entity.AnimationState airIdleAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState flyAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState diveAnimationState = new net.minecraft.world.entity.AnimationState();

    public final net.minecraft.world.entity.AnimationState goreAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState breathAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState chargeAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState roarAnimationState = new net.minecraft.world.entity.AnimationState();

    public final net.minecraft.world.entity.AnimationState shakeAnimationState = new net.minecraft.world.entity.AnimationState();

    public final net.minecraft.world.entity.AnimationState dieAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState dieWaterAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState dieAirAnimationState = new net.minecraft.world.entity.AnimationState();

    public final int AttackState = 0;


    public SteelgoreEntity(EntityType<? extends Animal> p_30341_, Level p_30342_) {
        super(p_30341_, p_30342_);


    }

    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return UpdraftEntities.STEELGORE.get().create(level);
    }

    public boolean isFood(ItemStack stack) {

        //if (stack.is(UpdraftTags.STEELGORE_DIET) && stack.getCount() == stack.getMaxStackSize())
        //    return true;
        //else {
            return false;
        //}
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1.0D, 40));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 120.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.ATTACK_DAMAGE, 16.0D);
    }

    public static boolean checkSteelgoreSpawnRules(EntityType<SteelgoreEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource rand) {
        Holder<Biome> holder = level.getBiome(position);

        //if (!holder.is(BiomeTags.POLAR_BEARS_SPAWN_ON_ALTERNATE_BLOCKS)) {
            return checkAnimalSpawnRules(entityType, level, spawnType, position, rand);
        //} else {
            //return isBrightEnoughToSpawn(level, position) && level.getBlockState(position.below()).is(UpdraftTags.STEELGORE_SPAWNABLE_BLOCKS);
        //}
    }

    public int getVariant() {
        return 4;
    }

    @Override
    public void checkAnimationState() {
        //walking
        this.idleAnimationState.animateWhen(true, this.tickCount);
        this.walkAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && !this.isInWaterOrBubble() && !this.isFallFlying(), this.tickCount);

        //swimming
        this.waterIdleAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && this.isInWaterOrBubble() && !this.isFallFlying(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && this.isInWaterOrBubble() && !this.isFallFlying(), this.tickCount);

        //flying
        this.airIdleAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && this.isFallFlying(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && !isDiving() && this.isFallFlying(), this.tickCount);
        this.diveAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && isDiving() && this.isFallFlying(), this.tickCount);

        //attacking
        this.goreAnimationState.animateWhen(this.isAlive() && this.AttackState == 1, this.tickCount);
        this.breathAnimationState.animateWhen(this.isAlive() && this.AttackState == 2, this.tickCount);
        this.chargeAnimationState.animateWhen(this.isAlive() && this.AttackState == 3, this.tickCount);
        this.roarAnimationState.animateWhen(!this.isAlive() && this.AttackState == 4, this.tickCount);

        //special
        this.shakeAnimationState.animateWhen(!this.isAlive() && this.justGotOutOfWater(), this.tickCount);

        //death
        this.dieAnimationState.animateWhen(!this.isAlive() && !this.isInWaterOrBubble(), this.tickCount);
        this.dieWaterAnimationState.animateWhen(!this.isAlive() && this.isInWaterOrBubble(), this.tickCount);
        this.dieAirAnimationState.animateWhen(!this.isAlive() && this.isFallFlying(), this.tickCount);
    }
}
