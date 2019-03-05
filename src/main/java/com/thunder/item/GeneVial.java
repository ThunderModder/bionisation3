package com.thunder.item;

import com.thunder.bionisation.CommonProxy;
import com.thunder.util.Utilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class GeneVial extends ItemBionisation {

    public static final String GENE_KEY = Utilities.getModIdString("gene");
    public static final int [] GENES = new int[]{28, 29, 30, 31, 32, 36};

    public GeneVial(){
        super();
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        NBTTagCompound tag = Utilities.getNbt(stack);
        if (tag.hasKey(GENE_KEY)) {
            int gene = tag.getInteger(GENE_KEY);
            tooltip.add(I18n.format("tooltip.gene.gene") + " " + (gene == 0 ? TextFormatting.RED + "<" + I18n.format("tooltip.empty") + ">" : TextFormatting.GREEN + "<" + I18n.format("tooltip.gene." + gene) + ">"));
        }
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if(tab == CommonProxy.tabBionisation) {
            for (int i : GENES) {
                ItemStack stack = new ItemStack(this);
                NBTTagCompound tag = Utilities.getNbt(stack);
                tag.setInteger(GENE_KEY, i);
                items.add(stack);
            }
        }
    }
}
