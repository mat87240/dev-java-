package mainMapMaker;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.MouseListener;

import Global.Static;
import Niveau.BlockList;

public class MovementHandler {
	private float preMouseX, preMouseY;
	private float deltaX, deltaY;
	private boolean newBlock;
	private boolean dragging;
	private int maxDelta;
	private boolean dragged = false;;
	private float dragStartX = 0, dragStartY = 0, dragEndX = 0, dragEndY = 0;

	public MovementHandler(GameContainer gc) {
		deltaX = 0;
		deltaY = 0;
		maxDelta = 0;
	}

	public void update(Input input, GameContainer gc, BlockList blockList) {
		if (Static.getMovhandstate() == 0) { // move action
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				if (!dragging) {
					dragging = true;
					preMouseX = input.getMouseX();
					preMouseY = input.getMouseY();
				} else {
					// Adjust the sign of the delta values here
					deltaX += (preMouseX - input.getMouseX());
					deltaY -= (preMouseY - input.getMouseY());

					preMouseX = input.getMouseX();
					preMouseY = input.getMouseY();

					deltaX = Math.max(deltaX, -100);
					deltaY = Math.max(deltaY, -gc.getScreenHeight() / 3);
					deltaY = Math.min(deltaY, (95) * 100 * 1 / Static.getZoom() - gc.getScreenHeight());

					maxDelta = (int) Math.max(maxDelta, (int) deltaX * 1 / Static.getZoom());
					Static.setMaxDelta(maxDelta);
				}

				if (Static.getDebugPower() >= 4) {
					System.out.println("Delta = " + deltaX + " & " + deltaY);
					System.out.println("MaxDelta = " + maxDelta);
				}

			} else {
				dragging = false;
			}
		}

		if (Static.getCanPressButton() == true) {
			if (Static.getMovhandstate() == 3) { // edit action
				newBlock = false;
			}
			if (Static.getMovhandstate() == 2) { // delete action
				newBlock = false;
			}
			if (Static.getMovhandstate() == 1) {
				if (Static.getSnape()) {
					if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
						if (!dragged) {
							dragStartX = input.getMouseX() + deltaX * Static.getZoom();
							dragStartY = deltaY - input.getMouseY() + gc.getScreenHeight() * Static.getZoom() + 1;
							dragged = true;
						} else {
							dragEndX = input.getMouseX() + deltaX * Static.getZoom();
							dragEndY = deltaY - input.getMouseY() + gc.getScreenHeight() * Static.getZoom();
						}
					} else if (dragged) {
						dragged = false;
						System.out.println("Checking blocks...");
						System.out.println("x1: " + (dragStartX) + ", y1: " + (dragStartY));
						System.out.println("x2: " + (dragEndX) + ", y2: " + (dragEndY));
						System.out.println("___________________");
					}

				} else {
					if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
						System.out.println(((input.getMouseX() + deltaX) * Static.getZoom()));
						blockList.addBlock((input.getMouseX() + deltaX) * Static.getZoom(),
								(deltaY - input.getMouseY() + gc.getScreenHeight()) * Static.getZoom() + 1, input);
					}
				}
			}
		} else if (Static.getCanPressButton() == false)

		{
			if (Static.getSwipe() == true) {
				if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
					newBlock = true;

				}
			}
		}
	}

	public float calcXCoord(float maxDelta) {
		return maxDelta / 100;
	}

	public float calcYCoord(float y) {
		return y / 100;
	}

	public boolean getNewBlock() {
		return newBlock;
	}

	public void setNewBlock(boolean newBlock) {
		this.newBlock = newBlock;
	}

	public float getDeltaX() {
		return deltaX;
	}

	public float getDeltaY() {
		return -deltaY;
	}

	public int getMaxDelta() {
		return maxDelta;
	}

	public void setMaxDelta(int maxDelta) {
		this.maxDelta = maxDelta;
	}
}
