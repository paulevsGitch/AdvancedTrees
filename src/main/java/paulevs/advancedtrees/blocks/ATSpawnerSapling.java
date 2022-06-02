package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.trees.structure.AdvancedTreeStructure;

import java.util.function.Supplier;

public class ATSpawnerSapling extends ATTemplateNotFullBlock {
	private Supplier<AdvancedTreeStructure> structureSupplier;
	
	public ATSpawnerSapling(Identifier identifier, Supplier<AdvancedTreeStructure> structureSupplier) {
		super(identifier, Material.PLANT);
		setSounds(GRASS_SOUNDS);
		this.structureSupplier = structureSupplier;
	}
	
	@Override
	public void onBlockPlaced(Level level, int x, int y, int z) {
		structureSupplier.get().generate(level, level.rand, x, y, z);
	}
}
