package paulevs.advancedtrees.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ToolsUtil;

public class ATStaticLogBlock extends ATLoglikeBlock {
	private static int initMinAge;
	private static int initMaxAge;
	private final int minAge;
	private final int maxAge;
	private IntProperty age;
	
	public ATStaticLogBlock(Identifier identifier) {
		this(identifier, Material.WOOD);
		ToolsUtil.setAxe(this, 0);
	}
	
	public ATStaticLogBlock(Identifier identifier, Material material) {
		super(identifier, material);
		setSounds(WOOD_SOUNDS);
		setBlastResistance(0.5F);
		setHardness(1F);
		this.minAge = initMinAge;
		this.maxAge = initMaxAge;
		setDefaultState(getDefaultState().with(age, minAge).with(ATBlockProperties.DIRECTION, Direction.DOWN));
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
	public int getAge(BlockState state) {
		return state.get(age);
	}
	
	public static void setAgeLimits(int minAge, int maxAge) {
		initMinAge = minAge;
		initMaxAge = maxAge;
	}
}
