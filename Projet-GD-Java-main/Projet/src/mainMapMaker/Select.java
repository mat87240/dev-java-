package mainMapMaker;

import Global.Static;
import Niveau.Block;
import Niveau.BlockList;

public class Select {
	private BlockList blockList;

	public Select(BlockList blockList) {
		this.blockList = blockList;
	}

	// Method to check which block is under the mouse cursor
	public void checkBlockUnderMouse(float gridX, float gridY, float mouseX, float mouseY) {
		System.out.println(mouseY + " ; " + Static.getScreenHeight() * 0.69f);
		if (mouseY <= Static.getScreenHeight() * 0.69f) {
			System.out.println(gridX + " ; " + gridY);
			Block[] blocks = blockList.getBlockList();
			for (Block block : blocks) {
				if (block != null && isMouseOverBlock(block, gridX, gridY)) {
					System.out.println("Block under mouse: " + block);
					return; // Stop searching after finding the first block
				}
			}
			System.out.println("No block under mouse.");
		}
	}

	// Helper method to check if the mouse cursor is over a block
	private boolean isMouseOverBlock(Block block, float mouseX, float mouseY) {
		return mouseX >= block.getX() && mouseX <= block.getX() + block.getWidth() && mouseY >= block.getY()
				&& mouseY <= block.getY() + block.getHeight();
	}
}
