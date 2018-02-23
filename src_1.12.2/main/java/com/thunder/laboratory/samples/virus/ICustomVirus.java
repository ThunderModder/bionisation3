package com.thunder.laboratory.samples.virus;

import com.thunder.laboratory.IVirus;

public interface ICustomVirus extends IVirus {

    boolean isCustom();
    void setCustom(boolean custom);
    void addOrReaddObservable();
}
