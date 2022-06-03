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
		colors.registerColourProvider(TreeClientUtil.getBiomeColorBlock(), VanillaTrees.OAK.getLeaves());
		colors.registerColourProvider(TreeClientUtil.getSpruceColorBlock(), VanillaTrees.SPRUCE.getLeaves());
	}
	
	@EventListener
	public void registerItemColors(ItemColoursRegisterEvent event) {
		ItemColours colors = event.getItemColours();
		colors.register(TreeClientUtil.getBiomeColorItem(), VanillaTrees.OAK.getLeaves());
		colors.register(TreeClientUtil.getSpruceColorItem(), VanillaTrees.SPRUCE.getLeaves());
	}
}
