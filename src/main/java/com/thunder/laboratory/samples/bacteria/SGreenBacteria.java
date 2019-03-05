package com.thunder.laboratory.samples.bacteria;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;


public class SGreenBacteria extends AbstractEffect {

    public SGreenBacteria() {
        this(Constants.DURATION_SMALLGREENBACTERIA, 0);
    }

    public SGreenBacteria(int duration, int power) {
        super(Constants.ID_SMALLGREENBACTERIA, duration, power, true, "Small Green Bacteria", SampleType.BACTERIA);
        observablePotions.add(Constants.POTION_REGENERATION_ID);
        observablePotions.add(Constants.POTION_SATURATION_ID);
        observablePotions.add(Constants.POTION_JUMPBOOST_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_REGENERATION_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_SATURATION_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_JUMPBOOST_ID, power, -1, wasPowerChanged);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}