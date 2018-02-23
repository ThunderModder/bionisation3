package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.symbiosis.EWSymbiosis;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.List;

public class WitherVirus extends AbstractVirusEffect {

    public WitherVirus() {
        this(Constants.STANDART_VIRUS_DURATION, 0);
    }

    public WitherVirus(int duration, int power) {
        super(Constants.ID_WITHER_VIRUS, duration, power, true, "Wither Virus", Constants.DNA_WITHER_VIRUS, SampleType.VIRUS);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                //check viruses
                IBioSample v = cap.getEffectById(Constants.ID_ENDER_VIRUS);
                if(v != null) cap.addEffectIntoQueue(new EWSymbiosis(Constants.STANDART_VIRUS_DURATION, Utilities.getPowerFromImmunity(cap.getImmunityLevel())));
                //effects
                AxisAlignedBB box = player.getEntityBoundingBox().expand(5, 5, 5);
                List<Entity> entities = player.world.getEntitiesWithinAABBExcludingEntity(player, box);
                for (Entity e : entities) {
                    if (e instanceof EntityLivingBase && !e.equals(player) && !(e instanceof EntityCreeper)) {
                        Utilities.addPotionEffect((EntityLivingBase) e, Constants.POTION_WITHER_ID, this.power, 40, wasPowerChanged);
                    }
                }
                Utilities.spreadEffect(this, player, EntityLivingBase.class, 5);
            }
            if(Utilities.isTickerEqual(cap.getTicker(), 600)){
                Utilities.addPotionEffect(player, Constants.POTION_BLINDNESS_ID, this.power, 40, wasPowerChanged);
                Utilities.addPotionEffect(player, Constants.POTION_NAUSEA_ID, this.power, 120, wasPowerChanged);
            }
            if(Utilities.isTickerEqual(cap.getTicker(), 1200)) cap.removeImmunity(1);

        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                AxisAlignedBB box = entity.getEntityBoundingBox().expand(5, 5, 5);
                List<Entity> entities = entity.world.getEntitiesWithinAABBExcludingEntity(entity, box);
                for (Entity e : entities) {
                    if (e instanceof EntityLivingBase && !e.equals(entity) && !(e instanceof EntityCreeper)) {
                        Utilities.addPotionEffect((EntityLivingBase) e, Constants.POTION_WITHER_ID, this.power, 40, wasPowerChanged);
                    }
                }
            }
        }
    }
}
