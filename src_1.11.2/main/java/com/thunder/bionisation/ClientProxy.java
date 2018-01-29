package com.thunder.bionisation;

import com.thunder.block.BlockRegistry;
import com.thunder.event.ClientEventHandler;
import com.thunder.gui.BloodBarOverlay;
import com.thunder.item.ItemRegistry;
import com.thunder.sound.SoundHandler;
import net.minecraftforge.common.MinecraftForge;


public class ClientProxy extends CommonProxy {

    @Override
    public void preInit() {

        super.preInit();
    }

    @Override
    public void init() {
        BlockRegistry.initBlocksClient();
        ItemRegistry.initItemsClient();
        MinecraftForge.EVENT_BUS.register(new BloodBarOverlay());
        ClientEventHandler.register();
        super.init();
    }

    @Override
    public void postInit() {

        super.postInit();
    }
}
