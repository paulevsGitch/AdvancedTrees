package paulevs.advancedtrees.tileentities;

import net.minecraft.block.entity.BaseBlockEntity;
import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.api.registry.Identifier;
import paulevs.advancedtrees.trees.VanillaTrees;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviour;
import paulevs.advancedtrees.trees.behaviour.TreeBehaviourRegistry;
import paulevs.bhcore.interfaces.IndependentTileEntity;

import java.util.Optional;

public class TreeBlockEntity extends BaseBlockEntity implements IndependentTileEntity {
	private TreeBehaviour behaviour;
	private int maxAge;
	
	public TreeBlockEntity() {
		this(8, VanillaTrees.OAK_BEHAVIOUR);
	}
	
	public TreeBlockEntity(int maxAge, TreeBehaviour behaviour) {
		this.maxAge = maxAge;
		this.behaviour = behaviour;
	}
	
	public int getMaxAge() {
		return maxAge;
	}
	
	@Override
	public void readIdentifyingData(CompoundTag arg) {
		super.readIdentifyingData(arg);
		maxAge = arg.getShort("maxAge");
		String id = arg.getString("behaviour");
		if (!id.isEmpty()) {
			Optional<TreeBehaviour> optional = TreeBehaviourRegistry.INSTANCE.get(Identifier.of(id));
			if (optional.isPresent()) behaviour = optional.get();
		}
	}
	
	@Override
	public void writeIdentifyingData(CompoundTag arg) {
		super.writeIdentifyingData(arg);
		arg.put("maxAge", (short) maxAge);
		arg.put("behaviour", TreeBehaviourRegistry.INSTANCE.getIdentifier(behaviour).toString());
	}
	
	@Override
	public boolean isInvalid() {
		return false;
	}
	
	public TreeBehaviour getBehaviour() {
		return behaviour;
	}
}
