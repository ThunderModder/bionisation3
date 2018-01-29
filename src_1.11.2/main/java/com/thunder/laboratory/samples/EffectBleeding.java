package com.thunder.laboratory.samples;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.Event;


public class EffectBleeding extends AbstractEffect {

    public EffectBleeding() {
        this(Constants.DURATION_BLEEDING, 0);
    }

    public EffectBleeding(int duration, int power) {
        super(Constants.ID_BLEEDING, duration, power, false, "Bleeding", SampleType.EFFECT);
        observablePotions.add(Constants.POTION_WEAKENSS_ID);
        observablePotions.add(Constants.POTION_SLOWNESS_ID);
        needToBeSynced = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 300)){
                cap.removeBlood(2);
                cap.syncBloodCap(player);
            }
            Utilities.addPotionEffect(player, Constants.POTION_WEAKENSS_ID, this.power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, this.power, -1, wasPowerChanged);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK) {
            if(Utilities.isTickerEqual(cap.getTicker(), 300))
                entity.attackEntityFrom(DamageSource.GENERIC, 1f);
            Utilities.addPotionEffect(entity, Constants.POTION_WEAKENSS_ID, this.power, -1, wasPowerChanged);
            Utilities.addPotionEffect(entity, Constants.POTION_SLOWNESS_ID, this.power, -1, wasPowerChanged);
        }
    }
}
