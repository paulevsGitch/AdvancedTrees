package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;

public class ATTemplateNotFullBlock extends TemplateBlockBase {
	public ATTemplateNotFullBlock(Identifier identifier, Material material) {
		super(identifier, material);
	}
	
	@Override
	public boolean isFullOpaque() {
		return false;
	}
	
	@Override
	public boolean isFullCube() {
		return false;
	}
}
