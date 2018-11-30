package SGE.client;

import SGE.SGERuntime;
import SGE.api.audio.ISoundController;
import SGE.api.defs.IContainerDefinition;
import SGE.api.defs.IRecipeDefinition;
import SGE.api.rendering.IParticleMachine;
import SGE.blocks.BlockBrazier;
import SGE.blocks.BlockConfigurator;
import SGE.blocks.BlockDHD;
import SGE.blocks.BlockSGEDoor;
import SGE.blocks.BlockObelisk;
import SGE.client.openal.ClientSoundController;
import SGE.client.opengl.ParticleMachine;
import SGE.client.render.fabs.blocks.BlockBrazierRenderer;
import SGE.client.render.fabs.blocks.BlockConfiguratorRenderer;
import SGE.client.render.fabs.blocks.BlockDHDRenderer;
import SGE.client.render.fabs.blocks.BlockDoorRenderer;
import SGE.client.render.fabs.blocks.BlockObeliskRenderer;
import SGE.client.render.fabs.entities.EntityStaffProjectileRenderer;
import SGE.client.render.fabs.items.ItemDecoratorRenderer;
import SGE.client.render.fabs.tiles.TileConfiguratorRenderer;
import SGE.client.render.fabs.tiles.TileDHDRenderer;
import SGE.client.render.fabs.tiles.TileDoorRenderer;
import SGE.client.render.fabs.tiles.TileStargateBaseRenderer;
import SGE.client.render.fabs.tiles.TileTransportRingRenderer;
import SGE.common.SGELog;
import SGE.common.base.SGEBlock;
import SGE.common.base.SGEItem;
import SGE.common.base.SGETile;
import SGE.common.base.pipeline.LCBlockRenderPipeline;
import SGE.common.base.pipeline.LCEntityRenderPipeline;
import SGE.common.base.pipeline.LCItemRenderPipeline;
import SGE.common.base.pipeline.LCTileRenderPipeline;
import SGE.common.impl.registry.DefinitionRegistry;
import SGE.entity.EntityStaffProjectile;
import SGE.items.ItemDecorator;
import SGE.server.HintProviderServer;
import SGE.tiles.TileConfigurator;
import SGE.tiles.TileDHD;
import SGE.tiles.TileSGEDoor;
import SGE.tiles.TileStargateBase;
import SGE.tiles.TileTransportRing;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

/**
 * Client-side hint provider implementation
 * 
 * @author Exodus Games
 * 
 */
public class HintProviderClient extends HintProviderServer {

	private SGEBlockRenderPipeline blockRenderingHook;
	private SGETileRenderPipeline tileRenderingHook;
	private SGEItemRenderPipeline itemRenderingHook;
	private SGEEntityRenderPipeline entityRenderingHook;

	private ClientSoundController soundController;
	private ParticleMachine particleMachine;

	/** Default constructor */
	public HintProviderClient() {
		super();
		SGELog.debug("HintProviderClient providing client-side hints");
	}

	@Override
	public void preInit() {
		super.preInit();
		blockRenderingHook = new SGEBlockRenderPipeline(
				RenderingRegistry.getNextAvailableRenderId());
		tileRenderingHook = new SGETileRenderPipeline();
		itemRenderingHook = new SGEItemRenderPipeline();
		entityRenderingHook = new SGEEntityRenderPipeline();
		soundController = new ClientSoundController();
		particleMachine = new ParticleMachine();
		RenderingRegistry.registerBlockHandler(
				blockRenderingHook.getRenderId(), blockRenderingHook);
	}

	@Override
	public void init() {
		super.init();

		DefinitionRegistry registry = (DefinitionRegistry) SGERuntime.runtime
				.registries().definitions();
		registry.registerTileRenderer(TileStargateBase.class,
				TileStargateBaseRenderer.class);
		registry.registerTileRenderer(TileTransportRing.class,
				TileTransportRingRenderer.class);
		registry.registerItemRenderer(ItemDecorator.class,
				ItemDecoratorRenderer.class);

		registry.registerBlockRenderer(BlockLanteaDoor.class,
				BlockDoorRenderer.class);
		registry.registerBlockRenderer(BlockObelisk.class,
				BlockObeliskRenderer.class);
		registry.registerBlockRenderer(BlockBrazier.class,
				BlockBrazierRenderer.class);
		registry.registerBlockRenderer(BlockConfigurator.class,
				BlockConfiguratorRenderer.class);
		registry.registerBlockRenderer(BlockDHD.class, BlockDHDRenderer.class);
		registry.registerTileRenderer(TileLanteaDoor.class,
				TileDoorRenderer.class);
		registry.registerTileRenderer(TileDHD.class, TileDHDRenderer.class);
		registry.registerTileRenderer(TileConfigurator.class,
				TileConfiguratorRenderer.class);

		registry.registerEntityRenderer(EntityStaffProjectile.class,
				EntityStaffProjectileRenderer.class);
	}

	@Override
	public void postInit() {
		super.postInit();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void provideHints(IContainerDefinition definition) {
		super.provideHints(definition);
		if (definition.getBlock() != null) {
			SGEBlock theBlock = (SGEBlock) definition.getBlock();
			theBlock.setRenderer(blockRenderingHook.getRenderId());
		}

		if (definition.getTileType() != null) {
			Class<? extends SGETile> theTile = (Class<? extends SGETile>) definition
					.getTileType();
			ClientRegistry.bindTileEntitySpecialRenderer(theTile,
					tileRenderingHook);
		}

		if (definition.getItem() != null
				&& definition.getItem() instanceof SGEItem) {
			SGEItem theItem = (SGEItem) definition.getItem();
			MinecraftForgeClient.registerItemRenderer(theItem,
					itemRenderingHook);
		}

		if (definition.getEntityType() != null) {
			Class<? extends Entity> theEntity = definition.getEntityType();
			RenderingRegistry.registerEntityRenderingHandler(theEntity,
					entityRenderingHook);
		}

	}

	@Override
	public void provideHints(IRecipeDefinition definition) {
		super.provideHints(definition);
		// TODO Auto-generated method stub

	}

	@Override
	public ISoundController audio() {
		return soundController;
	}

	@Override
	public IParticleMachine particles() {
		return particleMachine;
	}

}
