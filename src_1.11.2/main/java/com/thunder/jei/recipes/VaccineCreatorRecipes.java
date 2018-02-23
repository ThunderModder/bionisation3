package com.thunder.jei.recipes;

import com.thunder.item.ItemRegistry;
import com.thunder.laboratory.EffectContainer;
import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.IVirus;
import com.thunder.laboratory.SampleType;
import com.thunder.util.Utilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class VaccineCreatorRecipes {

    public static List<VaccineCreatorRecipes> recipes = new ArrayList<>();

    private ItemStack vial;
    private ItemStack out;

    public VaccineCreatorRecipes(ItemStack vial, ItemStack out){
       this.vial = vial;
       this.out = out;
    }

    public ItemStack getVial() {
        return vial;
    }

    public ItemStack getOut() {
        return out;
    }

    public static List<VaccineCreatorRecipes> getRecipes() {
        return recipes;
    }

    public static void initVCRecipes(){
        for(IBioSample smp : EffectContainer.getInstance().effects){
            if(smp.getType() == SampleType.VIRUS && smp instanceof IVirus){
                IVirus virus = (IVirus)smp;
                if(virus.hasDNA()){
                    ItemStack vial = new ItemStack(ItemRegistry.VIAL);
                    NBTTagCompound tagv = Utilities.getNbt(vial);
                    tagv.setString(Utilities.getModIdString("sdna"), virus.getDNA());
                    ItemStack inj = new ItemStack(ItemRegistry.VACCINE_INJECTOR);
                    NBTTagCompound taginj = Utilities.getNbt(inj);
                    taginj.setString(Utilities.getModIdString("vstability"), "Stable");
                    taginj.setString(Utilities.getModIdString("vdna"), virus.getDNA());
                    recipes.add(new VaccineCreatorRecipes(vial, inj));
                }
            }
        }
        ItemStack vial = new ItemStack(ItemRegistry.VIAL);
        NBTTagCompound tagv = Utilities.getNbt(vial);
        tagv.setString(Utilities.getModIdString("sdna"), "?:?:?:?:?:?:?;?");
        ItemStack inj = new ItemStack(ItemRegistry.VACCINE_INJECTOR);
        NBTTagCompound taginj = Utilities.getNbt(inj);
        taginj.setString(Utilities.getModIdString("vstability"), "Stable");
        taginj.setString(Utilities.getModIdString("vdna"), "?:?:?:?:?:?:?;?");
        recipes.add(new VaccineCreatorRecipes(vial, inj));
    }
}
