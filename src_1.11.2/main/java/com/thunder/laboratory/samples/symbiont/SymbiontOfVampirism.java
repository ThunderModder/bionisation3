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
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class SymbiontOfVampirism extends AbstractEffect {

    public SymbiontOfVampirism() {
        this(Constants.DURATION_VAMPIRISM_SYMBIONT, 0);
    }

    public SymbiontOfVampirism(int duration, int power) {
        super(Constants.ID_VAMPIRISM_SYMBIONT, duration, power, false, "Symbiont Of Vampirism", SampleType.SYMBIONT);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.ATTACK){
            LivingAttackEvent e = (LivingAttackEvent)event;
            EntityLivingBase target = e.getEntityLiving();
            float hpToSteal = power + 1f;
            target.attackEntityFrom(DamageSource.GENERIC, hpToSteal);
            player.heal(hpToSteal);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
