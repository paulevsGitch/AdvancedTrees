package paulevs.advancedtrees.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import net.modificationstation.stationapi.api.util.math.Direction.AxisDirection;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ToolsUtil;

public class ATLogBlock extends ATTemplateNotFullBlock {
	private static final Box BOUNDING_BOX = Box.create(0, 0, 0, 0, 0, 0);
	private static int initMinAge;
	private static int initMaxAge;
	private final int minAge;
	private final int maxAge;
	private IntProperty age;
	
	public ATLogBlock(Identifier identifier) {
		this(identifier, Material.WOOD);
		setSounds(WOOD_SOUNDS);
		setBlastResistance(0.5F);
		setHardness(1F);
		ToolsUtil.setAxe(this, 0);
		setDefaultState(getDefaultState().with(age, minAge).with(ATBlockProperties.DIRECTION, Direction.DOWN));
	}
	
	public ATLogBlock(Identifier identifier, Material material) {
		super(identifier, material);
		this.minAge = initMinAge;
		this.maxAge = initMaxAge;
	}
	
	public BlockState getWithAge(BlockState state, int age) {
		return state.with(this.age, age);
	}
	
	@Override
	public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder) {
		builder.add(ATBlockProperties.DIRECTION);
		age = ATBlockProperties.getAge(initMinAge, initMaxAge);
		builder.add(age);
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z, int facing) {
		Direction dir = BlocksUtil.fromFacing(facing).getOpposite();
		BlockState state = getDefaultState().with(ATBlockProperties.DIRECTION, dir);
		BlocksUtil.setBlockState(level, x, y, z, state);
	}
	
	@Override
	public Box getCollisionShape(Level level, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(level, x, y, z);
		if (state.getBlock() != this) return super.getCollisionShape(level, x, y, z);
		Direction dir = state.get(ATBlockProperties.DIRECTION);
		int age = state.get(this.age);
		updateBox(age, dir);
		return BOUNDING_BOX.method_102(x, y, z);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public Box getOutlineShape(Level level, int x, int y, int z) {
		return getCollisionShape(level, x, y, z);
	}
	
	@Override
	public void updateBoundingBox(BlockView tileView, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(tileView, x, y, z);
		Direction dir = state.get(ATBlockProperties.DIRECTION);
		int age = state.get(this.age);
		updateBox(age, dir);
		setBoundingBox(
			(float) BOUNDING_BOX.minX,
			(float) BOUNDING_BOX.minY,
			(float) BOUNDING_BOX.minZ,
			(float) BOUNDING_BOX.maxX,
			(float) BOUNDING_BOX.maxY,
			(float) BOUNDING_BOX.maxZ
		);
	}
	
	public static void setAgeLimits(int minAge, int maxAge) {
		initMinAge = minAge;
		initMaxAge = maxAge;
	}
	
	private static void updateBox(int age, Direction dir) {
		float min = (7 - age) / 16F;
		float max = (9 + age) / 16F;
		BOUNDING_BOX.method_99(min, min, min, max, max, max);
		
		boolean negative = dir.direction == AxisDirection.NEGATIVE;
		if (dir.axis == Axis.X) {
			if (negative) BOUNDING_BOX.minX -= 1;
			else BOUNDING_BOX.maxX += 1;
		}
		else if (dir.axis == Axis.Y) {
			if (negative) BOUNDING_BOX.minY -= 1;
			else BOUNDING_BOX.maxY += 1;
		}
		else {
			if (negative) BOUNDING_BOX.minZ -= 1;
			else BOUNDING_BOX.maxZ += 1;
		}
	}
}
