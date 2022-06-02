package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.blocks.ATDynamicBlock;
import paulevs.advancedtrees.blocks.ATLeavesBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSapling;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviours;
import paulevs.advancedtrees.trees.structure.AdvancedTreeStructure;
import paulevs.advancedtrees.trees.structure.TreeStructures;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class BlockListener {
	public static List<BlockBase> modBlocks = new ArrayList<>();
	public static ATStaticLogBlock oakLogStatic;
	public static ATDynamicBlock oakLogDynamic;
	public static ATLeavesBlock oakLeaves;
	public static ATSpawnerSapling oakSapling;
	
	@EventListener
	public void registerBlocks(BlockRegistryEvent event) {
		BlockRegistry registry = event.registry;
		
		oakLogStatic = registerLogStatic(registry, AdvancedTrees.makeID("oak_log_static"), 1, 7);
		oakLogDynamic = registerLogDynamic(registry, AdvancedTrees.makeID("oak_log_dynamic"), 1, () -> TreeBehaviours.OAK_TREE_BEHAVIOUR);
		oakLeaves = registerLeaves(registry, AdvancedTrees.makeID("oak_leaves"));
		oakSapling = registerSampling(registry, AdvancedTrees.makeID("oak_sapling"), () -> TreeStructures.OAK_TREE);
	}
	
	private ATDynamicBlock registerLogDynamic(BlockRegistry registry, Identifier id, int minAge, Supplier<TreeBehaviour> behaviourSupplier) {
		ATDynamicBlock log = new ATDynamicBlock(id, minAge, behaviourSupplier);
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
	
	private ATLeavesBlock registerLeaves(BlockRegistry registry, Identifier id) {
		ATLeavesBlock log = new ATLeavesBlock(id);
		registry.register(id, log);
		modBlocks.add(log);
		return log;
	}
	
	private ATSpawnerSapling registerSampling(BlockRegistry registry, Identifier id, Supplier<AdvancedTreeStructure> structureSupplier) {
		ATSpawnerSapling sapling = new ATSpawnerSapling(id, structureSupplier);
		registry.register(id, sapling);
		modBlocks.add(sapling);
		return sapling;
	}
}
