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
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SymbiontOfWitheringEye extends AbstractEffect {

    public SymbiontOfWitheringEye() {
        this(Constants.DURATION_WEYE_SYMBIONT, 0);
    }

    public SymbiontOfWitheringEye(int duration, int power) {
        super(Constants.ID_WEYE_SYMBIONT, duration, power, false, "Symbiont Of Withering Eye", SampleType.SYMBIONT);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            IBioSample witherV = cap.getEffectById(Constants.ID_WITHER_VIRUS);
            if (witherV != null) witherV.setExpired(true);
        }
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            if(!(target instanceof EntityCreeper))
                Utilities.addPotionEffect(target, Constants.POTION_WITHER_ID, power, 40);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
