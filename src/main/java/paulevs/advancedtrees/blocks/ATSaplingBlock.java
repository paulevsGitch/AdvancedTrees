package paulevs.advancedtrees.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.bhcore.util.BlocksUtil;

public class ATSaplingBlock extends ATTemplateNotFullBlock {
	private final ATDynamicLogBlock log;
	private final ATLeavesBlock leaves;
	
	public ATSaplingBlock(Identifier identifier, ATDynamicLogBlock log, ATLeavesBlock leaves) {
		this(identifier, Material.PLANT, log, leaves);
		setSounds(GRASS_SOUNDS);
	}
	
	public ATSaplingBlock(Identifier identifier, Material material, ATDynamicLogBlock log, ATLeavesBlock leaves) {
		super(identifier, material);
		setTicksRandomly(true);
		this.log = log;
		this.leaves = leaves;
	}
	
	@Override
	public boolean canPlaceAt(Level level, int x, int y, int z) {
		if (!super.canPlaceAt(level, x, y, z)) return false;
		BlockState state = BlocksUtil.getBlockState(level, x, y - 1, z);
		BlockBase block = state.getBlock();
		return block == BlockBase.GRASS || block == BlockBase.DIRT || block == BlockBase.FARMLAND;
	}
	
	@Override
	public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int face) {
		super.onAdjacentBlockUpdate(level, x, y, z, face);
		if (!canPlaceAt(level, x, y, z)) {
			this.drop(level, x, y, z, level.getTileMeta(x, y, z));
			level.setTile(x, y, z, 0);
		}
	}
}
