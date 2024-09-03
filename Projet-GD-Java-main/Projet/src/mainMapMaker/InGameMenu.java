package mainMapMaker;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Global.Static;

public class InGameMenu {
	private float x1, y1, x2, y2;
	private float sx1, sx2, sy1, sy2;
	private Image image;
	private boolean hovered, clicked;
	private int stateID, buttonID;
	private String buttonName;
	private String buttonType;

	public InGameMenu(Image image, float x1, float y1, float x2, float y2, float sx1, float sy1, float sx2, float sy2,
			String buttonName, int stateID, int buttonID, String buttonType) {
		this.image = image;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.sx1 = sx1;
		this.sy1 = sy1;
		this.sx2 = sx2;
		this.sy2 = sy2;
		this.stateID = stateID;
		this.buttonName = buttonName;
		this.buttonID = buttonID;
		this.buttonType = buttonType;
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(image, x1, y1, x2, y2, sy1, sx1, sx2, sy2);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		hovered = input.getMouseX() > x1 && input.getMouseX() < x2 && input.getMouseY() > y1 && input.getMouseY() < y2;
		clicked = hovered && input.isMousePressed(Input.MOUSE_LEFT_BUTTON);

		if (clicked) {
			System.out.println("dfkdfk");
			if (Static.getCanPressButton()) {
				if (this.stateID == 4) {
					if (this.buttonName == "Left") {
						if (Static.getPagemapbuild() - 1 >= 1) {
							Static.setPagemapbuild(Static.getPagemapbuild() - 1);
						}
					}
					if (this.buttonName == "Right") {
						if (Static.getPagemapbuild() + 1 <= Static.getMaxpagemapbuild()) {
							Static.setPagemapbuild(Static.getPagemapbuild() + 1);
						}
					}
				}

				if (buttonName == "Save") {
					Static.setDoSave(true);
				}
				if (this.buttonName == "Spike") {
					Static.setPagemapbuild(1);
					Static.setMaxpagemapbuild(1);
					Static.setBuildmod(3);
				}
				if (this.buttonName == "Orb") {
					Static.setPagemapbuild(1);
					Static.setMaxpagemapbuild(1);
					Static.setBuildmod(2);
				}
				if (this.buttonName == "Block") {
					Static.setPagemapbuild(1);
					Static.setMaxpagemapbuild(1);
					Static.setBuildmod(1);
				}

				if (this.stateID == 4) {
					if (this.buttonName == "pause") {
						if (!Static.getParamettre() && !Static.getPause()) {
							Static.setPause(true);
							Static.setMovhandstate(100);
							Static.setSelect(false);
							Static.setRotate(false);
							Static.setSwipe(false);
							Static.setSnape(false);
						} else {
							Static.setPause(false);
						}

					}
					if (this.buttonName == "paramettre") {
						if (!Static.getParamettre() && !Static.getPause()) {
							Static.setParamettre(true);
							Static.setMovhandstate(100);
							Static.setSelect(false);
							Static.setRotate(false);
							Static.setSwipe(false);
							Static.setSnape(false);
						} else {
							Static.setParamettre(false);
						}

					}
				}
			}

			if (!Static.getParamettre() && !Static.getPause()) {

				if (this.buttonType == "blocks") {
					Static.setSelectedBlock(buttonID);
					System.err.println(buttonID + (Static.getPagemapbuild() - 1) * 10);
				}

				if (this.buttonName == "Swipe") {
					if (Static.getMovhandstate() != 0) {
						if (!Static.getSwipe()) {
							Static.setSwipe(true);
							Static.setCanPressButton(false);
						} else {
							Static.setSwipe(false);
							Static.setCanPressButton(true);
						}
					}
				}
				if (this.buttonName == "Snape") {
					if (!Static.getSnape()) {
						Static.setSnape(true);
					} else {
						Static.setSnape(false);
					}

				}
				if (this.buttonName == "Rotate") {
					if (!Static.getRotate()) {
						Static.setRotate(true);
					} else {
						Static.setRotate(false);
					}

				}
				if (this.buttonName == "Select") {
					if (!Static.getSelect()) {
						Static.setSelect(true);
					} else {
						Static.setSelect(false);
					}

				}

				if (this.buttonID != Static.getMovhandstate() + 1) {
					switch (this.buttonID) {
					case 1:
						Static.setMovhandstate(0);
						Static.setSwipe(false);
						Static.setCanPressButton(false);
						break;
					case 2:
						Static.setMovhandstate(1);
						Static.setCanPressButton(true);

						break;
					case 3:
						Static.setMovhandstate(2);
						Static.setCanPressButton(true);

						break;
					case 4:
						Static.setMovhandstate(3);
						Static.setCanPressButton(true);

						break;
					}
				}
			}
		}
	}

	public boolean getClicked() {
		return this.clicked;
	}

	public void setY(double newY) {
		this.y1 = (float) (newY * Static.getScreenHeight());
		this.y2 = (float) (newY * Static.getScreenHeight() + 0.05f * Static.getScreenHeight());
	}

	public void setX(double newX) {
		this.x1 = (float) (newX * Static.getScreenWidth());
		this.x2 = (float) (newX * Static.getScreenWidth() + 0.05f * Static.getScreenWidth());
	}

	public void setName(String name) {
		this.buttonName = name;
	}

	public void setSize(int a, int b) {
		this.sx2 = a;
		this.sy2 = b;
	}

	public void setImage(Image image) {
		this.image = image;
	}
}
