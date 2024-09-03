package Gameplay;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import Global.BlockRendering;
import Global.Static;
import Niveau.Block;
import Niveau.BlockList;

public class GridRenderer {
	private int screenWidth;
	private int screenHeight;
	private float playerY;
	private int blockSize;
	private int rows;
	private float cellWidth, cellHeight;
	private float deltaX, deltaY, maxX;
	private BlockList blockList;
	private BlockRendering blockRendering;

	public GridRenderer(int screenWidth, int screenHeight, float playerY, float maxX, BlockList blockList)
			throws SlickException {
		this.screenWidth = screenWidth;
		this.screenHeight = screenHeight;
		this.playerY = playerY;
		this.blockSize = screenHeight / 9;
		this.rows = 90;
		this.cellWidth = 100;
		this.cellHeight = 100;
		this.deltaX = 0;
		this.deltaY = 0;
		this.maxX = maxX;

		this.blockList = blockList;
		this.blockRendering = new BlockRendering();
	}

	public void render(Graphics g, int screenHeight, float deltaX, float deltaY) throws SlickException {
		// Calculate adjusted start and end points for rendering based on deltaX and
		// deltaY
		float startX = -deltaX;
		float startY = screenHeight - deltaY;
		float endY = startY - rows * cellHeight * 1;

		float endX = (maxX * 100 + (screenHeight));
		float columns = endX / 150;
		
		// Render blocks if blockList is not null (assuming block rendering method
		// exists)
		if (blockList != null) {
			Block[] blocks = blockList.getBlockList();
			for (int i = 0; i < blocks.length; i++) {
				if (blocks[i] != null) {
					blockRendering.renderBlock(blocks[i], cellWidth, cellHeight, 0 - deltaX, startY,
							(int) blocks[i].getX(), (int) blocks[i].getY(), blocks[i].getIndexCat(),
							blocks[i].getIndexBlock(), g);
				}
			}
		}
	}

	public void render(Graphics g, int screenHeight) {
		g.setColor(Color.gray);

		// Draw horizontal lines
		for (int y = 0; y <= screenHeight; y += blockSize) {
			g.drawLine(0, screenHeight - y - (int) playerY % blockSize, screenWidth,
					screenHeight - y - (int) playerY % blockSize);
		}

		// Draw vertical lines
		for (int x = 0; x <= screenWidth; x += blockSize) {
			g.drawLine(x, 0, x, screenHeight);
		}
	}

	public void setPlayerY(float playerY) {
		this.playerY = playerY;
	}
}
