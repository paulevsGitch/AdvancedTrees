package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.minecraft.level.LightType;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.trees.structure.AdvancedTreeStructure;

public class ATSpawnerSaplingBlock extends ATSaplingLikeBlock {
	private AdvancedTreeStructure structure;
	
	public ATSpawnerSaplingBlock(Identifier identifier) {
		super(identifier);
	}
	
	public ATSpawnerSaplingBlock(Identifier identifier, Material material) {
		super(identifier, material);
	}
	
	public void setStructure(AdvancedTreeStructure structure) {
		this.structure = structure;
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		structure.generate(level, level.rand, x, y, z);
		// level.updateLight(LightType.SKY, x - 16, y - 16, z - 16, x + 16, y + 64, z + 16, false);
		// level.updateLight(LightType.BLOCK, x - 16, y - 16, z - 16, x + 16, y + 64, z + 16, false);
	}
}
