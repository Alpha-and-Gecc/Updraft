package com.alpha_and_gec.updraft.base.common.entities.base;

import com.alpha_and_gec.updraft.base.common.entities.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.registry.UpdraftItems;
import com.alpha_and_gec.updraft.base.util.IKSolver;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.behavior.Mount;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UpdraftDragon extends TamableAnimal {
    public static final EntityDataAccessor<Integer> ANIMATION_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> ATTACK_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.INT);
    //states determining which animation this beast should play

    private static final EntityDataAccessor<Integer> BEHAVIOUR_STATE = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.INT);
    //0 = wander
    //1 = sit
    //2 = follow

    private static final EntityDataAccessor<Boolean> IS_PINNED = SynchedEntityData.defineId(UpdraftDragon.class, EntityDataSerializers.BOOLEAN);
    //I made this a synchedData because it's important to stop the player from carving pinned dragons

    public DamageSource deathDamageSource;

    public float lootAmount = 0;
    public float maxLootAmount = 0;

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
        this.entityData.define(IS_PINNED, false);
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
        }
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
            //System.out.println("sitte");
        } else {
            super.travel(travelVector);
        }

    }

    @Override
    public void die(DamageSource pDamageSource) {
        super.die(pDamageSource);
        this.setPose(Pose.STANDING);
        this.deathDamageSource = pDamageSource;
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
}
