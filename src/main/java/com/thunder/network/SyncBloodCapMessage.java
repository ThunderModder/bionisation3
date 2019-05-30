package com.thunder.network;

import com.thunder.player.BioPlayerProvider;
import com.thunder.player.IBioPlayer;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;


public class SyncBloodCapMessage implements IMessage {

    public int value;

    public SyncBloodCapMessage(){}

    public SyncBloodCapMessage(int value){
        this.value = value;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        value = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(value);
    }

    public static class Handler implements IMessageHandler<SyncBloodCapMessage, IMessage> {

        @Override
        public IMessage onMessage(SyncBloodCapMessage message, MessageContext ctx) {
            Minecraft mc = Minecraft.getMinecraft();
            mc.addScheduledTask(() -> {
                IBioPlayer cap = mc.player.getCapability(BioPlayerProvider.BIO_PLAYER_CAPABILITY, null);
                if(cap != null)
                    cap.setBloodLevel(message.value);
            });
            return null;
        }
    }
}
