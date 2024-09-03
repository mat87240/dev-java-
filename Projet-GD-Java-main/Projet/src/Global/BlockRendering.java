package Global;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import Niveau.Block;

public class BlockRendering {
	private static BlockRendering instance;
	private Image[] images;

	// Private constructor to prevent instantiation from other classes
	public BlockRendering() throws SlickException {
		preloadImages();
	}

	// Public method to provide access to the single instance of the class
	public static BlockRendering getInstance() throws SlickException {
		if (instance == null) {
			instance = new BlockRendering();
		}
		return instance;
	}

	// Method to preload images
	private void preloadImages() throws SlickException {
		images = new Image[36];
		for (int i = 1; i < images.length; i++) {
			images[i] = new Image("blocks/images/orb/" + (i + 10) + ".png");
		}
	}

	// Method to render a block
	public void renderBlock(Block block, float cellWidth, float cellHeight, float startX, float startY, int gridX,
			int gridY, int indexCat, int indexBlock, Graphics g) throws SlickException {
		float zoom = Static.getZoom();
		float adjustedZoom = 1 / zoom; // Inverse of zoom scale

		float renderedCellWidth = 100 * adjustedZoom;
		float renderedCellHeight = 100 * adjustedZoom;

		float x = startX + (gridX * renderedCellWidth);
		float y = startY - (gridY * renderedCellHeight);

		if (indexCat != 2) {
			String[][] renderingOrder = BlockAccessor.getRenderingOrder(indexCat, indexBlock);

			if (renderingOrder != null && renderingOrder.length == 2) {
				String[] renderingOrderPart1 = renderingOrder[0];
				String[] renderingOrderPart2 = renderingOrder[1];

				for (String part1Coord : renderingOrderPart1) {
					String[] coords = part1Coord.split(" ");
					int partX = Integer.parseInt(coords[0]);
					int partY = Integer.parseInt(coords[1]);
					int partWidth = Integer.parseInt(coords[2]) - partX;
					int partHeight = Integer.parseInt(coords[3]) - partY;

					int scaledWidth = (int) (partWidth * renderedCellWidth / 100);
					int scaledHeight = (int) (partHeight * renderedCellHeight / 100);

					g.setColor(Color.white);
					g.fillRect((int) (x + partX * adjustedZoom), (int) (y + partY * adjustedZoom), scaledWidth,
							scaledHeight);
				}

				for (String part2Coord : renderingOrderPart2) {
					String[] coords = part2Coord.split(" ");
					int partX = Integer.parseInt(coords[0]);
					int partY = Integer.parseInt(coords[1]);
					int partWidth = Integer.parseInt(coords[2]) - partX;
					int partHeight = Integer.parseInt(coords[3]) - partY;

					int scaledWidth = (int) (partWidth * renderedCellWidth / 100);
					int scaledHeight = (int) (partHeight * renderedCellHeight / 100);

					g.setColor(Color.black);
					g.fillRect((int) (x + partX * adjustedZoom), (int) (y + partY * adjustedZoom), scaledWidth,
							scaledHeight);
				}

			} else {
				System.err.println(
						"Invalid or missing rendering order data for category " + indexCat + ", block " + indexBlock);
				g.setColor(Color.red);
				g.fillRect((int) x, (int) y, (int) renderedCellWidth, (int) renderedCellHeight);
			}
		} else {
			// Ensure indexBlock is within the bounds of the preloaded images array
			if (indexBlock >= 0 && indexBlock < images.length) {
				Image image = images[indexBlock];
				image.draw(x, y, image.getWidth() * adjustedZoom, image.getHeight() * adjustedZoom);
			} else {
				System.err.println("Invalid block index: " + indexBlock);
				g.setColor(Color.red);
				g.fillRect((int) x, (int) y, (int) renderedCellWidth, (int) renderedCellHeight);
			}
		}
	}

	public float imageHeight(int i) {
		Image image = images[i];
		return image.getHeight();
	}

	public float imageWitdh(int i) {
		Image image = images[i];
		return image.getWidth();
	}
}
