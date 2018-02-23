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
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SymbiontOfEffectPower extends AbstractEffect {

    public SymbiontOfEffectPower() {
        this(Constants.DURATION_EPOWER_SYMBIONT, 0);
    }

    public SymbiontOfEffectPower(int duration, int power) {
        super(Constants.ID_EPOWER_SYMBIONT, duration, power, false, "Symbiont Of Effect Power", SampleType.SYMBIONT);
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            if(Utilities.isTickerEqual(cap.getTicker(), 100)) {
                Collection<PotionEffect> effs = player.getActivePotionEffects();
                List<PotionEffect> toEdit = new ArrayList<>();
                boolean wasModified = false;
                //do stuff with kostyli
                for(PotionEffect e : effs){
                    toEdit.add(new PotionEffect(e));
                }
                for (PotionEffect e : toEdit) {
                    if(player.isPotionActive(e.getPotion()))
                        player.removePotionEffect(e.getPotion());
                    player.addPotionEffect(new PotionEffect(e.getPotion(), e.getDuration(), e.getAmplifier() + power + 1, e.getIsAmbient(), e.doesShowParticles()));
                    wasModified = true;
                }
                if(wasModified)
                    isExpired = true;
            }
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {}
}
