package com.alpha_and_gec.updraft.base.common.entities.goals.Steelgore;

import com.alpha_and_gec.updraft.base.common.entities.SteelgoreEntity;
import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import com.alpha_and_gec.updraft.base.common.entities.goals.BaseAttackGoal;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Objects;

public class SteelgoreAttackGoal extends BaseAttackGoal {

    public Vec3 chargeMotion;

    protected final SteelgoreEntity creature;

    public SteelgoreAttackGoal(SteelgoreEntity beast) {
        super(beast);
        this.creature = beast;
    }

    @Override
    public void tick() {
        super.tick();

        LivingEntity target = this.creature.getTarget();

        //System.out.println(this.goalActiveTime);

        if (target != null) {
            double distance = this.creature.distanceTo(target);
            int attackState = this.creature.getAttackState();

            switch (attackState) {
                case 1 -> tickGore();
                case 2 -> tickBreath();
                case 3 -> tickCharge();
                case 4 -> tickRoar();

                default -> {
                    this.creature.getNavigation().moveTo(target, 2.0D);
                    this.creature.lookAt(target, 30F, 30F);
                    this.creature.getLookControl().setLookAt(target, 30F, 30F);
                    this.switchAttack(distance);
                }

            }
        }
    }

    protected void switchAttack(double distance) {
        int attackRNG = creature.getRandom().nextInt(0, 100);

        if (this.goalActiveTime < 10 && this.creature.roarCD == 0) {
            this.creature.setAttackState(4);
            //roars when starting combat
            //can't cheese it by getting it in a roar loop nuh uh
        }

        else if (distance <= this.creature.getMeleeRadius()) {
            this.creature.setAttackState(1);
            //gores when close enough
        }

        else if (distance > this.creature.getMeleeRadius() && this.creature.chargeCD == 0 && !this.creature.isFallFlying()) {
            this.creature.setAttackState(3);
            //charges with priority, but cannot charge if flying
        }

        else if (distance > this.creature.getMeleeRadius() && this.creature.breathCharge <= this.creature.breathCap && !this.creature.isUnderWater()) {
            this.creature.setAttackState(2);
            //whenever have breath and can't gore or charge, breath
            //also can't breath underwater
        }
    }

    public boolean requiresUpdateEveryTick() {
        return this.creature.getAttackState() > 0;
    }

    protected void tickGore() {
        System.out.println("gore");
        this.attackTime++;
        this.creature.getNavigation().stop();

        LivingEntity target = this.creature.getTarget();
        this.creature.lookAt(target, 360F, 30F);
        this.creature.getLookControl().setLookAt(target, 30F, 30F);

        if (this.attackTime == 11) {
            if (this.creature.distanceTo(Objects.requireNonNull(this.creature.getTarget())) < this.creature.getMeleeRadius()) {
                this.creature.doHurtTarget(this.creature.getTarget());
            }
        }
        if (this.attackTime >= 21) {
            this.attackTime = 0;
            this.creature.setAttackState(0);
            this.creature.goreCD = this.creature.goreCap;
            this.creature.chargeCD = Math.max(0, this.creature.chargeCD - 20);
        }
    }

    protected void tickCharge() {
        //System.out.println("charge");
        this.attackTime++;
        this.creature.getNavigation().stop();

        LivingEntity target = this.creature.getTarget();

        if (this.attackTime <= 10) {
            this.creature.lookAt(target, 360F, 30F);
            this.creature.getLookControl().setLookAt(target, 30F, 30F);
            this.creature.yBodyRot = this.creature.yHeadRot;
            //look at the target

            Vec3 targetPos = (target.position());
            //gets target data

            double x = -((this.creature.position().x - targetPos.x));
            double z = -((this.creature.position().z - targetPos.z));
            this.chargeMotion = new Vec3(x, this.creature.getDeltaMovement().y, z).normalize().multiply(3, 1, 3);
        }

        if (this.attackTime > 10 && this.attackTime < 30) {
            this.creature.setDeltaMovement(chargeMotion.x/2, this.creature.getDeltaMovement().y, chargeMotion.z/2);
            this.creature.setCanContactDamage(true);
            //Only start moving after tick 8(after it's charged up).
        } else {
            this.creature.setCanContactDamage(false);
        }

        if (this.attackTime >= 30) {
            this.attackTime = 0;
            this.creature.setAttackState(0);
            this.creature.chargeCD = this.creature.chargeCap;
        }
    }

    protected void tickRoar() {
        //System.out.println("roar");
        //System.out.println(this.attackTime);
        this.attackTime++;
        this.creature.getNavigation().stop();

        LivingEntity target = this.creature.getTarget();
        this.creature.lookAt(target, 360F, 30F);
        this.creature.getLookControl().setLookAt(target, 30F, 30F);

        if (this.attackTime == 11) {
            this.creature.playSound(SoundEvents.RAVAGER_ROAR);
        }

        if (this.attackTime >= 21) {
            //System.out.println("roarEnd");
            this.attackTime = 0;
            this.creature.setAttackState(0);
            this.creature.roarCD = this.creature.roarCap;
        }
    }

    protected void tickBreath() {
        System.out.println("breath");
        System.out.println(this.creature.breathCharge);
        this.attackTime++;
        this.creature.getNavigation().stop();
        LivingEntity target = this.creature.getTarget();

        if (this.attackTime == 13) {
            if (target != null) {
                this.creature.playSound(SoundEvents.BLAZE_BURN, 2.0F, 1.0F);
                this.creature.lookAt(target, 360F, 360F);
                this.creature.getLookControl().setLookAt(target, 30F, 30F);
            }
        }

        if (this.attackTime >= 13 && this.attackTime <= 41) {
            if (target != null) {
                this.creature.getLookControl().setLookAt(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(), 1.5F, 80);

                Vec3 origin = this.creature.tailKinematics.getTorsoFront();

                double d1 = target.getX() - origin.x();
                double d2 = target.getY(0.5D) - origin.y();
                double d3 = target.getZ() - origin.z();
                double d4 = Math.sqrt(Math.sqrt(origin.distanceToSqr(target.position()))) * 0.5D;

                for (int que = 0; que < 3; que ++){
                    //System.out.println("produce");
                    SmallFireball smog = new SmallFireball(this.creature.level(), this.creature, this.creature.getRandom().triangle(d1, 2.297D * d4), d2, this.creature.getRandom().triangle(d3, 2.297D * d4));
                    smog.setPos(smog.getX(), origin.y, smog.getZ());
                    smog.setOwner(this.creature);
                    this.creature.level().addFreshEntity(smog);
                }


                this.creature.breathCharge += 3;
                //accumulates 2 tick of cooldown for every tick
            }
        }
        if (this.attackTime > 50) {
            this.attackTime = 0;
            this.creature.setAttackState(0);
        }
    }

}
