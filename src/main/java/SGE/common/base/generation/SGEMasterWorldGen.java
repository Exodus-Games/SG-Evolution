package SGE.common.base.generation;

import java.util.Random;

import SGE.common.SGELog;
import SGE.common.base.generation.scattered.SGEScatteredFeatureGenerator;
import SGE.common.base.generation.structure.SGEFeatureGenerator;
import SGE.common.util.Tracer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.common.IWorldGenerator;

/**
 * SG Evolution world decorator hook.
 * 
 * @author Exodus Games
 *
 */
public class SGEMasterWorldGen implements IWorldGenerator {

	/** Scattered feature generator */
	protected final SGEScatteredFeatureGenerator scatteredGenerator = new SGEScatteredFeatureGenerator();
	/** Special feature generator */
	protected final SGEFeatureGenerator featureGenerator = new SGEFeatureGenerator();

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator,
			IChunkProvider chunkProvider) {
		/*
		 * Ask the master generators to generate all known structures. It
		 * doesn't matter if the Block[] array is null at this stage.
		 */
		try {
			Tracer.begin(this, "scattered feature generator allocation");
			scatteredGenerator.func_151539_a(chunkProvider, world, chunkX, chunkZ, null);
		} catch (Throwable t) {
			SGELog.warn("Problem populating scattered features for chunk.", t);
		} finally {
			Tracer.end();
		}
		try {
			Tracer.begin(this, "ordered feature generator allocation");
			featureGenerator.func_151539_a(chunkProvider, world, chunkX, chunkX, null);
		} catch (Throwable t) {
			SGELog.warn("Problem populating features for chunk.", t);
		} finally {
			Tracer.end();
		}

		/*
		 * Ask the mater generators to actually place the structures it has
		 * declared in the world.
		 */
		boolean flag = false;
		try {
			Tracer.begin(this, "scattered feature population");
			scatteredGenerator.generateStructuresInChunk(world, random, chunkX, chunkZ);
		} catch (Throwable t) {
			SGELog.warn("Failed to generate scattered structures.", t);
		} finally {
			Tracer.end();
		}
		if (!flag)
			try {
				Tracer.begin(this, "ordered feature population");
				flag = featureGenerator.generateStructuresInChunk(world, random, chunkX, chunkZ);
			} catch (Throwable t) {
				SGELog.warn("Failed to generate structures.", t);
			} finally {
				Tracer.end();
			}
	}
}
