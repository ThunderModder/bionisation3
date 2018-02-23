package com.thunder.jei;

import com.thunder.block.BlockRegistry;
import com.thunder.item.ItemRegistry;
import com.thunder.jei.categories.DNAFormerRecipeCategory;
import com.thunder.jei.categories.HerbalStationRecipeCategory;
import com.thunder.jei.categories.VaccineCreatorRecipeCategory;
import com.thunder.jei.categories.VirusReplicatorRecipeCategory;
import com.thunder.jei.recipes.DNAFormerRecipes;
import com.thunder.jei.recipes.HerbalStationRecipes;
import com.thunder.jei.recipes.VaccineCreatorRecipes;
import com.thunder.jei.recipes.VirusReplicatorRecipes;
import com.thunder.jei.wrappers.DNAFormerRecipeWrapper;
import com.thunder.jei.wrappers.HerbalStationRecipeWrapper;
import com.thunder.jei.wrappers.VaccineCreatorRecipeWrapper;
import com.thunder.jei.wrappers.VirusReplicatorRecipeWrapper;
import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class JEIBionisationPlugin implements IModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(ItemRegistry.CREATIVE_VIAL, ItemRegistry.GENE_VIAL, ItemRegistry.SYMBIONT_VIAL, ItemRegistry.POTION_CURE);
    }

    @Override
    public void registerIngredients(IModIngredientRegistration registry) {

    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        registry.addRecipeCategories(new HerbalStationRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new VaccineCreatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new DNAFormerRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
        registry.addRecipeCategories(new VirusReplicatorRecipeCategory(registry.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void register(IModRegistry registry) {
        //herbal station
        registry.addRecipes(HerbalStationRecipes.getRecipes(), HerbalStationRecipeCategory.UID);
        registry.handleRecipes(HerbalStationRecipes.class, recipe -> new HerbalStationRecipeWrapper(recipe), HerbalStationRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.HERBAL_STATION), HerbalStationRecipeCategory.UID);
        //vaccine creator
        registry.addRecipes(VaccineCreatorRecipes.getRecipes(), VaccineCreatorRecipeCategory.UID);
        registry.handleRecipes(VaccineCreatorRecipes.class, recipe -> new VaccineCreatorRecipeWrapper(recipe), VaccineCreatorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.VACCINE_CREATOR), VaccineCreatorRecipeCategory.UID);
        //dna former
        registry.addRecipes(DNAFormerRecipes.getRecipes(), DNAFormerRecipeCategory.UID);
        registry.handleRecipes(DNAFormerRecipes.class, recipe -> new DNAFormerRecipeWrapper(recipe), DNAFormerRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.DNA_FORMER), DNAFormerRecipeCategory.UID);
        //virus replicator
        registry.addRecipes(VirusReplicatorRecipes.getRecipes(), VirusReplicatorRecipeCategory.UID);
        registry.handleRecipes(VirusReplicatorRecipes.class, recipe -> new VirusReplicatorRecipeWrapper(recipe), VirusReplicatorRecipeCategory.UID);
        registry.addRecipeCatalyst(new ItemStack(BlockRegistry.VIRUS_REPLICATOR), VirusReplicatorRecipeCategory.UID);
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {

    }
}
