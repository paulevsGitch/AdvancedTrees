package paulevs.advancedtrees.trees.behaviour.simple;

import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.TreeUtil;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ClientUtil;
import paulevs.bhcore.util.MathUtil;

import java.util.Random;

public class SimpleCactusBehaviour implements TreeBehaviour {
	private final int maxAge;
	
	public SimpleCactusBehaviour(int maxAge) {
		this.maxAge = maxAge;
	}
	
	@Override
	public void grow(TreeContext context) {
		Level level = context.getLevel();
		Vec3I blockPos = context.getBlockPos();
		Direction dir = context.getBlockState().get(ATBlockProperties.DIRECTION);
		BlockState log = context.getTreeInfo().getLogStatic().getDefaultState().with(ATBlockProperties.DIRECTION, dir);
		BlocksUtil.setBlockState(level, blockPos, log);
		
		int dist = context.getDistanceToOrigin();
		if (dist > maxAge) return;
		int maxStoredAge = context.getMaxAge();
		if (dist > maxStoredAge) return;
		
		
		Vec3I treePos = context.getTreePos();
		Vec3I pos = blockPos.clone();
		int gen = context.getGeneration();
		
		BlockState state = context.getBlockState();
		
		if (gen == 0) {
			growTrunk(level, treePos, blockPos, pos, dist, state);
		}
		else {
			growBranch(level, blockPos, pos, state, dist);
		}
		
		TreeUtil.increaseAge(level, blockPos, maxAge);
	}
	
	@Override
	public int getAge(Random random) {
		return MathUtil.randomRange(maxAge * 2 / 3, maxAge, random);
	}
	
	private void growTrunk(Level level, Vec3I treePos, Vec3I blockPos, Vec3I pos, int dist, BlockState state) {
		Direction dir;
		
		// Branching
		if (dist > 2) {
			int index = (dist + (int) MathHelper.hashCode(treePos.x, treePos.y, treePos.z)) & 3;
			dir = TreeUtil.HORIZONTAL_FIXED[index];
			if (TreeUtil.canGrow(BlocksUtil.getBlockState(level, pos.set(blockPos).move(dir)))) {
				BlocksUtil.setBlockState(level, pos, state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
			}
		}
		
		dir = TreeUtil.getVerticalGrowDir(level, blockPos);
		if (dir != null) {
			BlocksUtil.setBlockState(level, pos.set(blockPos).move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
			ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
		}
	}
	
	private void growBranch(Level level, Vec3I blockPos, Vec3I pos, BlockState state, int dist) {
		Direction dir;
		
		if (level.rand.nextBoolean() || dist > 1) {
			dir = TreeUtil.getVerticalGrowDir(level, blockPos);
		}
		else {
			dir = TreeUtil.getHorizontalGrowDir(level, blockPos);
		}
		
		if (dir != null) {
			BlocksUtil.setBlockState(level, pos.set(blockPos).move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
			ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
		}
	}
}
