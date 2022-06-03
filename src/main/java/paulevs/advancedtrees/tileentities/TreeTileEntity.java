package paulevs.advancedtrees.tileentities;

import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.io.CompoundTag;
import paulevs.bhcore.interfaces.IndependentTileEntity;

public class TreeTileEntity extends TileEntityBase implements IndependentTileEntity {
	private int maxAge;
	
	public TreeTileEntity() {}
	
	public TreeTileEntity(int maxAge) {
		this.maxAge = maxAge;
	}
	
	public int getMaxAge() {
		return maxAge;
	}
	
	@Override
	public void readIdentifyingData(CompoundTag arg) {
		super.readIdentifyingData(arg);
		maxAge = arg.getShort("maxAge");
	}
	
	@Override
	public void writeIdentifyingData(CompoundTag arg) {
		super.writeIdentifyingData(arg);
		arg.put("maxAge", (short) maxAge);
	}
	
	@Override
	public boolean isInvalid() {
		return false;
	}
}
