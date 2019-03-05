package com.thunder.laboratory.samples.virus.custom;

import com.thunder.laboratory.EventType;
import com.thunder.laboratory.SampleType;
import com.thunder.laboratory.samples.virus.AbstractVirusEffect;
import com.thunder.laboratory.samples.virus.ICustomVirus;
import com.thunder.mob.IBioMob;
import com.thunder.player.IBioPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class AbstractCustomVirusEffect extends AbstractVirusEffect implements ICustomVirus {

    protected boolean isCustom;

    protected AbstractCustomVirusEffect(int id, int duration, int power, boolean isDangerous, String name, String dna, SampleType type) {
        super(id, duration, power, isDangerous, name, dna, type);
        isCustom = true;
    }

    @Override
    public boolean isCustom() {
        return isCustom;
    }

    @Override
    public void setCustom(boolean custom) {
        isCustom = custom;
    }

    @Override
    public void addOrReaddObservable(){}
}
