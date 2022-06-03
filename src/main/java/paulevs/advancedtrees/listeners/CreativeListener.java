package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemInstance;
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
		tab = new SimpleTab(AdvancedTrees.makeID("at_tab"), (ItemConvertible) BlockBase.LOG);
		event.register(tab);
		addItems(VanillaTrees.OAK.getAvailableItems());
		addItems(VanillaTrees.SPRUCE.getAvailableItems());
		addItems(VanillaTrees.CACTUS.getAvailableItems());
	}
	
	private void addItems(List<ItemConvertible> items) {
		items.forEach(item -> CreativeListener.tab.addItem(new ItemInstance(item.asItem())));
	}
}
