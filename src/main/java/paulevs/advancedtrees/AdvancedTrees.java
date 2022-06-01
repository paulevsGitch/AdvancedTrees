package paulevs.advancedtrees;

import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;

public class AdvancedTrees {
	public static final ModID MOD_ID = ModID.of("advancedtrees");
	
	public static Identifier makeID(String name) {
		return MOD_ID.id(name);
	}
}
