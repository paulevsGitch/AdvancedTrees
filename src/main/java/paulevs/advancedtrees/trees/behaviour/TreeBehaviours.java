package paulevs.advancedtrees.trees.behaviour;

import paulevs.advancedtrees.listeners.BlockListener;

public class TreeBehaviours {
	public static final TreeBehaviour OAK_TREE_BEHAVIOUR = new SimpleTreeBehaviour(
		8,
		BlockListener.oakLogStatic,
		BlockListener.oakLeaves
	);
	
	/*public static final TreeBehaviour SIMPLE_TREE = (context) -> {
		Level level = context.getLevel();
		Vec3I treePos = context.getTreePos();
		Vec3I blockPos = context.getBlockPos();
		Vec3I pos = blockPos.clone();
		int gen = context.getGeneration();
		boolean needUpdate = false;
		
		Direction dir = context.getBlock().get(ATBlockProperties.DIRECTION);
		ATStaticLogBlock log = context.getStaticLogBlock();
		BlockState state = log.getDefaultState().with(ATBlockProperties.DIRECTION, dir);
		BlocksUtil.setBlockState(level, pos, state);
		
		int dist = context.getDistanceToOrigin();
		// If too large
		if (dist > 15) {
			return;
		}
		
		// If trunk
		if (gen == 0) {
			// Branching
			if (dist > 2) {
				int index = (dist + (int) MathHelper.hashCode(treePos.x, treePos.y, treePos.z)) & 3;
				dir = TreeUtil.HORIZONTAL_FIXED[index];
				if (TreeUtil.canGrow(BlocksUtil.getBlockState(level, pos.move(dir)))) {
					BlocksUtil.setBlockState(level, pos, context.getBlock().with(ATBlockProperties.DIRECTION, dir.getOpposite()));
					ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
					needUpdate = true;
				}
				pos.set(blockPos);
			}
			
			dir = TreeUtil.getVerticalGrowDir(level, pos);
			if (dir != null) {
				BlocksUtil.setBlockState(level, pos.move(dir), context.getBlock().with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
				needUpdate = true;
			}
		}
		// If branch
		else {
			// Branching
			if (level.rand.nextBoolean()) {
				dir = TreeUtil.getHorizontalGrowDir(level, pos);
				if (dir != null) {
					BlocksUtil.setBlockState(level, pos.move(dir), context.getBlock().with(ATBlockProperties.DIRECTION, dir.getOpposite()));
					ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
					needUpdate = true;
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
				BlocksUtil.setBlockState(level, pos.move(dir), context.getBlock().with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				ClientUtil.updateBlock(level, pos.x, pos.y, pos.z);
				needUpdate = true;
			}
			pos.set(blockPos);
		}
		
		//if (needUpdate) {
			TreeUtil.increaseAge(level, blockPos, 16);
		//}
	};*/
}
