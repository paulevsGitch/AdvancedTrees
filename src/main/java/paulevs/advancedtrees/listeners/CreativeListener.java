package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BaseBlock;
import net.minecraft.item.ItemStack;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import paulevs.advancedtrees.AdvancedTrees;
import paulevs.advancedtrees.trees.VanillaTrees;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

import java.util.List;

public class CreativeListener {
	public static CreativeTab tab;
	
	@EventListener
	public void onTabInit(TabRegistryEvent event) {
		tab = new SimpleTab(AdvancedTrees.makeID("at_tab"), (ItemConvertible) BaseBlock.LOG);
		event.register(tab);
		addItems(VanillaTrees.OAK_BLOCKS.getAvailableItems());
		addItems(VanillaTrees.SPRUCE_BLOCKS.getAvailableItems());
		addItems(VanillaTrees.BIRCH_BLOCKS.getAvailableItems());
		addItems(VanillaTrees.CACTUS_BLOCKS.getAvailableItems());
	}
	
	private void addItems(List<ItemConvertible> items) {
		items.forEach(item -> CreativeListener.tab.addItem(new ItemStack(item.asItem())));
	}
}
