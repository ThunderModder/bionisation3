package com.thunder.misc.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityAINearestAttackableTargetR<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {

    public EntityAINearestAttackableTargetR(EntityCreature creature, Class classTarget, boolean checkSight) {
        super(creature, classTarget, checkSight);
    }
}
