package com.thunder.laboratory.samples.symbiont;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SymbiontOfMagicAffinity extends AbstractEffect {

    public SymbiontOfMagicAffinity() {
        this(Constants.DURATION_MAFFINITY_SYMBIONT, 0);
    }

    public SymbiontOfMagicAffinity(int duration, int power) {
        super(Constants.ID_MAFFINITY_SYMBIONT, duration, power, false, "Symbiont Of Magic Affinity", SampleType.SYMBIONT);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.HURT){
            LivingHurtEvent e = (LivingHurtEvent)event;
            if(e.getSource() == DamageSource.MAGIC) e.setCanceled(true);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
