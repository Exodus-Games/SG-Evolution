package lc.blocks;

import java.util.List;
import java.util.Random;

import lc.LCRuntime;
import lc.api.components.ComponentType;
import lc.api.defs.Definition;
import lc.api.world.OreType;
import lc.common.base.LCBlock;
import lc.common.configuration.xml.ComponentConfig;
import lc.common.resource.ResourceAccess;
import lc.items.ItemBlockLanteaOre;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * LanteaCraft standard ore block implementation.
 *
 * @author AfterLifeLochie
 *
 */
@Definition(name = "blockLanteaOre", type = ComponentType.CORE, blockClass = BlockLanteaOre.class, itemBlockClass = ItemBlockLanteaOre.class)
public class BlockLanteaOre extends LCBlock {
	private IIcon missing;

	/** Default constructor */
	public BlockLanteaOre() {
		super(Material.ground);
		setHardness(5.0F);
		setResistance(10.0F);
		setStepSound(soundTypeStone);
		setHarvestLevel("pickaxe", 3);
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		missing = register.registerIcon(ResourceAccess.formatResourceName("${ASSET_KEY}:missing"));
		OreType.NAQUADAH.setOreTexture(register.registerIcon(ResourceAccess.formatResourceName(
				"${ASSET_KEY}:%s_${TEX_QUALITY}", "naquadah_ore")));
		OreType.NAQUADRIAH.setOreTexture(register.registerIcon(ResourceAccess.formatResourceName(
				"${ASSET_KEY}:%s_${TEX_QUALITY}", "naquadriah_ore")));
		OreType.TRINIUM.setOreTexture(register.registerIcon(ResourceAccess.formatResourceName(
				"${ASSET_KEY}:%s_${TEX_QUALITY}", "trinium_ore")));
	}

	@Override
	public IIcon getIcon(int side, int data) {
		if (data > OreType.values().length)
			return missing;
		return OreType.values()[data].getOreTexture();
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
		return LCRuntime.runtime.items().lanteaOreItem.getItem();
	}

	@Override
	public int quantityDropped(Random rand) {
		return 2 + rand.nextInt(5);
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
