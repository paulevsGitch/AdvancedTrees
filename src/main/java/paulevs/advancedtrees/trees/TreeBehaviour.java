package paulevs.advancedtrees.trees;

import net.minecraft.level.Level;

@FunctionalInterface
public interface TreeBehaviour {
	void grow(Level level, TreeContext context);
}
