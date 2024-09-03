package mainMapMaker;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Global.BlockRendering;
import Global.Static;
import Niveau.Block;
import Niveau.BlockList;

public class Grid {
	private int rows;
	private float cellWidth;
	private float cellHeight;
	private BlockList blockList;
	private MovementHandler movementHandler;
	private float maxDelta;
	private BlockRendering blockRendering;

	public Grid(int rows, BlockList blockList, MovementHandler movementHandler, GameContainer gc) throws SlickException {
		this.rows = rows;
		this.cellWidth = 100;
		this.cellHeight = 100;

		this.blockList = blockList;
		this.movementHandler = movementHandler;
		this.maxDelta = -movementHandler.getDeltaX() + gc.getScreenWidth();
		this.blockRendering = new BlockRendering();
	}

	public void render(Graphics g, GameContainer gc) throws SlickException {
		float zoom = Static.getZoom();

		g.setColor(Color.gray);
		float deltaY = movementHandler.getDeltaY();
		float deltaX = -movementHandler.getDeltaX();
		float startX = deltaX;

		float startY = gc.getScreenHeight() - deltaY;
		float endY = startY - rows * cellHeight * 1 / zoom;
		;

		float maxDelta = Static.getMaxDelta();

		float endX = (movementHandler.calcXCoord(maxDelta * zoom + (gc.getScreenWidth())) * 150 * zoom);
		float columns = endX / 150;

		for (int i = 0; i <= rows; i++) {
			float y = startY - i * cellHeight * 1 / zoom;
			g.drawLine(startX, y, endX, y);
		}

		for (int i = 0; i <= columns; i++) {
			float x = startX + i * cellWidth * 1 / zoom;
			g.drawLine(x, startY, x, endY);
		}
		Block[] blocks = blockList.getBlockList();
		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != null && blocks[i].getIndexCat() != 2) {
				blockRendering.renderBlock(blocks[i], cellWidth, cellHeight, startX, startY, (int) blocks[i].getX(),
						(int) blocks[i].getY(), blocks[i].getIndexCat(), blocks[i].getIndexBlock(), g);
			}
		}

		for (int i = 0; i < blocks.length; i++) {
			if (blocks[i] != null && blocks[i].getIndexCat() == 2) {
				blockRendering.renderBlock(blocks[i], cellWidth, cellHeight, startX, startY, (int) blocks[i].getX(),
						(int) blocks[i].getY(), blocks[i].getIndexCat(), blocks[i].getIndexBlock(), g);
			}
		}

	}

	public int getCellWidth() {
		return (int) this.cellWidth;
	}

	public int getCellHeight() {
		return (int) this.cellHeight;
	}

	public float getWidth() {
		return (int) ((maxDelta + (cellWidth * Static.getScreenWidth() / 150)) / cellWidth);
	}

}