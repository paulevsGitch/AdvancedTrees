package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.blocks.ATLogBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSapling;

import java.util.ArrayList;
import java.util.List;

public class BlockListener {
	public static List<BlockBase> modBlocks = new ArrayList<>();
	public static ATLogBlock oakLog;
	
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		BlockRegistry registry = event.registry;
		
		oakLog = register(registry, AdvancedTrees.makeID("oak_log"), 0, 7);
		registerSampling(registry, AdvancedTrees.makeID("oak_sapling"), oakLog);
	}
	
	private ATLogBlock register(BlockRegistry registry, Identifier id, int minAge, int maxAge) {
		ATLogBlock.setAgeLimits(minAge, maxAge);
		ATLogBlock log = new ATLogBlock(id);
		registry.register(id, log);
		modBlocks.add(log);
		return log;
	}
	
	private void registerSampling(BlockRegistry registry, Identifier id, ATLogBlock log) {
		ATSpawnerSapling sapling = new ATSpawnerSapling(id, log);
		registry.register(id, sapling);
		modBlocks.add(sapling);
	}
}
