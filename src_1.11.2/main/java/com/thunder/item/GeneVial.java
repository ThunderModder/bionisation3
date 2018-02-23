package com.thunder.item;

import com.thunder.util.Utilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class GeneVial extends ItemBionisation {

    public static final String GENE_KEY = Utilities.getModIdString("gene");
    public static final int [] GENES = new int[]{28, 29, 30, 31, 32, 36};

    public GeneVial(){
        super();
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        NBTTagCompound tag = Utilities.getNbt(stack);
        if (tag.hasKey(GENE_KEY)) {
            int gene = tag.getInteger(GENE_KEY);
            tooltip.add(I18n.format("tooltip.gene.gene") + " " + (gene == 0 ? TextFormatting.RED + "<" + I18n.format("tooltip.empty") + ">" : TextFormatting.GREEN + "<" + I18n.format("tooltip.gene." + gene) + ">"));
        }
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for(int i : GENES){
            ItemStack stack = new ItemStack(this);
            NBTTagCompound tag = Utilities.getNbt(stack);
            tag.setInteger(GENE_KEY, i);
            subItems.add(stack);
        }
    }
}
