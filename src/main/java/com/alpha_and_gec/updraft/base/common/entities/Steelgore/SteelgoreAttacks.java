package com.alpha_and_gec.updraft.base.common.entities.Steelgore;

import com.alpha_and_gec.updraft.base.util.MathHelpers;
import com.alpha_and_gec.updraft.base.util.PisslikeHitboxes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.projectile.SmallFireball;
import net.minecraft.world.phys.Vec3;

import java.awt.geom.AffineTransform;
import java.util.Objects;
import java.util.function.Predicate;

public class SteelgoreAttacks {

    public static final Vec3 goreOffset = new Vec3(0.0D, 0.0D, 3.0D);


    public static void tickGore(SteelgoreEntity creature) {
        //System.out.println("gore");
        creature.attackTimeIncrement();
        creature.getNavigation().stop();

        LivingEntity target = creature.getTarget();
        if(target != null) {
            creature.lookAt(target, 360F, 30F);
            creature.getLookControl().setLookAt(target, 30F, 30F);
        }

        if (creature.getAttackTime() == 11 && !creature.level().isClientSide()) {
            PisslikeHitboxes.GenerateHitbox(creature, goreOffset, 0.5, (ServerLevel) creature.level(), 1, 1, false);
        }
        if (creature.getAttackTime() >= 20) {
            creature.setAttackTime(0);
            creature.setAttackState(0);
            creature.goreCD = SteelgoreEntity.goreCap;
        }
    }
    
    public static void tickBreath(SteelgoreEntity creature) {
        //System.out.println("breath");
        //System.out.println(this.creature.breathCharge);
        creature.attackTimeIncrement();
        creature.getNavigation().stop();
        LivingEntity target = creature.getTarget();

        if (creature.getAttackTime() == 10) {
            if (target != null) {
                creature.playSound(SoundEvents.BLAZE_BURN, 2.0F, 1.0F);
                creature.lookAt(target, 360F, 360F);
                creature.getLookControl().setLookAt(target, 30F, 30F);
            }
        }

        if (creature.getAttackTime() >= 10 && creature.getAttackTime() <= 58 && !creature.level().isClientSide()) {

            Vec3 origin = creature.tailKinematics.getTorsoFront();
            Vec3 dir = MathHelpers.makeDirVector3D(creature);

            if (target != null) {
                creature.getLookControl().setLookAt(target.getX(), target.getY() + target.getBbHeight() / 2, target.getZ(), 1.5F, 80);
                creature.setYBodyRot(creature.getYHeadRot());

                double d1 = target.getX() - origin.x();
                double d2 = target.getY(0.5D) - origin.y();
                double d3 = target.getZ() - origin.z();
                double d4 = Math.sqrt(Math.sqrt(origin.distanceToSqr(target.position()))) * 0.5D;

                for (int que = 0; que < 3; que ++){
                    //System.out.println("produce");
                    SmallFireball smog = new SmallFireball(creature.level(), creature, creature.getRandom().triangle(d1, 2.297D * d4), d2, creature.getRandom().triangle(d3, 2.297D * d4));
                    smog.setPos(origin.x(), origin.y() + 1, origin.z());
                    smog.setOwner(creature);
                    creature.level().addFreshEntity(smog);
                }

            } else {
                //targetless version for mounted combat
                double d1 = dir.x();
                double d2 = dir.y();
                double d3 = dir.z();

                for (int que = 0; que < 3; que++) {
                    //System.out.println("produce");
                    SmallFireball smog = new SmallFireball(creature.level(), creature, creature.getRandom().triangle(d1, 0.55), d2, creature.getRandom().triangle(d3, 0.55));
                    smog.setPos(origin.x(), origin.y() + 1, origin.z());
                    smog.setOwner(creature);
                    creature.level().addFreshEntity(smog);
                }
            }

            //creature.breathCharge -= SteelgoreEntity.breathDrain;
        }
        if (creature.getAttackTime() > 65) {
            creature.setAttackTime(0);
            creature.setAttackState(0);
            creature.AIbreathCD = SteelgoreEntity.breathCap;
        }
    }

    public static void tickCharge(SteelgoreEntity creature) {
        //System.out.println("charge");
        creature.attackTimeIncrement();
        creature.getNavigation().stop();

        LivingEntity target = creature.getTarget();

        if (creature.getAttackTime() <= 10) {
            if(target != null) {
                creature.lookAt(target, 360F, 30F);
                creature.getLookControl().setLookAt(target, 30F, 30F);
                creature.setYBodyRot(creature.getYHeadRot());

                Vec3 targetPos = (target.position());
                //gets target data

                double x = -((creature.position().x - targetPos.x));
                double z = -((creature.position().z - targetPos.z));
                creature.chargeMotion = new Vec3(x, creature.getDeltaMovement().y, z).normalize().multiply(3, 1, 3);

            } else {
                //targetless case for mounted attack

                creature.setYBodyRot(creature.getYHeadRot());
                //look at the target

                Vec3 dir = MathHelpers.makeDirVectorFlat(creature);
                creature.chargeMotion = new Vec3(dir.x(), creature.getDeltaMovement().y, dir.z()).normalize().multiply(3, 1, 3);
            }

        }

        if (creature.getAttackTime() > 10 && creature.getAttackTime() < 25) {
            creature.setDeltaMovement(creature.chargeMotion.x/2, creature.getDeltaMovement().y, creature.chargeMotion.z/2);
            creature.setCanContactDamage(true);
            //Only start moving after tick 8(after it's charged up).
        } else {
            creature.setCanContactDamage(false);
        }

        if (creature.getAttackTime() >= 25) {
            creature.setAttackTime(0);
            creature.setAttackState(0);
            creature.chargeCD = SteelgoreEntity.chargeCap;
        }
    }

    public static void tickRoar(SteelgoreEntity creature) {
        //System.out.println("roar");
        //System.out.println(this.attackTime);
        creature.attackTimeIncrement();
        creature.getNavigation().stop();


        LivingEntity target = creature.getTarget();

        if(target != null) {
            creature.lookAt(target, 360F, 30F);
            creature.getLookControl().setLookAt(target, 30F, 30F);
        }

        if (creature.getAttackTime() == 11) {
            //Predicate<Entity> NOT_A_STEELGORE = (p_33346_) -> p_33346_.isAlive() && !(p_33346_ instanceof SteelgoreEntity) && creature.isSelfFriendly();
            creature.playSound(SoundEvents.RAVAGER_ROAR);

            for(LivingEntity livingentity : creature.level().getEntitiesOfClass(LivingEntity.class, creature.getBoundingBox().inflate(4.0F))) {
                PisslikeHitboxes.knockbackFromSelf(livingentity, creature, 1);
                creature.poof(true);
            }

        }

        if (creature.getAttackTime() >= 80) {
            //System.out.println("roarEnd");
            creature.setAttackTime(0);
            creature.setAttackState(0);
            creature.roarCD = SteelgoreEntity.roarCap;
        }
    }
}
