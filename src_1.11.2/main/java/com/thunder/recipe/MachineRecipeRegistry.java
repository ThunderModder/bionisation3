package com.thunder.recipe;

import com.google.common.collect.Maps;
import com.thunder.block.BlockRegistry;
import com.thunder.item.GeneVial;
import com.thunder.item.ItemBlood;
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
    private final Map<ItemStack, Integer> DNA_FORMER_RECIPE_MAP = Maps.newHashMap();

    private final Map<String, Integer> BLOOD_MAP = Maps.newHashMap();

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

        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.GHAST_TEAR, 1), 1);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.SLIME_BALL, 1), 2);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.BAT_WING, 1), 3);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Blocks.RED_MUSHROOM, 1), 4);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.WOLFS_TOOTH, 1), 5);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.GOLDEN_CARROT, 1), 6);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.RABBIT_HIDE, 1), 7);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.SPIDER_EYE, 1), 8);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.SPECKLED_MELON, 1), 9);

        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.MAGMA_CREAM, 1), 11);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.FISH, 1), 12);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.SPECTRAL_DUST, 1), 13);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.SPIDER_LEG, 1), 14);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.ENDER_CORE, 1), 15);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.ROTTEN_FLESH, 1), 16);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.BONE, 1), 17);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.FERMENTED_SPIDER_EYE, 1), 18);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.DARK_HEART, 1), 19);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.GUARDIAN_BRAIN, 1), 20);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Blocks.BROWN_MUSHROOM, 1), 21);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.GLOWING_LIQUID, 1), 22);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.FEATHER, 1), 23);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(Items.RABBIT_FOOT, 1), 24);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.CHICKEN_HEAD, 1), 25);

        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.BLAZE_CORE, 1), 33);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.HEART_OF_CREEPER, 1), 34);
        DNA_FORMER_RECIPE_MAP.put(new ItemStack(ItemRegistry.ENDER_SUBSTANCE, 1), 35);

        BLOOD_MAP.put("Bear", 10);
        BLOOD_MAP.put("Spider", 26);
        BLOOD_MAP.put("Zombie", 27);
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

    public int getDNAFormerOutput(ItemStack reagent){
        for(Map.Entry<ItemStack, Integer> entry : DNA_FORMER_RECIPE_MAP.entrySet()){
            ItemStack mStack = entry.getKey();
            if(compareStacks(reagent, mStack)){
                return entry.getValue();
            }
        }
        //for gene vials
        if(reagent.getItem().equals(ItemRegistry.GENE_VIAL)){
            NBTTagCompound tag = Utilities.getNbt(reagent);
            if(tag.hasKey(GeneVial.GENE_KEY))
                return tag.getInteger(GeneVial.GENE_KEY);
            //for blood vials
        }else if(reagent.getItem().equals(ItemRegistry.ITEM_BLOOD)){
            NBTTagCompound tag = Utilities.getNbt(reagent);
            String blood = tag.getString(ItemBlood.BLOOD_KEY);
            for(Map.Entry<String, Integer> entry : BLOOD_MAP.entrySet()){
                if(entry.getKey().equalsIgnoreCase(blood))
                    return entry.getValue();
            }
        }
        return 0;
    }

    private boolean compareStacks(ItemStack stack1, ItemStack stack2){
        return stack1.getItem().equals(stack2.getItem()) && stack1.getCount() >= stack2.getCount();
    }
}
