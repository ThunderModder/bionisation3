package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.symbiosis.APSymbiosis;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class PteroVirus extends AbstractVirusEffect {

    public PteroVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public PteroVirus(int duration, int power) {
        super(Constants.ID_PTERO_VIRUS, duration, power, true, "Ptero Virus", Constants.DNA_PTERO_VIRUS, SampleType.VIRUS);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)){
                //check viruses
                IBioSample v = cap.getEffectById(Constants.ID_AER_VIRUS);
                if(v != null) cap.addEffectIntoQueue(new APSymbiosis(Constants.STANDART_VIRUS_DURATION, Utilities.getPowerFromImmunity(cap.getImmunityLevel())));
                //effects
                AxisAlignedBB box = player.getEntityBoundingBox().expand(10, 10, 10);
                List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, box);
                for (Entity e : entities){
                    if(e instanceof EntityLivingBase && !e.equals(player)){
                        Utilities.addPotionEffect((EntityLivingBase) e, Constants.POTION_LEVITATION_ID, this.power, 100, wasPowerChanged);
                    }
                }
            }
            if(Utilities.isTickerEqual(cap.getTicker(), 3600))
                cap.removeImmunity(1);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 400))
                Utilities.addPotionEffect(entity, Constants.POTION_LEVITATION_ID, this.power, 100, wasPowerChanged);
        }
    }
}
