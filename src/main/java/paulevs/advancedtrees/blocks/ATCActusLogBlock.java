package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.util.math.Direction.Axis;

public class ATCActusLogBlock extends ATStemBlock {
	public ATCActusLogBlock(Identifier identifier) {
		super(identifier);
	}
	
	public ATCActusLogBlock(Identifier identifier, Material material) {
		super(identifier, material);
	}
	
	@Override
	protected void updateBox(Axis axis) {
		BOUNDING_BOX.method_99(0.0625F, 0.0625F, 0.0625F, 0.9375F, 0.9375F, 0.9375F);
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
