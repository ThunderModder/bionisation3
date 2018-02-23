package com.thunder.jei.recipes;

import com.thunder.item.DNAPattern;
import com.thunder.item.GeneVial;
import com.thunder.item.ItemBlood;
import com.thunder.item.ItemRegistry;
import com.thunder.recipe.MachineRecipeRegistry;
import com.thunder.util.Utilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DNAFormerRecipes {

    public static List<DNAFormerRecipes> recipes = new ArrayList<>();

    private ItemStack input;
    private ItemStack output;

    public DNAFormerRecipes(ItemStack in, ItemStack out){
        this.input = in;
        this.output = out;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public static List<DNAFormerRecipes> getRecipes() {
        return recipes;
    }

    public static void initDNAFRecipes(){
        for(Map.Entry<ItemStack, Integer> entry : MachineRecipeRegistry.getInstance().getDNA_FORMER_RECIPE_MAP().entrySet()){
            recipes.add(new DNAFormerRecipes(entry.getKey().copy(), getPattern(entry.getValue())));
        }
        for(Map.Entry<String, Integer> entry : MachineRecipeRegistry.getInstance().getBLOOD_MAP().entrySet()){
            ItemStack blood = new ItemStack(ItemRegistry.ITEM_BLOOD);
            NBTTagCompound tag = Utilities.getNbt(blood);
            tag.setString(ItemBlood.BLOOD_KEY, entry.getKey());
            recipes.add(new DNAFormerRecipes(blood, getPattern(entry.getValue())));
        }
        int [] genes = GeneVial.GENES;
        for(int i : genes){
            ItemStack genev = new ItemStack(ItemRegistry.GENE_VIAL);
            NBTTagCompound tag = Utilities.getNbt(genev);
            tag.setInteger(GeneVial.GENE_KEY, i);
            recipes.add(new DNAFormerRecipes(genev, getPattern(i)));
        }
    }

    private static ItemStack getPattern(int dnai){
        ItemStack dnap = new ItemStack(ItemRegistry.DNA_PATTERN);
        NBTTagCompound tag = Utilities.getNbt(dnap);
        int [] dna = new int[1];
        dna[0] = dnai;
        tag.setIntArray(DNAPattern.DNA_ARRAY_KEY, dna);
        return dnap;
    }
}
