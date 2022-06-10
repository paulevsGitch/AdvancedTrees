package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ClientUtil;

import java.util.Random;

public class ATSaplingBlock extends ATSaplingLikeBlock {
	private ATDynamicLogBlock log;
	private ATLeavesBlock leaves;
	
	public ATSaplingBlock(Identifier identifier) {
		super(identifier);
	}
	
	public ATSaplingBlock(Identifier identifier, Material material) {
		super(identifier, material);
	}
	
	public void setLogAndLeaves(ATDynamicLogBlock log, ATLeavesBlock leaves) {
		this.leaves = leaves;
		this.log = log;
	}
	
	@Override
	public boolean canPlaceAt(Level level, int x, int y, int z) {
		if (!super.canPlaceAt(level, x, y, z)) return false;
		return canStay(level, x, y, z);
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random random) {
		if (!canStay(level, x, y, z)) {
			this.drop(level, x, y, z, level.getTileMeta(x, y, z));
			level.setTile(x, y, z, 0);
		}
		else if (canGrowTree(level, x, y, z)) {
			BlocksUtil.setBlockState(level, x, y, z, log.getDefaultState());
			BlocksUtil.setBlockState(level, x, y + 1, z, leaves.getDefaultState().with(ATBlockProperties.CONNECTED, true));
			log.createEntity(level, x, y, z);
			ClientUtil.updateArea(level, x, y, z, x, y + 1, z);
		}
	}
	
	protected boolean canGrowTree(Level level, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(level, x, y + 1, z);
		return state.getMaterial().isReplaceable();
	}
	
	
}
