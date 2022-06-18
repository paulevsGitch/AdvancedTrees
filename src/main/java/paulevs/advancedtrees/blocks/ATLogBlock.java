package paulevs.advancedtrees.blocks;

import net.minecraft.block.BaseBlock;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.state.StateManager;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;
import paulevs.bhcore.util.BlocksUtil;
import paulevs.bhcore.util.BurnableUtil;
import paulevs.bhcore.util.ToolsUtil;

public class ATLogBlock extends TemplateBlockBase {
	public ATLogBlock(Identifier identifier) {
		this(identifier, Material.WOOD);
		setSounds(WOOD_SOUNDS);
		ToolsUtil.setAxe(this, 0);
		BurnableUtil.registerBurnable(this);
	}
	
	public ATLogBlock(Identifier identifier, Material material) {
		super(identifier, material);
		setTranslationKey(identifier.modID, identifier.id);
		setBlastResistance(0.5F);
		setHardness(1F);
	}
	
	@Override
	public void appendProperties(StateManager.Builder<BaseBlock, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(ATBlockProperties.AXIS);
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z, int facing) {
		Axis axis = BlocksUtil.fromFacing(facing).axis;
		BlockState state = getDefaultState().with(ATBlockProperties.AXIS, axis);
		BlocksUtil.setBlockState(level, x, y, z, state);
	}
}
