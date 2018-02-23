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


public class SeaBacteria extends AbstractEffect {

    public SeaBacteria() {
        this(Constants.DURATION_SEABACTERIA, 0);
    }

    public SeaBacteria(int duration, int power) {
        super(Constants.ID_SEABACTERIA, duration, power, true, "Sea Bacteria", SampleType.BACTERIA);
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(player.isInWater()){
                player.motionX *= 1.2000000000000003D;
                player.motionZ *= 1.2000000000000003D;
            }
            if(Utilities.isTickerEqual(cap.getTicker(), 3600)) cap.removeImmunity(2);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
