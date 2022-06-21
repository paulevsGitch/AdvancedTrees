package paulevs.advancedtrees.trees;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.block.FoliageColour;
import net.minecraft.util.maths.BlockPos;
import net.modificationstation.stationapi.api.client.colour.block.BlockColourProvider;
import net.modificationstation.stationapi.api.client.colour.item.ItemColourProvider;
import net.modificationstation.stationapi.api.client.colour.world.BiomeColours;
import paulevs.bhcore.noise.OpenSimplexNoise;

import java.awt.Color;

@Environment(EnvType.CLIENT)
public class TreeClientUtil {
	private static final OpenSimplexNoise NOISE_HUE = new OpenSimplexNoise("foliage_hue".hashCode());
	private static final OpenSimplexNoise NOISE_VAL = new OpenSimplexNoise("foliage_val".hashCode());
	
	public static final BlockColourProvider TREE_BIOME_COLOR_BLOCK = (state, world, pos, index) -> {
		if (world == null || pos == null) return FoliageColour.getOakColor();
		else return mutateColor(BiomeColours.getFoliageColour(world, pos), pos);
	};
	public static final BlockColourProvider SPRUCE_COLOR_BLOCK = (state, world, pos, index) -> {
		int rgb = FoliageColour.getSpruceColor();
		if (world == null || pos == null) return rgb;
		else return mutateColor(rgb, pos);
	};
	public static final BlockColourProvider BIRCH_COLOR_BLOCK = (state, world, pos, index) -> {
		int rgb = FoliageColour.getBirchColor();
		if (world == null || pos == null) return rgb;
		else return mutateColor(rgb, pos);
	};
	
	public static final ItemColourProvider TREE_BIOME_COLOR_ITEM = (instance, index) -> FoliageColour.getOakColor();
	public static final ItemColourProvider SPRUCE_COLOR_ITEM = (instance, index) -> FoliageColour.getSpruceColor();
	public static final ItemColourProvider BIRCH_COLOR_ITEM = (instance, index) -> FoliageColour.getBirchColor();
	
	private static final float[] HSV_CACHE = new float[3];
	private static int mutateColor(int rgb, BlockPos pos) {
		int r = (rgb >> 16) & 255;
		int g = (rgb >> 8) & 255;
		int b = rgb & 255;
		Color.RGBtoHSB(r, g, b, HSV_CACHE);
		
		HSV_CACHE[0] += (float) NOISE_HUE.eval(pos.x * 0.1, pos.y * 0.1, pos.z * 0.1) * 0.05F;
		HSV_CACHE[2] += (float) NOISE_VAL.eval(pos.x * 0.1, pos.y * 0.1, pos.z * 0.1) * 0.2F;
		if (HSV_CACHE[2] > 1.0F) HSV_CACHE[2] = 1.0F;
		
		return Color.HSBtoRGB(HSV_CACHE[0], HSV_CACHE[1], HSV_CACHE[2]);
	}
}
