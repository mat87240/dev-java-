package mainMapMaker;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Affichage.menuButton;
import Niveau.ScanFolder;
import Global.Static;

public class MenuMapMaker extends BasicGameState {
	private menuButton backButton, leftButton, rightButton;
	private Image back, left, right, background, background2;
	private List<String> jsonList;
	private int listSize;
	private int page, maxPage, levelsOnPage;
	private menuButton[] viewButtons = new menuButton[10];

	public MenuMapMaker(int stateID) {
	}

	public void init(GameContainer gc, StateBasedGame g) throws SlickException {
		background = new Image("images/background mylevel.png");
		background2 = new Image("images/background mylevel2.png");

		back = new Image("images/backbutton.png");
		backButton = new menuButton(back, Static.getScreenWidth() / 40, Static.getScreenHeight() / 24,
				Static.getScreenWidth() / 12, Static.getScreenHeight()/8, 0, 0, 100, 100, "Back", 0, 0);
		left = new Image("images/fleche_gauche.png");
		leftButton = new menuButton(left, Static.getScreenWidth() / 40,
				Static.getScreenHeight()/2-Static.getScreenHeight()/16, Static.getScreenWidth() / 40*4,
				Static.getScreenHeight()/2+Static.getScreenHeight()/16, 0, 0, 500, 500, "Left", 3, 0);
		right = new Image("images/fleche_droite.png");
		rightButton = new menuButton(right, Static.getScreenWidth() / 40 * 36,
				Static.getScreenHeight()/2-Static.getScreenHeight()/16, Static.getScreenWidth() / 40*39,
				Static.getScreenHeight()/2+Static.getScreenHeight()/16, 0, 0, 500, 500, "Right", 3, 0);

		List<String> fileNames = ScanFolder.getFolderNamesInFolder("level/");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = null;
		try {
			writer = new FileWriter("level/List_levels.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		gson.toJson(fileNames, writer);
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		jsonList = ScanFolder.readJsonFile("level/List_levels.json");
		listSize = jsonList.size() - 2;
		Static.setIndexMax((int) (listSize / 10) + (listSize % 10 != 0 ? 1 : 0));
		page = Static.getIndex();

		double MenuScroll = Static.getMenuScroll();
		// TO FIX
		int levelsOnPage = Math.min(listSize - (page - 1) * 10, 10);
		viewButtons = new menuButton[levelsOnPage];
		for (int i = 1; i <= levelsOnPage; i++) {
			double y = ((i - MenuScroll / 10) * 0.154f - 0.04f) * Static.getScreenHeight();
			Image view = new Image("images/view.png");
			viewButtons[i - 1] = new menuButton(view, 0.7f * Static.getScreenWidth(),
					(float) (y + 0.027f * Static.getScreenHeight()), 0.80f * Static.getScreenWidth(),
					(float) (y + 0.127f * Static.getScreenHeight()), 0, 0, 500, 500,
					ScanFolder.extractLines(jsonList, i + 1 + (page - 1) * 10), 4, 0);
		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		gc.getInput();
		mouseWheelMoved(delta);
		backButton.update(gc, sbg, delta);
		leftButton.update(gc, sbg, delta);
		rightButton.update(gc, sbg, delta);

		for (int i = 0; i < viewButtons.length; i++) {
			if (viewButtons[i] != null) {
				viewButtons[i].update(gc, sbg, delta);
			}
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		page = Static.getIndex();
		levelsOnPage = Math.min(listSize - (page - 1) * 10, 10);
		g.setColor(Color.white);
		g.drawImage(background, 0, 0, gc.getWidth(), gc.getHeight(), 0, 0, 1000, 1000);
		double MenuScroll = Static.getMenuScroll();
		for (int i = 1; i <= levelsOnPage; i++) {
			double y = ((i - MenuScroll / 10) * 0.154f - 0.04f) * Static.getScreenHeight();

			g.drawRect(0.14f * Static.getScreenWidth(), (float) y, 0.72f * Static.getScreenWidth(),
					0.154f * Static.getScreenHeight());
			g.drawString(ScanFolder.extractLines(jsonList, i + 1 + (page - 1) * 10), 0.15f * Static.getScreenWidth(),
					(float) (y + 0.077f * Static.getScreenHeight()));

			if (viewButtons[i - 1] != null) {
				double newY = (y + 0.027f * Static.getScreenHeight());
				viewButtons[i - 1].setY((float) newY);
				viewButtons[i - 1].render(gc, sbg, g);
				viewButtons[i - 1].setName(ScanFolder.extractLines(jsonList, i + 1 + (page - 1) * 10));
			}
		}
		g.drawImage(background2, 0, 0, gc.getWidth(), gc.getHeight(), 0, 0, 1000, 1000);
		backButton.render(gc, sbg, g);
		leftButton.render(gc, sbg, g);
		rightButton.render(gc, sbg, g);
	}

	public int getID() {
		return 3;
	}

	public void mouseWheelMoved(int change) {
		if (Math.abs(change) >= 120) {
			double MenuScroll = Static.getMenuScroll() - (change / 120 * Static.getScreenHeight() * 0.003f);
			if (MenuScroll < 0 || levelsOnPage <= 5) {
				MenuScroll = 0;
			}

			if (MenuScroll > levelsOnPage * Math.abs(change / 120 * Static.getScreenHeight() * 0.003f)) {
				MenuScroll = levelsOnPage * Math.abs(change / 120 * Static.getScreenHeight() * 0.003f);
			}

			Static.setMenuScroll(MenuScroll);
		}
	}
}