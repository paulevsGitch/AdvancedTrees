package paulevs.advancedtrees.trees.behaviour;

import paulevs.advancedtrees.trees.TreeContext;

import java.util.Random;

public interface TreeBehaviour {
	void grow(TreeContext context);
	int getAge(Random random);
}
