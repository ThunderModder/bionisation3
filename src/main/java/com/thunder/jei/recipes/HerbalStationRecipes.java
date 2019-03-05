package com.thunder.jei.recipes;

import com.thunder.item.ItemRegistry;
import com.thunder.item.PotionCure;
import com.thunder.recipe.MachineRecipeRegistry;
import com.thunder.util.Utilities;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HerbalStationRecipes {

    public static List<HerbalStationRecipes> recipes = new ArrayList<>();

    private ItemStack input1;
    private ItemStack input2;
    private ItemStack output;

    public HerbalStationRecipes(ItemStack reagent1, ItemStack reagent2, ItemStack out){
        this.input1 = reagent1;
        this.input2 = reagent2;
        this.output = out;
    }

    public ItemStack getInput1() {
        return input1;
    }

    public void setInput1(ItemStack input1) {
        this.input1 = input1;
    }

    public ItemStack getInput2() {
        return input2;
    }

    public void setInput2(ItemStack input2) {
        this.input2 = input2;
    }

    public ItemStack getOutput() {
        return output;
    }

    public void setOutput(ItemStack output) {
        this.output = output;
    }

    public static List<HerbalStationRecipes> getRecipes() {
        return recipes;
    }

    public static void initHBRecipes(){
        for(Map.Entry<ItemStack [], Integer> entry : MachineRecipeRegistry.getInstance().getHERBAL_STATION_RECIPE_MAP().entrySet()){
            ItemStack potion = new ItemStack(ItemRegistry.POTION_CURE);
            NBTTagCompound nbt = Utilities.getNbt(potion);
            nbt.setInteger(PotionCure.BKEY_ID, entry.getValue());
            recipes.add(new HerbalStationRecipes(entry.getKey()[0].copy(), entry.getKey()[1].copy(), potion));
        }
    }
}
