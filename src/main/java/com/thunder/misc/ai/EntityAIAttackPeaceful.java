package com.thunder.misc.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;

public class EntityAIAttackPeaceful extends EntityAIAttackMelee {

    public EntityAIAttackPeaceful(EntityCreature creature, double speedIn, boolean useLongMemory) {
        super(creature, speedIn, useLongMemory);
    }

    protected void checkAndPerformAttack(EntityLivingBase entity, double distance) {
        double d0 = getAttackReachSqr(entity);
        if (distance <= d0 && this.attackTick <= 0) {
            this.attackTick = 20;
            this.attacker.swingArm(EnumHand.MAIN_HAND);
            entity.attackEntityFrom(DamageSource.causeMobDamage(this.attacker), 5);
        }
    }
}
