package com.alpha_and_gec.updraft.base.common.entities.base;

import com.alpha_and_gec.updraft.base.registry.UpdraftItems;
import com.alpha_and_gec.updraft.base.util.IKSolver;
import com.alpha_and_gec.updraft.base.util.MathHelpers;
import com.google.common.collect.UnmodifiableIterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.*;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

public class UpdraftDragon extends TamableAnimal implements Saddleable, GeoAnimatable {
    public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.INT);
    //states determining which animation this beast should play
    //0 = no attack
    //1 = base attack
    //2 = ranged attack
    //3 = special attack
    //4 = cosmetic roar
    //5 and above are for unique special attacks, note that these are not eligible to be keybound by default
    //Attackstate is read in the entity's attack goal and riddenTick in order to tick attacks

    private static final EntityDataAccessor<Integer> BEHAVIOUR_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.INT);
    //0 = wander
    //1 = sit
    //2 = follow

    private static final EntityDataAccessor<Boolean> TAKE_OFF_FLAG = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> FLIGHT_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> IS_PINNED = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.BOOLEAN);
    //I made this a synchedData because it's important to stop the player from carving pinned dragons

    public IKSolver tailKinematics;

    public SimpleContainer inventory;

    public int inventorySize;

    public LazyOptional<?> itemHandler = null;

    public DamageSource deathDamageSource;

    public float lootAmount = 0;
    public float maxLootAmount = 0;

    private boolean selfFriendly = false;
    //^do NOT edit this variable outside of the constructor

    public int attackTime = 0;

    public boolean contactDmg = false;

    public boolean isPowered = false;

    public float meleeRadius;

    public float maxFlightVelocity;

    public float stallVelocity;

    public float turnSpeed;
    //Number between 0 and 1

    public int wetness = 0;
    public int wetlim = 100;
    //how many ticks does it take to dry

    public Vec3 prevPos = Vec3.ZERO;
    public Vec3 nowPos = Vec3.ZERO;

    public float prevYrot;
    public float nowYrot;

    public float prevYawDiff;
    public float nowYawDiff;

    public float tailPrevYaw = 0;


    protected final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    protected UpdraftDragon(EntityType<? extends TamableAnimal> p_30341_, Level p_30342_) {
        super(p_30341_, p_30342_);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANIMATION_STATE, 1);
        this.entityData.define(ATTACK_STATE, 0);
        this.entityData.define(BEHAVIOUR_STATE, 0);
        this.entityData.define(IS_PINNED, false);
        this.entityData.define(FLIGHT_STATE, false);
        this.entityData.define(TAKE_OFF_FLAG, false);
    }

    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.putFloat("LootAmount", this.getLootAmount());
        pCompound.putBoolean("SelfFriendly", this.isSelfFriendly());
        pCompound.putInt("Wetness", this.wetness);
        pCompound.putBoolean("ContactDmg", this.canContactDamage());
        pCompound.putInt("InvSize", this.inventorySize);
        pCompound.putFloat("TurnSpeed", this.turnSpeed);

        super.addAdditionalSaveData(pCompound);
    }

    public void readAdditionalSaveData(CompoundTag pCompound) {
        this.setLootAmount(pCompound.getFloat("LootAmount"));
        this.setSelfFriendly(pCompound.getBoolean("SelfFriendly"));
        this.setCanContactDamage(pCompound.getBoolean("ContactDmg"));
        this.wetness = pCompound.getInt("Wetness");
        this.inventorySize = pCompound.getInt("InvSize");
        this.turnSpeed = pCompound.getFloat("TurnSpeed");

        super.readAdditionalSaveData(pCompound);
    }


    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel p_146743_, AgeableMob p_146744_) {
        return null;
    }

    public int getVariant() {
        return 1;
    }

    //@Override
    public boolean canBeRiddenInWater(Entity rider) {
        return true;
    }

    @Override
    public boolean isNoAi() {
        return this.isDeadOrDying() || super.isNoAi();
    }

    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 20 && !this.level().isClientSide()) {
            //give it a second to fully die before carving is available
            if (this.deathTime == 20) {
                this.level().broadcastEntityEvent(this, (byte) 60);
                //spawns death particles
            }

            if (this.isCarved()) {
                this.remove(Entity.RemovalReason.KILLED);
            }

            if (this.isInWater()) {
                //slow sink in water
                Vec3 vec3 = this.getDeltaMovement();

                double d0 = Math.max(-0.1D, vec3.y - 0.01D);

                this.setDeltaMovement(vec3.x, d0, vec3.z);

            } else if (!this.onGround()) {
                //fall if die in air
                Vec3 vec3 = this.getDeltaMovement();

                double d0 = Math.max(-0.5D, vec3.y - 0.05D);

                this.setDeltaMovement(vec3.x, d0, vec3.z);
            }

        }

        this.move(MoverType.SELF, this.getDeltaMovement());
        this.setOnGroundWithKnownMovement(this.level().getBlockState(this.blockPosition().below()).isSolid(), collide(this.getDeltaMovement()));
    }

    public void tick() {
        super.tick();

        this.prevPos = this.nowPos;
        this.nowPos = this.position();

        this.prevYrot = nowYrot;
        this.nowYrot = this.getYRot();

        this.prevYawDiff = this.nowYawDiff;
        this.nowYawDiff = this.nowYrot - this.prevYrot;

        if (this.isFlying()) {
            this.setNoGravity(true);
            this.resetFallDistance();

            if (this.tickCount % 5 == 0) {
                this.getPassengers().forEach(p -> p.resetFallDistance());
                //every 5 ticks reset fall distance for all passengers
                //wait is to make it less laggy
            }

        } else {
            this.setNoGravity(false);
        }

        if (!this.level().isClientSide() && !this.hasTarget() && !this.hasControllingPassenger()) {
            this.setAttackState(0);
            this.setAttackTime(0);
            this.land();
        }
        //TODO: remove this and use something to detect dismounts, this sucks

        if (this.tailKinematics != null) {
            this.tailKinematics.takePerTickAction(this);
        }
        this.tickCDs();

        if (this.isInWaterRainOrBubble()) {
            wetness = Math.min(wetlim, wetness + 1);
        } else {
            wetness = Math.max(0, wetness - 1);
        }

        if (!this.level().isClientSide()){
            this.switchAnimationState();
        } else {
            this.checkAnimationState();
        }
    }

    public void travel(Vec3 travelVector) {

        //System.out.println(this.justTookOff());

        if (this.isOrderedToSit()) {
            this.getNavigation().stop();
            this.setTarget(null);
            //System.out.println("sitte");

        } else if (this.isControlledByLocalInstance() && !this.isInWater() && (this.isFlying() || this.justTookOff())) {

            BlockPos ground = getBlockPosBelowThatAffectsMyMovement();
            float f = 0.91F;
            if (this.onGround()) {
                f = this.level().getBlockState(ground).getFriction(this.level(), ground, this) * 0.91F;
            }

            if (this.justTookOff()) {
                this.setDeltaMovement(travelVector.add(0, 1, 0));
                this.setTookOffFlag(false);

            } else {
                this.setDeltaMovement(travelVector);
            }

            this.move(MoverType.SELF, this.getDeltaMovement());

            this.setDeltaMovement(this.getDeltaMovement().scale(f));
            //^ deceleration

            //System.out.println(travelVector);

        } else {
            super.travel(travelVector);
        }



    }

    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
        this.setPose(Pose.STANDING);
        this.deathDamageSource = pDamageSource;
        this.setAttackTime(0);
        this.setAttackState(0);
    }

    @Override
    public @NotNull InteractionResult interactAt(Player pPlayer, @NotNull Vec3 vec, @NotNull InteractionHand hand) {
        ItemStack stack = pPlayer.getItemInHand(hand);
        //this process runs twice when you right click, for each hand

        if (this.isDeadOrDying() && pPlayer.mayBuild() && !level().isClientSide() && hand == InteractionHand.MAIN_HAND) {
            if (!stack.isEmpty() && stack.getItem() != null && stack.getItem() == Items.GLASS_BOTTLE) {
                //System.out.println("bleed");
                if (!pPlayer.isCreative()) {
                    stack.shrink(1);
                }

                pPlayer.getInventory().add(new ItemStack(UpdraftItems.WYRMBLOOD.get(), 1));
                this.setLootAmount(this.getLootAmount() - 1);
                return InteractionResult.SUCCESS;
                //gets you drakeblood

            } else {
                //System.out.println("carve");
                int fortune = 1;

                if (pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getAllEnchantments().containsKey(Enchantments.BLOCK_FORTUNE)){
                    fortune = Math.max(fortune, pPlayer.getItemInHand(InteractionHand.MAIN_HAND).getAllEnchantments().get(Enchantments.BLOCK_FORTUNE));
                    //System.out.println("fort");
                    //having a fortune tool when looting a dragon boosts odds of rare stuff
                }

                this.carve(1f/fortune);
            }
            return InteractionResult.SUCCESS;
        }
        return super.interactAt(pPlayer, vec, hand);
    }

    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        if (!this.isVehicle() && !this.isBaby() && this.isTame()) {
                ItemStack itemstack = pPlayer.getItemInHand(pHand);

                if (!itemstack.isEmpty()) {
                    //uhh interact with the item in hand before riding
                    InteractionResult interactionresult = itemstack.interactLivingEntity(pPlayer, this, pHand);
                    if (interactionresult.consumesAction()) {
                        return interactionresult;
                    }
                }

                this.doPlayerRide(pPlayer);
                return InteractionResult.sidedSuccess(this.level().isClientSide);

        } else {
            return super.mobInteract(pPlayer, pHand);
        }
    }

    @Override
    public boolean isInWater() {
        return this.isInFluidType();
    }

    public void tickCDs() {
        //method ran per - tick to tick down cooldowns
    }

    public void zeroCDs() {
        //this.breathCharge = breathCap;
    }

    public void setFlightVelocityBreakpoints(float maxFlightVelocity, float stallVelocity) {
        this.maxFlightVelocity = maxFlightVelocity;
        this.stallVelocity = stallVelocity;
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


    public void setFlying(boolean state) {
        this.entityData.set(FLIGHT_STATE, state, true);
    }

    public boolean isFlying() {
        //check if the dragon is in the flying state
        return this.entityData.get(FLIGHT_STATE);
    }



    public void setTookOffFlag(boolean state) {
        this.entityData.set(TAKE_OFF_FLAG, state, true);
    }

    public boolean justTookOff() {
        //check if the dragon is in the flying state
        return this.entityData.get(TAKE_OFF_FLAG);
    }



    public void takeOff() {
        if (!this.isInWater()) {
            this.setFlying(true);
            this.setNoGravity(true);
            this.poof();

            this.setTookOffFlag(true);
        }
    }

    public void tryLand() {
        if (this.onGround()) {
            this.land();
        }
    }

    public void land() {
        this.setFlying(false);
        this.setNoGravity(false);
    }


    public void setPowered(boolean state) {
        this.isPowered = state;
    }

    public boolean isPowered() {
        //check if the dragon is in powered flight
        return this.isPowered;
    }


    public boolean isDiving() {
        //check if the dragon is at the correct angle to play the diving animation
        //idk why this samples from 0 - 4 while other shit samples from o - 90

        float angle = this.getXRot();

        //System.out.println(angle);

        if (!this.onGround() && this.isFlying() && angle > 3.5) {
            return true;
        } else {
            return false;
        }
    }

    public float evaluateFlapSpeed(float speedScale) {
        float angle = this.getXRot();
        //System.out.println(angle);
        return (float) (speedScale * (1.25 - (angle/90)));
    }

    public boolean isMovingForwards() {
        return this.getVelocityThroughPos() > this.getAttributeValue(Attributes.MOVEMENT_SPEED)/10;
    }

    public boolean isWalking() {
        return this.getHorizontalVelocity() > this.getAttributeValue(Attributes.MOVEMENT_SPEED)/10;
    }

    public boolean isEligibleToLand() {
        return this.getHorizontalVelocity() < this.stallVelocity;
    }

    public double getVelocityThroughPos() {
        Vec3 vec3 = this.nowPos.subtract(this.prevPos);

        //System.out.println(vec3.length());
        return vec3.length();
    }

    public double getHorizontalVelocity() {
        Vec2 vec3 = new Vec2((float) (this.prevPos.x - this.nowPos.x), (float) (this.prevPos.z - this.nowPos.z));

        return vec3.length();
    }

    public double getVelocityThroughDeltamovement() {
        Vec3 vec3 = this.getDeltaMovement();

        return vec3.length();
    }

    public boolean isEating() {
        //check if the dragon is eating smthing
        return false;
    }

    public void setAnimationState(int state) {
        this.entityData.set(ANIMATION_STATE, state);
    }

    public int getAnimationState() {
        return this.entityData.get(ANIMATION_STATE);
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


    public int getAttackTime() {
        return this.attackTime;
    }

    public void setAttackTime(int state) {
        this.attackTime = state;
    }

    public void attackTimeIncrement() {
        this.attackTime++;
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

    public void setPinned(Boolean state) {
        this.entityData.set(IS_PINNED, state);
    }

    public Boolean isPinned() {
        return this.entityData.get(IS_PINNED);
    }

    public void setLootAmount(float amt) {
        this.lootAmount = amt;
    }

    public float getLootAmount() {
        return this.lootAmount;
    }

    public void carve(float amt) {
        //basically what happens when the player right clicks a dragon corpse
        this.setLootAmount(this.getLootAmount() - amt);

        if (!this.level().isClientSide()) {
            boolean flag = this.lastHurtByPlayerTime > 0;
            //only drop loot when hurt by player or tame
            if (this.shouldDropLoot() && this.level().getGameRules().getBoolean(GameRules.RULE_DOMOBLOOT)) {
                this.dropCarveLoot();
            }
        }
    }

    public boolean isCarved() {
        return this.lootAmount <= 0;
    }

    public void dropCarveLoot() {
        if (this.deathDamageSource == null) {
            this.deathDamageSource = this.damageSources().genericKill();
        }

        ResourceLocation resourcelocation = this.getLootTable();
        LootTable loottable = this.level().getServer().getLootData().getLootTable(resourcelocation);

        LootParams.Builder lootparams$builder = (new LootParams.Builder((ServerLevel)this.level())).withParameter(LootContextParams.THIS_ENTITY, this).withParameter(LootContextParams.ORIGIN, this.position()).withParameter(LootContextParams.DAMAGE_SOURCE, this.deathDamageSource).withOptionalParameter(LootContextParams.KILLER_ENTITY, this.deathDamageSource.getEntity()).withOptionalParameter(LootContextParams.DIRECT_KILLER_ENTITY, this.deathDamageSource.getDirectEntity());
        if (this.lastHurtByPlayer != null) {
            lootparams$builder = lootparams$builder.withParameter(LootContextParams.LAST_DAMAGE_PLAYER, this.lastHurtByPlayer).withLuck(this.lastHurtByPlayer.getLuck());
        }
        LootParams lootparams = lootparams$builder.create(LootContextParamSets.ENTITY);

        for (ItemStack itemstack : loottable.getRandomItems(lootparams)) {
            //Each carve action is the equivalent of an entire entity death loot drop.
            //That means guaranteed death drops drop every carve.
            //Something with a 0.5 chance to drop per kill is instead 0.5 chance per carve.
            this.spawnAtLocation(itemstack);
        }
    }

    @Override
    public boolean isSaddleable() {
        return this.isAlive() && !this.isBaby() && this.isTame();
    }

    @Override
    public void equipSaddle(@javax.annotation.Nullable SoundSource pSource) {
        this.inventory.setItem(0, new ItemStack(Items.SADDLE));
    }

    @Override
    public boolean isSaddled() {
        //override this if the dragon needs saddle to ride
        return true;
    }

    protected void doPlayerRide(Player pPlayer) {
        if (!this.level().isClientSide) {
            pPlayer.setYRot(this.getYRot());
            pPlayer.setXRot(this.getXRot());
            pPlayer.startRiding(this);
            this.wander();
        }

    }

    @Override
    protected void tickRidden(Player pPlayer, Vec3 pTravelVector) {
        super.tickRidden(pPlayer, pTravelVector);
        Vec2 vec2 = this.getRiddenRotation(pPlayer);
        //this.setRot(vec2.y, vec2.x);
        //System.out.println(vec2);

        if (this.isMovingForwards()) {
            //Limit turn speed, and only turn if the creature is moving
            this.setRot((float) (Mth.RAD_TO_DEG * MathHelpers.LerpAngle(Mth.DEG_TO_RAD * this.getYRot(), Mth.DEG_TO_RAD * vec2.y, this.turnSpeed)),
                    (float) (Mth.RAD_TO_DEG * MathHelpers.LerpAngle(Mth.DEG_TO_RAD * this.getXRot(), Mth.DEG_TO_RAD * vec2.x, this.turnSpeed)));
            this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        }

        this.setTarget(null);
        //System.out.println(this.yBodyRot);

    }

    protected Vec2 getRiddenRotation(LivingEntity pEntity) {
        return new Vec2(pEntity.getXRot(), pEntity.getYRot());
    }

    @Override
    protected Vec3 getRiddenInput(Player pPlayer, Vec3 pTravelVector) {
        float f = pPlayer.xxa * 0.5F;
        float f1 = pPlayer.zza * 0.5F;
        float f2 = pPlayer.yya;
        if (f1 <= 0.0F) {
            f1 *= 0.25F;
        }

        if (this.isInWater()) {
            //water mounted motion
            return new Vec3(f, f2, f1);

        } else if (this.isFlying()){
            Vec3 MotionVector = (this.getViewVector(0.0f).normalize().scale(maxFlightVelocity));
            return MotionVector;

        } else {
            //land mounted motion
            return new Vec3(f, 0.0f, f1);

        }
    }

    @Override
    public void travelRidden(Player pPlayer, Vec3 pTravelVector) {
        Vec3 vec3 = this.getRiddenInput(pPlayer, pTravelVector);
        this.tickRidden(pPlayer, vec3);

        if (this.isControlledByLocalInstance()) {
            this.setSpeed(this.getRiddenSpeed(pPlayer));
            this.travel(vec3);
            //triggered clientside

        } else {
            this.calculateEntityAnimation(false);
            this.setDeltaMovement(Vec3.ZERO);
            this.tryCheckInsideBlocks();
            //triggered serverside

        }
    }

    @Override
    protected float getRiddenSpeed(Player pPlayer) {
        return (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    @Override
    protected void positionRider(Entity pPassenger, Entity.MoveFunction pCallback) {
        super.positionRider(pPassenger, pCallback);

        Vec3 offset = getPassengerPosOffset(pPassenger, getPassengers().indexOf(pPassenger));

        Vec3 pos = MathHelpers.rotateXZVectorByYawAngle(yBodyRot, offset.x, offset.z).add(getX(), getY() + offset.y + pPassenger.getMyRidingOffset(), getZ());
        pPassenger.setPos(pos.x, pos.y, pos.z);
    }

    public Vec3 getPassengerPosOffset(Entity entity, int index) {
        //Note: Method will, probably, be Overriden in child classes
        return new Vec3(0, getPassengersRidingOffset(), 0);
    }

    @Override
    @javax.annotation.Nullable
    public LivingEntity getControllingPassenger() {
        Entity entity = this.getFirstPassenger();
        if (entity instanceof Mob) {
            return (Mob)entity;
        } else {
            if (this.isSaddled()) {
                entity = this.getFirstPassenger();
                if (entity instanceof Player) {
                    return (Player)entity;
                }
            }

            return null;
        }
    }

    @javax.annotation.Nullable
    private Vec3 getDismountLocationInDirection(Vec3 pDirection, LivingEntity pPassenger) {
        double d0 = this.getX() + pDirection.x;
        double d1 = this.getBoundingBox().minY;
        double d2 = this.getZ() + pDirection.z;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        UnmodifiableIterator var10 = pPassenger.getDismountPoses().iterator();

        while(var10.hasNext()) {
            Pose pose = (Pose)var10.next();
            blockpos$mutableblockpos.set(d0, d1, d2);
            double d3 = this.getBoundingBox().maxY + (double)0.75F;

            while(true) {
                double d4 = this.level().getBlockFloorHeight(blockpos$mutableblockpos);
                if ((double)blockpos$mutableblockpos.getY() + d4 > d3) {
                    break;
                }

                if (DismountHelper.isBlockFloorValid(d4)) {
                    AABB aabb = pPassenger.getLocalBoundsForPose(pose);
                    Vec3 vec3 = new Vec3(d0, (double)blockpos$mutableblockpos.getY() + d4, d2);
                    if (DismountHelper.canDismountTo(this.level(), pPassenger, aabb.move(vec3))) {
                        pPassenger.setPose(pose);
                        return vec3;
                    }
                }

                blockpos$mutableblockpos.move(Direction.UP);
                if (!((double)blockpos$mutableblockpos.getY() < d3)) {
                    break;
                }
            }
        }

        return null;
    }

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity pLivingEntity) {
        Vec3 vec3 = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.RIGHT ? 90.0F : -90.0F));
        Vec3 vec31 = this.getDismountLocationInDirection(vec3, pLivingEntity);
        if (vec31 != null) {
            return vec31;
        } else {
            Vec3 vec32 = getCollisionHorizontalEscapeVector(this.getBbWidth(), pLivingEntity.getBbWidth(), this.getYRot() + (pLivingEntity.getMainArm() == HumanoidArm.LEFT ? 90.0F : -90.0F));
            Vec3 vec33 = this.getDismountLocationInDirection(vec32, pLivingEntity);
            return vec33 != null ? vec33 : this.position();
        }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public double getTick(Object o) {
        return tickCount;
    }

    @javax.annotation.Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor worldIn, DifficultyInstance difficultyIn, MobSpawnType reason, @javax.annotation.Nullable SpawnGroupData spawnDataIn, @javax.annotation.Nullable CompoundTag dataTag) {
        spawnDataIn = super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.zeroCDs();
        this.prevPos = this.position();
        this.nowPos = this.position();

        return super.finalizeSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        //TODO: implement proper spawning(spawns around shipwrecks and swamp huts)
    }

    public void poof() {
        //make some puffy smoke
        Vec3 vec3 = this.getBoundingBox().getCenter();
        for(int i = 0; i < 40; ++i) {
            double d0 = this.random.nextGaussian() * 0.2;
            double d1 = this.random.nextGaussian() * 0.2;
            double d2 = this.random.nextGaussian() * 0.2;

            if (!this.level().isClientSide()) {
                ((ServerLevel) this.level()).sendParticles(ParticleTypes.POOF, vec3.x, vec3.y, vec3.z, 1, d0, d1, d2, 0.1D);
            } else {
                this.level().addAlwaysVisibleParticle(ParticleTypes.POOF, vec3.x, vec3.y, vec3.z, d0, d1, d2);
            }
        }
    }
}
