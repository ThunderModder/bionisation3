package com.thunder.item;

import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;


public class ImmunityReceiver extends ItemBionisation {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        IBioPlayer cap = playerIn.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
        int lvl = cap.getImmunityLevel();
        if(worldIn.isRemote)
            playerIn.sendMessage(new TextComponentString("Immunity: " + lvl + " %"));
        return new ActionResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
