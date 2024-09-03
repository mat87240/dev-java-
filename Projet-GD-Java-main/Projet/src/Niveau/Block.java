package Niveau;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import mainMapMaker.Grid;

public class Block {
	private float x;
	private float y;
	private float width;
	private float height;
	private int indexCat;
	private int indexBlock;
	private float scale;

	public Block(float x, float y, float width, float height, int indexCat, int indexBlock, float scale) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.indexCat = indexCat;
		this.indexBlock = indexBlock;
		this.scale = scale;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getIndexCat() {
		return indexCat;
	}

	public void setIndexCat(int index) {
		this.indexCat = index;
	}

	public int getIndexBlock() {
		return indexBlock;
	}

	public void setIndexBlock(int index) {
		this.indexBlock = index;
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		return "Block: (" + x + ", " + y + ", " + indexCat + ", " + indexBlock + ")";
	}
}
