package paulevs.advancedtrees.trees.behaviour.simple;

import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.MathHelper;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.TreeUtil;
import paulevs.advancedtrees.trees.behaviour.crown.PatternCrown;
import paulevs.advancedtrees.trees.info.TreeInfo;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ClientUtil;
import paulevs.bhcore.util.MathUtil;

public class BirchTreeBehaviour extends SimpleTreeBehaviour {
	private static final PatternCrown[] CROWNS;
	
	public BirchTreeBehaviour(int maxAge) {
		super(maxAge);
	}
	
	@Override
	protected void growTrunk(TreeContext context, TreeInfo info, Vec3I pos) {
		int dist = context.getDistanceToOrigin();
		Vec3I treePos = context.getTreePos();
		Vec3I blockPos = context.getBlockPos();
		BlockState state = context.getBlockState();
		Level level = context.getLevel();
		Direction dir;
		
		int minHeight = context.getMaxAge() / 2;
		int hash = (int) MathHelper.hashCode(treePos.x, treePos.y, treePos.z);
		
		// Branching
		if (dist > minHeight && ((pos.y + hash) & 1) == 0) {
			int index = ((dist >> 1) + hash) & 3;
			dir = TreeUtil.HORIZONTAL_FIXED[index];
			if (TreeUtil.canGrow(BlocksUtil.getBlockState(level, pos.set(blockPos).move(dir)))) {
				removeOldLeaves(level, blockPos, info);
				BlocksUtil.setBlockState(level, pos, state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				growNewLeaves(level, pos, new Vec3I(), dist, info);
				ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
			}
		}
		
		dir = TreeUtil.getVerticalGrowDir(level, blockPos);
		if (dir != null) {
			removeOldLeaves(level, blockPos, info);
			BlocksUtil.setBlockState(level, pos.set(blockPos).move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
			growNewLeaves(level, pos, new Vec3I(), dist, info);
			ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
		}
	}
	
	@Override
	protected void growBranch(TreeContext context, TreeInfo info, Vec3I pos) {
		int dist = context.getDistanceToOrigin();
		Vec3I blockPos = context.getBlockPos();
		BlockState state = context.getBlockState();
		Level level = context.getLevel();
		Direction dir;
		
		if (level.rand.nextInt(6) == 0) {
			dir = TreeUtil.getHorizontalGrowDir(level, blockPos);
			if (dir != null) {
				removeOldLeaves(level, blockPos, info);
				BlocksUtil.setBlockState(level, pos.set(blockPos).move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
				growNewLeaves(level, pos, new Vec3I(), dist, info);
				ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
			}
		}
		
		if (level.rand.nextInt(4) == 0) {
			dir = TreeUtil.getHorizontalGrowDir(level, blockPos);
		}
		else {
			dir = TreeUtil.getVerticalGrowDir(level, blockPos);
		}
		
		if (dir != null) {
			removeOldLeaves(level, blockPos, info);
			BlocksUtil.setBlockState(level, pos.set(blockPos).move(dir), state.with(ATBlockProperties.DIRECTION, dir.getOpposite()));
			growNewLeaves(level, pos, new Vec3I(), dist, info);
			ClientUtil.updateArea(level, pos.x - 1, pos.y, pos.z - 1, pos.x + 1, pos.y + 1, pos.z + 1);
		}
	}
	
	@Override
	protected void growNewLeaves(Level level, Vec3I blockPos, Vec3I pos, int dis, TreeInfo info) {
		int index = MathUtil.clamp((int) (dis * CROWNS.length * 1.5F / maxAge), 0, CROWNS.length - 1);
		CROWNS[index].place(level, blockPos, info);
	}
	
	static {
		CROWNS = new PatternCrown[] {
			new PatternCrown(new String[][] {
				new String[] {
					"###",
					"# #",
					"###"
				},
				new String[] {
					" # ",
					"###",
					" # "
				}
			}, new Vec3I(1, 0, 1)),
			new PatternCrown(new String[][] {
				new String[] {
					"# #",
					"   ",
					"# #"
				},
				new String[] {
					"###",
					"# #",
					"###"
				},
				new String[] {
					" # ",
					"###",
					" # "
				}
			}, new Vec3I(1, 1, 1)),
			new PatternCrown(new String[][] {
				new String[] {
					"#  ",
					"   ",
					"  #"
				},
				new String[] {
					"# #",
					"   ",
					"# #"
				},
				new String[] {
					"###",
					"# #",
					"###"
				},
				new String[] {
					" # ",
					"###",
					" # "
				}
			}, new Vec3I(1, 2, 1)),
			new PatternCrown(new String[][] {
				new String[] {
					"     ",
					"   # ",
					"     ",
					" #   ",
					"     "
				},
				new String[] {
					"     ",
					" # # ",
					"     ",
					" # # ",
					"     "
				},
				new String[] {
					"  #  ",
					" ### ",
					"## ##",
					" ### ",
					"  #  "
				},
				new String[] {
					"  #  ",
					" ### ",
					"## ##",
					" ### ",
					"  #  "
				},
				new String[] {
					"     ",
					" ### ",
					" ### ",
					" ### ",
					"     "
				},
				new String[] {
					"     ",
					"  #  ",
					" ### ",
					"  #  ",
					"     "
				}
			}, new Vec3I(2, 3, 2))
		};
	}
}
