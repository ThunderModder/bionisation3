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
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.Event;


public class GlowingBacteria extends AbstractEffect {

    public GlowingBacteria() {
        this(Constants.DURATION_GLOWINGBACTERIA, 0);
    }

    public GlowingBacteria(int duration, int power) {
        super(Constants.ID_GLOWINGBACTERIA, duration, power, true, "Glowing Bacteria", SampleType.BACTERIA);
        observablePotions.add(Constants.POTION_GLOWING_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_GLOWING_ID, power, -1, wasPowerChanged);
            Utilities.removeIfActive(player, Constants.POTION_FIRERESISTANCE_ID);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}