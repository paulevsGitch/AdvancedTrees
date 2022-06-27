package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;

public class CactusLogBlock extends ATStemBlock {
	public CactusLogBlock(Identifier identifier) {
		super(identifier);
		setSounds(WOOL_SOUNDS);
	}
	
	public CactusLogBlock(Identifier identifier, Material material) {
		super(identifier, material);
	}
	
	@Override
	protected void updateBox(Axis axis) {
		BOUNDING_BOX.set(0.0625F, 0.0625F, 0.0625F, 0.9375F, 0.9375F, 0.9375F);
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
