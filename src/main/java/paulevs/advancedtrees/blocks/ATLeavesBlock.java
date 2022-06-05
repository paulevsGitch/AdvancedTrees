package paulevs.advancedtrees.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.util.math.Direction;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.BurnableUtil;
import paulevs.bhcore.util.ToolsUtil;

public class ATLeavesBlock extends TemplateBlockBase {
	private ItemConvertible drop;
	private int dropChance;
	
	public ATLeavesBlock(Identifier identifier) {
		this(identifier, Material.LEAVES);
		setSounds(GRASS_SOUNDS);
		ToolsUtil.setShears(this, 0);
		BurnableUtil.registerBurnable(this, 30, 60);
	}
	
	public ATLeavesBlock(Identifier identifier, Material material) {
		super(identifier, material);
		setTranslationKey(identifier.modID, identifier.id);
		setHardness(0.2F);
		setDefaultState(getDefaultState()
			.with(ATBlockProperties.DIRECTION, Direction.DOWN)
			.with(ATBlockProperties.CONNECTED, false)
			.with(ATBlockProperties.PERSISTENT, false)
		);
	}
	
	public void setDrop(ItemConvertible drop, int dropChance) {
		this.dropChance = dropChance;
		this.drop = drop;
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z, int facing) {
		Direction dir = BlocksUtil.fromFacing(facing).getOpposite();
		BlockState log = BlocksUtil.getBlockState(level, new Vec3I(x, y, z).move(dir));
		BlockState state = getDefaultState().with(ATBlockProperties.DIRECTION, dir).with(ATBlockProperties.CONNECTED, canConnect(log));
		BlocksUtil.setBlockState(level, x, y, z, state);
	}
	
	@Override
	public void afterPlaced(Level level, int x, int y, int z, Living entity) {
		if (entity instanceof PlayerBase) {
			BlockState state = BlocksUtil.getBlockState(level, x, y, z);
			BlocksUtil.setBlockState(level, x, y, z, state.with(ATBlockProperties.PERSISTENT, true));
		}
	}
	
	@Override
	public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ATBlockProperties.DIRECTION);
		builder.add(ATBlockProperties.CONNECTED);
		builder.add(ATBlockProperties.PERSISTENT);
	}
	
	@Override
	public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int face) {
		BlockState state = BlocksUtil.getBlockState(level, x, y, z);
		if (state.get(ATBlockProperties.PERSISTENT)) return;
		Vec3I pos = new Vec3I(x, y, z).move(state.get(ATBlockProperties.DIRECTION));
		BlockState state2 = BlocksUtil.getBlockState(level, pos);
		if (state2.isAir()) {
			this.drop(level, x, y, z, level.getTileMeta(x, y, z));
			level.setTile(x, y, z, 0);
		}
		state2 = state.with(ATBlockProperties.CONNECTED, canConnect(state2));
		if (state != state2) {
			BlocksUtil.setBlockState(level, x, y, z, state2);
		}
	}
	
	@Override
	public boolean isFullOpaque() {
		return false;
	}
	
	@Override
	public void beforeDestroyedByExplosion(Level level, int x, int y, int z, int meta, float chance) {
		if (drop != null && level.rand.nextInt(dropChance) == 0) {
			drop(level, x, y, z, new ItemInstance(drop.asItem()));
		}
	}
	
	@Override
	public void afterBreak(Level level, PlayerBase player, int x, int y, int z, int meta) {
		ItemInstance item = player.getHeldItem();
		if (item != null && ToolsUtil.isShears(item)) {
			drop(level, x, y, z, new ItemInstance(this));
		}
		else {
			super.afterBreak(level, player, x, y, z, meta);
		}
	}
	
	public boolean canConnect(BlockState state) {
		return state.getBlock() instanceof ATLoglikeBlock;
	}
}
