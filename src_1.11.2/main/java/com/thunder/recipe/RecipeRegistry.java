package com.thunder.recipe;


import com.thunder.item.ItemRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class RecipeRegistry {

    public static void  init(){

        //ender core
        GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.ENDER_CORE), ItemRegistry.ENDER_SUBSTANCE, Items.ENDER_PEARL);
        //glowing liquid
        GameRegistry.addShapelessRecipe(new ItemStack(ItemRegistry.GLOWING_LIQUID), ItemRegistry.STRANGE_LIQUID, Items.GLOWSTONE_DUST);
    }
}
