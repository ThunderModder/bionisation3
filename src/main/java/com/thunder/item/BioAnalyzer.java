package com.thunder.item;

import com.thunder.laboratory.IBioSample;
import com.thunder.laboratory.IVirus;
import com.thunder.laboratory.SampleType;
import com.thunder.network.NetworkHandler;
import com.thunder.network.OpenAnalyzerGuiMessage;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BioAnalyzer extends ItemBionisation {

    public BioAnalyzer(){
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItemMainhand();
        if(!worldIn.isRemote) {
            IBioPlayer cap = playerIn.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            //open gui
            String info = "";
            for (IBioSample smp : cap.getEffectList()){
                //fill info
                if(smp.getType() == SampleType.BACTERIA)
                    info += smp.getName() + "_";
                if(smp.getType() == SampleType.CUSTOM_VIRUS && !((IVirus)smp).isHidden())
                    info += "cvirus/" + smp.getName();
                if((smp.getType() == SampleType.VIRUS || smp.getType() == SampleType.SYMBIOSIS) && (smp instanceof IVirus && !((IVirus)smp).isHidden()))
                    info += "virus/" + smp.getName() + "/" + ((IVirus)smp).getDNA() + "_";
            }
            NetworkHandler.network.sendTo(new OpenAnalyzerGuiMessage(info), (EntityPlayerMP)playerIn);
        }
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }

    @SideOnly(Side.CLIENT)
    public EnumRarity getRarity(ItemStack stack) {
        return EnumRarity.RARE;
    }
}
