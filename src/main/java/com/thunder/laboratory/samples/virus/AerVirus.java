package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.symbiosis.APSymbiosis;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class AerVirus extends AbstractVirusEffect {

    public AerVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public AerVirus(int duration, int power) {
        super(Constants.ID_AER_VIRUS, duration, power, true, "Aer Virus", Constants.DNA_AER_VIRUS, SampleType.VIRUS);
        observablePotions.add(Constants.POTION_WEAKENSS_ID);
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_WEAKENSS_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                //check viruses
                IBioSample v = cap.getEffectById(Constants.ID_PTERO_VIRUS);
                if(v != null) cap.addEffectIntoQueue(new APSymbiosis(Constants.STANDART_VIRUS_DURATION, Utilities.getPowerFromImmunity(cap.getImmunityLevel())));
            }
            if(player.world.isRaining() || player.world.isThundering()){
                if(Utilities.isTickerEqual(cap.getTicker(), 20)) player.addVelocity(player.world.rand.nextDouble() * 0.5, player.world.rand.nextDouble() * 0.5, player.world.rand.nextDouble() * 0.5);            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
