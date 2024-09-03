package Skin;

import com.google.gson.JsonObject;

import java.io.FileWriter;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Global.Static;

public class SkinMenuButton {
	private float x1, y1, x2, y2;
	private float sx1, sx2, sy1, sy2;
	private Image image;
	private boolean hovered, clicked;
	private int stateID, buttonID;
	private String buttonName;

	public SkinMenuButton(Image image, float x1, float y1, float x2, float y2, float sx1, float sy1, float sx2,
			float sy2, String buttonName, int stateID, int buttonID) {
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
		g.drawImage(image, x1, y1, x2, y2, sy1, sx1, sx2, sy2); // draw de l'image
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		hovered = input.getMouseX() > x1 && input.getMouseX() < x2 && input.getMouseY() > y1 && input.getMouseY() < y2;//detecte si la la souris est sur l'image
		clicked = hovered && input.isMousePressed(Input.MOUSE_LEFT_BUTTON);//detecte si un click de dessus

		if (clicked) {
			if (this.stateID == 2) {
				if (this.buttonName == "Leftskin") {
					if (Static.getPageskin() - 1 >= 1) {
						Static.setPageskin(Static.getPageskin() - 1);
					}
				}
				if (this.buttonName == "Rightskin") {
					if (Static.getPageskin() + 1 <= Static.getMaxpageskin()) {
						Static.setPageskin(Static.getPageskin() + 1);
					}
				}
				if (this.buttonName == "Leftcolor") {
					if (Static.getPagecolor() - 1 >= 1) {
						Static.setPagecolor(Static.getPagecolor() - 1);
					}
				}
				if (this.buttonName == "Rightcolor") {
					if (Static.getPagecolor() + 1 <= Static.getMaxpagecolor()) {
						Static.setPagecolor(Static.getPagecolor() + 1);
					}
				}
			}
			// création + ecriture dans le fichier json "color" 
			if (this.buttonName.startsWith("color")) { 
			    JsonObject jsonObject = new JsonObject();
			    jsonObject.addProperty("ID", Static.getColorNum());
			    jsonObject.addProperty("Color", buttonName);

			    try (FileWriter fileWriter = new FileWriter("icons/color.json")) {
			        if (Static.getColorNum() == 1) {
			            fileWriter.write(jsonObject.toString());
			        } else if (Static.getColorNum() == 2) {
			            fileWriter.write(jsonObject.toString());
			        } else {
			            fileWriter.write(jsonObject.toString());
			        }
			        fileWriter.flush();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}


			switch (this.buttonName) {
			case "Cube":
				Static.setSkinmod(1);
				Static.setPageskin(1);
				break;
			case "Ship":
				Static.setSkinmod(2);
				Static.setPageskin(1);
				break;
			case "Ball":
				Static.setSkinmod(3);
				Static.setPageskin(1);
				break;
			case "Ufo":
				Static.setSkinmod(4);
				Static.setPageskin(1);
				break;
			case "Wave":
				Static.setSkinmod(5);
				Static.setPageskin(1);
				break;
			case "Robot":
				Static.setSkinmod(6);
				Static.setPageskin(1);
				break;
			case "Spider":
				Static.setSkinmod(7);
				Static.setPageskin(1);
				break;
			case "Swing":
				Static.setSkinmod(8);
				Static.setPageskin(1);
				break;
			}
		}
		
		if (this.stateID == 0) {//pour le render des skin
			Static.getSkinmod();
		}
		if (this.stateID == 0) {//pour le render des color
		}
	}

	public boolean getClicked() {
		return this.clicked;
	}

	public void setY(double newY) {
		this.y1 = (float) (newY * Static.getScreenHeight());
		this.y2 = (float) (newY * Static.getScreenHeight() + 0.09f * Static.getScreenHeight());
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
	public void setId(int num) {
		this.buttonID = num;
	}
}
