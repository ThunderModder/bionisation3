package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.symbiosis.APSymbiosis;
import com.thunder.laboratory.samples.virus.symbiosis.OVSymbiosis;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

import static com.thunder.util.Constants.STANDART_VIRUS_DURATION;

public class OceanVirus extends AbstractVirusEffect {

    public OceanVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public OceanVirus(int duration, int power) {
        super(Constants.ID_OCEAN_VIRUS, duration, power, true, "Ocean Virus", Constants.DNA_OCEAN_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_SLOWNESS_ID);
        observablePotions.add(Constants.POTION_WEAKENSS_ID);
        observablePotions.add(Constants.POTION_SPEED_ID);
        observablePotions.add(Constants.POTION_REGENERATION_ID);
        isHidden = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                //check viruses
                IBioSample v = cap.getEffectById(Constants.ID_VAMPIRE_VIRUS);
                if(v != null) cap.addEffectIntoQueue(new OVSymbiosis(Constants.STANDART_VIRUS_DURATION, Utilities.getPowerFromImmunity(cap.getImmunityLevel())));
            }
            if(duration > 1200){
                if(isHidden) isHidden = false;
                if(player.isWet()){
                    Utilities.removeIfActive(player, Constants.POTION_WEAKENSS_ID);
                    Utilities.removeIfActive(player, Constants.POTION_SLOWNESS_ID);
                    Utilities.addPotionEffect(player, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
                    Utilities.addPotionEffect(player, Constants.POTION_REGENERATION_ID, power, -1, wasPowerChanged);
                }else{
                    Utilities.removeIfActive(player, Constants.POTION_SPEED_ID);
                    Utilities.removeIfActive(player, Constants.POTION_REGENERATION_ID);
                    Utilities.addPotionEffect(player, Constants.POTION_SLOWNESS_ID, power, -1, wasPowerChanged);
                    Utilities.addPotionEffect(player, Constants.POTION_WEAKENSS_ID, power, -1, wasPowerChanged);
                }
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100))
                Utilities.spreadEffect(this, entity, EntityPlayer.class, 8);
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            Utilities.addEffectToLiving(new OceanVirus(STANDART_VIRUS_DURATION, 1), target);
        }
    }
}
