package com.thunder.laboratory;

import com.thunder.laboratory.samples.*;
import com.thunder.laboratory.samples.bacteria.*;
import com.thunder.laboratory.samples.cure.*;
import com.thunder.laboratory.samples.virus.*;
import com.thunder.laboratory.samples.virus.symbiosis.APSymbiosis;
import com.thunder.laboratory.samples.virus.symbiosis.EWSymbiosis;
import com.thunder.laboratory.samples.virus.symbiosis.OVSymbiosis;
import com.thunder.laboratory.samples.virus.symbiosis.RSSymbiosis;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;

import java.util.ArrayList;
import java.util.List;


public class EffectContainer {

    private static final EffectContainer EFFECT_CONTAINER = new EffectContainer();
    public final List<IBioSample> effects = new ArrayList<>();

    private EffectContainer(){
       effects.add(new EffectFracture());
       effects.add(new EffectFoodPoisoning());
       effects.add(new EffectInternalBleeding());
       effects.add(new EffectLackOfBlood());
       effects.add(new EffectBleeding());
       effects.add(new EffectFatigue());
       effects.add(new EffectSunstroke());
       effects.add(new EffectCold());
       effects.add(new BlackBacteria());
       effects.add(new SwampBacteria());
       effects.add(new GlowingBacteria());
       effects.add(new WaterBacteria());
       effects.add(new EnderBacteria());
       effects.add(new CactusBacteria());
       effects.add(new BoneBacteria());
       effects.add(new TeraBacteria());
       effects.add(new MyceliumBacteria());
       effects.add(new SGreenBacteria());
       effects.add(new BGreenBacteria());
       effects.add(new SeaBacteria());
       effects.add(new CloneBacteria());
       effects.add(new CureBleeding());
       effects.add(new CureCold());
       effects.add(new CureFPoison());
       effects.add(new CureBlackBacteria());
       effects.add(new CureGlowingBacteria());
       effects.add(new CureWaterBacteria());
       effects.add(new EnderBacteria());
       effects.add(new CureCactusBacteria());
       effects.add(new CureBoneBacteria());
       effects.add(new CureTeraBacteria());
       effects.add(new CureMyceliumBacteria());
       effects.add(new CureSGreenBacteria());
       effects.add(new CureSeaBacteria());
       effects.add(new EffectImmunity());
       effects.add(new GiantVirus());
       effects.add(new PteroVirus());
       effects.add(new EnderVirus());
       effects.add(new BrainVirus());
       effects.add(new WitherVirus());
       effects.add(new BatVirus());
       effects.add(new BloodVirus());
       effects.add(new CreeperVirus());
       effects.add(new RedVirus());
       effects.add(new OceanVirus());
       effects.add(new SkullVirus());
       effects.add(new Rabies());
       effects.add(new PolarVirus());
       effects.add(new AerVirus());
       effects.add(new DesertVirus());
       effects.add(new APSymbiosis());
       effects.add(new EWSymbiosis());
       effects.add(new OVSymbiosis());
       effects.add(new RSSymbiosis());
    }

    public static EffectContainer getInstance(){
        return EFFECT_CONTAINER;
    }

    public void addToEffects(IBioSample sample){
        if(Utilities.findEffectById(sample.getId()) == null)
            effects.add(sample);
    }
}
