package com.alpha_and_gec.updraft.base.common.entities.Steelgore;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import com.alpha_and_gec.updraft.base.common.entities.goals.DragonHurtByTargetGoal;
import com.alpha_and_gec.updraft.base.common.entities.navigation.DragonLandRotcappedMoveControl;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import com.alpha_and_gec.updraft.base.registry.UpdraftTags;
import com.alpha_and_gec.updraft.base.util.IKSolver;
import com.alpha_and_gec.updraft.base.util.MathHelpers;
import com.alpha_and_gec.updraft.base.util.PisslikeHitboxes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import javax.annotation.Nullable;

public class SteelgoreEntity extends UpdraftDragon {
    private static final RawAnimation STEELGORE_STUNNED = RawAnimation.begin().thenLoop("animation.steelgore.stunned");
    private static final RawAnimation STEELGORE_SIT = RawAnimation.begin().thenLoop("animation.steelgore.sit");
    private static final RawAnimation STEELGORE_IDLE = RawAnimation.begin().thenLoop("animation.steelgore.idle");
    private static final RawAnimation STEELGORE_WALK = RawAnimation.begin().thenLoop("animation.steelgore.walk");

    private static final RawAnimation STEELGORE_WATER_IDLE = RawAnimation.begin().thenLoop("animation.steelgore.swim_idle");
    private static final RawAnimation STEELGORE_SWIM = RawAnimation.begin().thenLoop("animation.steelgore.swim");

    private static final RawAnimation STEELGORE_HOVER = RawAnimation.begin().thenLoop("animation.steelgore.hover");
    private static final RawAnimation STEELGORE_FLY = RawAnimation.begin().thenLoop("animation.steelgore.fly");
    private static final RawAnimation STEELGORE_GLIDE = RawAnimation.begin().thenLoop("animation.steelgore.glide");
    private static final RawAnimation STEELGORE_DIVE = RawAnimation.begin().thenLoop("animation.steelgore.dive");

    private static final RawAnimation STEELGORE_ATK_GORE = RawAnimation.begin().thenLoop("animation.steelgore.attack_gore");
    private static final RawAnimation STEELGORE_ATK_CHARGE = RawAnimation.begin().thenLoop("animation.steelgore.attack_charge");
    private static final RawAnimation STEELGORE_ATK_BREATH = RawAnimation.begin().thenLoop("animation.steelgore.attack_breath");
    private static final RawAnimation STEELGORE_ATK_BREATH_HOLD = RawAnimation.begin().thenLoop("animation.steelgore.attack_breath_hold");
    private static final RawAnimation STEELGORE_ATK_ROAR = RawAnimation.begin().thenLoop("animation.steelgore.attack_roar");

    private static final RawAnimation STEELGORE_FEED = RawAnimation.begin().thenLoop("animation.steelgore.feed");
    private static final RawAnimation STEELGORE_FED = RawAnimation.begin().thenLoop("animation.steelgore.tame_chew");

    private static final RawAnimation STEELGORE_DEATH = RawAnimation.begin().thenPlay("animation.steelgore.die");
    private static final RawAnimation STEELGORE_DEATH_WATER = RawAnimation.begin().thenPlay("animation.steelgore.swim_die");
    private static final RawAnimation STEELGORE_DEATH_AIR = RawAnimation.begin().thenLoop("animation.steelgore.fall");
    private static final RawAnimation STEELGORE_NULL = RawAnimation.begin().thenLoop("animation.steelgore.null");


    public double chargeCD;
    //cooldown for charge attack, only usable if it is at 0
    public static final double chargeCap = 1000;

    public Vec3 chargeMotion = Vec3.ZERO;

    public double goreCD;
    //cooldown for gore attack, only usable if it is at 0
    public static final double goreCap = 5;

    //public double breathCharge;
    public double AIbreathCD;
    //cooldown for breath usage, this is unique in that as long as it is below cap the breath charge can be used, and each tick of usage increases the cooldown up to the cap
    //this represents how many ticks it can breath fire for
    public static final double breathCap = 400;
    //public static final double breathRegen = 1;
    //public static final double breathDrain = 10;

    public double roarCD;
    //cooldown for roaring, ONLY APPLIES TO WILD
    public static final double roarCap = 3600;


    public boolean isStunned = false;

    public boolean isCharging = false;

    public SteelgoreEntity(EntityType<? extends TamableAnimal> p_30341_, Level p_30342_) {
        super(p_30341_, p_30342_);

        this.moveControl = new DragonLandRotcappedMoveControl(this, 1);

        this.tailKinematics = new IKSolver(this, 4, 2, 2, 0.75, 2, false, true);
        this.setMeleeRadius(4.5f);
        this.setMaxUpStep(1);
        this.maxLootAmount = 6;
        this.lootAmount = 6;
        this.turnSpeed = 0.05F;
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

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, LivingEntity.class, true, entity -> entity.getType().is(UpdraftTags.STEELGORE_INTOLERANT)));
        this.targetSelector.addGoal(2, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(3, new OwnerHurtTargetGoal(this));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 120.0D)
                .add(Attributes.ARMOR, 10.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1D)
                .add(Attributes.ATTACK_DAMAGE, 16.0D)
                .add(Attributes.ATTACK_KNOCKBACK, 5D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.FOLLOW_RANGE, 40.0D);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putBoolean("Stunned", this.isStunned());
        pCompound.putDouble("ChargeCooldown", this.chargeCD);
        pCompound.putDouble("GoreCooldown", this.goreCD);
        //pCompound.putDouble("BreathCharge", this.breathCharge);
        pCompound.putDouble("RoarCooldown", this.roarCD);

        super.addAdditionalSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.chargeCD = pCompound.getDouble("ChargeCooldown");
        this.goreCD = pCompound.getDouble("GoreCooldown");
        //this.breathCharge = pCompound.getDouble("BreathCharge");
        this.roarCD = pCompound.getDouble("RoarCooldown");
        this.isStunned = pCompound.getBoolean("Stunned");

        super.readAdditionalSaveData(pCompound);
    }

    public void tick() {
        if (this.isStunned()) {
            this.getNavigation().stop();
            this.setTarget(null);
            this.setDeltaMovement(Vec3.ZERO);
            //System.out.println("stun");
        }

        //if (this.getOwner() != null) {
        //    System.out.println("tamed");
        //}

        //System.out.println(this.entityData.get(ANIMATION_STATE));

        super.tick();
    }

    @Override
    public void zeroCDs() {
        this.chargeCD = 0;
        this.goreCD = 0;
        this.roarCD = 0;
        //this.breathCharge = breathCap;
    }

    @Override
    public void tickCDs() {
        //method ran per - tick to tick down cooldowns
        super.tickCDs();

        this.chargeCD = Math.max(0, chargeCD - 1);
        this.goreCD = Math.max(0, goreCD - 1);
        this.roarCD = Math.max(0, roarCD - 1);
        this.AIbreathCD = Math.max(0, AIbreathCD - 1);
        //this.breathCharge = Math.min(breathCap, this.breathCharge + breathRegen);

        //System.out.println(this.breathCharge);
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
        if (pSource.is(DamageTypes.FALLING_ANVIL) && pSource.getEntity() instanceof FallingBlockEntity m) {
            this.stun();

            Vec3 dirDiff = m.position().subtract(this.position()).normalize().scale(this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 0.25);
            m.disableDrop();
            ItemEntity pingus = m.spawnAtLocation(m.getBlockState().getBlock());
            m.discard();
            if (pingus != null) {
                pingus.setDeltaMovement(dirDiff);
            }
            //breaks the falling anvil entity and deflects the dropped anvil item away

            //System.out.println(m.getDeltaMovement());
            return super.hurt(pSource, pAmount/5);

        } else {
            return super.hurt(pSource, pAmount);
        }
    }


    @Override
    public void push (Entity pEntity) {
        //System.out.println(pEntity);

        if (this.canContactDamage() && this.getTarget() != null) {
            pEntity.hurt(this.damageSources().mobAttack(this), (float) (this.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * 3));


            if (pEntity instanceof LivingEntity liver) {
                liver.setLastHurtByMob(this);
                PisslikeHitboxes.knockbackFromSelf(liver, this, 1);
                liver.setDeltaMovement(liver.getDeltaMovement().add(0, this.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * 0.15, 0));
                //yes I know vertical knockback is not proportional to horizontal knockback, it looks funnier
            }
        } else {
            this.setCanContactDamage(false);
        }

        super.push(pEntity);
    }

    public void poof(boolean server) {
        //make some puffy smoke
        Vec3 vec3 = this.getBoundingBox().getCenter();
        for(int i = 0; i < 40; ++i) {
            double d0 = this.random.nextGaussian() * 0.2;
            double d1 = this.random.nextGaussian() * 0.2;
            double d2 = this.random.nextGaussian() * 0.2;

            if (server) {
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.POOF, vec3.x, vec3.y, vec3.z, 1, d0, d1, d2, 0.1D);
            } else {
                this.level().addAlwaysVisibleParticle(ParticleTypes.POOF, vec3.x, vec3.y, vec3.z, d0, d1, d2);
            }
        }
    }

    public boolean isFood(ItemStack stack) {
        //System.out.println(stack.is(UpdraftTags.STEELGORE_DIET));
        return stack.is(UpdraftTags.STEELGORE_DIET);
    }

    public boolean tryTame(Player futureOwner, int amount) {
        //basically, depending on how much food is fed(amount), there is a 1/amount chance to tame it

        int rng = random.nextInt(0, 100);

        if (rng < amount && !futureOwner.level().isClientSide()) {
            this.tame(futureOwner);
            this.unstun();
            this.level().broadcastEntityEvent(this, (byte)7);
            //System.out.println("tamed");
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

        }

        if (this.isDeadOrDying()) {
            return InteractionResult.SUCCESS;
        }

        if (this.isFood(itemstack)) {
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
            //System.out.println(this.isTame());
            //System.out.println((this.getOwner()));

            if (this.isTame() && this.isOwnedBy(pPlayer) && pPlayer.isCrouching() && !this.isInWaterOrBubble()) {
                //if the owner is holding a non - food item whilst shifting, cycle through its behaviour
                //can't do it if it's swimming, swimming steelgores always follow the player
                this.cycleBehaviour();
                return InteractionResult.sidedSuccess(this.level().isClientSide);
            }
        }

        return super.mobInteract(pPlayer, pHand);
    }

    @Override
    protected void tickRidden(Player pPlayer, Vec3 pTravelVector) {
        super.tickRidden(pPlayer, pTravelVector);

        if (!this.level().isClientSide()) {
            switch (this.getAttackState()) {
                case 1 -> SteelgoreAttacks.tickGore(this);
                case 2 -> {
                    if (this.AIbreathCD == 0) {
                        SteelgoreAttacks.tickBreath(this);
                    } else {
                        this.setAttackState(0);
                    }
                }
                case 3 -> {
                    if (!this.isFlying() && this.chargeCD == 0) {
                        SteelgoreAttacks.tickCharge(this);
                    } else {
                        this.setAttackState(0);
                    }
                }
                case 4 -> {
                    if (this.onGround()) {
                        SteelgoreAttacks.tickRoar(this);
                    } else {
                        this.setAttackState(0);
                    }
                }
            }
        }
    }

    public void switchAnimationState() {
        //method ran per - tick to assert which animation a dragon ought to play
        //serverside only, ran to guarantee checkAnimationState works

        //System.out.println(this.getHorizontalVelocity());

        if (!this.isAlive()) {

            if (!this.onGround() && !this.isInWaterOrBubble()) {
                this.setAnimationState(4);
                //air death

            } else if (this.isInWaterOrBubble()) {
                this.setAnimationState(1);
                //water death

            } else {
                this.setAnimationState(8);
                //land death
            }

        } else if (this.isStunned) {
            this.setAnimationState(0);
            //stun

        } else if (this.isInWaterOrBubble()) {
            //water anims
                if (this.isWalking()) {
                    this.setAnimationState(2);
                    //swim

                } else {
                    //water idle
                    this.setAnimationState(3);
                }

        } else if (!this.onGround() && this.isFlying()){
            //flying anims

            if (this.isWalking()) {

                if (this.isDiving()) {
                    this.setAnimationState(5);
                    //dive

                } else {
                    this.setAnimationState(6);
                    //fly
                }

            } else {
                //hover
                this.setAnimationState(7);
            }

        } else {
            //land anims

            if (!this.isAlive()) {
                this.setAnimationState(8);
                //land death

            } else {
                if (this.isOrderedToSit()) {
                    this.setAnimationState(9);
                    //sit

                } else if (isWalking()) {
                    this.setAnimationState(10);
                    //walk

                } else {
                    //idle
                    this.setAnimationState(11);
                }
            }

        }
    }

    protected <E extends SteelgoreEntity> PlayState Controller(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        double attributespeed = this.getAttributeValue(Attributes.MOVEMENT_SPEED);

        if (this.getAttackState() == 3) {
            event.setAndContinue(STEELGORE_ATK_CHARGE);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;

        } else if (this.getAttackState() == 4) {
            event.setAndContinue(STEELGORE_ATK_ROAR);
            event.getController().setAnimationSpeed(1.0F);
            return PlayState.CONTINUE;
        }

        switch (this.getAnimationState()) {
            case 0:
                event.setAndContinue(STEELGORE_STUNNED);
                event.getController().setAnimationSpeed(1.0F);
                break;
            case 1:
                event.setAnimation(STEELGORE_DEATH_WATER);
                event.getController().setAnimationSpeed(1.0F);
                break;
            case 2:
                event.setAndContinue(STEELGORE_SWIM);
                event.getController().setAnimationSpeed(this.getVelocityThroughPos()/attributespeed + 1.0F);
                break;
            case 3:
                event.setAndContinue(STEELGORE_WATER_IDLE);
                event.getController().setAnimationSpeed(this.getVelocityThroughPos()/attributespeed + 1.0F);
                break;
            case 4:
                event.setAndContinue(STEELGORE_DEATH_AIR);
                event.getController().setAnimationSpeed(1.0F);
                break;
            case 5:
                event.setAndContinue(STEELGORE_DIVE);
                event.getController().setAnimationSpeed(1.0F);
                break;
            case 6:
                event.setAndContinue(STEELGORE_FLY);
                event.getController().setAnimationSpeed(this.getVelocityThroughPos()/attributespeed + 1.0F);
                break;
            case 7:
                event.setAndContinue(STEELGORE_HOVER);
                event.getController().setAnimationSpeed(this.getVelocityThroughPos()/attributespeed + 1.0F);
                break;
            case 8:
                event.setAnimation(STEELGORE_DEATH);
                event.getController().setAnimationSpeed(1.0F);
                break;
            case 9:
                event.setAndContinue(STEELGORE_SIT);
                event.getController().setAnimationSpeed(1.0F);
                break;
            case 10:
                event.setAndContinue(STEELGORE_WALK);
                event.getController().setAnimationSpeed(this.getHorizontalVelocity()/attributespeed + 1.0F);
                break;
            case 11:
                event.setAndContinue(STEELGORE_IDLE);
                event.getController().setAnimationSpeed(this.getHorizontalVelocity()/attributespeed + 1.0F);
                break;
        }

        return PlayState.CONTINUE;
    }

    protected <E extends SteelgoreEntity> PlayState AtkController(final software.bernie.geckolib.core.animation.AnimationState<E> event) {

        switch (this.getAttackState()) {
            case 0:
                event.setAndContinue(STEELGORE_NULL);
                event.getController().setAnimationSpeed(1.0F);
                break;
            case 1:
                event.setAndContinue(STEELGORE_ATK_GORE);
                event.getController().setAnimationSpeed(1.0F);
                break;
            case 2:
                event.setAndContinue(STEELGORE_ATK_BREATH);
                event.getController().setAnimationSpeed(1.0F);
                break;
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "Normal", 5, this::Controller));
        controllers.add(new AnimationController<>(this, "Attack", 2, this::AtkController));
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


    public static boolean checkSteelgoreSpawnRules(EntityType<SteelgoreEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource rand) {
        return level.getBlockState(position.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && isBrightEnoughToSpawn(level, position);
        //note that steelgores, like all other plains grazers, cannot spawn at night.
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
    }

    public int getVariant() {
        return 4;
    }

}
