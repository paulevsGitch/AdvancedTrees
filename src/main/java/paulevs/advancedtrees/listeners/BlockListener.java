package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.modificationstation.stationapi.api.client.colour.block.BlockColourProvider;
import net.modificationstation.stationapi.api.client.colour.block.BlockColours;
import net.modificationstation.stationapi.api.client.event.colour.block.BlockColoursRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.blocks.ATDynamicBlock;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSapling;
import paulevs.advancedtrees.trees.TreeBehaviour;
import paulevs.advancedtrees.trees.TreeBehaviours;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.TreeUtil;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class BlockListener {
	public static List<BlockBase> modBlocks = new ArrayList<>();
	public static ATStaticLogBlock oakLog;
	
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		BlockRegistry registry = event.registry;
		
		oakLog = registerLogStatic(registry, AdvancedTrees.makeID("oak_log_static"), 1, 7);
		ATDynamicBlock dLog = registerLogDynamic(registry, AdvancedTrees.makeID("oak_log_dynamic"), 1, TreeBehaviours.SIMPLE_TREE, oakLog);
		registerSampling(registry, AdvancedTrees.makeID("oak_sapling"), dLog);
	}
	
	// TODO remove
	// For debug only!
	/*@EventListener
	public void registerColor(BlockColoursRegisterEvent event) {
		int[] rgb = new int[] {
			Color.RED.getRGB(),
			Color.GREEN.getRGB(),
			Color.BLUE.getRGB(),
			Color.MAGENTA.getRGB(),
			Color.BLACK.getRGB()
		};
		
		BlockColours colors = event.getBlockColours();
		BlockColourProvider provider = (state, world, pos, index) -> {
			TreeContext context = TreeContext.getInstance();
			context.update(world, pos.x, pos.y, pos.z);
			return rgb[context.getGeneration()];
		};
		colors.registerColourProvider(provider, oakLog);
	}*/
	
	private ATDynamicBlock registerLogDynamic(BlockRegistry registry, Identifier id, int minAge, TreeBehaviour behaviour, ATStaticLogBlock staticLogBlock) {
		ATDynamicBlock log = new ATDynamicBlock(id, minAge, behaviour, staticLogBlock);
		registry.register(id, log);
		//modBlocks.add(log);
		return log;
	}
	
	private ATStaticLogBlock registerLogStatic(BlockRegistry registry, Identifier id, int minAge, int maxAge) {
		ATStaticLogBlock.setAgeLimits(minAge, maxAge);
		ATStaticLogBlock log = new ATStaticLogBlock(id);
		registry.register(id, log);
		modBlocks.add(log);
		return log;
	}
	
	private void registerSampling(BlockRegistry registry, Identifier id, ATDynamicBlock log) {
		ATSpawnerSapling sapling = new ATSpawnerSapling(id, log);
		registry.register(id, sapling);
		//modBlocks.add(sapling);
	}
}
