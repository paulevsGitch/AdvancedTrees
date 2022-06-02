package paulevs.advancedtrees.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;

public class CactusStaticLogBlock extends ATStaticLogBlock {
	public CactusStaticLogBlock(Identifier identifier) {
		this(identifier, Material.WOOL);
		setHardness(0.5F);
	}
	
	public CactusStaticLogBlock(Identifier identifier, Material material) {
		super(identifier, material);
	}
	
	@Environment(value= EnvType.CLIENT)
	public int getRenderPass() {
		return 1;
	}
}
