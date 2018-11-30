package SGE.common.base.generation.scattered;

import java.lang.reflect.Constructor;
import java.util.Random;

import SGE.SGERuntime;
import SGE.api.defs.IStructureDefinition;
import SGE.common.SGELog;
import SGE.common.impl.registry.StructureRegistry;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

/**
 * LanteaCraft scattered feature map generator class.
 *
 * @author Exodus Games
 *
 */
public final class SGEScatteredFeatureGenerator extends MapGenStructure {
	private final StructureRegistry registry;

	/** Default constructor */
	public SGEScatteredFeatureGenerator() {
		registry = (StructureRegistry) SGERuntime.runtime.registries().structures();
	}

	@Override
	public String func_143025_a() {
		return "LanteaCraft";
	}

	private IStructureDefinition findStructureStart(int x, int z) {
		IStructureDefinition[] defs = registry.allDefs(LCScatteredFeatureStart.class);
		for (IStructureDefinition def : defs)
			if (def.canGenerateAt(worldObj, rand, x, z))
				return def;
		return null;
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int par1, int par2) {
		return findStructureStart(par1, par2) != null;
	}

	@Override
	protected StructureStart getStructureStart(int cx, int cz) {
		IStructureDefinition def = findStructureStart(cx, cz);
		if (def != null)
			try {
				Class<? extends StructureStart> start = def.getStructureClass();
				Constructor<? extends StructureStart> ctr = start.getConstructor(World.class, Random.class, int.class,
						int.class);
				if (ctr != null)
					return ctr.newInstance(worldObj, rand, cx, cz);
			} catch (Throwable t) {
				LCLog.fatal("Couldn't initialize new structure start for type %s.", def.getName(), t);

			}
		return null;
	}

}
