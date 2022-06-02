package paulevs.advancedtrees.trees;

import net.minecraft.block.BlockBase;
import net.minecraft.block.Fluid;
import net.minecraft.block.Leaves;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.jetbrains.annotations.Nullable;
import paulevs.advancedtrees.blocks.ATBlockProperties;
import paulevs.advancedtrees.blocks.ATLeavesBlock;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ClientUtil;

import java.util.Random;

public class TreeUtil {
	private static final Vec3I INTERNAL = new Vec3I();
	public static final Direction[] HORIZONTAL_FIXED = new Direction[] {
		Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
	};
	public static final Direction[] HORIZONTAL_RANDOM = new Direction[] {
		Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST
	};
	public static final Direction[] VERTICAL_GROW = new Direction[] {
		Direction.UP, Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST
	};
	
	public static void increaseAge(Level level, Vec3I start, int distance) {
		Vec3I p = start.clone();
		for (int i = 0; i < distance; i++) {
			float delta = (float) i / distance;
			BlockState state = BlocksUtil.getBlockState(level, p);
			if (state.getBlock() instanceof ATStaticLogBlock) {
				ATStaticLogBlock block = (ATStaticLogBlock) state.getBlock();
				int age = block.getAge(state);
				state = block.getWithAge(state, delta);
				int age2 = block.getAge(state);
				if (age2 < age) return;
				if (age2 != age) {
					BlocksUtil.setBlockState(level, p, state);
					ClientUtil.updateBlock(level, p.x, p.y, p.z);
				}
				p.move(state.get(ATBlockProperties.DIRECTION));
			}
			else {
				return;
			}
		}
	}
	
	// TODO Replace condition with tags
	public static boolean canGrow(BlockState state) {
		if (state.isAir()) return true;
		BlockBase block = state.getBlock();
		return block instanceof ATLeavesBlock || block instanceof Leaves || block instanceof Fluid;
	}
	
	/**
	 * Get possible grow direction from array of variants (vertical grow). Array will be mutated.
	 * @param level {@link Level} to check blocks
	 * @param pos {@link Vec3I} center position
	 * @return {@link Direction} or {@code null}
	 */
	@Nullable
	public static Direction getVerticalGrowDir(Level level, Vec3I pos) {
		return getGrowDirection(level, pos, VERTICAL_GROW, true);
	}
	
	@Nullable
	public static Direction getHorizontalGrowDir(Level level, Vec3I pos) {
		return getGrowDirection(level, pos, HORIZONTAL_RANDOM, false);
	}
	
	/**
	 * Get possible grow direction from array of variants. Array will be mutated.
	 * @param level {@link Level} to check blocks
	 * @param pos {@link Vec3I} center position
	 * @param variants array of possible {@link Direction}
	 * @return {@link Direction} or {@code null}
	 */
	@Nullable
	public static Direction getGrowDirection(Level level, Vec3I pos, Direction[] variants, boolean ignoreFirst) {
		if (ignoreFirst) {
			shuffleIgnoreFirst(level.rand, variants);
		}
		else {
			shuffle(level.rand, variants);
		}
		
		for (Direction dir: variants) {
			if (canGrow(BlocksUtil.getBlockState(level, INTERNAL.set(pos).move(dir)))) {
				return dir;
			}
		}
		
		return null;
	}
	
	/**
	 * Shuffle direction array and ignore first value.
	 * @param random {@link Random} to shuffle
	 * @param directions array of {@link Direction} values to shuffle
	 */
	public static void shuffleIgnoreFirst(Random random, Direction[] directions) {
		byte max = (byte) (directions.length - 1);
		for (byte i = 1; i < directions.length; i++) {
			byte i2 = (byte) (random.nextInt(max) + 1);
			Direction d = directions[i];
			directions[i] = directions[i2];
			directions[i2] = d;
		}
	}
	
	/**
	 * Shuffle direction array.
	 * @param random {@link Random} to shuffle
	 * @param directions array of {@link Direction} values to shuffle
	 */
	public static void shuffle(Random random, Direction[] directions) {
		for (byte i = 1; i < directions.length; i++) {
			byte i2 = (byte) random.nextInt(directions.length);
			Direction d = directions[i];
			directions[i] = directions[i2];
			directions[i2] = d;
		}
	}
}
