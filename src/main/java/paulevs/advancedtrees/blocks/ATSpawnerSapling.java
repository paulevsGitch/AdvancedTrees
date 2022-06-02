package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.trees.structure.AdvancedTreeStructure;

public class ATSpawnerSapling extends ATTemplateNotFullBlock {
	private AdvancedTreeStructure structure;
	
	public ATSpawnerSapling(Identifier identifier, AdvancedTreeStructure structure) {
		super(identifier, Material.PLANT);
		setSounds(GRASS_SOUNDS);
		this.structure = structure;
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		structure.generate(level, level.rand, x, y, z);
	}
}
