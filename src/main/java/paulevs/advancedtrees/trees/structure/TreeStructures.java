package paulevs.advancedtrees.trees.structure;

import paulevs.advancedtrees.listeners.BlockListener;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviours;

public class TreeStructures {
	public static final AdvancedTreeStructure OAK_TREE = new AdvancedTreeStructure(
		BlockListener.oakLogDynamic,
		TreeBehaviours.OAK_TREE_BEHAVIOUR
	);
}
