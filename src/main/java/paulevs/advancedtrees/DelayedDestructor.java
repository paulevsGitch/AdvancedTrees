package paulevs.advancedtrees;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.BaseBlock;
import net.minecraft.block.BlockSounds;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.render.particle.DiggingParticle;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.block.States;
import net.modificationstation.stationapi.api.level.BlockStateView;
import paulevs.bhcore.storage.vector.Vec3I;
import paulevs.bhcore.util.ClientUtil;

import java.util.ArrayList;
import java.util.List;

public class DelayedDestructor {
	private static final boolean IS_CLIENT = FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
	private static List<PosInfo> positions = new ArrayList<>();
	private static Thread main = Thread.currentThread();
	private static Thread thread = new Thread(() -> {
		while (main.isAlive()) {
			int size = positions.size();
			for (int i = 0; i < size; i++) {
				PosInfo info = positions.get(i);
				
				synchronized (positions) {
					positions.remove(i);
				}
				size--;
				i--;
				
				Vec3I p = info.pos;
				Level level = info.level;
				
				if (level.isAreaLoaded(p.x, p.y, p.z, p.x, p.y, p.z)) {
					BlockStateView view = (BlockStateView) info.level;
					BlockState state = view.getBlockState(p.x, p.y, p.z);
					if (state.isAir()) continue;
					
					BaseBlock block = state.getBlock();
					
					synchronized (level) {
						view.setBlockState(p.x, p.y, p.z, States.AIR.get());
						level.updateAdjacentBlocks(p.x, p.y, p.z, 0);
						ClientUtil.updateBlock(level, p.x, p.y, p.z);
					}
					
					block.drop(level, p.x, p.y, p.z, 0);
					
					BlockSounds sound = block.sounds;
					level.playSound(
						p.x + 0.5,
						p.y + 0.5,
						p.z + 0.5,
						sound.getBreakSound(),
						sound.getVolume() * 0.5f,
						sound.getPitch() * 0.75f
					);
					
					if (IS_CLIENT) {
						int n = 4;
						ParticleManager manager = ClientUtil.getMinecraft().particleManager;
						for (int i2 = 0; i2 < n; ++i2) {
							for (int i3 = 0; i3 < n; ++i3) {
								for (int i4 = 0; i4 < n; ++i4) {
									double d = p.x + (i2 + 0.5) / n;
									double d2 = p.y + (i3 + 0.5) / n;
									double d3 = p.z + (i4 + 0.5) / n;
									int n2 = level.rand.nextInt(6);
									manager.addParticle(new DiggingParticle(
										level,
										d,
										d2,
										d3,
										d - p.x - 0.5,
										d2 - p.y - 0.5,
										d3 - p.z - 0.5,
										block,
										n2,
										0
									).applyColor(p.x, p.y, p.z));
								}
							}
						}
					}
				}
			}
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	});
	
	public static void add(Level level, Vec3I pos) {
		PosInfo info = new PosInfo(level, pos);
		synchronized (positions) {
			positions.add(info);
		}
	}
	
	private static class PosInfo {
		final Level level;
		final Vec3I pos;
		
		private PosInfo(Level level, Vec3I pos) {
			this.level = level;
			this.pos = pos;
		}
	}
	
	static {
		thread.start();
	}
}
