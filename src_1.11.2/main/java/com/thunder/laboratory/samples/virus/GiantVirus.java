package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.Event;

public class GiantVirus extends AbstractVirusEffect {

    public GiantVirus() {
        this(Constants.DURATION_GIANT_VIRUS, 0);
    }

    public GiantVirus(int duration, int power) {
        super(Constants.ID_GIANT_VIRUS, duration, power, true, "Giant Virus", Constants.DNA_GIANT_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_STRENGTH_ID);
        observablePotions.add(Constants.POTION_HEALTHBOOST_ID);
        needToBeSynced = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK) isExpired = true;
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(entity.isBurning()) entity.extinguish();
            Utilities.addPotionEffect(entity, Constants.POTION_STRENGTH_ID, this.power, -1, wasPowerChanged);
            Utilities.addPotionEffect(entity, Constants.POTION_HEALTHBOOST_ID, this.power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) Utilities.spreadEffect(this, entity, EntityMob.class, 5);
            if(isExpired) entity.attackEntityFrom(DamageSource.GENERIC, entity.getMaxHealth() + 1000f);
        }
    }
}
