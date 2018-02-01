package com.thunder.recipe;

import com.google.common.collect.Maps;
import com.thunder.block.BlockRegistry;
import com.thunder.item.ItemRegistry;
import com.thunder.item.PotionCure;
import com.thunder.util.Utilities;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nullable;
import java.util.Map;

import static com.thunder.util.Constants.*;

public class MachineRecipeRegistry {

    public static final MachineRecipeRegistry INSTANCE = new MachineRecipeRegistry();

    private final Map<ItemStack [], Integer> HERBAL_STATION_RECIPE_MAP = Maps.newHashMap();

    public static MachineRecipeRegistry getInstance(){
        return INSTANCE;
    }

    private MachineRecipeRegistry(){
        //herbal station recipes
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.LOTUS, 1), new ItemStack(Items.SPECKLED_MELON, 1)}, ID_CURE_BLEEDING);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(ItemRegistry.YARROW_BUNCH, 1), ItemStack.EMPTY}, ID_CURE_СOLD);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(ItemRegistry.DILL_BUNCH, 1), ItemStack.EMPTY}, ID_CURE_FPOISON);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.LIVING_BONE, 1), new ItemStack(Items.SPIDER_EYE, 1)}, ID_CURE_BLACKBACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.CRYSTAL_FLOWER, 1), new ItemStack(Blocks.WATERLILY, 1)}, ID_CURE_SWAMPBACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.PHOENIX_FLOWER, 1), new ItemStack(ItemRegistry.STRANGE_LIQUID, 1)}, ID_CURE_GLOWINGBACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.LOTUS, 1), new ItemStack(ItemRegistry.GUARDIAN_BRAIN, 1)}, ID_CURE_WATERBACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.ENDER_FLOWER, 1), new ItemStack(ItemRegistry.ENDER_CORE, 1)}, ID_CURE_ENDERBACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.DESERT_FLOWER, 1), new ItemStack(Blocks.CACTUS, 1)}, ID_CURE_CACTUSBACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.LIVING_BONE, 1), new ItemStack(ItemRegistry.WOLFS_TOOTH, 1)}, ID_CURE_BONEBACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.ANCIENT_FLOWER, 1), new ItemStack(Items.BEETROOT, 1)}, ID_CURE_TERABACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.ANCIENT_FLOWER, 1), new ItemStack(Blocks.MYCELIUM, 1)}, ID_CURE_MYCELIUMBACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.LIVING_BONE, 1), new ItemStack(ItemRegistry.HEART_OF_CREEPER, 1)}, ID_CURE_SMALLGREENBACTERIA);
        HERBAL_STATION_RECIPE_MAP.put(new ItemStack[]{new ItemStack(BlockRegistry.CRYSTAL_FLOWER, 1), new ItemStack(Items.FISH, 1)}, ID_CURE_SEABACTERIA);
    }

    public ItemStack getHerbalStationOutput(ItemStack flower, ItemStack reagent){
        for(Map.Entry<ItemStack [], Integer> entry : HERBAL_STATION_RECIPE_MAP.entrySet()){
            ItemStack [] stacks = entry.getKey();
            if(compareStacks(flower, stacks[0]) && compareStacks(reagent, stacks[1])){
                ItemStack potion = new ItemStack(ItemRegistry.POTION_CURE);
                NBTTagCompound nbt = Utilities.getNbt(potion);
                nbt.setInteger(PotionCure.BKEY_ID, entry.getValue());
                return potion;
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean compareStacks(ItemStack stack1, ItemStack stack2){
        return stack1.getItem().equals(stack2.getItem()) && stack1.getCount() >= stack2.getCount();
    }
}
