package com.thunder.laboratory;

public interface IVirus extends IBioSample {

    String getDNA();
    void setDNA(String newDNA);
    boolean hasDNA();
    void setHasDNA(boolean b);
    boolean isCanMutate();
    void setCanMutate(boolean canMutate);
    boolean isHidden();
    void setHidden(boolean hidden);
}

