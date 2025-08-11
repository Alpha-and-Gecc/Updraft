package com.alpha_and_gec.updraft.base.common.entities.Steelgore;

import com.alpha_and_gec.updraft.base.common.entities.goals.BaseAttackGoal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;


public class SteelgoreAttackGoal extends BaseAttackGoal {

    public boolean selfFriendly = true;

    protected final SteelgoreEntity creature;

    public SteelgoreAttackGoal(SteelgoreEntity beast) {
        super(beast);
        this.creature = beast;
        this.selfFriendly = beast.isSelfFriendly();
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
                case 1 -> SteelgoreAttacks.tickGore(this.creature);
                case 2 -> SteelgoreAttacks.tickBreath(this.creature);
                case 3 -> SteelgoreAttacks.tickCharge(this.creature);
                case 4 -> SteelgoreAttacks.tickRoar(this.creature);

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
        //int attackRNG = creature.getRandom().nextInt(0, 100);

        //System.out.println("START");

        if (this.creature.getAttackState() == 0) {

            if (this.goalActiveTime < 20 && this.creature.roarCD == 0) {
                //System.out.println("roar");
                this.creature.setAttackState(4);
                //roars when starting combat
                //can't cheese it by getting it in a roar loop nuh uh

            } else {
                if (distance <= this.creature.getMeleeRadius() && this.creature.goreCD == 0) {
                    //System.out.println("gore");
                    this.creature.setAttackState(1);
                    //gores when close enough

                } else if (distance > this.creature.getMeleeRadius() && this.creature.chargeCD == 0 && !this.creature.isFallFlying()) {
                    //System.out.println("charge");
                    this.creature.setAttackState(3);
                    //charges with priority, but cannot charge if flying

                } else if (this.creature.AIbreathCD == 0 && !this.creature.isUnderWater()) {
                    //System.out.println("breath");
                    this.creature.setAttackState(2);
                    //Breath is last priority, but only breath when the breath gauge is full
                    //also can't breath underwater
                }
            }
        }
    }

    public boolean requiresUpdateEveryTick() {
        return this.creature.getAttackState() > 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.creature.setCanContactDamage(false);
        this.creature.setAttackState(0);
        //System.out.println("goalEnd");
    }

}
