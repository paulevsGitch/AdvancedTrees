package paulevs.advancedtrees.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BaseBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemStack;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.util.math.Direction;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import net.modificationstation.stationapi.api.util.math.Direction.AxisDirection;
import paulevs.advancedtrees.trees.TreeUtil;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.BlocksUtil;

import java.util.ArrayList;
import java.util.List;

public class ATLoglikeBlock extends ATTemplateNotFullBlock {
	protected static final Box BOUNDING_BOX = Box.create(0, 0, 0, 0, 0, 0);
	private ItemConvertible drop;
	
	public ATLoglikeBlock(Identifier identifier, Material material) {
		super(identifier, material);
	}
	
	public void setDrop(ItemConvertible drop) {
		this.drop = drop;
	}
	
	@Override
	public void appendProperties(StateManager.Builder<BaseBlock, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ATBlockProperties.DIRECTION);
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z, int facing) {
		Direction dir = BlocksUtil.fromFacing(facing).getOpposite();
		BlockState state = getDefaultState().with(ATBlockProperties.DIRECTION, dir);
		BlocksUtil.setBlockState(level, x, y, z, state);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public Box getOutlineShape(Level level, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(level, x, y, z);
		if (state.getBlock() != this) return super.getCollisionShape(level, x, y, z);
		Direction dir = state.get(ATBlockProperties.DIRECTION);
		int age = getAge(state);
		updateBox(age, dir);
		return BOUNDING_BOX.move(x, y, z);
	}
	
	@Override
	public void updateBoundingBox(BlockView tileView, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(tileView, x, y, z);
		Direction dir = state.get(ATBlockProperties.DIRECTION);
		int age = getAge(state);
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
	
	@Override
	public void doesBoxCollide(Level level, int x, int y, int z, Box box, ArrayList list) {
		updateBoundingBox(level, x, y, z);
		super.doesBoxCollide(level, x, y, z, box, list);
	}
	
	@Override
	public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int face) {
		BlockState state = BlocksUtil.getBlockState(level, x, y, z);
		Vec3I pos = new Vec3I(x, y, z).move(state.get(ATBlockProperties.DIRECTION));
		state = BlocksUtil.getBlockState(level, pos);
		if (isSupport(state)) {
			return;
		}
		List<Vec3I> connectedBlocks = TreeUtil.getConnectedBlocks(level, pos.set(x, y, z));
		
		for (int i = connectedBlocks.size() - 1; i >= 0; i--) {
			pos = connectedBlocks.get(i);
			BaseBlock block = BlocksUtil.getBlockState(level, pos).getBlock();
			block.drop(level, pos.x, pos.y, pos.z, level.getBlockMeta(pos.x, pos.y, pos.z));
			level.setBlock(pos.x, pos.y, pos.z, 0);
		}
		
		level.removeBlockEntity(x, y, z);
		this.drop(level, x, y, z, level.getBlockMeta(x, y, z));
		level.setBlock(x, y, z, 0);
	}
	
	@Override
	public void onBlockRemoved(Level level, int x, int y, int z) {
		level.removeBlockEntity(x, y, z);
	}
	
	@Override
	public void drop(Level level, int x, int y, int z, int meta, float chance) {
		if (drop != null) {
			drop(level, x, y, z, new ItemStack(drop.asItem()));
		}
	}
	
	@Override
	public void afterBreak(Level level, PlayerBase player, int x, int y, int z, int meta) {
		if (drop != null) {
			drop(level, x, y, z, new ItemStack(drop.asItem()));
		}
	}
	
	@Override
	public boolean canPlaceAt(Level level, int x, int y, int z, int facing) {
		Direction dir = BlocksUtil.fromFacing(facing).getOpposite();
		Vec3I pos = new Vec3I(x, y, z).move(dir);
		return isSupport(BlocksUtil.getBlockState(level, pos));
	}
	
	public int getAge(BlockState state) {
		return 1;
	}
	
	protected void updateBox(int age, Direction dir) {
		float min = (7 - age) / 16F;
		float max = (9 + age) / 16F;
		BOUNDING_BOX.set(min, min, min, max, max, max);
		
		float distance = min - (max - 1F);
		boolean negative = dir.direction == AxisDirection.NEGATIVE;
		if (dir.axis == Axis.X) {
			if (negative) BOUNDING_BOX.minX -= distance;
			else BOUNDING_BOX.maxX += distance;
		}
		else if (dir.axis == Axis.Y) {
			if (negative) BOUNDING_BOX.minY -= distance;
			else BOUNDING_BOX.maxY += 1;
		}
		else {
			if (negative) BOUNDING_BOX.minZ -= distance;
			else BOUNDING_BOX.maxZ += distance;
		}
	}
	
	protected boolean isSupport(BlockState state) {
		return state.getBlock() instanceof ATLoglikeBlock || state.getBlock() == GRASS || state.getBlock() == DIRT || state.getBlock() == FARMLAND;
	}
}
