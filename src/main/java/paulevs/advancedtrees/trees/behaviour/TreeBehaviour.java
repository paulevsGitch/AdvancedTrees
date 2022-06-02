package paulevs.advancedtrees.trees.behaviour;

import paulevs.advancedtrees.trees.TreeContext;

@FunctionalInterface
public interface TreeBehaviour {
	void grow(TreeContext context);
}
