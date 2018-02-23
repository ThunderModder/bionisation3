package com.thunder.jei.categories;

import com.thunder.bionisation.Config;
import com.thunder.bionisation.Information;
import com.thunder.jei.wrappers.HerbalStationRecipeWrapper;
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

public class HerbalStationRecipeCategory implements IRecipeCategory<HerbalStationRecipeWrapper> {

    public static final String UID = Utilities.getModIdString("herbal_station");

    private IDrawableStatic gui;

    public HerbalStationRecipeCategory(IGuiHelper helper){
        this.gui = helper.createDrawable(new ResourceLocation(Information.MOD_ID + ":textures/gui/machine/gui_herbalstation.png"), 49, 14, 124, 69);
    }

    @Override
    public String getUid() {
        return UID;
    }

    @Override
    public String getTitle() {
        return I18n.format("inventory.herbalstation.name");
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
        minecraft.fontRendererObj.drawString(I18n.format("jei.time") + " " + Config.herbalStationProcessTime, 72, 6, 0xffffffff, true);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, HerbalStationRecipeWrapper recipeWrapper, IIngredients ingredients) {
        IGuiItemStackGroup isg = recipeLayout.getItemStacks();
        isg.init(0, true, 8, 2);
        isg.set(0, new ItemStack(Items.POTIONITEM));
        isg.init(1, true, 30, 2);
        isg.set(1, recipeWrapper.getInputs().get(0));
        isg.init(2, true, 52, 2);
        isg.set(2, recipeWrapper.getInputs().get(1));
        isg.init(3, false, 30, 46);
        isg.set(3, recipeWrapper.getOutput());
        isg.init(4, true, 102, 46);
        isg.set(4, new ItemStack(Items.COAL));
    }

    @Override
    public List<String> getTooltipStrings(int mouseX, int mouseY) {
        return new ArrayList<>();
    }
}
