package Gameplay;

import java.io.FileReader;
import java.io.IOException;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import Global.BlockAccessor;
import Global.BlockHitbox;
import Global.Static;
import Niveau.Block;
import Niveau.BlockList;
import Personnage.IconsRendering;
import mainMapMaker.Grid;
import mainMapMaker.InGameMenu;
import mainMapMaker.MovementHandler;

public class MainGame extends BasicGameState {
	private GameMovement gameMovement;
	private float cubeY, screenPos;
	private GridRenderer gridRenderer;
	private String levelName;
	private BlockList blockList;
	private float deltaX, deltaY, maxX;
	private Hitbox cubeHitbox;

	public MainGame(int stateID) throws SlickException {
		this.cubeY = 0;
		this.screenPos = 0;
		this.cubeHitbox = new Hitbox(0, cubeY, 100, 100);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		IconsRendering.getRenderIcons();
		BlockHitbox.readBlockProperties();

		this.deltaX = 0;
		this.deltaY = 0;
		blockList = new BlockList();

		levelName = "mainlvl/level.json";
		if (levelName != null) {
			String levelFolderPath = "mainlvl/level.json";

			BlockAccessor.readBlockProperties();

			Gson gson = new Gson();
			try (FileReader reader = new FileReader(levelFolderPath)) {
				JsonElement jsonElement = gson.fromJson(reader, JsonElement.class);
				JsonObject jsonObject = jsonElement.getAsJsonObject();
				JsonArray blocksArray = jsonObject.getAsJsonArray("blocks");

				float maxX = Float.MIN_VALUE; 

				for (JsonElement element : blocksArray) {
					JsonObject blockObject = element.getAsJsonObject();
					float x = blockObject.get("x").getAsFloat();
					float y = blockObject.get("y").getAsFloat();
					int indexCat = blockObject.get("index catégorie").getAsInt();
					int indexBlock = blockObject.get("index block").getAsInt();
					float width = blockObject.get("width").getAsFloat();
					float height = blockObject.get("height").getAsFloat();
					float scale = blockObject.get("scale").getAsFloat();

					if (x > maxX) {
						maxX = x; 
					}

					Block block = new Block(x, y, width, height, indexCat, indexBlock, scale);
					blockList.addBlock(block);
				}

				this.maxX = maxX;

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println(maxX);

		gameMovement = new GameMovement(cubeY, deltaX, deltaY);
		gridRenderer = new GridRenderer(Static.getScreenWidth(), Static.getScreenHeight(), cubeY, maxX, blockList);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		cubeHitbox.setPosition(deltaX, cubeY);
		
		Block[] listInter = cubeHitbox.intersects(blockList);
		for (int i = 0; i < listInter.length; i++) {
			if (listInter[i] != null) {
				System.out.println(listInter[i]);
			}
		}
		gameMovement.update(gc, sbg, delta, listInter);
		
		gameMovement.adjustVelocityOnCollision(cubeHitbox.nextFloor(blockList));
		cubeY = gameMovement.getCubeY();

		float preCubeY = cubeY;
		screenPos += (cubeY - preCubeY);

		cubeHitbox.isDead(blockList);

		
		if (Static.getIsDead() == true) {
			deltaX = 0;
			Static.setIsDead(false);
		}
		deltaX += 7 * gameMovement.getSpeed();;


	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		int iconIndex = Static.getModState() + 1;
		int cubeWidth = 100;
		int cubeHeight = 100;
		int screenWidth = Static.getScreenWidth();
		int screenHeight = Static.getScreenHeight();
		int x = (screenWidth - cubeWidth) / 6;
		int y = screenHeight - (int) (cubeY + screenPos) - cubeHeight;

		Input input = gc.getInput();

		IconsRendering.render(g, iconIndex, x, y, screenPos);
		gridRenderer.render(g, screenHeight, deltaX, deltaY);
	}

	@Override
	public int getID() {
		return 1; // Return a unique ID for this state
	}
}
