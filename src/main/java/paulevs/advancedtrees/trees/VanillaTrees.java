package paulevs.advancedtrees.trees;

import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviourRegistry;
import paulevs.advancedtrees.trees.behaviour.simple.BirchTreeBehaviour;
import paulevs.advancedtrees.trees.behaviour.simple.CedarTreeBehaviour;
import paulevs.advancedtrees.trees.behaviour.simple.SimpleCactusBehaviour;
import paulevs.advancedtrees.trees.behaviour.simple.SimpleTreeBehaviour;
import paulevs.advancedtrees.trees.info.TreeBlockSet;
import paulevs.advancedtrees.trees.info.TreeInfoBuilder;
import paulevs.advancedtrees.trees.structure.AdvancedTreeStructure;
import paulevs.advancedtrees.trees.structure.SpawnChecker;

public class VanillaTrees {
	public static final TreeBehaviour OAK_BEHAVIOUR = register("oak", new SimpleTreeBehaviour(8));
	public static final TreeBehaviour LARGE_OAK_BEHAVIOUR = register("large_oak", new SimpleTreeBehaviour(16));
	public static final TreeBehaviour SPRUCE_BEHAVIOUR = register("spruce", new CedarTreeBehaviour(13));
	public static final TreeBehaviour PINE_BEHAVIOUR = register("pine", new BirchTreeBehaviour(13));
	public static final TreeBehaviour BIRCH_BEHAVIOUR = register("birch", new BirchTreeBehaviour(13));
	public static final TreeBehaviour CACTUS_BEHAVIOUR = register("cactus", new SimpleCactusBehaviour(5));
	
	public static final TreeBlockSet OAK_BLOCKS = TreeInfoBuilder.simpleTree(AdvancedTrees.makeID("oak"), 1, 7, 64);
	public static final TreeBlockSet SPRUCE_BLOCKS = TreeInfoBuilder.simpleTree(AdvancedTrees.makeID("spruce"), 1, 7, 64);
	public static final TreeBlockSet BIRCH_BLOCKS = TreeInfoBuilder.simpleTree(AdvancedTrees.makeID("birch"), 1, 5, 64);
	public static final TreeBlockSet CACTUS_BLOCKS = TreeInfoBuilder.cactusTree(AdvancedTrees.makeID("cactus"), 2, 4);
	
	public static final SpawnChecker OAK_SPAWN = OAK_BLOCKS.getSapling()::canPlaceAt;
	public static final SpawnChecker CACTUS_SPAWN = CACTUS_BLOCKS.getLogDynamic()::canPlaceAt;
	
	public static final AdvancedTreeStructure OAK_STRUCTURE = new AdvancedTreeStructure(OAK_BLOCKS, OAK_BEHAVIOUR, OAK_SPAWN);
	public static final AdvancedTreeStructure LARGE_OAK_STRUCTURE = new AdvancedTreeStructure(OAK_BLOCKS, LARGE_OAK_BEHAVIOUR, OAK_SPAWN);
	public static final AdvancedTreeStructure SPRUCE_STRUCTURE = new AdvancedTreeStructure(SPRUCE_BLOCKS, SPRUCE_BEHAVIOUR, OAK_SPAWN);
	public static final AdvancedTreeStructure PINE_STRUCTURE = new AdvancedTreeStructure(SPRUCE_BLOCKS, PINE_BEHAVIOUR, OAK_SPAWN);
	public static final AdvancedTreeStructure BIRCH_STRUCTURE = new AdvancedTreeStructure(BIRCH_BLOCKS, BIRCH_BEHAVIOUR, OAK_SPAWN);
	public static final AdvancedTreeStructure CACTUS_STRUCTURE = new AdvancedTreeStructure(CACTUS_BLOCKS, CACTUS_BEHAVIOUR, CACTUS_SPAWN);
	
	public static void init() {}
	
	private static TreeBehaviour register(String name, TreeBehaviour behaviour) {
		TreeBehaviourRegistry.INSTANCE.register(AdvancedTrees.makeID(name), behaviour);
		return behaviour;
	}
}
