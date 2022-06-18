package paulevs.advancedtrees.blocks;

import net.minecraft.block.BaseBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.bhcore.util.MathUtil;
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
		return state.with(this.age, MathUtil.clamp(age, minAge, maxAge));
	}
	
	public BlockState getWithAge(BlockState state, float delta) {
		return state.with(this.age, MathHelper.floor(MathUtil.lerp(minAge, maxAge, delta) + 0.5F));
	}
	
	@Override
	public void appendProperties(StateManager.Builder<BaseBlock, BlockState> builder) {
		super.appendProperties(builder);
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
