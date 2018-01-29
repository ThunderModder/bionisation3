package com.thunder.item;

import com.thunder.laboratory.IBioSample;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import com.thunder.util.Constants;
import com.thunder.util.Utilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;


public class Bandage extends ItemBionisation {

    public Bandage(){
        super();
        this.setMaxStackSize(16);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if(!worldIn.isRemote) {
            IBioPlayer cap = playerIn.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
            IBioSample sample = cap.getEffectById(Constants.ID_BLEEDING);
            int index = playerIn.inventory.currentItem;
            if (sample != null) {
                int power = sample.getPower();
                if (power > 0) {
                    sample.setPower(power - 1);
                    Utilities.decreaseOrRemove(playerIn, index);
                }else{
                    cap.removeEffect(sample, playerIn);
                    Utilities.decreaseOrRemove(playerIn, index);
                }
            }
        }
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
