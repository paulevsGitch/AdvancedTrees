package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.tileentities.TreeTileEntity;
import paulevs.advancedtrees.trees.TreeContext;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.bhcore.util.ToolsUtil;

import java.util.Random;
import java.util.function.Supplier;

public class ATDynamicLogBlock extends ATLoglikeBlock {
	private final Supplier<TreeBehaviour> behaviourSupplier;
	private final int age;
	
	public ATDynamicLogBlock(Identifier identifier, int age, Supplier<TreeBehaviour> behaviourSupplier) {
		this(identifier, Material.WOOD, age, behaviourSupplier);
		ToolsUtil.setAxe(this, 0);
	}
	
	public ATDynamicLogBlock(Identifier identifier, Material material, int age, Supplier<TreeBehaviour> behaviourSupplier) {
		super(identifier, material);
		setTicksRandomly(true);
		this.age = age;
		this.behaviourSupplier = behaviourSupplier;
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random random) {
		TreeContext treeContext = TreeContext.getInstance();
		treeContext.update(level, x, y, z);
		if (treeContext.isValid()) {
			behaviourSupplier.get().grow(treeContext);
			treeContext.restoreEntity();
		}
	}
	
	@Override
	public int getAge(BlockState state) {
		return age;
	}
	
	public void createEntity(Level level, int x, int y, int z) {
		int age = behaviourSupplier.get().getAge(level.rand);
		TreeTileEntity treeTileEntity = new TreeTileEntity(age);
		level.setTileEntity(x, y, z, treeTileEntity);
	}
}
