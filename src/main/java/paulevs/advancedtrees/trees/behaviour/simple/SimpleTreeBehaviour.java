package paulevs.advancedtrees.trees.behaviour.simple;

import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.blocks.ATLeavesBlock;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.TreeUtil;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ClientUtil;
import paulevs.bhcore.util.MathUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimpleTreeBehaviour implements TreeBehaviour {
	protected final ATStaticLogBlock logStatic;
	protected final ATLeavesBlock leaves;
	protected final int maxAge;
	
	public SimpleTreeBehaviour(int maxAge, ATStaticLogBlock logStatic, ATLeavesBlock leaves) {
		this.logStatic = logStatic;
		this.leaves = leaves;
		this.maxAge = maxAge;
	}
	
	@Override
	public void grow(TreeContext context) {
		Level level = context.getLevel();
		Vec3I blockPos = context.getBlockPos();
		Direction dir = context.getBlock().get(ATBlockProperties.DIRECTION);
		BlockState log = logStatic.getDefaultState().with(ATBlockProperties.DIRECTION, dir);
		BlocksUtil.setBlockState(level, blockPos, log);
		
		int dist = context.getDistanceToOrigin();
		if (dist > maxAge) return;
		int maxStoredAge = context.getMaxAge();
		if (dist > maxStoredAge) return;
		
		
		Vec3I treePos = context.getTreePos();
		Vec3I pos = blockPos.clone();
		int gen = context.getGeneration();
		
		BlockState state = context.getBlock();
		
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
	
	protected void growTrunk(Level level, Vec3I treePos, Vec3I blockPos, Vec3I pos, int dist, BlockState state) {
		Direction dir;
		
		// Branching
		if (dist > 2) {
			int index = (dist + (int) MathHelper.hashCode(treePos.x, treePos.y, treePos.z)) & 3;
			dir = TreeUtil.HORIZONTAL_FIXED[index];
			if (TreeUtil.canGrow(BlocksUtil.getBlockState(level, pos.set(blockPos).move(dir)))) {
				removeOldLeaves(level, blockPos);
				BlocksUtil.setBlockState(level, pos, state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				growNewLeaves(level, pos, new Vec3I(), dist);
				ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
			}
		}
		
		dir = TreeUtil.getVerticalGrowDir(level, blockPos);
		if (dir != null) {
			removeOldLeaves(level, blockPos);
			BlocksUtil.setBlockState(level, pos.set(blockPos).move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
			growNewLeaves(level, pos, new Vec3I(), dist);
			ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
		}
	}
	
	protected void growBranch(Level level, Vec3I blockPos, Vec3I pos, BlockState state, int dist) {
		Direction dir;
		
		if (level.rand.nextBoolean()) {
			dir = TreeUtil.getHorizontalGrowDir(level, blockPos);
			if (dir != null) {
				removeOldLeaves(level, blockPos);
				BlocksUtil.setBlockState(level, pos.set(blockPos).move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				growNewLeaves(level, pos, new Vec3I(), dist);
				ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
			}
		}
		
		if (level.rand.nextInt(4) == 0) {
			dir = TreeUtil.getVerticalGrowDir(level, blockPos);
		}
		else {
			dir = TreeUtil.getHorizontalGrowDir(level, blockPos);
		}
		
		if (dir != null) {
			removeOldLeaves(level, blockPos);
			BlocksUtil.setBlockState(level, pos.set(blockPos).move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
			growNewLeaves(level, pos, new Vec3I(), dist);
			ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
		}
	}
	
	protected void removeOldLeaves(Level level, Vec3I blockPos) {
		Vec3I pos = new Vec3I();
		Vec3I pos2 = new Vec3I();
		List<Vec3I> positions = new ArrayList<>();
		for (Direction dir: MathUtil.DIRECTIONS) {
			pos.set(blockPos).move(dir);
			BlockState state = BlocksUtil.getBlockState(level, pos);
			if (state.getBlock() == leaves && state.get(ATBlockProperties.DIRECTION).getOpposite() == dir) {
				removeOldLeaves(level, pos, pos2, positions);
			}
		}
		for (int i = positions.size() - 1; i >= 0; i--) {
			Vec3I p = positions.get(i);
			level.setTile(p.x, p.y, p.z, 0);
		}
	}
	
	protected void removeOldLeaves(Level level, Vec3I blockPos, Vec3I pos, List<Vec3I> positions) {
		positions.add(blockPos.clone());
		for (Direction dir: MathUtil.DIRECTIONS) {
			pos.set(blockPos).move(dir);
			BlockState state = BlocksUtil.getBlockState(level, pos);
			if (state.getBlock() == leaves && state.get(ATBlockProperties.DIRECTION).getOpposite() == dir) {
				removeOldLeaves(level, pos.clone(), pos, positions);
			}
		}
	}
	
	protected void growNewLeaves(Level level, Vec3I blockPos, Vec3I pos, int dis) {
		BlockState defaultState = leaves.getDefaultState();
		BlockState connected = defaultState.with(ATBlockProperties.CONNECTED, true);
		
		for (Direction dir: TreeUtil.VERTICAL_GROW) {
			pos.set(blockPos).move(dir);
			BlockState state = BlocksUtil.getBlockState(level, pos);
			if (state.isAir()) {
				state = connected.with(ATBlockProperties.DIRECTION, dir.getOpposite());
				BlocksUtil.setBlockState(level, pos, state);
			}
		}
		
		if (dis > 2) {
			for (Direction dir: TreeUtil.HORIZONTAL_RANDOM) {
				pos.set(blockPos).move(dir);
				BlockState state = BlocksUtil.getBlockState(level, pos);
				if (state.getBlock() == leaves) {
					Direction dir2 = MathUtil.rotateCW(dir);
					pos.move(dir2);
					state = BlocksUtil.getBlockState(level, pos);
					if (state.isAir()) {
						state = defaultState.with(ATBlockProperties.DIRECTION, dir2.getOpposite());
						BlocksUtil.setBlockState(level, pos, state);
					}
				}
				
				pos.set(blockPos).move(dir);
				pos.y++;
				
				state = BlocksUtil.getBlockState(level, pos);
				if (state.isAir()) {
					state = defaultState.with(ATBlockProperties.DIRECTION, dir.getOpposite());
					BlocksUtil.setBlockState(level, pos, state);
				}
			}
		}
		
		if (dis > 4) {
			for (Direction dir: TreeUtil.HORIZONTAL_RANDOM) {
				Direction side = MathUtil.rotateCW(dir);
				pos.set(blockPos).move(dir, 2).move(side, -2);
				for (byte i = 0; i < 3; i++) {
					BlockState state = BlocksUtil.getBlockState(level, pos.move(side));
					if (state.isAir()) {
						state = BlocksUtil.getBlockState(level, pos.move(dir.getOpposite()));
						if (state.getBlock() == leaves) {
							state = defaultState.with(ATBlockProperties.DIRECTION, dir.getOpposite());
							BlocksUtil.setBlockState(level, pos.move(dir), state);
						}
					}
				}
				
				pos.set(blockPos).move(dir).move(side);
				BlockState state = BlocksUtil.getBlockState(level, pos);
				if (state.getBlock() == leaves) {
					pos.y++;
					if (state.isAir()) {
						BlocksUtil.setBlockState(level, pos, defaultState);
					}
				}
			}
		}
	}
}
