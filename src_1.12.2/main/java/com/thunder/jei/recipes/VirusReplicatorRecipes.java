package com.thunder.jei.recipes;

import com.thunder.item.DNAPattern;
import com.thunder.item.ItemRegistry;
import com.thunder.item.VirusSprayer;
import com.thunder.util.Utilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class VirusReplicatorRecipes {

    public static List<VirusReplicatorRecipes> recipes = new ArrayList<>();

    private ItemStack input;
    private ItemStack output;

    public VirusReplicatorRecipes(ItemStack in, ItemStack out){
        this.input = in;
        this.output = out;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public static List<VirusReplicatorRecipes> getRecipes() {
        return recipes;
    }

    public static void initVRRecipes(){
        for(DNAFormerRecipes recipe : DNAFormerRecipes.recipes){
            ItemStack dnas = recipe.getOutput().copy();
            NBTTagCompound tag = Utilities.getNbt(dnas);
            if(tag.hasKey(DNAPattern.DNA_ARRAY_KEY)){
                int gene = tag.getIntArray(DNAPattern.DNA_ARRAY_KEY)[0];
                ItemStack sp = new ItemStack(ItemRegistry.VIRUS_SPRAYER);
                NBTTagCompound tags = Utilities.getNbt(sp);
                tags.setString(VirusSprayer.DNA_KEY, gene + ":");
                recipes.add(new VirusReplicatorRecipes(dnas, sp));
            }
        }
    }
}
