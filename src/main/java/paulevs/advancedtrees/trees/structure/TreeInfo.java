package paulevs.advancedtrees.trees.structure;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.BlockBase;
import net.modificationstation.stationapi.api.item.ItemConvertible;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.blocks.CactusLogBlock;
import paulevs.advancedtrees.blocks.ATDynamicLogBlock;
import paulevs.advancedtrees.blocks.ATLeavesBlock;
import paulevs.advancedtrees.blocks.ATLogBlock;
import paulevs.advancedtrees.blocks.ATSaplingBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSaplingBlock;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.blocks.ATStemBlock;
import paulevs.advancedtrees.blocks.CactusDynamicLogBlock;
import paulevs.advancedtrees.blocks.CactusSpawnerSaplingBlock;
import paulevs.advancedtrees.blocks.CactusStaticLogBlock;
import paulevs.advancedtrees.blocks.CactusStemBlock;
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
	private final ATStemBlock stem;
	private final BlockBase log;
	private final Identifier id;
	
	public TreeInfo(Identifier id, ATStaticLogBlock logStatic, ATDynamicLogBlock logDynamic, BlockBase log, ATStemBlock stem, ATLeavesBlock leaves, ATSaplingBlock sapling, ATSpawnerSaplingBlock spawnerSapling, Supplier<AdvancedTreeStructure> structure) {
		this.spawnerSapling = spawnerSapling;
		this.logDynamic = logDynamic;
		this.structure = structure;
		this.logStatic = logStatic;
		this.sapling = sapling;
		this.leaves = leaves;
		this.stem = stem;
		this.log = log;
		this.id = id;
		
		List<ItemConvertible> items = new ArrayList<>();
		if (sapling != null) items.add(sapling);
		items.add(spawnerSapling);
		items.add(stem);
		items.add((ItemConvertible) log);
		if (leaves != null) items.add(leaves);
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
		ATStemBlock stemBlock = new ATStemBlock(Identifier.of(id.modID, id.id + "_stem"));
		ATStaticLogBlock logStatic = new ATStaticLogBlock(Identifier.of(id.modID, id.id + "_log_static"), () -> stemBlock);
		ATDynamicLogBlock logDynamic = new ATDynamicLogBlock(Identifier.of(id.modID, id.id + "_log_dynamic"), minAge, behaviour);
		ATSaplingBlock[] sapling = new ATSaplingBlock[1];
		ATLeavesBlock leaves = new ATLeavesBlock(Identifier.of(id.modID, id.id + "_leaves"), () -> sapling[0], 64);
		sapling[0] = new ATSaplingBlock(Identifier.of(id.modID, id.id + "_sapling"), logDynamic, leaves);
		AdvancedTreeStructure tree = new AdvancedTreeStructure(logDynamic, sapling[0]::canPlaceAt);
		Supplier<AdvancedTreeStructure> structure = () -> tree;
		ATSpawnerSaplingBlock spawnerSapling = new ATSpawnerSaplingBlock(Identifier.of(id.modID, id.id + "_spawner_sapling"), structure);
		ATLogBlock log = new ATLogBlock(Identifier.of(id.modID, id.id + "_log"));
		return new TreeInfo(id, logStatic, logDynamic, log, stemBlock, leaves, sapling[0], spawnerSapling, structure);
	}
	
	public static TreeInfo makeLeaflessTree(Identifier id, int minAge, int maxAge, Supplier<TreeBehaviour> behaviour) {
		ATStaticLogBlock.setAgeLimits(minAge, maxAge);
		ATStemBlock stemBlock = new ATStemBlock(Identifier.of(id.modID, id.id + "_stem"));
		ATStaticLogBlock logStatic = new ATStaticLogBlock(Identifier.of(id.modID, id.id + "_log_static"), () -> stemBlock);
		ATDynamicLogBlock logDynamic = new ATDynamicLogBlock(Identifier.of(id.modID, id.id + "_log_dynamic"), minAge, behaviour);
		AdvancedTreeStructure tree = new AdvancedTreeStructure(logDynamic, logDynamic::canPlaceAt);
		Supplier<AdvancedTreeStructure> structure = () -> tree;
		ATSpawnerSaplingBlock spawnerSapling = new ATSpawnerSaplingBlock(Identifier.of(id.modID, id.id + "_spawner_sapling"), structure);
		ATLogBlock log = new ATLogBlock(Identifier.of(id.modID, id.id + "_log"));
		return new TreeInfo(id, logStatic, logDynamic, log, stemBlock, null, null, spawnerSapling, structure);
	}
	
	public static TreeInfo makeCactusTree(Identifier id, int minAge, int maxAge, Supplier<TreeBehaviour> behaviour) {
		ATStaticLogBlock.setAgeLimits(minAge, maxAge);
		ATStemBlock stemBlock = new CactusStemBlock(Identifier.of(id.modID, id.id + "_stem"));
		ATStaticLogBlock logStatic = new CactusStaticLogBlock(Identifier.of(id.modID, id.id + "_log_static"), () -> stemBlock);
		ATDynamicLogBlock logDynamic = new CactusDynamicLogBlock(Identifier.of(id.modID, id.id + "_log_dynamic"), minAge, behaviour);
		ATSpawnerSaplingBlock[] sapling = new ATSpawnerSaplingBlock[1];
		Supplier<AdvancedTreeStructure> structure = () -> new AdvancedTreeStructure(logDynamic, sapling[0]::canPlaceAt);
		sapling[0] = new CactusSpawnerSaplingBlock(Identifier.of(id.modID, id.id + "_spawner_sapling"), structure);
		CactusLogBlock log = new CactusLogBlock(Identifier.of(id.modID, id.id + "_log"));
		return new TreeInfo(id, logStatic, logDynamic, log, stemBlock, null, null, sapling[0], structure);
	}
}
