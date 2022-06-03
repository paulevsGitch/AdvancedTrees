package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.tileentity.TileEntityRegisterEvent;
import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.tileentities.TreeTileEntity;
import paulevs.advancedtrees.trees.VanillaTrees;

public class BlockListener {
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		VanillaTrees.init();
	}
	
	@EventListener
	public void registerEntities(TileEntityRegisterEvent event) {
		event.register(TreeTileEntity.class, AdvancedTrees.makeID("tree").toString());
	}
}
