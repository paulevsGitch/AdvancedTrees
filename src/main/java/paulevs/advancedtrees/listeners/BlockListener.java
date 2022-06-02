package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.blocks.ATDynamicBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSapling;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviours;
import paulevs.advancedtrees.trees.structure.AdvancedTreeStructure;
import paulevs.advancedtrees.trees.structure.TreeStructures;

import java.util.ArrayList;
import java.util.List;

public class BlockListener {
	public static List<BlockBase> modBlocks = new ArrayList<>();
	public static ATStaticLogBlock oakLogStatic;
	public static ATDynamicBlock oakLogDynamic;
	
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		BlockRegistry registry = event.registry;
		
		oakLogStatic = registerLogStatic(registry, AdvancedTrees.makeID("oak_log_static"), 1, 7);
		oakLogDynamic = registerLogDynamic(registry, AdvancedTrees.makeID("oak_log_dynamic"), 1);
		
		oakLogDynamic.linkBehaviour(TreeBehaviours.OAK_TREE_BEHAVIOUR);
		
		registerSampling(registry, AdvancedTrees.makeID("oak_sapling"), TreeStructures.OAK_TREE);
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
	
	private ATDynamicBlock registerLogDynamic(BlockRegistry registry, Identifier id, int minAge) {
		ATDynamicBlock log = new ATDynamicBlock(id, minAge);
		registry.register(id, log);
		modBlocks.add(log);
		return log;
	}
	
	private ATStaticLogBlock registerLogStatic(BlockRegistry registry, Identifier id, int minAge, int maxAge) {
		ATStaticLogBlock.setAgeLimits(minAge, maxAge);
		ATStaticLogBlock log = new ATStaticLogBlock(id);
		registry.register(id, log);
		modBlocks.add(log);
		return log;
	}
	
	private void registerSampling(BlockRegistry registry, Identifier id, AdvancedTreeStructure structure) {
		ATSpawnerSapling sapling = new ATSpawnerSapling(id, structure);
		registry.register(id, sapling);
		modBlocks.add(sapling);
	}
}
