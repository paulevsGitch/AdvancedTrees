package paulevs.advancedtrees.trees.structure;

import com.google.common.collect.ImmutableList;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.blocks.ATDynamicLogBlock;
import paulevs.advancedtrees.blocks.ATLeavesBlock;
import paulevs.advancedtrees.blocks.ATSaplingBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSaplingBlock;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class TreeInfo {
	private final Supplier<AdvancedTreeStructure> structure;
	private final List<ItemConvertible> availableItems;
	private final ATSpawnerSaplingBlock spawnerSapling;
	private final ATDynamicLogBlock logDynamic;
	private final ATStaticLogBlock logStatic;
	private final ATLeavesBlock leaves;
	private final ATSaplingBlock sapling;
	private final Identifier id;
	
	public TreeInfo(Identifier id, ATStaticLogBlock logStatic, ATDynamicLogBlock logDynamic, ATLeavesBlock leaves, ATSaplingBlock sapling, ATSpawnerSaplingBlock spawnerSapling, Supplier<AdvancedTreeStructure> structure) {
		this.spawnerSapling = spawnerSapling;
		this.logDynamic = logDynamic;
		this.structure = structure;
		this.logStatic = logStatic;
		this.sapling = sapling;
		this.leaves = leaves;
		this.id = id;
		
		List<ItemConvertible> items = new ArrayList<>();
		items.add(sapling);
		items.add(spawnerSapling);
		availableItems = ImmutableList.copyOf(items);
	}
	
	public AdvancedTreeStructure getStructure() {
		return structure.get();
	}
	
	public ATStaticLogBlock getLogStatic() {
		return logStatic;
	}
	
	public ATDynamicLogBlock getLogDynamic() {
		return logDynamic;
	}
	
	public ATLeavesBlock getLeaves() {
		return leaves;
	}
	
	public ATSaplingBlock getSapling() {
		return sapling;
	}
	
	public ATSpawnerSaplingBlock getSpawnerSapling() {
		return spawnerSapling;
	}
	
	public List<ItemConvertible> getAvailableItems() {
		return availableItems;
	}
	
	public Identifier getID() {
		return id;
	}
	
	public static TreeInfo makeSimpleTree(Identifier id, int minAge, int maxAge, Supplier<TreeBehaviour> behaviour) {
		ATStaticLogBlock.setAgeLimits(minAge, maxAge);
		ATStaticLogBlock logStatic = new ATStaticLogBlock(Identifier.of(id.modID, id.id + "_log_static"));
		ATDynamicLogBlock logDynamic = new ATDynamicLogBlock(Identifier.of(id.modID, id.id + "_log_dynamic"), minAge, behaviour);
		ATLeavesBlock leaves = new ATLeavesBlock(Identifier.of(id.modID, id.id + "_leaves"));
		ATSaplingBlock sapling = new ATSaplingBlock(Identifier.of(id.modID, id.id + "_sapling"), logDynamic, leaves);
		AdvancedTreeStructure tree = new AdvancedTreeStructure(logDynamic, sapling::canPlaceAt);
		Supplier<AdvancedTreeStructure> structure = () -> tree;
		ATSpawnerSaplingBlock spawnerSapling = new ATSpawnerSaplingBlock(Identifier.of(id.modID, id.id + "_spawner_sapling"), structure);
		return new TreeInfo(id, logStatic, logDynamic, leaves, sapling, spawnerSapling, structure);
	}
}
