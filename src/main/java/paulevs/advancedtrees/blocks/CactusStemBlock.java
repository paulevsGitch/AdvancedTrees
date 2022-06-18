package paulevs.advancedtrees.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.entity.BaseEntity;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;

public class CactusStemBlock extends ATStemBlock {
	public CactusStemBlock(Identifier identifier) {
		this(identifier, Material.WOOL);
		setHardness(0.5F);
	}
	
	public CactusStemBlock(Identifier identifier, Material material) {
		super(identifier, material);
	}
	
	@Environment(value= EnvType.CLIENT)
	public int getRenderPass() {
		return 1;
	}
	
	@Override
	public void onEntityCollision(Level level, int x, int y, int z, BaseEntity entity) {
		entity.damage(null, 1);
	}
}
