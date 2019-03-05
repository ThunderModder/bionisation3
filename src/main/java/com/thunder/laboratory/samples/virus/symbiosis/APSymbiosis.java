package com.thunder.laboratory.samples.virus.symbiosis;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.AbstractVirusEffect;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class APSymbiosis extends AbstractVirusEffect {

    public APSymbiosis() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public APSymbiosis(int duration, int power) {
        super(Constants.ID_AP_SYMBIOSIS, duration, power, true, "AP Symbiosis", Constants.DNA_AP_SYMBIOSIS, SampleType.SYMBIOSIS);
        observablePotions.add(Constants.POTION_SPEED_ID);
        observablePotions.add(Constants.POTION_HASTE_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_HASTE_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 80)){
                //check viruses
                IBioSample v1 = cap.getEffectById(Constants.ID_AER_VIRUS);
                IBioSample v2 = cap.getEffectById(Constants.ID_PTERO_VIRUS);
                if(v1 != null) v1.setExpired(true);
                if(v2 != null) v2.setExpired(true);
                //effect
                AxisAlignedBB box = player.getEntityBoundingBox().expand(5, 5, 5);
                List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, box);
                for (Entity e : entities){
                    if(e instanceof EntityLivingBase && !(e instanceof EntityPlayer)){
                        e.attackEntityFrom(DamageSource.GENERIC, ((EntityLivingBase) e).getMaxHealth() + 1000f);
                    }
                }
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
