package Affichage;

import Global.Static;
import Niveau.ScanFolder;
import mainMapMaker.MenuMapMaker;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.GameState;

public class menuButton {
	private float x1, y1, x2, y2;
	private float sx1, sx2, sy1, sy2;
	private Image image;
	private boolean hovered, clicked;
	private int stateID, buttonID;
	private String buttonName;

	public menuButton(Image image, float x1, float y1, float x2, float y2, float sx1, float sy1, float sx2, float sy2,
			String buttonName, int stateID, int buttonID) {

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
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(image, x1, y1, x2, y2, sy1, sx1, sx2, sy2);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		//Handle Check for mouse over button
		hovered = input.getMouseX() > x1 && input.getMouseX() < x2 && input.getMouseY() > y1 && input.getMouseY() < y2;
		//Handle clicked state for each button
		clicked = hovered && input.isMousePressed(Input.MOUSE_LEFT_BUTTON);

		//Handle action from different button
		if (clicked) {
			//Goes into every non dynamic next sbg init
			if (this.stateID != 4 && this.buttonName != "playLevelStart" && this.buttonName != "Exit") {
				sbg.enterState(stateID);
			}
			
			//Handle MapMakerMenu Left an Right page swap
			if (this.stateID == 3) {
				if (this.buttonName == "Left") {
					if (Static.getIndex() - 1 >= 1) {
						Static.setIndex(Static.getIndex() - 1);
						Static.setMenuScroll(0);
					}
				}
				if (this.buttonName == "Right") {
					if (Static.getIndex() + 1 <= Static.getIndexMax()) {
						Static.setIndex(Static.getIndex() + 1);
						Static.setMenuScroll(0);
					}
				}
			}
			
			//Init MapMaker again + enter sbg and set choosenlevel
			if (this.stateID == 4) {
				Static.setLevelName(this.buttonName);
				GameState nextState = sbg.getState(4);

				nextState.init(gc, sbg);
				sbg.enterState(4);
			}
			
			
			//Handle Main level choice (not done)
			if (this.buttonName == "playLevelStart") {
			}
			
			//handle exit button
			if (this.buttonName == "Exit") {
				gc.exit();
			}

			// Debug
			if (Static.getDebugPower() >= 1) {
				System.out.println("Enter StateID : " + this.stateID);
				System.out.println("------");
			}
		}
	}

	
	//Geters and Seters
	public boolean getClicked() {
		return this.clicked;
	}

	public void setY(float newY) {
		float deltaY = newY - this.y1;
		this.y1 = newY;
		this.y2 += deltaY;
	}

	public void setName(String name) {
		this.buttonName = name;
	}
}
