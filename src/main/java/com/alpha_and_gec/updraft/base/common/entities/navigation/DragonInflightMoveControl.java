package com.alpha_and_gec.updraft.base.common.entities.navigation;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Phantom;
import net.minecraft.world.phys.Vec3;

class DragonInFlightMovecontrol extends MoveControl {
    private float speed = 0.1F;

    public DragonInFlightMovecontrol(UpdraftDragon pMob) {
        super(pMob);
    }

    /*public void tick() {
        if (mob.horizontalCollision) {
            mob.setYRot(mob.getYRot() + 180.0F);
            this.speed = 0.1F;
        }

        double d0 = Phantom.this.moveTargetPoint.x - Phantom.this.getX();
        double d1 = Phantom.this.moveTargetPoint.y - Phantom.this.getY();
        double d2 = Phantom.this.moveTargetPoint.z - Phantom.this.getZ();
        //^distance to moveTargetPoint in individual axes

        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        //^euclidean distance to moveTargetPoint

        if (Math.abs(d3) > (double)1.0E-5F) {

            double d4 = 1.0D - Math.abs(d1 * (double)0.7F) / d3;
            //slightly modified ratio of vertical distance over horizontal distance

            d0 *= d4;
            d2 *= d4;
            d3 = Math.sqrt(d0 * d0 + d2 * d2);
            //set all the ds to have the same ratio

            double d5 = Math.sqrt(d0 * d0 + d2 * d2 + d1 * d1);

            float f = Phantom.this.getYRot();
            float f1 = (float) Mth.atan2(d2, d0);

            float f2 = Mth.wrapDegrees(Phantom.this.getYRot() + 90.0F);
            float f3 = Mth.wrapDegrees(f1 * (180F / (float)Math.PI));

            Phantom.this.setYRot(Mth.approachDegrees(f2, f3, 4.0F) - 90.0F);
            Phantom.this.yBodyRot = Phantom.this.getYRot();

            if (Mth.degreesDifferenceAbs(f, Phantom.this.getYRot()) < 3.0F) {
                this.speed = Mth.approach(this.speed, 1.8F, 0.005F * (1.8F / this.speed));
            } else {
                this.speed = Mth.approach(this.speed, 0.2F, 0.025F);
            }

            float f4 = (float)(-(Mth.atan2(-d1, d3) * (double)(180F / (float)Math.PI)));
            Phantom.this.setXRot(f4);
            float f5 = Phantom.this.getYRot() + 90.0F;
            double d6 = (double)(this.speed * Mth.cos(f5 * ((float)Math.PI / 180F))) * Math.abs(d0 / d5);
            double d7 = (double)(this.speed * Mth.sin(f5 * ((float)Math.PI / 180F))) * Math.abs(d2 / d5);
            double d8 = (double)(this.speed * Mth.sin(f4 * ((float)Math.PI / 180F))) * Math.abs(d1 / d5);
            Vec3 vec3 = Phantom.this.getDeltaMovement();
            Phantom.this.setDeltaMovement(vec3.add((new Vec3(d6, d8, d7)).subtract(vec3).scale(0.2D)));
        }

    }*/
}
