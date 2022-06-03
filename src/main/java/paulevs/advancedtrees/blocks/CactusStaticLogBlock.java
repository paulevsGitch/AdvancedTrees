package paulevs.advancedtrees.blocks;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityBase;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.util.function.Supplier;

public class CactusStaticLogBlock extends ATStaticLogBlock {
	public CactusStaticLogBlock(Identifier identifier, Supplier<ItemConvertible> drop) {
		this(identifier, Material.WOOL, drop);
		setSounds(WOOL_SOUNDS);
		setHardness(0.5F);
	}
	
	public CactusStaticLogBlock(Identifier identifier, Material material, Supplier<ItemConvertible> drop) {
		super(identifier, material, drop);
	}
	
	@Environment(value= EnvType.CLIENT)
	public int getRenderPass() {
		return 1;
	}
	
	@Override
	protected boolean isSupport(BlockState state) {
		return state.getBlock() instanceof ATLoglikeBlock || state.getBlock() == SAND;
	}
	
	@Override
	public void onEntityCollision(Level level, int x, int y, int z, EntityBase entity) {
		entity.damage(null, 1);
	}
}
