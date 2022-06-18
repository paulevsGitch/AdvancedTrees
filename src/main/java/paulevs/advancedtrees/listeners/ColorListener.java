package paulevs.advancedtrees.listeners;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.colour.block.BlockColours;
import net.modificationstation.stationapi.api.client.colour.item.ItemColours;
import net.modificationstation.stationapi.api.client.event.colour.block.BlockColoursRegisterEvent;
import net.modificationstation.stationapi.api.client.event.colour.item.ItemColoursRegisterEvent;
import paulevs.advancedtrees.trees.TreeClientUtil;
import paulevs.advancedtrees.trees.VanillaTrees;

@Environment(EnvType.CLIENT)
public class ColorListener {
	@EventListener
	public void registerBlockColors(BlockColoursRegisterEvent event) {
		BlockColours colors = event.getBlockColours();
		colors.registerColourProvider(TreeClientUtil.TREE_BIOME_COLOR_BLOCK, VanillaTrees.OAK_BLOCKS.getLeaves());
		colors.registerColourProvider(TreeClientUtil.SPRUCE_COLOR_BLOCK, VanillaTrees.SPRUCE_BLOCKS.getLeaves());
		colors.registerColourProvider(TreeClientUtil.BIRCH_COLOR_BLOCK, VanillaTrees.BIRCH_BLOCKS.getLeaves());
	}
	
	@EventListener
	public void registerItemColors(ItemColoursRegisterEvent event) {
		ItemColours colors = event.getItemColours();
		colors.register(TreeClientUtil.TREE_BIOME_COLOR_ITEM, VanillaTrees.OAK_BLOCKS.getLeaves());
		colors.register(TreeClientUtil.SPRUCE_COLOR_ITEM, VanillaTrees.SPRUCE_BLOCKS.getLeaves());
		colors.register(TreeClientUtil.BIRCH_COLOR_ITEM, VanillaTrees.BIRCH_BLOCKS.getLeaves());
	}
}
