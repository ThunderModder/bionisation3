package com.thunder.jei.categories;

import com.thunder.bionisation.Config;
import com.thunder.bionisation.Information;
import com.thunder.jei.wrappers.VirusReplicatorRecipeWrapper;
import com.thunder.util.Utilities;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class VirusReplicatorRecipeCategory implements IRecipeCategory<VirusReplicatorRecipeWrapper> {

    public static final String UID = Utilities.getModIdString("virus_replicator");

    private IDrawableStatic gui;

    public VirusReplicatorRecipeCategory(IGuiHelper helper){
        this.gui = helper.createDrawable(new ResourceLocation(Information.MOD_ID + ":textures/gui/machine/gui_virusreplicator.png"), 49, 14, 124, 69);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("inventory.virusreplicator.name");
    }

    @Override
    public String getModName() {
        return Information.MOD_NAME;
    }

    @Override
    public IDrawable getBackground() {
        return gui;
    }

    @Nullable
    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        minecraft.fontRendererObj.drawString(I18n.format("jei.time") + " " + Config.virusReplicatorProcessTime, 72, 6, 0xffffffff, true);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, VirusReplicatorRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup isg = recipeLayout.getItemStacks();
        isg.init(0, true, 30, 2);
        isg.set(0, recipeWrapper.getInput());
        isg.init(1, false, 30, 46);
        isg.set(1, recipeWrapper.getOutput());
        isg.init(4, true, 102, 46);
        isg.set(4, new ItemStack(Items.COAL));
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return new ArrayList<>();
    }
}
