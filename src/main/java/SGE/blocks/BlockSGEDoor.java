package SGE.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import SGE.SGERuntime;
import SGE.api.components.ComponentType;
import SGE.api.defs.Definition;
import SGE.common.base.SGEBlock;
import SGE.common.configuration.xml.ComponentConfig;
import SGE.items.ItemSGEDoor;
import SGE.tiles.TileSGEDoor;

/**
 * LanteaCraft door block implementation
 * 
 * @author Exodus Games
 *
 */
@Definition(name = "SGEDoor", type = ComponentType.DECOR, blockClass = BlockSGEDoor.class, itemBlockClass = ItemSGEDoor.class, tileClass = TileSGEDoor.class)
public class BlockSGEDoor extends SGEBlock {

	private static final int blockCount = 2;

	/** Default constructor */
	public BlockSGEDoor() {
		super(Material.ground);
		setOpaque(false).setProvidesInventory(false).setProvidesTypes(true).setCanRotate(true);
	}

	@Override
	public void registerBlockIcons(IIconRegister register) {
	}

	@Override
	public IIcon getIcon(int side, int metadata) {
		return null;
	}

	@Override
	public boolean canPlaceBlockAt(World w, int x, int y, int z) {
		if (w.getBlock(x, y - 1, z) == this)
			return true;
		if (!World.doesBlockHaveSolidTopSurface(w, x, y - 1, z))
			return false;
		return w.isAirBlock(x, y, z) || super.canPlaceBlockAt(w, x, y, z);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer p, int par6, float par7, float par8,
			float par9) {
		if (world.isRemote)
			return true;
		TileSGEDoor doorTile = (TileLanteaDoor) world.getTileEntity(x, y, z);
		if (doorTile == null)
			return true;
		doorTile.openOrCloseDoor();
		return true;
	}

	public Item getItemDropped(int metadata, Random random, int fortune) {
		return SGERuntime.runtime.blocks().SGEDoor.getItem();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < blockCount; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	/**
	 * Set the bounds of the block
	 * 
	 * @param aabb
	 *            The axis-aligned bounding box to update
	 * @return The updated AABB
	 */
	protected AxisAlignedBB setBlockBounds(AxisAlignedBB aabb) {
		if (aabb == null)
			aabb = AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		setBlockBounds((float) aabb.minX, (float) aabb.minY, (float) aabb.minZ, (float) aabb.maxX, (float) aabb.maxY,
				(float) aabb.maxZ);
		return aabb;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileSGEDoor te = (TileSGEDoor) world.getTileEntity(x, y, z);
		if (te == null)
			return;
		setBlockBounds(te.getBoundingBox());
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z) {
		TileSGEDoor te = (TileSGEDoor) world.getTileEntity(x, y, z);
		if (te == null)
			return AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		AxisAlignedBB aabb = te.getBoundingBox();
		if (aabb == null)
			return AxisAlignedBB.getBoundingBox(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
		return aabb.offset(x, y, z);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
		TileSGEDoor te = (TileLanteaDoor) world.getTileEntity(x, y, z);
		if (te == null)
			return null;
		AxisAlignedBB aabb = te.getBoundingBox();
		if (aabb == null)
			return null;
		return setBlockBounds(aabb.offset(x, y, z));
	}

	@Override
	public void onBlockHarvested(World w, int x, int y, int z, int md, EntityPlayer p) {
		if (w.getBlock(x, y - 1, z) == this)
			w.setBlockToAir(x, y - 1, z);
		if (w.getBlock(x, y + 1, z) == this)
			w.setBlockToAir(x, y + 1, z);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b) {
		if (!world.isRemote) {
			int strength = world.getStrongestIndirectPower(x, y, z);
			TileSGEDoor te = (TileSGEDoor) world.getTileEntity(x, y, z);
			if (te != null)
				te.setRedstoneState(strength);
		}

	}

	@Override
	public void configure(ComponentConfig c) {
		// TODO Auto-generated method stub

	}

}
