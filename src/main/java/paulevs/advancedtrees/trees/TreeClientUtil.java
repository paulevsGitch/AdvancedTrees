package paulevs.advancedtrees.trees;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.FoliageColour;
import net.modificationstation.stationapi.api.client.colour.block.BlockColourProvider;
import net.modificationstation.stationapi.api.client.colour.item.ItemColourProvider;
import net.modificationstation.stationapi.api.client.colour.world.BiomeColours;

@Environment(EnvType.CLIENT)
public class TreeClientUtil {
	private static final ItemColourProvider TREE_BIOME_COLOR_ITEM = (instance, index) -> FoliageColour.method_1083();
	private static final BlockColourProvider TREE_BIOME_COLOR_BLOCK = (state, world, pos, index) -> {
		if (world == null || pos == null)
			return FoliageColour.method_1083();
		else {
			return BiomeColours.getFoliageColour(world, pos);
		}
	};
	
	public static BlockColourProvider getBiomeColorBlock() {
		return TREE_BIOME_COLOR_BLOCK;
	}
	
	public static ItemColourProvider getBiomeColorItem() {
		return TREE_BIOME_COLOR_ITEM;
	}
}
