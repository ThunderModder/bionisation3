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
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.Event;


public class BoneBacteria extends AbstractEffect {

    public BoneBacteria() {
        this(Constants.DURATION_BONEBACTERIA, 0);
    }

    public BoneBacteria(int duration, int power) {
        super(Constants.ID_BONEBACTERIA, duration, power, true, "Bone Bacteria", SampleType.BACTERIA);
        observablePotions.add(Constants.POTION_SPEED_ID);
        canUpdatePower = true;
    }

    @Override
    public void performPlayer(Event event, EntityPlayer player, EventType type, IBioPlayer cap) {
        if(type == EventType.TICK){
            Utilities.addPotionEffect(player, Constants.POTION_SPEED_ID, power, -1, wasPowerChanged);
            if(!player.getHeldItem(player.getActiveHand()).isEmpty() && player.getActiveItemStack().getItem() == Items.BOW)
                player.dropItem(true);
            if(Utilities.isTickerEqual(cap.getTicker(), 2400)) cap.removeImmunity(1);
        }
    }

    @Override
    public void performEntity(Event event, EntityLivingBase entity, EventType type, IBioMob cap) {

    }
}
