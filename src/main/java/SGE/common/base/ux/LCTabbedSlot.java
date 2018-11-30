package SGE.common.base.ux;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SGETabbedSlot extends Slot {
	
	private int posX, posY;

	public SGETabbedSlot(IInventory inv, int idx, int x, int y) {
		super(inv, idx, x, y);
		this.posX = x;
		this.posY = y;
	}
	
	public void hideSlot() {
		this.xDisplayPosition = -999;
		this.yDisplayPosition = -999;
	}
	
	public void showSlot() {
		this.xDisplayPosition = posX;
		this.yDisplayPosition = posY;
	}
	
	

}
