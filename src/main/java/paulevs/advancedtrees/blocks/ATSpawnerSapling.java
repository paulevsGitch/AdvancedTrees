package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.bhcore.util.BlocksUtil;

public class ATSpawnerSapling extends ATTemplateNotFullBlock {
	private ATLogBlock testLog;
	
	public ATSpawnerSapling(Identifier identifier, ATLogBlock testLog) {
		super(identifier, Material.PLANT);
		setSounds(GRASS_SOUNDS);
		this.testLog = testLog;
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		BlockState state = testLog.getDefaultState();
		for (int i = 0; i < 8; i++) {
			BlockState placement = testLog.getWithAge(state, 7 - i);
			BlocksUtil.setBlockState(level, x, y + i, z, placement);
		}
	}
}
