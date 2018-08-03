package com.thunder.network;

import com.thunder.gui.BAnalyzerGuiScreen;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OpenAnalyzerGuiMessage implements IMessage {

    public String info;

    public OpenAnalyzerGuiMessage(){}

    public OpenAnalyzerGuiMessage(String info){
        this.info = info;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        info = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, info);
    }

    public static class Handler implements IMessageHandler<OpenAnalyzerGuiMessage, IMessage> {

        @Override
        public IMessage onMessage(OpenAnalyzerGuiMessage message, MessageContext ctx) {
            FMLCommonHandler.instance().showGuiScreen(new BAnalyzerGuiScreen(message.info));
            return null;
        }
    }
}
