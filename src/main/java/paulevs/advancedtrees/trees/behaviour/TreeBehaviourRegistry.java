package paulevs.advancedtrees.trees.behaviour;

import net.modificationstation.stationapi.api.registry.Registry;
import paulevs.advancedtrees.AdvancedTrees;

public class TreeBehaviourRegistry extends Registry<TreeBehaviour> {
	public static final TreeBehaviourRegistry INSTANCE = new TreeBehaviourRegistry();
	
	private TreeBehaviourRegistry() {
		super(AdvancedTrees.makeID("tree_behaviour"));
	}
}
