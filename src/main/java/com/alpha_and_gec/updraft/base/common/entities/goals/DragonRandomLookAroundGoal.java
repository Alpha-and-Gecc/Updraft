package com.alpha_and_gec.updraft.base.common.entities.goals;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;

public class DragonRandomLookAroundGoal extends RandomLookAroundGoal {
    public DragonRandomLookAroundGoal(Mob pMob) {
        super(pMob);
    }

    public boolean canUse() {
        if (mob instanceof UpdraftDragon drg) {
            if (drg.hasControllingPassenger() || drg.isOrderedToSit() || drg.hasTarget()) {
                return false;
            }
        }

        return super.canUse();
    }
}
