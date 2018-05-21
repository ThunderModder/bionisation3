package com.thunder.item;

import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.IVirus;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Utilities;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class Vial extends ItemBionisation {

    public Vial(){
        super();
        setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItemMainhand();
        if(!worldIn.isRemote) {
            NBTTagCompound nbt = Utilities.getNbt(stack);
            IBioPlayer cap = playerIn.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            if(!nbt.hasKey(Utilities.getModIdString("sdna"))) {
                String dna = "";
                for (IBioSample smp : cap.getEffectList()) {
                    if (smp instanceof IVirus) {
                        IVirus virus = (IVirus) smp;
                        if (virus.hasDNA() && !virus.isHidden()) {
                            dna += virus.getDNA() + "_";
                        }
                    }
                }
                if (!dna.isEmpty())
                    nbt.setString(Utilities.getModIdString("sdna"), dna);
            }

        }
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        if(stack.hasTagCompound() && Utilities.getNbt(stack).hasKey(Utilities.getModIdString("sdna"))){
            NBTTagCompound nbt = Utilities.getNbt(stack);
            String [] dnas = nbt.getString(Utilities.getModIdString("sdna")).split("_");
            tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.vial.samples") + " ");
            for (String s : dnas){
                if(!s.isEmpty())
                    tooltip.add(TextFormatting.GRAY + I18n.format("tooltip.vial.dna") + " " + TextFormatting.RED + s);
            }
        }
    }
}
