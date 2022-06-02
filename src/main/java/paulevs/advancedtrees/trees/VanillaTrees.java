package paulevs.advancedtrees.trees;

import paulevs.advancedtrees.trees.behaviour.SimpleTreeBehaviour;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.advancedtrees.trees.structure.TreeInfo;

public class VanillaTrees {
	public static final TreeInfo OAK = TreeInfo.makeSimpleTree(paulevs.advancedtrees.AdvancedTrees.makeID("oak"), 1, 7, () -> VanillaTrees.OAK_BEHAVIOUR);
	
	public static final TreeBehaviour OAK_BEHAVIOUR = new SimpleTreeBehaviour(8, VanillaTrees.OAK.getLogStatic(), VanillaTrees.OAK.getLeaves());
	
	public static void init() {}
}
