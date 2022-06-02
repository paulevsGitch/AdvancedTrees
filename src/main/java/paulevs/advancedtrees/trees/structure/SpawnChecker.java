package paulevs.advancedtrees.trees.structure;

import net.minecraft.level.Level;

@FunctionalInterface
public interface SpawnChecker {
	boolean canSpawn(Level leve, int x, int y, int z);
}
