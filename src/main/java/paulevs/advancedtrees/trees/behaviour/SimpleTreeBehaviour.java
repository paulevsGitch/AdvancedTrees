package paulevs.advancedtrees.trees.behaviour;

import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.TreeUtil;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ClientUtil;

public class SimpleTreeBehaviour implements TreeBehaviour {
	private final ATStaticLogBlock logStatic;
	private final int maxAge;
	
	public SimpleTreeBehaviour(int maxAge, ATStaticLogBlock logStatic) {
		this.logStatic = logStatic;
		this.maxAge = maxAge;
	}
	
	@Override
	public void grow(TreeContext context) {
		Level level = context.getLevel();
		Vec3I treePos = context.getTreePos();
		Vec3I blockPos = context.getBlockPos();
		Vec3I pos = blockPos.clone();
		int gen = context.getGeneration();
		
		BlockState state = context.getBlock();
		Direction dir = context.getBlock().get(ATBlockProperties.DIRECTION);
		BlockState log = logStatic.getDefaultState().with(ATBlockProperties.DIRECTION, dir);
		BlocksUtil.setBlockState(level, pos, log);
		
		int dist = context.getDistanceToOrigin();
		// If too large
		if (dist > maxAge) {
			return;
		}
		
		if (gen == 0) {
			growTrunk(level, treePos, blockPos, pos, dist, state);
		}
		else {
			growBranch(level, blockPos, pos, state);
		}
		
		TreeUtil.increaseAge(level, blockPos, maxAge);
	}
	
	private void growTrunk(Level level, Vec3I treePos, Vec3I blockPos, Vec3I pos, int dist, BlockState state) {
		Direction dir;
		
		// Branching
		if (dist > 2) {
			int index = (dist + (int) MathHelper.hashCode(treePos.x, treePos.y, treePos.z)) & 3;
			dir = TreeUtil.HORIZONTAL_FIXED[index];
			if (TreeUtil.canGrow(BlocksUtil.getBlockState(level, pos.move(dir)))) {
				BlocksUtil.setBlockState(level, pos, state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
			}
			pos.set(blockPos);
		}
		
		dir = TreeUtil.getVerticalGrowDir(level, pos);
		if (dir != null) {
			BlocksUtil.setBlockState(level, pos.move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
			ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
		}
	}
	
	private void growBranch(Level level, Vec3I blockPos, Vec3I pos, BlockState state) {
		Direction dir;
		
		if (level.rand.nextBoolean()) {
			dir = TreeUtil.getHorizontalGrowDir(level, pos);
			if (dir != null) {
				BlocksUtil.setBlockState(level, pos.move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
			}
			pos.set(blockPos);
		}
		
		if (level.rand.nextInt(4) == 0) {
			dir = TreeUtil.getVerticalGrowDir(level, pos);
		}
		else {
			dir = TreeUtil.getHorizontalGrowDir(level, pos);
		}
		
		if (dir != null) {
			BlocksUtil.setBlockState(level, pos.move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
			ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
		}
		pos.set(blockPos);
	}
}
