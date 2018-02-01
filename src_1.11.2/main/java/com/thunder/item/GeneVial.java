package com.thunder.item;

import com.thunder.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class GeneVial extends ItemBionisation {

    public static final String GENE_KEY = Utilities.getModIdString("gene");

    public GeneVial(){
        super();
        setMaxStackSize(1);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        NBTTagCompound tag = Utilities.getNbt(stack);
        if (tag.hasKey(GENE_KEY)) {
            int gene = tag.getInteger(GENE_KEY);
            tooltip.add("Gene: " + (gene == 0 ? TextFormatting.RED + "<Empty>" : TextFormatting.GREEN + "<" + gene + ">"));
        }
    }
}
