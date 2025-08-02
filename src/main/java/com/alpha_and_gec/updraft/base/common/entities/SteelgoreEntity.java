package com.alpha_and_gec.updraft.base.common.entities;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import com.alpha_and_gec.updraft.base.common.entities.goals.DragonHurtByTargetGoal;
import com.alpha_and_gec.updraft.base.common.entities.goals.Steelgore.SteelgoreAttackGoal;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import com.alpha_and_gec.updraft.base.registry.UpdraftTags;
import com.alpha_and_gec.updraft.base.util.IKSolver;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

public class SteelgoreEntity extends UpdraftDragon {
    public final net.minecraft.world.entity.AnimationState stunAnimationState = new net.minecraft.world.entity.AnimationState();
    public final net.minecraft.world.entity.AnimationState sitAnimationState = new net.minecraft.world.entity.AnimationState();
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


    public double chargeCD;
    //cooldown for charge attack, only usable if it is at 0
    public final double chargeCap = 100;

    public double goreCD;
    //cooldown for gore attack, only usable if it is at 0
    public final double goreCap = 20;

    public double breathCharge;
    //cooldown for breath usage, this is unique in that as long as it is below cap the breath charge can be used, and each tick of usage increases the cooldown up to the cap
    //this represents how many ticks it can breath fire for
    public final double breathCap = 60;

    public double roarCD;
    //cooldown for roaring, ONLY APPLIES TO WILD
    public final double roarCap = 3600;


    public boolean isStunned = false;

    public SteelgoreEntity(EntityType<? extends TamableAnimal> p_30341_, Level p_30342_) {
        super(p_30341_, p_30342_);

        this.tailKinematics = new IKSolver(this, 6, 2, 1, 0.75, 2, false, true);
        this.setMeleeRadius(3.5f);
        this.setMaxUpStep(1);
    }

    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return UpdraftEntities.STEELGORE.get().create(level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new SteelgoreAttackGoal(this));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new DragonHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Ravager.class, true));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.ARMOR, 20.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 2D)
                .add(Attributes.ATTACK_DAMAGE, 16.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 5D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 40.0D);
    }

    public void tick() {
        if (this.isStunned()) {
            this.getNavigation().stop();
            this.setTarget(null);
            System.out.println("stun");
        }

        //if (this.getOwner() != null) {
        //    System.out.println("tamed");
        //}

        super.tick();
    }

    public void travel(Vec3 travelVector) {
        if (!this.isStunned()) {
            super.travel(travelVector);
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        boolean blowthrough = false;
        if (source.getDirectEntity() instanceof AbstractArrow) {
            if (((AbstractArrow) source.getDirectEntity()).getPierceLevel() >= 1) {
                blowthrough = true;
            }
        }
        return source.is(DamageTypes.FALL) ||
                source.is(DamageTypes.IN_WALL) ||
                source.is(DamageTypes.CACTUS) ||
                (source.is(DamageTypes.ARROW) && !blowthrough) ||
                source.is(DamageTypes.IN_FIRE) ||
                source.is(DamageTypes.LAVA) ||
                source.is(DamageTypes.HOT_FLOOR) ||
                source.is(DamageTypes.ON_FIRE) ||
                source.is(DamageTypes.UNATTRIBUTED_FIREBALL) ||
                source.is(DamageTypes.FIREBALL) ||
                super.isInvulnerableTo(source);
    }

    public boolean fireImmune() {
        return true;
    }

    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypes.FALLING_ANVIL)) {
            this.stun();
            return super.hurt(pSource, pAmount/5);
        } else {
            return super.hurt(pSource, pAmount);
        }
    }


    @Override
    public void push (Entity pEntity) {
        super.push(pEntity);

        if (this.canContactDamage() && this.getTarget() != null) {
            pEntity.hurt(this.damageSources().mobAttack(this), (float) (this.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 3));

            if (pEntity instanceof LivingEntity liver) {
                Vec3 dirDiff = this.position().subtract(pEntity.position()).normalize();
                liver.knockback(this.getAttributeValue(Attributes.ATTACK_KNOCKBACK), dirDiff.x, dirDiff.z);
                liver.setDeltaMovement(liver.getDeltaMovement().add(0, this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 0.1, 0));
                //yes I know vertical knockback is not proportional to horizontal knockback, it looks funnier
            }
        } else {
            this.setCanContactDamage(false);
        }
    }

    public boolean isFood(ItemStack stack) {
        //System.out.println(stack.is(UpdraftTags.STEELGORE_DIET));
        return stack.is(UpdraftTags.STEELGORE_DIET);
    }

    public boolean tryTame(Player futureOwner, int amount) {
        //basically, depending on how much food is fed(amount), there is a 1/amount chance to tame it

        int rng = random.nextInt(0, 100);

        if (rng < amount/2 && !futureOwner.level().isClientSide()) {
            this.tame(futureOwner);
            this.unstun();
            this.level().broadcastEntityEvent(this, (byte)7);
            System.out.println("tamed");
            return true;
        } else {
            this.unstun();
            this.setTarget(futureOwner);
            return false;
        }
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        // The steelgore can be fed when:
        //Stunned, to tame
        //Tamed and unstunned, to heal and breed
        //Juvenile and unstunned, to grow

        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        int amount = itemstack.getCount();


        if (this.level().isClientSide) {
            boolean flag = this.isOwnedBy(pPlayer) || this.isTame() || isFood(itemstack);
            return flag ? InteractionResult.CONSUME : InteractionResult.PASS;
            //just makes it so that it skips the interaction if it's clientside

        } else if (this.isFood(itemstack)) {
            if (this.isStunned()) {
                if(!this.hasOwner()) {
                    //Try taming if there isn't already an owner
                    this.tryTame(pPlayer, amount);
                    itemstack.shrink(amount);
                    return InteractionResult.SUCCESS;
                }
            } else {
                if(this.getHealth() < this.getMaxHealth()) {
                    //heal
                    float loss = this.getMaxHealth() - this.getHealth();
                    this.heal(loss);
                    itemstack.shrink((int) loss);

                } else if(this.hasTarget()){
                    //grow or breed if can't heal AS LONG AS IT ISN'T AGGRO'd
                    if (this.isBaby()) {
                        //grow
                        this.usePlayerItem(pPlayer, pHand, itemstack);
                        this.ageUp(getSpeedUpSecondsWhenFeeding(-this.getAge()), true);
                        return InteractionResult.sidedSuccess(this.level().isClientSide);

                    } else {
                        //breed
                        if (!this.level().isClientSide && this.getAge() == 0 && this.canFallInLove()) {
                            this.usePlayerItem(pPlayer, pHand, itemstack);
                            this.setInLove(pPlayer);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            }
        } else {
            System.out.println(this.isTame());
            System.out.println((this.getOwner()));

            if (this.isTame() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching()) {
                //if the owner is holding a non - food item whilst shifting, cycle through its behaviour
                this.cycleBehaviour();
                System.out.println(this.getBehaviour());
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    public void tickCDs() {
        //method ran per - tick to tick down cooldowns
        super.tickCDs();

        this.chargeCD = Math.max(0, chargeCD - 1);
        this.goreCD = Math.max(0, goreCD - 1);
        this.roarCD = Math.max(0, breathCharge - 1);
        this.breathCharge = Math.max(0, breathCharge - 1);
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
        this.stunAnimationState.animateWhen(this.isAlive() && !this.walkAnimation.isMoving() && !this.isInWaterOrBubble() && !this.isFallFlying() && this.isStunned(), this.tickCount);
        this.sitAnimationState.animateWhen(this.isAlive() && !this.walkAnimation.isMoving() && !this.isInWaterOrBubble() && !this.isFallFlying() && this.isOrderedToSit() && !this.isStunned(), this.tickCount);
        this.idleAnimationState.animateWhen(this.isAlive() && !this.walkAnimation.isMoving() && !this.isInWaterOrBubble() && !this.isFallFlying() && !this.isOrderedToSit() && !this.isStunned(), this.tickCount);
        this.walkAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && !this.isInWaterOrBubble() && !this.isFallFlying(), this.tickCount);

        //swimming
        this.waterIdleAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && this.isInWaterOrBubble() && !this.isFallFlying(), this.tickCount);
        this.swimAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && this.isInWaterOrBubble() && !this.isFallFlying(), this.tickCount);

        //flying
        this.airIdleAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && this.isFallFlying(), this.tickCount);
        this.flyAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && !isDiving() && this.isFallFlying(), this.tickCount);
        this.diveAnimationState.animateWhen(this.isAlive() && this.walkAnimation.isMoving() && isDiving() && this.isFallFlying(), this.tickCount);

        //attacking
        this.goreAnimationState.animateWhen(this.isAlive() && this.getAttackState() == 1, this.tickCount);
        this.breathAnimationState.animateWhen(this.isAlive() && this.getAttackState() == 2, this.tickCount);
        this.chargeAnimationState.animateWhen(this.isAlive() && this.getAttackState() == 3, this.tickCount);
        this.roarAnimationState.animateWhen(!this.isAlive() && this.getAttackState() == 4, this.tickCount);

        //special
        this.shakeAnimationState.animateWhen(!this.isAlive() && this.justGotOutOfWater(), this.tickCount);
        this.shakeAnimationState.animateWhen(!this.isAlive() && this.isEating(), this.tickCount);

        //death
        this.dieAnimationState.animateWhen(!this.isAlive() && !this.isInWaterOrBubble(), this.tickCount);
        this.dieWaterAnimationState.animateWhen(!this.isAlive() && this.isInWaterOrBubble(), this.tickCount);
        this.dieAirAnimationState.animateWhen(!this.isAlive() && this.isFallFlying(), this.tickCount);
    }

    public void stun() {
        this.isStunned = true;
    }

    public void unstun() {
        this.isStunned = false;
    }

    public boolean isStunned() {
        return this.isStunned;
    }
}
