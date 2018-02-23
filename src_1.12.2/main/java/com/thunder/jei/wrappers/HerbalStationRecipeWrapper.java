package com.thunder.jei.wrappers;

import com.thunder.jei.recipes.HerbalStationRecipes;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class HerbalStationRecipeWrapper implements IRecipeWrapper {

    private List<ItemStack> inputs;
    private ItemStack output;

    public HerbalStationRecipeWrapper(HerbalStationRecipes recipe){
        this.inputs = new ArrayList<>();
        this.inputs.add(recipe.getInput1());
        this.inputs.add(recipe.getInput2());
        this.output = recipe.getOutput();
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, inputs);
        ingredients.setOutput(ItemStack.class, output);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return new ArrayList<>();
    }

    @Override
    public boolean handleClick(Minecraft minecraft, int mouseX, int mouseY, int mouseButton) {
        return false;
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public ItemStack getOutput() {
        return output;
    }
}
