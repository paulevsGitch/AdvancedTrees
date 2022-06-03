package paulevs.advancedtrees.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.maths.MathHelper;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.state.property.IntProperty;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.bhcore.util.MathUtil;
import paulevs.bhcore.util.ToolsUtil;

import java.util.function.Supplier;

public class ATStaticLogBlock extends ATLoglikeBlock {
	private static int initMinAge;
	private static int initMaxAge;
	private Supplier<ItemConvertible> drop;
	private final int minAge;
	private final int maxAge;
	private IntProperty age;
	
	public ATStaticLogBlock(Identifier identifier, Supplier<ItemConvertible> drop) {
		this(identifier, Material.WOOD, drop);
		ToolsUtil.setAxe(this, 0);
	}
	
	public ATStaticLogBlock(Identifier identifier, Material material, Supplier<ItemConvertible> drop) {
		super(identifier, material);
		setSounds(WOOD_SOUNDS);
		setBlastResistance(0.5F);
		setHardness(1F);
		this.minAge = initMinAge;
		this.maxAge = initMaxAge;
		this.drop = drop;
		setDefaultState(getDefaultState().with(age, minAge).with(ATBlockProperties.DIRECTION, Direction.DOWN));
	}
	
	public BlockState getWithAge(BlockState state, int age) {
		return state.with(this.age, MathUtil.clamp(age, minAge, maxAge));
	}
	
	public BlockState getWithAge(BlockState state, float delta) {
		return state.with(this.age, MathHelper.floor(MathUtil.lerp(minAge, maxAge, delta) + 0.5F));
	}
	
	@Override
	public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder) {
		super.appendProperties(builder);
		age = ATBlockProperties.getAge(initMinAge, initMaxAge);
		builder.add(age);
	}
	
	@Override
	public int getAge(BlockState state) {
		return state.get(age);
	}
	
	@Override
	public void beforeDestroyedByExplosion(Level level, int x, int y, int z, int meta, float chance) {
		drop(level, x, y, z, new ItemInstance(drop.get().asItem()));
	}
	
	@Override
	public void afterBreak(Level level, PlayerBase player, int x, int y, int z, int meta) {
		drop(level, x, y, z, new ItemInstance(drop.get().asItem()));
	}
	
	public static void setAgeLimits(int minAge, int maxAge) {
		initMinAge = minAge;
		initMaxAge = maxAge;
	}
}
