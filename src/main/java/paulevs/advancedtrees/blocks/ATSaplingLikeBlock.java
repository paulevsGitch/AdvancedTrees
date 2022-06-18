package paulevs.advancedtrees.blocks;

import net.minecraft.block.BaseBlock;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.minecraft.util.maths.Box;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.bhcore.util.BlocksUtil;

public class ATSaplingLikeBlock extends ATTemplateNotFullBlock {
	public ATSaplingLikeBlock(Identifier identifier) {
		this(identifier, Material.PLANT);
		setSounds(GRASS_SOUNDS);
	}
	
	public ATSaplingLikeBlock(Identifier identifier, Material material) {
		super(identifier, material);
		setTicksRandomly(true);
		float f = 0.4f;
		this.setBoundingBox(0.5f - f, 0.0f, 0.5f - f, 0.5f + f, f * 2.0f, 0.5f + f);
	}
	
	protected boolean canStay(Level level, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(level, x, y - 1, z);
		BaseBlock block = state.getBlock();
		return block == BaseBlock.GRASS || block == BaseBlock.DIRT || block == BaseBlock.FARMLAND;
	}
	
	@Override
	public Box getCollisionShape(Level arg, int i, int j, int k) {
		return null;
	}
	
	@Override
	public void onAdjacentBlockUpdate(Level level, int x, int y, int z, int face) {
		super.onAdjacentBlockUpdate(level, x, y, z, face);
		if (!canStay(level, x, y, z)) {
			this.drop(level, x, y, z, level.getBlockMeta(x, y, z));
			level.setBlock(x, y, z, 0);
		}
	}
	
	@Override
	public boolean canPlaceAt(Level level, int x, int y, int z) {
		if (!super.canPlaceAt(level, x, y, z)) return false;
		return canStay(level, x, y, z);
	}
}
