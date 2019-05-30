package com.thunder.network;

import com.thunder.mob.BioMobProvider;
import com.thunder.mob.IBioMob;
import io.netty.buffer.ByteBuf;
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


public class SyncEntityCapMessage implements IMessage {

    public int entId;
    public NBTTagCompound nbt;

    public SyncEntityCapMessage() {
    }

    public SyncEntityCapMessage(int entId, NBTTagCompound nbt) {
        this.entId = entId;
        this.nbt = nbt;
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

    public static class Handler implements IMessageHandler<SyncEntityCapMessage, IMessage> {

        @Override
        public IMessage onMessage(SyncEntityCapMessage message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                if(mc.player != null){
                    World world = mc.player.world;
                    Entity ent = world.getEntityByID(message.entId);
                    if(ent instanceof EntityLivingBase){
                        IBioMob cap = ent.getCapability(BioMobProvider.BIO_MOB_CAPABILITY, null);
                        if(cap != null)
                            cap.readFromNBT(message.nbt);
                    }
                }
            });
            return null;
        }
    }
}