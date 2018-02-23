package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.AbstractEffect;
import com.thunder.laboratory.IVirus;
import com.thunder.laboratory.SampleType;


public abstract class AbstractVirusEffect extends AbstractEffect implements IVirus {

    protected boolean hasDNA;
    protected boolean canMutate;
    protected boolean isHidden;
    protected String DNA;

    protected AbstractVirusEffect(int id, int duration, int power, boolean isDangerous, String name, String dna, SampleType type) {
        super(id, duration, power, isDangerous, name, type);

        this.hasDNA = true;
        this.canMutate = true;
        this.isHidden = false;
        this.DNA = dna;
    }

    @Override
    public String getDNA(){
        return this.DNA;
    }

    @Override
    public void setDNA(String newDNA){
        this.DNA = newDNA;
    }

    @Override
    public boolean hasDNA(){
        return this.hasDNA;
    }

    @Override
    public void setHasDNA(boolean b){
        this.hasDNA = b;
    }

    @Override
    public boolean isCanMutate() {
        return canMutate;
    }

    @Override
    public void setCanMutate(boolean canMutate) {
        this.canMutate = canMutate;
    }

    @Override
    public boolean isHidden(){
        return isHidden;
    }

    @Override
    public void setHidden(boolean hidden){
        this.isHidden = hidden;
    }
}

