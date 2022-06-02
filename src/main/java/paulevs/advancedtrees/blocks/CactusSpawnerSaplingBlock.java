package paulevs.advancedtrees.blocks;

import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.trees.structure.AdvancedTreeStructure;
import paulevs.bhcore.util.BlocksUtil;

import java.util.function.Supplier;

public class CactusSpawnerSaplingBlock extends ATSpawnerSaplingBlock {
	public CactusSpawnerSaplingBlock(Identifier identifier, Supplier<AdvancedTreeStructure> structureSupplier) {
		super(identifier, structureSupplier);
	}
	
	public CactusSpawnerSaplingBlock(Identifier identifier, Material material, Supplier<AdvancedTreeStructure> structureSupplier) {
		super(identifier, material, structureSupplier);
	}
	
	@Override
	public boolean canPlaceAt(Level level, int x, int y, int z) {
		BlockState state = BlocksUtil.getBlockState(level, x, y, z);
		if (!state.getMaterial().isReplaceable()) return false;
		state = BlocksUtil.getBlockState(level, x, y - 1, z);
		BlockBase block = state.getBlock();
		return block == BlockBase.SAND;
	}
}
