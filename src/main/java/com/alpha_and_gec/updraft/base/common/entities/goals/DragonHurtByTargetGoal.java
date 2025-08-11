package com.alpha_and_gec.updraft.base.common.entities.goals;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;

public class DragonHurtByTargetGoal extends HurtByTargetGoal {

    UpdraftDragon mob;

    public DragonHurtByTargetGoal(PathfinderMob pMob, Class<?>... pToIgnoreDamage) {
        super(pMob, pToIgnoreDamage);
    }


    public boolean canUse() {
        if (this.mob == null || this.mob.getLastHurtByMob() == null) {
            //assert nonull
            return false;
        }

        if ((this.mob.getLastHurtByMob().getClass() == this.mob.getClass() && this.mob.isSelfFriendly()) || this.mob.isOwnedBy(this.mob.getLastHurtByMob()) || this.mob.isOrderedToSit()) {
            //cannot attack mobs of equal class if the mob is self friendly and cannot attack the mob's owner
            return false;

        } else{
            return super.canUse();
        }

    }

}
