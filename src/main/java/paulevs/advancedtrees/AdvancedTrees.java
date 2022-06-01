package paulevs.advancedtrees;

import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.math.Direction;

public class AdvancedTrees {
	public static final ModID MOD_ID = ModID.of("advancedtrees");
	private static final Direction[] DIRS = Direction.values();
	
	public static Identifier makeID(String name) {
		return MOD_ID.id(name);
	}
	
	public static Direction fromFacing(int facing) {
		return DIRS[facing];
	}
}
