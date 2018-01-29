package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.EffectBleeding;
import com.thunder.laboratory.samples.EffectInternalBleeding;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class BloodVirus extends AbstractVirusEffect {

    public BloodVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public BloodVirus(int duration, int power) {
        super(Constants.ID_BLOOD_VIRUS, duration, power, true, "Blood Virus", Constants.DNA_BLOOD_VIRUS, SampleType.VIRUS);
        isHidden = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(duration > 6000){
                if(isHidden) isHidden = false;
                if(Utilities.isTickerEqual(cap.getTicker(), 100))
                    cap.addEffectIntoQueue(new EffectInternalBleeding(Constants.DURATION_BLEEDING, 2));
                if(Utilities.isTickerEqual(cap.getTicker(), 3600))
                    cap.removeImmunity(2);
            }
            if(duration > 12000)
                if(Utilities.isTickerEqual(cap.getTicker(), 100))
                    cap.addEffectIntoQueue(new EffectBleeding(Constants.DURATION_BLEEDING, 2));
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new BloodVirus(STANDART_VIRUS_DURATION, 1), target);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(duration > 1200) {
                if(isHidden) isHidden = false;
                if (Utilities.isTickerEqual(cap.getTicker(), 100))
                    cap.addEffectIntoQueue(new EffectBleeding(Constants.DURATION_BLEEDING, 2));
            }
            if (Utilities.isTickerEqual(cap.getTicker(), 800))
                Utilities.spreadEffect(this, entity, EntityLivingBase.class, 8);
        }
    }
}
