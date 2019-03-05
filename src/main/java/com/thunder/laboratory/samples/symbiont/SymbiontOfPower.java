package com.thunder.laboratory.samples.symbiont;

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

public class SymbiontOfPower extends AbstractEffect {

    public SymbiontOfPower() {
        this(Constants.DURATION_POWER_SYMBIONT, 0);
    }

    public SymbiontOfPower(int duration, int power) {
        super(Constants.ID_POWER_SYMBIONT, duration, power, false, "Symbiont Of Power", SampleType.SYMBIONT);
        observablePotions.add(Constants.POTION_STRENGTH_ID);
        observablePotions.add(Constants.POTION_REGENERATION_ID);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_STRENGTH_ID, power, -1, wasPowerChanged);
            Utilities.addPotionEffect(player, Constants.POTION_REGENERATION_ID, power, -1, wasPowerChanged);
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
