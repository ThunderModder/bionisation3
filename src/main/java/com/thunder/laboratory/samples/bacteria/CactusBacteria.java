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
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;


public class CactusBacteria extends AbstractEffect {

    public CactusBacteria() {
        this(Constants.DURATION_CACTUSBACTERIA, 0);
    }

    public CactusBacteria(int duration, int power) {
        super(Constants.ID_CACTUSBACTERIA, duration, power, true, "Cactus Bacteria", SampleType.BACTERIA);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.HURT){
            LivingHurtEvent ev = (LivingHurtEvent)event;
            DamageSource source = ev.getSource();
            if(source.getTrueSource() instanceof EntityLivingBase){
                EntityLivingBase ent = (EntityLivingBase)source.getTrueSource();
                if(!Utilities.isEffectActive(Constants.ID_CACTUSBACTERIA, ent))
                    ent.attackEntityFrom(DamageSource.GENERIC, 0.5f);
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}