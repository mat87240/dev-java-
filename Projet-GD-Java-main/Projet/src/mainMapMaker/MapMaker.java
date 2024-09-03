package mainMapMaker;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Global.BlockAccessor;
import Global.Static;
import Affichage.menuButton;
import Niveau.Block;
import Niveau.BlockList;
import Niveau.Save;
import Niveau.ScanFolder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MapMaker extends BasicGameState {
	private InGameMenu saveButton;
	private Image save;
	private Grid grid;
	private MovementHandler movementHandler;
	private BlockList blockList;
	private String levelName;
	private boolean renderMapMaker = false;
	private InGameMenu paramettreButton, pauseButton, leftButton, rightButton, rondButton, carreButton, triangleButton,
			buildButton, deleteButton, editButton, moveButton, rotateButton, selectButton, snapeButton, swipeButton;
	private Image paramettre, pause, left, right, rond, carre, triangle, build, delete, edit, move, rotate, select,
			construct, snape, swipe, option;
	private String[][] tabBuild;
	int gridSize = 150;
	private List<String> jsonList;
	private List<String> blockNames;
	private List<String> spikeNames;
	private List<String> orbNames;
	private List<String> slopesNames;
	private int listBlockSize, listOrbSize, listSlopesSize, listSpikeSize;
	private int page, maxpage, BuildOnPage;
	private String filenames;
	private String chemin;
	private InGameMenu[] BuildButtons = new InGameMenu[10];
	private Select selected;

	public MapMaker(int stateID) {
	}

	public void init(GameContainer gc, StateBasedGame g) throws SlickException {
		filenames = "";
		page = 1;
		maxpage = 1;
		tabBuild = new String[4][];
		tabBuild[0] = new String[listBlockSize];
		tabBuild[1] = new String[listOrbSize];
		tabBuild[2] = new String[listSlopesSize];
		tabBuild[3] = new String[listSpikeSize];
		// ###################################################################
		// Block :
		List<String> blockFileNames = ScanFolder.getFileNamesWithExtension("blocks/images/block", ".png");
		Gson gsonblock = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writerblock = null;
		try {
			writerblock = new FileWriter("blocks/images/block/List_block.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		gsonblock.toJson(blockFileNames, writerblock);
		try {
			writerblock.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		blockNames = ScanFolder.readJsonFile("blocks/images/block/List_block.json");
		listBlockSize = (blockNames.size() > 2) ? blockNames.size() - 2 : 0;
		if (listBlockSize > 0) {
			tabBuild[0] = new String[listBlockSize];
			for (int j = 0; j < tabBuild[0].length; j++) {
				tabBuild[0][j] = ScanFolder.extractLines(blockNames, j + 1);
			}
		}
		// ###################################################################
		// Orb :
		List<String> orbFileNames = ScanFolder.getFileNamesWithExtension("blocks/images/orb", ".png");
		Gson gsonorb = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writerorb = null;
		try {
			writerorb = new FileWriter("blocks/images/orb/List_orb.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		gsonorb.toJson(orbFileNames, writerorb);
		try {
			writerorb.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		orbNames = ScanFolder.readJsonFile("blocks/images/orb/List_orb.json");
		listOrbSize = (orbNames.size() > 2) ? orbNames.size() - 2 : 0;
		if (listOrbSize > 0) {
			tabBuild[1] = new String[listOrbSize];
			for (int j = 0; j < tabBuild[1].length; j++) {
				tabBuild[1][j] = ScanFolder.extractLines(orbNames, j + 1);
			}
		}
		// ###################################################################
		// Slopes :
		List<String> slopesFileNames = ScanFolder.getFileNamesWithExtension("blocks/images/slopes", ".png");
		Gson gsonslopes = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writerslopes = null;
		try {
			writerslopes = new FileWriter("blocks/images/slopes/List_slopes.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		gsonslopes.toJson(slopesFileNames, writerslopes);
		try {
			writerslopes.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		slopesNames = ScanFolder.readJsonFile("blocks/images/slopes/List_slopes.json");
		listSlopesSize = (slopesNames.size() > 2) ? slopesNames.size() - 2 : 0;
		if (listSlopesSize > 0) {
			tabBuild[2] = new String[listSlopesSize];
			for (int j = 0; j < tabBuild[2].length; j++) {
				tabBuild[2][j] = ScanFolder.extractLines(slopesNames, j + 1);
			}
		}
		// ###################################################################
		// Spike :
		List<String> spikeFileNames = ScanFolder.getFileNamesWithExtension("blocks/images/spike", ".png");
		Gson gsonspike = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writerspike = null;
		try {
			writerspike = new FileWriter("blocks/images/spike/List_spike.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
		gsonspike.toJson(spikeFileNames, writerspike);
		try {
			writerspike.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		spikeNames = ScanFolder.readJsonFile("blocks/images/spike/List_spike.json");
		listSpikeSize = (spikeNames.size() > 2) ? spikeNames.size() - 2 : 0;
		if (listSpikeSize > 0) {
			tabBuild[3] = new String[listSpikeSize];
			for (int j = 0; j < tabBuild[3].length; j++) {
				tabBuild[3][j] = ScanFolder.extractLines(spikeNames, j + 1);
			}
		}
		// ###################################################################
		Static.setMaxpagemapbuild((int) (listBlockSize / 10) + (listBlockSize % 10 != 0 ? 1 : 0));
		page = Static.getIndex();

		int BuildOnPage = Math.min(listBlockSize - (page - 1) * 10, 10);
		BuildButtons = new InGameMenu[BuildOnPage];
		for (int i = 1; i <= BuildOnPage; i++) {
			// filenames = "images/view.png";
			filenames = String.format("blocks/images/block/%s",
					ScanFolder.extractLines(blockNames, i + 1 + (page - 1) * 10));
			Image Build = new Image(filenames);
			BuildButtons[i - 1] = new InGameMenu(Build, 0, 0, 1, 1, 0, 0, Build.getWidth(), Build.getHeight(),
					ScanFolder.extractLines(blockNames, i + 1 + (page - 1) * 10), 4, i, "blocks");
		}
		// ###################################################################

		construct = new Image("images/frame construction.png");
		option = new Image("images/option.png");

		paramettre = new Image("images/buttonparamettre.png");
		paramettreButton = new InGameMenu(paramettre, Static.getScreenWidth() * 0.01f, Static.getScreenHeight() * 0.04f,
				Static.getScreenWidth() * 0.06f, Static.getScreenHeight() * 0.1f, 0, 0, 262, 262, "paramettre", 4, 10,
				"menu");
		pause = new Image("images/buttonpause.png");
		pauseButton = new InGameMenu(pause, Static.getScreenWidth() * 0.94f, Static.getScreenHeight() * 0.04f,
				Static.getScreenWidth() * 0.99f, Static.getScreenHeight() * 0.1f, 0, 0, 499, 500, "pause", 4, 11,
				"menu");

		left = new Image("images/fleche_gauche.png");
		leftButton = new InGameMenu(left, Static.getScreenWidth() / 4 - Static.getScreenWidth() / 16,
				Static.getScreenHeight() * 0.825f, Static.getScreenWidth() / 4, Static.getScreenHeight() * 0.975f, 0, 0,
				500, 500, "Left", 4, 0, "action");
		right = new Image("images/fleche_droite.png");
		rightButton = new InGameMenu(right, Static.getScreenWidth() / 8 * 7 - Static.getScreenWidth() / 8,
				Static.getScreenHeight() * 0.825f, Static.getScreenWidth() / 8 * 7 - Static.getScreenWidth() / 16,
				Static.getScreenHeight() * 0.975f, 0, 0, 500, 500, "Right", 4, 0, "action");
		rond = new Image("images/buttonrond.png");

		rondButton = new InGameMenu(rond, Static.getScreenWidth() * 0.35f, Static.getScreenHeight() * 0.73f,
				Static.getScreenWidth() * 0.4f, Static.getScreenHeight() * 0.81f, 0, 0, 500, 500, "Orb", 4, 0,
				"action");
		carre = new Image("images/buttoncarre.png");
		carreButton = new InGameMenu(carre, Static.getScreenWidth() * 0.45f, Static.getScreenHeight() * 0.73f,
				Static.getScreenWidth() * 0.5f, Static.getScreenHeight() * 0.81f, 0, 0, 500, 500, "Block", 4, 0,
				"action");
		triangle = new Image("images/buttontriangle.png");
		triangleButton = new InGameMenu(triangle, Static.getScreenWidth() * 0.55f, Static.getScreenHeight() * 0.73f,
				Static.getScreenWidth() * 0.6f, Static.getScreenHeight() * 0.81f, 0, 0, 500, 500, "Spike", 4, 0,
				"action");

		select = new Image("images/Select.png");
		selectButton = new InGameMenu(select, gc.getWidth() * 0.83f, gc.getHeight() * 0.93f, gc.getWidth() * 0.98f,
				gc.getHeight() * 0.98f, 0, 0, 461, 107, "select", 4, 0, "action");
		snape = new Image("images/Snape.png");
		snapeButton = new InGameMenu(snape, gc.getWidth() * 0.83f, gc.getHeight() * 0.87f, gc.getWidth() * 0.98f,
				gc.getHeight() * 0.92f, 0, 0, 461, 107, "Snape", 4, 0, "action");
		rotate = new Image("images/Rotate.png");
		rotateButton = new InGameMenu(rotate, gc.getWidth() * 0.83f, gc.getHeight() * 0.81f, gc.getWidth() * 0.98f,
				gc.getHeight() * 0.86f, 0, 0, 461, 107, "Rotate", 4, 0, "action");
		swipe = new Image("images/Swipe.png");
		swipeButton = new InGameMenu(swipe, gc.getWidth() * 0.83f, gc.getHeight() * 0.75f, gc.getWidth() * 0.98f,
				gc.getHeight() * 0.8f, 0, 0, 461, 107, "Swipe", 4, 0, "action");

		delete = new Image("images/Delete.png");
		deleteButton = new InGameMenu(delete, gc.getWidth() * 0.02f, gc.getHeight() * 0.93f, gc.getWidth() * 0.17f,
				gc.getHeight() * 0.98f, 0, 0, 461, 107, "Delete", 4, 3, "action");
		edit = new Image("images/Edit.png");
		editButton = new InGameMenu(edit, gc.getWidth() * 0.02f, gc.getHeight() * 0.87f, gc.getWidth() * 0.17f,
				gc.getHeight() * 0.92f, 0, 0, 461, 107, "Edit", 4, 4, "action");
		build = new Image("images/Build.png");
		buildButton = new InGameMenu(build, gc.getWidth() * 0.02f, gc.getHeight() * 0.81f, gc.getWidth() * 0.17f,
				gc.getHeight() * 0.86f, 0, 0, 461, 107, "Build", 4, 2, "action");
		move = new Image("images/Move.png");
		moveButton = new InGameMenu(move, gc.getWidth() * 0.02f, gc.getHeight() * 0.75f, gc.getWidth() * 0.17f,
				gc.getHeight() * 0.8f, 0, 0, 461, 107, "Move", 4, 1, "action");

		List<String> blockProperties = BlockAccessor.readBlockProperties();

		levelName = Static.getLevelName();
		if (levelName != null) {
			String levelFolderPath = "level/" + levelName + "/level.json";

			BlockAccessor.readBlockProperties();

			movementHandler = new MovementHandler(gc);
			blockList = new BlockList(movementHandler);
			grid = new Grid(90, blockList, movementHandler, gc);

			save = new Image("images/savebutton.png");
			saveButton = new InGameMenu(save, gc.getWidth() - grid.getCellWidth() * 2,
					gc.getHeight() / 2 - grid.getCellHeight() * 2, gc.getWidth() - grid.getCellWidth(),
					gc.getHeight() / 2 - grid.getCellHeight(), 0, 0, 100, 100, "Save", 3, 0, "menu");

			Gson gson = new Gson();
			try (FileReader reader = new FileReader(levelFolderPath)) {
				JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				JsonArray blocksArray = jsonObject.getAsJsonArray("blocks");

				for (JsonElement element : blocksArray) {
					JsonObject blockObject = element.getAsJsonObject();
					float x = blockObject.get("x").getAsFloat();
					float y = blockObject.get("y").getAsFloat();
					int indexCat = blockObject.get("index catégorie").getAsInt();
					int indexBlock = blockObject.get("index block").getAsInt();
					float width = blockObject.get("width").getAsFloat();
					float height = blockObject.get("height").getAsFloat();
					float scale = blockObject.get("scale").getAsFloat();

					Block block = new Block(x, y, width, height, indexCat, indexBlock, scale);
					blockList.addBlock(block);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			renderMapMaker = true;

			// Debug
			if (Static.getDebugPower() >= 1) {
				System.out.println("Selected Level: " + levelName);
				System.out.println("Level files path : " + levelFolderPath);
			}

			if (Static.getDebugPower() >= 3) {
				System.out.println("");
				blockList.printBlockList();
			}
			blockList.printBlockList();

			selected = new Select(blockList);

		}
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		saveButton.update(gc, sbg, delta);
		paramettreButton.update(gc, sbg, delta);
		pauseButton.update(gc, sbg, delta);
		leftButton.update(gc, sbg, delta);
		rightButton.update(gc, sbg, delta);
		rondButton.update(gc, sbg, delta);
		carreButton.update(gc, sbg, delta);
		triangleButton.update(gc, sbg, delta);
		selectButton.update(gc, sbg, delta);
		snapeButton.update(gc, sbg, delta);
		rotateButton.update(gc, sbg, delta);
		swipeButton.update(gc, sbg, delta);
		moveButton.update(gc, sbg, delta);
		buildButton.update(gc, sbg, delta);
		editButton.update(gc, sbg, delta);
		deleteButton.update(gc, sbg, delta);

		for (int i = 0; i < BuildButtons.length; i++) {
			if (BuildButtons[i] != null) {
				BuildButtons[i].update(gc, sbg, delta);
			}
		}

		movementHandler.update(input, gc, blockList);

		if (movementHandler.getNewBlock() == true) {
			movementHandler.setNewBlock(false);

		}

		if (input.isMousePressed(Input.MOUSE_LEFT_BUTTON)) {
			selected.checkBlockUnderMouse(
					(movementHandler.calcXCoord(input.getMouseX() + movementHandler.getDeltaX()) * Static.getZoom()),
					(movementHandler
							.calcYCoord(-movementHandler.getDeltaY() - input.getMouseY() + gc.getScreenHeight()))
							* Static.getZoom() + 1,
					input.getMouseX(), input.getMouseY());
		}

		if (Static.getDoSave() == true) {
			Block[] blocks = blockList.getBlockList();
			Save.writeToFile(levelName, blocks);

			sbg.enterState(3);
		}

		// CETTE MERDE N'EST JAMAIS ECRIT DANS LA LIBRARY EN LIGNE PUTIN
		this.mouseWheelMoved(delta);
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.setBackground(new Color(0, 0, 125));
		g.clear();
		grid.render(g, gc);
		g.setColor(Color.black);
		g.fillRect(0, Static.getScreenHeight() * 0.69f, Static.getScreenWidth(), Static.getScreenHeight() * 0.02f);
		g.setColor(null);
		g.drawImage(construct, Static.getScreenWidth() * 0.25f, Static.getScreenHeight() * 0.8f,
				Static.getScreenWidth() * 0.75f, Static.getScreenHeight() * 0.99f, 0, 0, 500, 250);
		saveButton.render(gc, sbg, g);
		paramettreButton.render(gc, sbg, g);
		pauseButton.render(gc, sbg, g);
		leftButton.render(gc, sbg, g);
		rightButton.render(gc, sbg, g);
		rondButton.render(gc, sbg, g);
		carreButton.render(gc, sbg, g);
		triangleButton.render(gc, sbg, g);
		selectButton.render(gc, sbg, g);
		snapeButton.render(gc, sbg, g);
		rotateButton.render(gc, sbg, g);
		swipeButton.render(gc, sbg, g);
		moveButton.render(gc, sbg, g);
		buildButton.render(gc, sbg, g);
		editButton.render(gc, sbg, g);
		deleteButton.render(gc, sbg, g);
		page = Static.getPagemapbuild();
		switch (Static.getBuildmod()) {
		case 1:
			Static.setMaxpagemapbuild((int) (listBlockSize / 10) + (listBlockSize % 10 != 0 ? 1 : 0));
			BuildOnPage = Math.min(listBlockSize - (page - 1) * 10, 10);
			jsonList = ScanFolder.readJsonFile("blocks/images/block/List_block.json");
			chemin = "blocks/images/block/";
			break;
		case 3:
			Static.setMaxpagemapbuild((int) (listSpikeSize / 10) + (listSpikeSize % 10 != 0 ? 1 : 0));
			BuildOnPage = Math.min(listSpikeSize - (page - 1) * 10, 10);
			jsonList = ScanFolder.readJsonFile("blocks/images/spike/List_spike.json");
			chemin = "blocks/images/spike/";
			break;
		case 2:
			Static.setMaxpagemapbuild((int) (listOrbSize / 10) + (listOrbSize % 10 != 0 ? 1 : 0));
			BuildOnPage = Math.min(listOrbSize - (page - 1) * 10, 10);
			jsonList = ScanFolder.readJsonFile("blocks/images/orb/List_orb.json");
			chemin = "blocks/images/orb/";
			break;
		case 4:
			Static.setMaxpagemapbuild((int) (listSlopesSize / 10) + (listSlopesSize % 10 != 0 ? 1 : 0));
			BuildOnPage = Math.min(listSlopesSize - (page - 1) * 10, 10);
			jsonList = ScanFolder.readJsonFile("blocks/images/slopes/List_slopes.json");
			chemin = "blocks/images/slopes/";
			break;
		}
		if (BuildOnPage > 5) {
			for (int j = 0; j <= 1; j++) {
				for (int i = 1 + j * 5; i <= 5 + j * (BuildOnPage - 5); i++) {
					// filenames = "images/view.png";
					filenames = String.format(chemin + "%s",
							ScanFolder.extractLines(jsonList, i + 1 + (page - 1) * 10));
					Image Build = new Image(filenames);
					if (BuildButtons[i - 1] != null) {
						double newY = j * 0.08f + 0.83f;
						double newX = (i - 1 - (j * 5)) * 0.1f + 0.28f;
						BuildButtons[i - 1].setImage(Build);
						BuildButtons[i - 1].setX((float) newX);
						BuildButtons[i - 1].setY((float) newY);
						BuildButtons[i - 1].render(gc, sbg, g);
						BuildButtons[i - 1].setName(ScanFolder.extractLines(jsonList, i + 1 + (page - 1) * 10));
						BuildButtons[i - 1].setSize(Build.getWidth(), Build.getHeight());
					}
				}
			}
		} else {
			for (int i = 1; i <= BuildOnPage; i++) {
				// filenames = "images/view.png";
				filenames = String.format(chemin + "%s", ScanFolder.extractLines(jsonList, i + 1 + (page - 1) * 10));
				Image Build = new Image(filenames);
				if (BuildButtons[i - 1] != null) {
					double newY = 0.83f;
					double newX = (i - 1) * 0.1f + 0.28f;
					BuildButtons[i - 1].setImage(Build);
					BuildButtons[i - 1].setY((float) newY);
					BuildButtons[i - 1].setX((float) newX);
					BuildButtons[i - 1].render(gc, sbg, g);
					BuildButtons[i - 1].setName(ScanFolder.extractLines(jsonList, i + 1 + (page - 1) * 10));
					BuildButtons[i - 1].setSize(Build.getWidth(), Build.getHeight());
				}
			}
		}

		if (Static.getParamettre() || Static.getPause()) {
			g.drawImage(option, 0, 0, Static.getScreenWidth(), Static.getScreenHeight(), 0, 0, 666, 375);
		}

	}

	public int getID() {
		return 4;
	}

	public void mouseWheelMoved(int change) {
		if (Math.abs(change) >= 120) {
			int totalScroll = Static.getTotalScroll() - change;
			float zoom = 1;

			if (totalScroll == 880 && change > 0) {
				totalScroll = 760;
			}

			else if (totalScroll == 880 && change < 0) {
				totalScroll = 1000;
			}

			if (totalScroll > 2920) {
				totalScroll = 2920;
			}
			if (totalScroll < 280) {
				totalScroll = 280;
			}

			if (totalScroll > 1000) {
				zoom = 1 + (((float) (totalScroll - 1000) / 120) / 4);
			}

			if (totalScroll < 1000) {
				zoom = (float) (0.15 * (totalScroll - 280) / 120.0 + 0.25);
				;
			}
			Static.setZoom(zoom);
			Static.setTotalScroll(totalScroll);
			change = 0; // Resetting change to 0 after processing
		}
	}
}