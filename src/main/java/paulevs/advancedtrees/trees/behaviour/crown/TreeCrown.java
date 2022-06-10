package paulevs.advancedtrees.trees.behaviour.crown;

import net.minecraft.level.Level;
import paulevs.advancedtrees.trees.info.TreeInfo;
import paulevs.bhcore.storage.vector.Vec3I;

public interface TreeCrown {
	void place(Level level, Vec3I pos, TreeInfo info);
}
