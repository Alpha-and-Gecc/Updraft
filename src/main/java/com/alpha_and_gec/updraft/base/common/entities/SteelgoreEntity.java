package com.alpha_and_gec.updraft.base.common.entities;

import com.alpha_and_gec.updraft.base.common.entities.base.UpdraftDragon;
import com.alpha_and_gec.updraft.base.registry.UpdraftEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.PolarBear;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import javax.annotation.Nullable;

public class SteelgoreEntity extends UpdraftDragon {

    public SteelgoreEntity(EntityType<? extends Animal> p_30341_, Level p_30342_) {
        super(p_30341_, p_30342_);
    }

    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
        return UpdraftEntities.STEELGORE.get().create(level);
    }

    public boolean isFood(ItemStack stack) {

        //if (stack.is(UpdraftTags.STEELGORE_DIET) && stack.getCount() == stack.getMaxStackSize())
        //    return true;
        //else {
            return false;
        //}
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomSwimmingGoal(this, 1.0D, 40));
        this.goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 120.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.FOLLOW_RANGE, 40.0D).add(Attributes.ATTACK_DAMAGE, 16.0D);
    }

    public static boolean checkSteelgoreSpawnRules(EntityType<SteelgoreEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource rand) {
        Holder<Biome> holder = level.getBiome(position);

        //if (!holder.is(BiomeTags.POLAR_BEARS_SPAWN_ON_ALTERNATE_BLOCKS)) {
            return checkAnimalSpawnRules(entityType, level, spawnType, position, rand);
        //} else {
            //return isBrightEnoughToSpawn(level, position) && level.getBlockState(position.below()).is(UpdraftTags.STEELGORE_SPAWNABLE_BLOCKS);
        //}
    }

    public int getVariant() {
        return 1;
    }
}
