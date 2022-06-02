package paulevs.advancedtrees.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;

import java.util.function.Supplier;

public class CactusDynamicLogBlock extends ATDynamicLogBlock {
	public CactusDynamicLogBlock(Identifier identifier, int age, Supplier<TreeBehaviour> behaviourSupplier) {
		this(identifier, Material.WOOL, age, behaviourSupplier);
		setHardness(0.5F);
	}
	
	public CactusDynamicLogBlock(Identifier identifier, Material material, int age, Supplier<TreeBehaviour> behaviourSupplier) {
		super(identifier, material, age, behaviourSupplier);
	}
	
	@Environment(value= EnvType.CLIENT)
	public int getRenderPass() {
		return 1;
	}
}