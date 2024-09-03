package Personnage;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class IconsRendering {
	private static String[][][] allRenderingOrders; // Declaring allRenderingOrders as a static member

	public static void getRenderIcons() {

		allRenderingOrders = new String[8][2][];
		for (int i = 1; i <= 8; i++) {
			String[][] renderingOrder = IconsAccessor.getRenderingOrder(i, IconsAccessor.getIconId(i));

			allRenderingOrders[i - 1] = renderingOrder; // Store rendering order in the array
		}
	}

	public static void render(Graphics g, int iconIndex, int x, int y, float screenPos) {
		if (allRenderingOrders == null) {
			System.err.println("Rendering orders not initialized. Call getRenderIcons() first.");
			return;
		}

		if (iconIndex < 1 || iconIndex > 8) {
			System.err.println("Invalid icon index. Icon index should be between 1 and 8.");
			return;
		}

		String[] renderingOrderPart1 = allRenderingOrders[iconIndex - 1][0];
		String[] renderingOrderPart2 = allRenderingOrders[iconIndex - 1][1];
		
		for (int i = 0; i < renderingOrderPart1.length; i++) {
			String[] part1Coords = renderingOrderPart1[i].split(" ");
			
			int part1X = Integer.parseInt(part1Coords[0]);
			int part1Y = Integer.parseInt(part1Coords[1]);
			int part1Width = Integer.parseInt(part1Coords[2]) - part1X;
			int part1Height = Integer.parseInt(part1Coords[3]) - part1Y;

			g.setColor(Color.gray);
			g.fillRect(x + part1X, y + part1Y, part1Width, part1Height);
		}

		for (int i = 0; i < renderingOrderPart2.length; i++) {
			String[] part2Coords = renderingOrderPart2[i].split(" ");
			int part2X = Integer.parseInt(part2Coords[0]);
			int part2Y = Integer.parseInt(part2Coords[1]);
			int part2Width = Integer.parseInt(part2Coords[2]) - part2X;
			int part2Height = Integer.parseInt(part2Coords[3]) - part2Y;

			g.setColor(Color.white);
			g.fillRect(x + part2X, y + part2Y, part2Width, part2Height);
		}

	}
}
