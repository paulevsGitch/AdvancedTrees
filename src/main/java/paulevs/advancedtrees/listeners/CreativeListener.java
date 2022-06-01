package paulevs.advancedtrees.listeners;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import paulevs.advancedtrees.AdvancedTrees;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class CreativeListener {
	public static CreativeTab tab;
	
	@EventListener
	public void onTabInit(TabRegistryEvent event) {
		tab = new SimpleTab(AdvancedTrees.makeID("at_tab"), (ItemConvertible) ItemBase.apple);
		event.register(tab);
		BlockListener.modBlocks.forEach(block -> CreativeListener.tab.addItem(new ItemInstance(block)));
	}
}
