package com.alpha_and_gec.updraft.base.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

public class PisslikeHitboxes {
    public static void GenerateHitbox(LivingEntity source,
                                             Vec3 boxOffset,
                                             double attackInflation,
                                             ServerLevel world,
                                             float damageMult,
                                             float knockbackMult,
                                             boolean disableShield) {
        //attackInflation is in blocks

        Vec3 sourcePos = source.position();
        double entityAngle = (source.getYHeadRot());
        Vec3 truePos = sourcePos.add(boxOffset);

        double[] offsetXZ = {truePos.x, truePos.z};

        AffineTransform.getRotateInstance(Math.toRadians(entityAngle), sourcePos.x, sourcePos.z).transform(offsetXZ, 0, offsetXZ, 0, 1);
        Vec3 rotatedPos = new Vec3(offsetXZ[0], truePos.y, offsetXZ[1]);

        //Vec3 rotatedPos = sourcePos.add(MathHelpers.makeDirVector3D(source).scale(boxOffset.z()));
        //this is also an option but less optimal

        AABB Hitbox = new AABB(rotatedPos.x - 1, rotatedPos.y - 1, rotatedPos.z - 1, rotatedPos.x + 1, rotatedPos.y + 1, rotatedPos.z + 1);
        Hitbox = Hitbox.inflate(attackInflation);
        //manual inflation makes sure the aabb is properly aligned

        //System.out.println(sourcePos.distanceTo(Hitbox.getCenter()));
        hitboxOutline(Hitbox, world);

        List<Entity> victims = new ArrayList<>(world.getEntitiesOfClass(LivingEntity.class, Hitbox));

        for (Entity victim : victims) {
            if (victim != source && source.hasLineOfSight(victim)) {

                if (victim instanceof LivingEntity liver) {
                    if (victim instanceof Player puh && disableShield == true) {
                        disableShield(puh, puh.getMainHandItem(), puh.getOffhandItem(), source);
                    }

                    knockbackFromSelf(liver, source, knockbackMult);
                }

                victim.hurt(source.damageSources().mobAttack(source), (float) (source.getAttribute(Attributes.ATTACK_DAMAGE).getValue() * damageMult));
                source.doHurtTarget(victim);

            }
        }
    }


    public static void disableShield(Player pPlayer, ItemStack mainHand, ItemStack offHand, Entity source) {

        if (!mainHand.isEmpty() && mainHand.is(Items.SHIELD) && pPlayer.isBlocking()) {
            //float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(source) * 0.05F;
            pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
            source.level().broadcastEntityEvent(pPlayer, (byte)30);

        } else if (!offHand.isEmpty() && offHand.is(Items.SHIELD) && pPlayer.isBlocking()) {
            //float f = 0.25F + (float) EnchantmentHelper.getBlockEfficiency(source) * 0.05F;
            pPlayer.getCooldowns().addCooldown(Items.SHIELD, 100);
            source.level().broadcastEntityEvent(pPlayer, (byte)30);
        }

    }

    public static void knockbackFromSelf(LivingEntity target, LivingEntity attacker, float multiplier) {
        Vec3 dirDiff = attacker.position().subtract(target.position()).normalize();
        target.knockback(attacker.getAttributeValue(Attributes.ATTACK_KNOCKBACK) * multiplier, dirDiff.x, dirDiff.z);
    }


    public static void hitboxOutline (AABB box, ServerLevel world) {
        //System.out.println(sourcePos.distanceTo(Hitbox.getCenter()));
        world.sendParticles(ParticleTypes.FLAME, box.getCenter().x(), box.getCenter().y(), box.getCenter().z(), 1, 0, 0, 0, 0);

        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.maxY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.minY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.minY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.maxX), (box.maxY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);

        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.maxY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.minY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.minY), (box.maxZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        world.sendParticles(ParticleTypes.CAMPFIRE_COSY_SMOKE, (box.minX), (box.maxY), (box.minZ), 1, 0.0D, 0.0D, 0.0D, 0.0D);
    }
}
