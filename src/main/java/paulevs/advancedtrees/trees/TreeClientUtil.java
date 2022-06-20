package paulevs.advancedtrees.trees;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.FoliageColour;
import net.modificationstation.stationapi.api.client.colour.block.BlockColourProvider;
import net.modificationstation.stationapi.api.client.colour.item.ItemColourProvider;
import net.modificationstation.stationapi.api.client.colour.world.BiomeColours;

@Environment(EnvType.CLIENT)
public class TreeClientUtil {
	public static final BlockColourProvider TREE_BIOME_COLOR_BLOCK = (state, world, pos, index) -> {
		if (world == null || pos == null)
			return FoliageColour.getOakColor();
		else {
			return BiomeColours.getFoliageColour(world, pos);
		}
	};
	public static final BlockColourProvider SPRUCE_COLOR_BLOCK = (state, world, pos, index) -> FoliageColour.getSpruceColor();
	public static final BlockColourProvider BIRCH_COLOR_BLOCK = (state, world, pos, index) -> FoliageColour.getBirchColor();
	
	public static final ItemColourProvider TREE_BIOME_COLOR_ITEM = (instance, index) -> FoliageColour.getOakColor();
	public static final ItemColourProvider SPRUCE_COLOR_ITEM = (instance, index) -> FoliageColour.getSpruceColor();
	public static final ItemColourProvider BIRCH_COLOR_ITEM = (instance, index) -> FoliageColour.getBirchColor();
}
