package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import paulevs.advancedtrees.trees.VanillaTrees;

public class BlockListener {
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		VanillaTrees.init();
	}
}
