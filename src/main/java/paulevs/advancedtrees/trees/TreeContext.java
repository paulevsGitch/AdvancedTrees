package paulevs.advancedtrees.trees;

import net.minecraft.block.entity.BaseBlockEntity;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.blocks.ATLoglikeBlock;
import paulevs.advancedtrees.tileentities.TreeBlockEntity;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.advancedtrees.trees.info.TreeBlockSet;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;

public final class TreeContext {
	private static final TreeContext INSTANCE = new TreeContext();
	private static final Vec3I INTERNAL = new Vec3I();
	private final Vec3I treePos = new Vec3I();
	private final Vec3I blockPos = new Vec3I();
	private final Vec3I splitPos = new Vec3I();
	private TreeBehaviour behaviour;
	private TreeBlockEntity entity;
	private int distanceToOrigin;
	private int distanceToSplit;
	private int generation;
	private BlockState block;
	private TreeBlockSet info;
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
	public BlockState getBlockState() {
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
	
	/**
	 * Restores tile entity if it was destroyed by block setting.
	 */
	public void restoreEntity() {
		if (entity != null && level.getBlockEntity(treePos.x, treePos.y, treePos.z) != entity) {
			level.setBlockEntity(treePos.x, treePos.y, treePos.z, entity);
		}
	}
	
	/**
	 * Provide information about current tree.
	 * @return {@link TreeBlockSet}.
	 */
	public TreeBlockSet getTreeInfo() {
		return info;
	}
	
	/**
	 * Updates tree context for local tree
	 * @param level {@link Level}
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @param z Z coordinate
	 * @param info {@link TreeBlockSet}
	 */
	public void update(Level level, int x, int y, int z, TreeBlockSet info) {
		this.level = level;
		this.info = info;
		
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
		for (byte i = 0; i < 127 && validState(state); i++) {
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
		
		BaseBlockEntity entity = level.getBlockEntity(treePos.x, treePos.y, treePos.z);
		if (entity != null && entity instanceof TreeBlockEntity) {
			this.entity = (TreeBlockEntity) entity;
			maxAge = this.entity.getMaxAge();
			if (distanceToOrigin >= maxAge) {
				level.removeBlockEntity(treePos.x, treePos.y, treePos.z);
				this.entity = null;
			}
		}
		
		if (this.entity != null) {
			behaviour = this.entity.getBehaviour();
		}
	}
	
	public void grow() {
		if (behaviour != null) {
			behaviour.grow(this);
			restoreEntity();
		}
	}
	
	public void updateAndGrow(Level level, int x, int y, int z, TreeBlockSet set) {
		update(level, x, y, z, set);
		if (isValid()) {
			grow();
		}
	}
	
	private boolean validState(BlockState state) {
		return state.getBlock() instanceof ATLoglikeBlock;
	}
}
