package SGE.blocks;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import SGE.api.components.ComponentType;
import SGE.api.defs.Definition;
import SGE.api.world.OreType;
import SGE.common.base.SGEBlock;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.common.resource.ResourceAccess;
import SGE.items.ItemBlockBrazier;
import SGE.tiles.TileBrazier;

/**
 * Brazier block implementation
 * 
 * @author Exodus Games
 *
 */
@Definition(name = "blockBrazier", type = ComponentType.DECOR, blockClass = BlockBrazier.class, itemBlockClass = ItemBlockBrazier.class, tileClass = TileBrazier.class)
public class BlockBrazier extends SGEBlock {

	private IIcon missing;

	/** Default constructor */
	public BlockBrazier() {
		super(Material.ground);
		setLightLevel(1.0F);
		setProvidesTypes(true);
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
		missing = register.registerIcon(ResourceAccess.formatResourceName("${ASSET_KEY}:missing"));
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

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1.4, z + 1);
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		return getCollisionBoundingBoxFromPool(world, x, y, z);
	}

}
