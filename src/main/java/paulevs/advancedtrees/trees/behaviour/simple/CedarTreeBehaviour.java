package paulevs.advancedtrees.trees.behaviour.simple;

import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.trees.TreeUtil;
import paulevs.advancedtrees.trees.info.TreeInfo;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ClientUtil;
import paulevs.bhcore.util.MathUtil;

public class CedarTreeBehaviour extends SimpleTreeBehaviour {
	public CedarTreeBehaviour(int maxAge) {
		super(maxAge);
	}
	
	@Override
	protected void growTrunk(Level level, Vec3I treePos, Vec3I blockPos, Vec3I pos, int dist, BlockState state, TreeInfo info) {
		int offset = (dist + (int) MathHelper.hashCode(treePos.x, treePos.y, treePos.z)) & 1;
		
		// Branching
		if (dist > 2 && offset == 0) {
			for (Direction dir: MathUtil.DIRECTIONS) {
				pos.set(blockPos).move(dir);
				if (TreeUtil.canGrow(BlocksUtil.getBlockState(level, pos))) {
					BlocksUtil.setBlockState(level, pos.set(blockPos).move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
					growNewLeaves(level, pos, new Vec3I(), dist, info);
					ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
				}
			}
		}
		
		Direction dir = TreeUtil.getVerticalGrowDir(level, blockPos);
		if (dir != null) {
			pos.set(blockPos).move(dir);
			removeOldLeaves(level, blockPos, info);
			BlocksUtil.setBlockState(level, pos, state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
			growNewLeaves(level, pos, new Vec3I(), dist, info);
			ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
		}
	}
	
	@Override
	protected void growBranch(Level level, Vec3I blockPos, Vec3I pos, BlockState state, int dist, TreeInfo info) {
		TreeUtil.shuffle(level.rand, TreeUtil.HORIZONTAL_RANDOM);
		for (Direction dir: TreeUtil.HORIZONTAL_RANDOM) {
			pos.set(blockPos).move(dir);
			if (level.rand.nextBoolean() && TreeUtil.canGrow(BlocksUtil.getBlockState(level, pos))) {
				removeOldLeaves(level, blockPos, info);
				BlocksUtil.setBlockState(level, pos, state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				growNewLeaves(level, pos, new Vec3I(), dist, info);
				ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
			}
		}
	}
}
