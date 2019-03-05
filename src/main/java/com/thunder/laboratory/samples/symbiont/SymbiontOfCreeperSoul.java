package com.thunder.laboratory.samples.symbiont;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SymbiontOfCreeperSoul extends AbstractEffect {

    public SymbiontOfCreeperSoul() {
        this(Constants.DURATION_CREEPER_SYMBIONT, 0);
    }

    public SymbiontOfCreeperSoul(int duration, int power) {
        super(Constants.ID_CREEPER_SYMBIONT, duration, power, false, "Symbiont Of Creeper Soul", SampleType.SYMBIONT);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                IBioSample creeperV = cap.getEffectById(Constants.ID_CREEPER_VIRUS);
                if (creeperV != null) creeperV.setExpired(true);
            }
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            target.world.createExplosion(target, target.posX, target.posY, target.posZ, 1.0f, false);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
