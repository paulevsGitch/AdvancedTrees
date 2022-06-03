package paulevs.advancedtrees.trees;

import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.blocks.ATLoglikeBlock;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.tileentities.TreeTileEntity;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;

public final class TreeContext {
	private static final TreeContext INSTANCE = new TreeContext();
	private static final Vec3I INTERNAL = new Vec3I();
	private final Vec3I treePos = new Vec3I();
	private final Vec3I blockPos = new Vec3I();
	private final Vec3I splitPos = new Vec3I();
	private int distanceToOrigin;
	private int distanceToSplit;
	private int generation;
	private BlockState block;
	private ATStaticLogBlock staticLogBlock;
	private Level level;
	private int maxAge;
	
	private TreeContext() {}
	
	public static TreeContext getInstance() {
		return INSTANCE;
	}
	
	/**
	 * Get tree origin position.
	 * @return {@link Vec3I} tree position
	 */
	public Vec3I getTreePos() {
		return treePos;
	}
	
	/**
	 * Get current block position.
	 * @return {@link Vec3I} block position
	 */
	public Vec3I getBlockPos() {
		return blockPos;
	}
	
	/**
	 * Get position of the last branch/trunk split.
	 * @return {@link Vec3I} split position
	 */
	public Vec3I getSplitPos() {
		return splitPos;
	}
	
	/**
	 * Get distance to the tree origin.
	 * @return {@code integer} distance
	 */
	public int getDistanceToOrigin() {
		return distanceToOrigin;
	}
	
	/**
	 * Get distance to the last branch/trunk split in connected blocks.
	 * @return {@code integer} distance
	 */
	public int getDistanceToSplit() {
		return distanceToSplit;
	}
	
	/**
	 * Get current generation. Each branch split will increase generation.
	 * @return {@code integer} generation
	 */
	public int getGeneration() {
		return generation;
	}
	
	/**
	 * Get current tree block.
	 * @return {@link BlockState}
	 */
	public BlockState getBlock() {
		return block;
	}
	
	/**
	 * Get current level
	 * @return {@link Level}
	 */
	public Level getLevel() {
		return level;
	}
	
	/**
	 * Check if block is valid to prevent errors.
	 */
	public boolean isValid() {
		return block.contains(ATBlockProperties.DIRECTION);
	}
	
	/**
	 * Get tree maximum age (if possible), otherwise return -1.
	 */
	public int getMaxAge() {
		return maxAge;
	}
	
	public void update(Level level, int x, int y, int z) {
		this.level = level;
		
		block = BlocksUtil.getBlockState(level, x, y, z);
		if (!isValid()) return;
		
		blockPos.set(x, y, z);
		treePos.set(x, y, z);
		
		distanceToOrigin = -1;
		distanceToSplit = -1;
		generation = 0;
		maxAge = -1;
		
		BlockState state = block;
		Direction dir = block.get(ATBlockProperties.DIRECTION);
		for (byte i = 0; i < 128 && validState(state); i++) {
			dir = state.get(ATBlockProperties.DIRECTION);
			treePos.move(dir);
			state = BlocksUtil.getBlockState(level, treePos);
			distanceToOrigin++;
			
			if (validState(state)) {
				Direction d2 = state.get(ATBlockProperties.DIRECTION);
				if (d2 != dir) {
					INTERNAL.set(treePos).move(d2.getOpposite());
					BlockState state2 = BlocksUtil.getBlockState(level, INTERNAL);
					if (validState(state2)) {
						generation++;
						if (distanceToSplit < 0) {
							distanceToSplit = distanceToOrigin;
							splitPos.set(treePos);
						};
					}
				}
			}
		}
		
		if (!validState(state)) {
			treePos.move(dir.getOpposite());
		}
		
		TileEntityBase entity = level.getTileEntity(treePos.x, treePos.y, treePos.z);
		if (entity != null && entity instanceof TreeTileEntity) {
			maxAge = ((TreeTileEntity) entity).getMaxAge();
			if (distanceToOrigin >= maxAge) {
				level.removeTileEntity(treePos.x, treePos.y, treePos.z);
			}
		}
	}
	
	private boolean validState(BlockState state) {
		return state.getBlock() instanceof ATLoglikeBlock;
	}
}
