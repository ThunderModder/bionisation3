package com.thunder.network;


import com.thunder.bionisation.Information;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	
	public static SimpleNetworkWrapper network;

	public static void init() {
		
		network = NetworkRegistry.INSTANCE.newSimpleChannel(Information.MOD_ID);
		network.registerMessage(SyncBloodCapMessage.Handler.class, SyncBloodCapMessage.class, 0, Side.CLIENT);
		network.registerMessage(SyncAllCapMessage.Handler.class, SyncAllCapMessage.class, 1, Side.CLIENT);
		network.registerMessage(SyncEntityCapMessage.Handler.class, SyncEntityCapMessage.class, 2, Side.CLIENT);
		network.registerMessage(OpenAnalyzerGuiMessage.Handler.class, OpenAnalyzerGuiMessage.class, 3, Side.CLIENT);
	}	
}
