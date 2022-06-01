package paulevs.advancedtrees.trees;

import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ClientUtil;

public class TreeBehaviours {
	public static final TreeBehaviour SIMPLE_TREE = (level, context) -> {
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
	};
}
