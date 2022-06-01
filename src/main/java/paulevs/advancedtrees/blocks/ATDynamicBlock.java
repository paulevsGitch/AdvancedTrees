package paulevs.advancedtrees.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.trees.TreeBehaviour;
import paulevs.bhcore.util.ToolsUtil;

import java.util.Random;

public class ATDynamicBlock extends ATLoglikeBlock {
	private final TreeBehaviour behaviour;
	private final int age;
	
	public ATDynamicBlock(Identifier identifier, int age, TreeBehaviour behaviour) {
		this(identifier, Material.WOOD, age, behaviour);
		ToolsUtil.setAxe(this, 0);
	}
	
	public ATDynamicBlock(Identifier identifier, Material material, int age, TreeBehaviour behaviour) {
		super(identifier, material);
		setTicksRandomly(true);
		this.behaviour = behaviour;
		this.age = age;
	}
	
	@Override
	public void onScheduledTick(Level level, int x, int y, int z, Random random) {
		System.out.println("Tick!");
	}
	
	@Override
	public int getAge(BlockState state) {
		return age;
	}
}
