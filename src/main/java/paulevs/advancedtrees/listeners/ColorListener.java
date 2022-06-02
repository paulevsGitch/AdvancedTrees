package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.render.block.FoliageColour;
import net.modificationstation.stationapi.api.client.colour.block.BlockColourProvider;
import net.modificationstation.stationapi.api.client.colour.block.BlockColours;
import net.modificationstation.stationapi.api.client.colour.item.ItemColourProvider;
import net.modificationstation.stationapi.api.client.colour.item.ItemColours;
import net.modificationstation.stationapi.api.client.colour.world.BiomeColours;
import net.modificationstation.stationapi.api.client.event.colour.block.BlockColoursRegisterEvent;
import net.modificationstation.stationapi.api.client.event.colour.item.ItemColoursRegisterEvent;

public class ColorListener {
	@EventListener
	public void registerBlockColors(BlockColoursRegisterEvent event) {
		BlockColours colors = event.getBlockColours();
		BlockColourProvider provider = (state, world, pos, index) -> {
			if (world == null || pos == null)
				return FoliageColour.method_1083();
			else {
				return BiomeColours.getFoliageColour(world, pos);
			}
		};
		colors.registerColourProvider(provider, BlockListener.oakLeaves);
	}
	
	@EventListener
	public void registerItemColors(ItemColoursRegisterEvent event) {
		ItemColours colors = event.getItemColours();
		ItemColourProvider provider = (instance, index) -> FoliageColour.method_1083();
		colors.register(provider, BlockListener.oakLeaves);
	}
}
