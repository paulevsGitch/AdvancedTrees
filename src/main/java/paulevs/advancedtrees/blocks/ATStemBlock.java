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
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.ToolsUtil;

import java.util.ArrayList;

public class ATStemBlock extends ATTemplateNotFullBlock {
	protected static final Box BOUNDING_BOX = Box.create(0, 0, 0, 0, 0, 0);
	
	public ATStemBlock(Identifier identifier) {
		this(identifier, Material.WOOD);
		setSounds(WOOD_SOUNDS);
		ToolsUtil.setAxe(this, 0);
	}
	
	public ATStemBlock(Identifier identifier, Material material) {
		super(identifier, material);
		setBlastResistance(0.5F);
		setHardness(1F);
	}
	
	@Override
	public void appendProperties(StateManager.Builder<BlockBase, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ATBlockProperties.AXIS);
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z, int facing) {
		Axis axis = BlocksUtil.fromFacing(facing).axis;
		BlockState state = getDefaultState().with(ATBlockProperties.AXIS, axis);
		BlocksUtil.setBlockState(level, x, y, z, state);
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public Box getOutlineShape(Level level, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(level, x, y, z);
		if (state.getBlock() != this) return super.getCollisionShape(level, x, y, z);
		updateBox(state.get(ATBlockProperties.AXIS));
		return BOUNDING_BOX.method_102(x, y, z);
	}
	
	@Override
	public void updateBoundingBox(BlockView tileView, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(tileView, x, y, z);
		updateBox(state.get(ATBlockProperties.AXIS));
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
	
	protected void updateBox(Axis axis) {
		BOUNDING_BOX.method_99(0.25F, 0.25F, 0.25F, 0.75F, 0.75F, 0.75F);
		if (axis == Axis.X) {
			BOUNDING_BOX.minX = 0.0F;
			BOUNDING_BOX.maxX = 1.0F;
		}
		else if (axis == Axis.Y) {
			BOUNDING_BOX.minY = 0.0F;
			BOUNDING_BOX.maxY = 1.0F;
		}
		else {
			BOUNDING_BOX.minZ = 0.0F;
			BOUNDING_BOX.maxZ = 1.0F;
		}
	}
}
