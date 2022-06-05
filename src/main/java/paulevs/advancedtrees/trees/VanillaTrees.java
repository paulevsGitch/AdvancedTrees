package paulevs.advancedtrees.trees;

import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.trees.behaviour.simple.BirchTreeBehaviour;
import paulevs.advancedtrees.trees.behaviour.simple.CedarTreeBehaviour;
import paulevs.advancedtrees.trees.behaviour.simple.SimpleCactusBehaviour;
import paulevs.advancedtrees.trees.behaviour.simple.SimpleTreeBehaviour;
import paulevs.advancedtrees.trees.info.TreeInfo;
import paulevs.advancedtrees.trees.info.TreeInfoBuilder;

public class VanillaTrees {
	public static final TreeInfo OAK = TreeInfoBuilder.simpleTree(AdvancedTrees.makeID("oak"), 1, 7, 64, new SimpleTreeBehaviour(8));
	public static final TreeInfo SPRUCE = TreeInfoBuilder.simpleTree(AdvancedTrees.makeID("spruce"), 1, 7, 64, new CedarTreeBehaviour(13));
	public static final TreeInfo BIRCH = TreeInfoBuilder.simpleTree(AdvancedTrees.makeID("birch"), 1, 5, 64, new BirchTreeBehaviour(13));
	public static final TreeInfo CACTUS = TreeInfoBuilder.cactusTree(AdvancedTrees.makeID("cactus"), 2, 4, new SimpleCactusBehaviour(5));
	
	public static void init() {}
}
