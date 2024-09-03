package Niveau;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;

import Global.Static;
import mainMapMaker.MovementHandler;

public class BlockList {
	Block[] blocks;
	float cellWidth = 150;
	float cellHeight = 150;
	MovementHandler movementHandler;
	int numBlocks;

	public BlockList() {
		blocks = new Block[0];
	}

	public BlockList(MovementHandler movementHandler) {
		blocks = new Block[0];
		this.movementHandler = movementHandler;
		numBlocks = 0;
	}

	public void printBlockList() {
		System.out.println("Block list ; all");
		for (Block block : blocks) {
			System.out.println(block);
		}
		System.out.println();
	}

	public void addBlock(float x, float y, Input input) {
		float gridX = (movementHandler.calcXCoord(x));
		float gridY = (movementHandler.calcYCoord(y));

		if (!blockExistsAt(gridX, gridY, Static.getBuildmod(), Static.getSelectedBlock())
				&& rightCoord(gridX, gridY, input)) {
			Block newBlock = new Block((int) gridX, (int) gridY, 100, 100, Static.getBuildmod(),
					Static.getSelectedBlock(), 1);
			addBlock(newBlock);
		}

		if (Static.getDebugPower() >= 3) {
			printBlockList();
		}
	}

	private boolean blockExistsAt(float gridX, float gridY, int type, int index) {
		for (Block block : blocks) {

			if (block != null && (int) block.getX() == (int) gridX && (int) block.getY() == (int) gridY
					&& block.getIndexCat() == type && block.getIndexBlock() == index) {
				return true;
			}
		}
		return false;
	}

	private boolean rightCoord(float gridX, float gridY, Input input) {
		if (gridY > 0 && gridY < 90 && gridX > 0 && Static.getScreenHeight() * 0.69f > input.getMouseY()) {
			return true;
		} else
			return false;
	}

	public Block[] addBlock(Block block) {
		if (numBlocks == blocks.length) {
			Block[] newArray = new Block[blocks.length + 1];
			System.arraycopy(blocks, 0, newArray, 0, blocks.length);
			blocks = newArray;
			System.out.println("fdkklfdklf");
		}

		this.blocks[numBlocks] = block;
		numBlocks++;
		return blocks;
	}

	public Block[] getBlockList() {
		return blocks;
	}
}
