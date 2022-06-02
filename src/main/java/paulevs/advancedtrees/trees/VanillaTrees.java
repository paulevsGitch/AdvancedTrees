package paulevs.advancedtrees.trees;

import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.trees.behaviour.SimpleCactusBehaviour;
import paulevs.advancedtrees.trees.behaviour.SimpleTreeBehaviour;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.advancedtrees.trees.structure.TreeInfo;

public class VanillaTrees {
	public static final TreeInfo OAK = TreeInfo.makeSimpleTree(AdvancedTrees.makeID("oak"), 1, 7, () -> VanillaTrees.OAK_BEHAVIOUR);
	public static final TreeInfo CACTUS = TreeInfo.makeCactusTree(AdvancedTrees.makeID("cactus"), 2, 4, () -> VanillaTrees.CACTUS_BEHAVIOUR);
	
	public static final TreeBehaviour OAK_BEHAVIOUR = new SimpleTreeBehaviour(8, VanillaTrees.OAK.getLogStatic(), VanillaTrees.OAK.getLeaves());
	public static final TreeBehaviour CACTUS_BEHAVIOUR = new SimpleCactusBehaviour(5, VanillaTrees.CACTUS.getLogStatic());
	
	public static void init() {}
}
