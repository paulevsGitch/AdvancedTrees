package paulevs.advancedtrees.blocks;

import net.modificationstation.stationapi.api.state.property.BooleanProperty;
import net.modificationstation.stationapi.api.state.property.DirectionProperty;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.HashMap;
import java.util.Map;

public class ATBlockProperties {
	public static final DirectionProperty DIRECTION = DirectionProperty.of("direction", Direction.values());
	public static final BooleanProperty CONNECTED = BooleanProperty.of("connected");
	private static final Map<Integer, IntProperty> AGES = new HashMap<>();
	
	public static IntProperty getAge(int minAge, int maxAge) {
		int key = minAge << 3 | maxAge;
		return AGES.computeIfAbsent(key, a ->  IntProperty.of("age", minAge, maxAge));
	}
}
