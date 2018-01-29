package com.thunder.laboratory.samples.bacteria;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;


public class BlackBacteria extends AbstractEffect {

    public BlackBacteria() {
        this(Constants.DURATION_BLACKBACTERIA, 0);
    }

    public BlackBacteria(int duration, int power) {
        super(Constants.ID_BLACKBACTERIA, duration, power, true, "Black Bacteria", SampleType.BACTERIA);
        observablePotions.add(Constants.POTION_BLINDNESS_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_BLINDNESS_ID, power, -1, wasPowerChanged);
            if(Utilities.isTickerEqual(cap.getTicker(), 6000)) cap.removeImmunity(1);
        }
        if(type == EventType.ATTACK){
            Utilities.addPotionEffect(((LivingAttackEvent)event).getEntityLiving(), Constants.POTION_POISON_ID, 1, 40);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
