package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.symbiosis.RSSymbiosis;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SkullVirus extends AbstractVirusEffect {

    public SkullVirus() {
        this(Constants.DURATION_SKULL_VIRUS, 0);
    }

    public SkullVirus(int duration, int power) {
        super(Constants.ID_SKULL_VIRUS, duration, power, true, "Skull Virus", Constants.DNA_SKULL_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_HUNGER_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                //check viruses
                IBioSample v = cap.getEffectById(Constants.ID_RED_VIRUS);
                if(v != null) cap.addEffectIntoQueue(new RSSymbiosis(Constants.STANDART_VIRUS_DURATION, Utilities.getPowerFromImmunity(cap.getImmunityLevel())));
            }
            if(duration < 3600){
                Utilities.addPotionEffect(player, Constants.POTION_HUNGER_ID, power, -1, wasPowerChanged);
                if(!player.getHeldItem(player.getActiveHand()).isEmpty())
                    player.dropItem(true);
            }
        }
        if(type == EventType.HURT){
            LivingHurtEvent e = (LivingHurtEvent)event;
            DamageSource source = e.getSource();
            if(source.isProjectile())
                e.setCanceled(true);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
