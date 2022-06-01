package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.blocks.ATLogBlock;

public class BlockListener {
	public static ATLogBlock oakLog;
	
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		BlockRegistry registry = event.registry;
		
		oakLog = register(registry, AdvancedTrees.makeID("oak_log"), 0, 7);
	}
	
	private ATLogBlock register(BlockRegistry registry, Identifier id, int minAge, int maxAge) {
		ATLogBlock.setAgeLimits(minAge, maxAge);
		ATLogBlock log = new ATLogBlock(id);
		registry.register(id, log);
		return log;
	}
}
