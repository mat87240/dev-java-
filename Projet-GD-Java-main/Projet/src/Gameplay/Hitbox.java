package Gameplay;

import java.nio.file.spi.FileSystemProvider;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Global.BlockHitbox;
import Global.BlockRendering;
import Global.Static;
import Niveau.Block;
import Niveau.BlockList;

public class Hitbox {
	private float x;
	private float y;
	private float width;
	private float height;
	private BlockRendering blockRendering;

	public Hitbox(float x, float y, float width, float height) throws SlickException {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.blockRendering = new BlockRendering();
	}

	public void setPosition(float x, float y) {
		this.x = (x + ((float) Static.getScreenWidth() / 6) + 90);
		this.y = y;

	}

	public float[] nextFloor(BlockList blockList) {
		float nextfloor[] = { 0, Static.getScreenHeight() - 100 };

		for (Block block : blockList.getBlockList()) {
			if (block.getIndexCat() != 2) {

				if ((block.getX() + 1) * 100 >= x - 99 && (block.getX() + 1) * 100 <= x + 99) {
					if ((block.getY() * 100) - 1 <= y) {
						nextfloor[0] = Math.max(nextfloor[0], block.getY() * 100);
					}
				}

				if ((block.getX() + 1) * 100 >= x - 99 && (block.getX() + 1) * 100 <= x + 99) {
					if ((block.getY() * 100) > y) {
						nextfloor[1] = Math.min((nextfloor[1] - 200), block.getY() * 100 - 200);

					}
				}
			}
		}
		return nextfloor;
	}

	public boolean isDead(BlockList blockList) {
		for (Block block : blockList.getBlockList()) {
			if (block.getIndexCat() != 2) {
				//Get the kill hitbox of the block
				String hitbox = BlockHitbox.getKillHtbox(block.getIndexCat(), block.getIndexBlock());

				//Parse the string into an array
				String[] rectangles = hitbox.split(",");
				for (String rectangle : rectangles) {
					String[] coordinates = rectangle.split(" ");
					float x1 = Integer.parseInt(coordinates[0]) + (block.getX() + 1) * 100;
					float y1 = Integer.parseInt(coordinates[1]) + block.getY() * 100;
					float x2 = Integer.parseInt(coordinates[2]) + (block.getX() + 1) * 100;
					float y2 = Integer.parseInt(coordinates[3]) + block.getY() * 100;

					//Check collide between hitbox and player
					if (x >= x1 && x <= x2 && y >= y1 && y <= y2) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public Block[] intersects(BlockList blockList) {
	    Block[] listInter = new Block[0];
	    for (Block block : blockList.getBlockList()) {
	    	//Check if a block (cat 2) intersect with the player
	        if (block.getIndexCat() == 2) {
	            if ((block.getX() + 1) * 100 <= x + 99
	                    && (block.getX() + 1) * 100 + blockRendering.imageWitdh(block.getIndexBlock()) >= x
	                    && (block.getY() * 100 - 99 - blockRendering.imageHeight(block.getIndexBlock())) <= y
	                    && block.getY() * 100 > y) {

	                Block[] newArray = new Block[listInter.length + 1];
	                System.arraycopy(listInter, 0, newArray, 0, listInter.length);
	                newArray[listInter.length] = block;
	                listInter = newArray;
	            }
	        }
	    }
	    return listInter;
	}
}
