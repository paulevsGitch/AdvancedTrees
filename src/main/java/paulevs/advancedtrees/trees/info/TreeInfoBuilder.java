package paulevs.advancedtrees.trees.info;

import net.minecraft.block.BaseBlock;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.blocks.ATDynamicLogBlock;
import paulevs.advancedtrees.blocks.ATLeavesBlock;
import paulevs.advancedtrees.blocks.ATLogBlock;
import paulevs.advancedtrees.blocks.ATSaplingBlock;
import paulevs.advancedtrees.blocks.ATSpawnerSaplingBlock;
import paulevs.advancedtrees.blocks.ATStaticLogBlock;
import paulevs.advancedtrees.blocks.ATStemBlock;
import paulevs.advancedtrees.blocks.CactusDynamicLogBlock;
import paulevs.advancedtrees.blocks.CactusLogBlock;
import paulevs.advancedtrees.blocks.CactusSpawnerSaplingBlock;
import paulevs.advancedtrees.blocks.CactusStaticLogBlock;
import paulevs.advancedtrees.blocks.CactusStemBlock;

public class TreeInfoBuilder {
	private static final TreeInfoBuilder INSTANCE = new TreeInfoBuilder();
	private ATSpawnerSaplingBlock spawnerSapling;
	private ATDynamicLogBlock logDynamic;
	private ATStaticLogBlock logStatic;
	private ATSaplingBlock sapling;
	private ATStemBlock stemBlock;
	private int saplingDropChance;
	private ATLeavesBlock leaves;
	private BaseBlock log;
	private Identifier id;
	private int minAge;
	private int maxAge;
	
	private TreeInfoBuilder() {}
	
	/**
	 * Start a process of building new {@link TreeBlockSet} instance.
	 * @param id {@link Identifier} that tree will use
	 * @return {@link TreeInfoBuilder} instance.
	 */
	public static TreeInfoBuilder start(Identifier id, int minAge, int maxAge) {
		INSTANCE.spawnerSapling = null;
		INSTANCE.logDynamic = null;
		INSTANCE.logStatic = null;
		INSTANCE.stemBlock = null;
		INSTANCE.sapling = null;
		INSTANCE.leaves = null;
		INSTANCE.log = null;
		INSTANCE.minAge = minAge;
		INSTANCE.maxAge = maxAge;
		INSTANCE.id = id;
		return INSTANCE;
	}
	
	private Identifier makeID(String name) {
		return Identifier.of(id.modID, id.id + "_" + name);
	}
	
	/**
	 * Add normal (full) log block to the {@link TreeBlockSet}.
	 * @param log {@link BaseBlock} to add as log
	 * @return same {@link TreeInfoBuilder} instance
	 */
	public TreeInfoBuilder addLog(BaseBlock log) {
		this.log = log;
		return this;
	}
	
	/**
	 * Add simple log block to the tree. Will call {@code addLogBlock(BaseBlock log)} with
	 * {@link ATLogBlock} instance as an argument.
	 * @return same {@link TreeInfoBuilder} instance
	 */
	public TreeInfoBuilder addSimpleLog() {
		return addLog(new ATLogBlock(makeID("log")));
	}
	
	/**
	 * Add cactus log block to the tree. Will call {@code addLogBlock(BaseBlock log)} with
	 * {@link CactusLogBlock} instance as an argument.
	 * @return same {@link TreeInfoBuilder} instance
	 */
	public TreeInfoBuilder addCactusLog() {
		return addLog(new CactusLogBlock(makeID("log")));
	}
	
	public TreeInfoBuilder addStaticLog(ATStaticLogBlock logStatic) {
		this.logStatic = logStatic;
		return this;
	}
	
	public TreeInfoBuilder addSimpleStaticLog() {
		ATStaticLogBlock.setAgeLimits(minAge, maxAge);
		return addStaticLog(new ATStaticLogBlock(makeID("log_static")));
	}
	
	public TreeInfoBuilder addCactusStaticLog() {
		ATStaticLogBlock.setAgeLimits(minAge, maxAge);
		return addStaticLog(new CactusStaticLogBlock(makeID("log_static")));
	}
	
	public TreeInfoBuilder addDynamicLog(ATDynamicLogBlock logDynamic) {
		this.logDynamic = logDynamic;
		return this;
	}
	
	public TreeInfoBuilder addSimpleDynamicLog() {
		return addDynamicLog(new ATDynamicLogBlock(makeID("log_dynamic"), minAge));
	}
	
	public TreeInfoBuilder addCactusDynamicLog() {
		return addDynamicLog(new CactusDynamicLogBlock(makeID("log_dynamic"), minAge));
	}
	
	public TreeInfoBuilder addStem(ATStemBlock stemBlock) {
		this.stemBlock = stemBlock;
		return this;
	}
	
	public TreeInfoBuilder addSimpleStem() {
		return addStem(new ATStemBlock(makeID("stem")));
	}
	
	public TreeInfoBuilder addCactusStem() {
		return addStem(new CactusStemBlock(makeID("stem")));
	}
	
	public TreeInfoBuilder addLeaves(ATLeavesBlock leaves) {
		this.leaves = leaves;
		return this;
	}
	
	public TreeInfoBuilder addSimpleLeaves(int dropChance) {
		this.saplingDropChance = dropChance;
		return addLeaves(new ATLeavesBlock(makeID("leaves")));
	}
	
	public TreeInfoBuilder addSapling(ATSaplingBlock sapling) {
		this.sapling = sapling;
		return this;
	}
	
	public TreeInfoBuilder addSimpleSapling() {
		return addSapling(new ATSaplingBlock(makeID("sapling")));
	}
	
	public TreeInfoBuilder addSpawnerSapling(ATSpawnerSaplingBlock spawnerSapling) {
		this.spawnerSapling = spawnerSapling;
		return this;
	}
	
	public TreeInfoBuilder addSimpleSpawnerSapling() {
		return addSpawnerSapling(new ATSpawnerSaplingBlock(makeID("spawner_sapling")));
	}
	
	public TreeInfoBuilder addCactusSpawnerSapling() {
		return addSpawnerSapling(new CactusSpawnerSaplingBlock(makeID("spawner_sapling")));
	}
	
	public TreeBlockSet build() {
		if (logStatic != null) logStatic.setDrop(stemBlock);
		if (logDynamic != null) logDynamic.setDrop(stemBlock);
		if (leaves != null) leaves.setDrop(sapling, saplingDropChance);
		if (sapling != null) sapling.setLogAndLeaves(logDynamic, leaves);
		TreeBlockSet info = new TreeBlockSet(id, logStatic, logDynamic, log, stemBlock, leaves, sapling, spawnerSapling);
		if (logDynamic != null) logDynamic.setTree(info);
		return info;
	}
	
	public static TreeBlockSet simpleTree(Identifier id, int minAge, int maxAge, int saplingDropChance) {
		return start(id, minAge, maxAge)
			.addSimpleLog()
			.addSimpleStem()
			.addSimpleStaticLog()
			.addSimpleDynamicLog()
			.addSimpleSapling()
			.addSimpleSpawnerSapling()
			.addSimpleLeaves(saplingDropChance)
			.build();
	}
	
	public static TreeBlockSet cactusTree(Identifier id, int minAge, int maxAge) {
		return start(id, minAge, maxAge)
			.addCactusLog()
			.addCactusStem()
			.addCactusStaticLog()
			.addCactusDynamicLog()
			.addCactusSpawnerSapling()
			.build();
	}
}
