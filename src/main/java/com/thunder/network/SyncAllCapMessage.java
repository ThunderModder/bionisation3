package com.thunder.network;

import com.thunder.mob.BioMobProvider;
import com.thunder.mob.IBioMob;
import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class SyncAllCapMessage implements IMessage {

    public NBTTagCompound nbt;
    public int entId;

    public SyncAllCapMessage(){}

    public SyncAllCapMessage(NBTTagCompound tag, int eId){
        nbt = tag;
        entId = eId;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        entId = ByteBufUtils.readVarInt(buf, 4);
        nbt = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarInt(buf, entId, 4);
        ByteBufUtils.writeTag(buf, nbt);
    }

    public static class Handler implements IMessageHandler<SyncAllCapMessage, IMessage> {

        @Override
        public IMessage onMessage(SyncAllCapMessage message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                if(mc.player != null){
                    World world = mc.player.world;
                    Entity ent = world.getEntityByID(message.entId);
                    if(ent instanceof EntityPlayer){
                        IBioPlayer cap = ent.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                        if(cap != null)
                            cap.readFromNBT(message.nbt);
                    }
                }
            });
            return null;
        }
    }
}
