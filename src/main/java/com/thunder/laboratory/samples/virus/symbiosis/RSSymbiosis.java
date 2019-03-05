package com.thunder.laboratory.samples.virus.symbiosis;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.AbstractVirusEffect;
import com.thunder.laboratory.samples.virus.OceanVirus;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class RSSymbiosis extends AbstractVirusEffect {

    public RSSymbiosis() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public RSSymbiosis(int duration, int power) {
        super(Constants.ID_RS_SYMBIOSIS, duration, power, true, "RS Symbiosis", Constants.DNA_RS_SYMBIOSIS, SampleType.SYMBIOSIS);

    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 80)) {
                //check viruses
                IBioSample v1 = cap.getEffectById(Constants.ID_RED_VIRUS);
                IBioSample v2 = cap.getEffectById(Constants.ID_SKULL_VIRUS);
                if (v1 != null) v1.setExpired(true);
                if (v2 != null) v2.setExpired(true);
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addPotionEffect(target, Constants.POTION_BLINDNESS_ID, power, 40);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}