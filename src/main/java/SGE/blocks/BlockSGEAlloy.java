package SGE.blocks;

import java.util.List;

import SGE.api.components.ComponentType;
import SGE.api.defs.Definition;
import SGE.api.world.OreType;
import SGE.common.base.SGEBlock;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.common.resource.ResourceAccess;
import SGE.items.ItemBlockSGEAlloy;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * SG Evolution standard ore block implementation.
 *
 * @author Exodus Games
 *
 */
@Definition(name = "blockLanteaAlloy", type = ComponentType.CORE, blockClass = BlockSGEAlloy.class, itemBlockClass = ItemBlockSGEAlloy.class)
public class BlockSGEAlloy extends SGEBlock {
	private IIcon missing;

	/** Default constructor */
	public BlockLanteaAlloy() {
		super(Material.ground);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(soundTypeStone);
		setHarvestLevel("pickaxe", 3);
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		missing = register.registerIcon(ResourceAccess.formatResourceName("${ASSET_KEY}:missing"));
		OreType.NAQUADAH.setItemAsBlockTexture(register.registerIcon(ResourceAccess.formatResourceName(
				"${ASSET_KEY}:%s_${TEX_QUALITY}", "naquadah_block")));
		OreType.NAQUADRIAH.setItemAsBlockTexture(register.registerIcon(ResourceAccess.formatResourceName(
				"${ASSET_KEY}:%s_${TEX_QUALITY}", "naquadriah_block")));
		OreType.TRINIUM.setItemAsBlockTexture(register.registerIcon(ResourceAccess.formatResourceName(
				"${ASSET_KEY}:%s_${TEX_QUALITY}", "trinium_block")));
	}

	@Override
	public IIcon getIcon(int side, int data) {
		if (data > OreType.values().length)
			return missing;
		return OreType.values()[data].getItemAsBlockTexture();
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < OreType.values().length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub
		
	}
}
