package com.alpha_and_gec.updraft.base.common.entities.goals;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;

import java.util.EnumSet;

public class BaseAttackGoal extends Goal{
    protected int attackTime = 0;
    protected int timeOut = 0;
    protected int goalActiveTime = 0;
    protected final UpdraftDragon creature;

    public BaseAttackGoal(UpdraftDragon monster) {
        this.setFlags(EnumSet.of(Flag.MOVE, Goal.Flag.LOOK));
        this.creature = monster;
    }

    @Override
    public void start() {
        this.creature.setAggressive(true);
        this.attackTime = 0;
        this.timeOut = 0;
        this.goalActiveTime = 0;
        this.creature.setAttackState(0);
        //System.out.println("goalStrt");
    }

    @Override
    public void tick() {
        this.goalActiveTime++;
        this.timeOut++;
    }

    @Override
    public void stop() {
        LivingEntity target = this.creature.getTarget();
        if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.creature.setTarget(null);
        }
        this.creature.setAggressive(false);
        this.creature.getNavigation().stop();
        this.creature.setAttackState(0);
        this.goalActiveTime = 0;
        this.attackTime = 0;
        this.timeOut = 0;
        //System.out.println("goalEnd");
    }

    @Override
    public boolean canUse() {
        return this.creature.getTarget() != null && this.creature.getTarget().isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.creature.getTarget();

        if (this.timeOut > 3600) {
            //System.out.println("timeout");
            return false;
            //after 5 minutes of not hitting shit stop attacking
        }

        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!this.creature.isWithinRestriction(target.blockPosition())) {
            return false;
        }

        if (target instanceof Player p) {
            if (p.isCreative() || !p.isSpectator()) {
                return false;
            } else if (this.creature.isOwnedBy(p)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    protected double getAttackReachSqr(LivingEntity target) {
        return this.creature.getBbWidth() * 2.0F * this.creature.getBbWidth() * 2.0F + target.getBbWidth();
    }

    public void resetTimeout() {
        this.timeOut = 0;
    }


}
