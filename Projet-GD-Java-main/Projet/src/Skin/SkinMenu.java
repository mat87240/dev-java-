package Skin;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import Global.Static;
import Affichage.menuButton;
import Niveau.ScanFolder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SkinMenu extends BasicGameState {
	private menuButton backButton ;
	private Image back ;

	private Image cube, ship, ball, ufo, wave, robot, spider, swing, background, frame, leftskin, rightskin;
	private SkinMenuButton cubeButton, shipButton, ballButton, ufoButton, waveButton, robotButton, spiderButton, swingButton;
	private SkinMenuButton leftskinButton, rightskinButton,leftcolorButton, rightcolorButton;
	private static String[][] tabSkin;
	int gridSize = 150;
	private List<String> FileNames;
	private List<String> cubeNames, shipNames, ballNames, ufoNames, waveNames, robotNames, spiderNames, swingNames, colorNames;
	private int listSize,listColorSize;
	private int page, pagecolor, SkinOnPage, ColorOnPage;
	private String chemin, filenames;
	private SkinMenuButton[] skinButtons = new SkinMenuButton[10];
	private SkinMenuButton[] colorButtons = new SkinMenuButton[10];

	public SkinMenu(int stateID) {
	}

	public void init(GameContainer gc, StateBasedGame g) throws SlickException {
		background = new Image("images/skinmenu.png");
		back = new Image("images/backbutton.png");
		backButton = new menuButton(back, Static.getScreenWidth() / 40, Static.getScreenHeight() / 24,
				Static.getScreenWidth() / 12, Static.getScreenHeight()/8, 0, 0, 100, 100, "Back", 0, 0);
		
		frame = new Image("images/frame construction.png");
		
		leftskin = new Image("images/fleche_gauche.png");
		leftskinButton = new SkinMenuButton(leftskin, Static.getScreenWidth() *0.22f,
				Static.getScreenHeight() * 0.45f, Static.getScreenWidth()*0.3f, Static.getScreenHeight() * 0.6f, 0, 0,
				500, 500, "Leftskin", 2, 0);
		rightskin = new Image("images/fleche_droite.png");
		rightskinButton = new SkinMenuButton(rightskin, Static.getScreenWidth() *0.73f,
				Static.getScreenHeight() * 0.45f, Static.getScreenWidth() *0.81f,
				Static.getScreenHeight() * 0.6f, 0, 0, 500, 500, "Rightskin", 2, 0);

		leftcolorButton = new SkinMenuButton(leftskin, Static.getScreenWidth() *0.22f,
				Static.getScreenHeight() * 0.75f, Static.getScreenWidth()*0.3f, Static.getScreenHeight() * 0.9f, 0, 0,
				500, 500, "Leftcolor", 2, 0);
		rightcolorButton = new SkinMenuButton(rightskin, Static.getScreenWidth() *0.73f,
				Static.getScreenHeight() * 0.75f, Static.getScreenWidth() *0.81f,
				Static.getScreenHeight() * 0.9f, 0, 0, 500, 500, "Rightcolor", 2, 0);
		
		
		page = 1;
		tabSkin = new String[8][];
		// ###################################################################
		cubeNames = createAndloadjson(FileNames, "skins/CubeSkin", "skins/CubeSkin/List_Cube.json", cubeNames, listSize , 0);
		listSize = tabSkin[0].length;
		
		shipNames = createAndloadjson(FileNames, "skins/ShipSkin", "skins/ShipSkin/List_Ship.json", shipNames, listSize , 1);
		
		ballNames = createAndloadjson(FileNames, "skins/BallSkin", "skins/BallSkin/List_Ball.json", ballNames, listSize , 2);
		
		ufoNames = createAndloadjson(FileNames, "skins/UfoSkin", "skins/UfoSkin/List_Ufo.json", ufoNames, listSize , 3);
		
		waveNames = createAndloadjson(FileNames, "skins/WaveSkin", "skins/WaveSkin/List_Wave.json", waveNames, listSize , 4);
		
		robotNames = createAndloadjson(FileNames, "skins/RobotSkin", "skins/RobotSkin/List_Robot.json", robotNames, listSize , 5);
		
		spiderNames = createAndloadjson(FileNames, "skins/SpiderSkin", "skins/SpiderSkin/List_Spider.json", spiderNames, listSize , 6);
		
		swingNames = createAndloadjson(FileNames, "skins/SwingSkin", "skins/SwingSkin/List_Swing.json", swingNames, listSize , 7);
		// ###################################################################
		Static.setMaxpageskin((int) (listSize / 10) + (listSize % 10 != 0 ? 1 : 0));
		page = Static.getPageskin();
		chemin = "skins/CubeSkin";

		SkinOnPage = Math.min(listSize - (page - 1) * 10, 10);
		skinButtons = new SkinMenuButton[SkinOnPage];
		for (int i = 1; i <= 10; i++) {
			//filenames = "images/view.png";
			String filename = new File(chemin, tabSkin[Static.getSkinmod()-1][i-1]).getAbsolutePath();//chemin depuis la racine jusqu'a l'image
			 if (new File(filename).exists()) {
	                Image Skin = new Image(filename);
			skinButtons[i - 1] = new SkinMenuButton(Skin, 0,0, 1, 1,0, 0, 
					Skin.getWidth(), Skin.getHeight(),filename, 4, 0);// creation de 10 button dans une liste
		}
		}
		Static.setMaxpagemapbuild((int) (listSize / 10) + (listSize % 10 != 0 ? 1 : 0));
		// ###################################################################
				// Color :
				List<String> blockFileNames = ScanFolder.getFileNamesWithExtension("skins/Color", ".png"); //on ecrie tout les couleur dans le json
				Gson gsonblock = new GsonBuilder().setPrettyPrinting().create();
				FileWriter writerblock = null;
				try {
					writerblock = new FileWriter("skins/Color/List_color.json");
				} catch (IOException e) {
					e.printStackTrace();
				}
				gsonblock.toJson(blockFileNames, writerblock);
				try {
					writerblock.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				pagecolor = Static.getPagecolor();
				colorNames = ScanFolder.readJsonFile("skins/Color/List_color.json");//on extrait toutes les couleur
				listColorSize = (colorNames.size() > 2) ? colorNames.size() - 2 : 0;
				ColorOnPage = Math.min(listColorSize - (pagecolor - 1) * 10, 10);
				colorButtons = new SkinMenuButton[ColorOnPage];
				for (int i = 1; i <= ColorOnPage; i++) {
					//filenames = "images/view.png";
					filenames = String.format("skins/Color/%s", ScanFolder.extractLines(colorNames, i + 1));
					Image color = new Image(filenames);
					colorButtons[i - 1] = new SkinMenuButton(color, 0,0, 1, 1,0, 0, color.getWidth(), color.getHeight(),
							ScanFolder.extractLines(colorNames, i + 1 + (pagecolor - 1) * 10), 4, 0);//creation de 10 button dans une liste
				}
				Static.setMaxpagecolor((int) (listColorSize / 10) + (listColorSize % 10 != 0 ? 1 : 0));
				// ###################################################################
	
		cube = new Image("skins/CubeSkin/Cube001.png");
		cubeButton = new SkinMenuButton(cube, Static.getScreenWidth() *0.35f, Static.getScreenHeight()* 0.35f,
				Static.getScreenWidth() * 0.37f, Static.getScreenHeight()*0.38f, 0, 0, cube.getWidth(), cube.getHeight(), "Cube", 0, 0);
		ship = new Image("skins/ShipSkin/Ship001.png");
		shipButton = new SkinMenuButton(ship, Static.getScreenWidth() *0.39f, Static.getScreenHeight()* 0.35f,
				Static.getScreenWidth() * 0.41f, Static.getScreenHeight()*0.38f, 0, 0, ship.getWidth(), ship.getHeight(), "Ship", 0, 0);
		ball = new Image("skins/BallSkin/Ball001.png");
		ballButton = new SkinMenuButton(ball, Static.getScreenWidth() *0.43f, Static.getScreenHeight()* 0.35f,
				Static.getScreenWidth() * 0.45f, Static.getScreenHeight()*0.38f, 0, 0, ball.getWidth(), ball.getHeight(), "Ball", 0, 0);
		ufo = new Image("skins/UfoSkin/UFO001.png");
		ufoButton = new SkinMenuButton(ufo, Static.getScreenWidth() *0.47f, Static.getScreenHeight()* 0.35f,
				Static.getScreenWidth() * 0.49f, Static.getScreenHeight()*0.38f, 0, 0, ufo.getWidth(), ufo.getHeight(), "Ufo", 0, 0);
		robot = new Image("skins/RobotSkin/Robot001.png");
		robotButton = new SkinMenuButton(robot, Static.getScreenWidth() *0.51f, Static.getScreenHeight()* 0.35f,
				Static.getScreenWidth() * 0.53f, Static.getScreenHeight()*0.38f, 0, 0, robot.getWidth(), robot.getHeight(), "Robot", 0, 0);
		wave = new Image("skins/WaveSkin/Wave001.png");
		waveButton = new SkinMenuButton(wave, Static.getScreenWidth() *0.55f, Static.getScreenHeight()* 0.35f,
				Static.getScreenWidth() * 0.57f, Static.getScreenHeight()*0.38f, 0, 0, wave.getWidth(), wave.getHeight(), "Wave", 0, 0);
		spider = new Image("skins/SpiderSkin/Spider001.png");
		spiderButton = new SkinMenuButton(spider, Static.getScreenWidth() *0.59f, Static.getScreenHeight()* 0.35f,
				Static.getScreenWidth() * 0.61f, Static.getScreenHeight()*0.38f, 0, 0, spider.getWidth(), spider.getHeight(), "Spider", 0, 0);
		swing = new Image("skins/SwingSkin/Swing001.png");
		swingButton = new SkinMenuButton(swing, Static.getScreenWidth() *0.63f, Static.getScreenHeight()* 0.35f,
				Static.getScreenWidth() * 0.65f, Static.getScreenHeight()*0.38f, 0, 0, swing.getWidth(), swing.getHeight(), "Swing", 0, 0);
	}

	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		backButton.update(gc, sbg, delta);
		cubeButton.update(gc, sbg, delta);
		shipButton.update(gc, sbg, delta);
		ballButton.update(gc, sbg, delta);
		ufoButton.update(gc, sbg, delta);
		robotButton.update(gc, sbg, delta);
		waveButton.update(gc, sbg, delta);
		spiderButton.update(gc, sbg, delta);
		swingButton.update(gc, sbg, delta);
		rightskinButton.update(gc, sbg, delta);
		leftskinButton.update(gc, sbg, delta);
		rightcolorButton.update(gc, sbg, delta);
		leftcolorButton.update(gc, sbg, delta);
		
		
		
		for (int i = 0; i < skinButtons.length; i++) {
			if (skinButtons[i] != null) {
				skinButtons[i].update(gc, sbg, delta);
			}
		}
		for (int i = 0; i < colorButtons.length; i++) {
			if (colorButtons[i] != null) {
				colorButtons[i].update(gc, sbg, delta);
			}
		}
	}

	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(background, 0, 0, gc.getWidth(), gc.getHeight(), 0, 0, background.getWidth(), background.getHeight());
		g.drawImage(frame, Static.getScreenWidth() * 0.3f, Static.getScreenHeight() * 0.4f,
				Static.getScreenWidth() * 0.73f, Static.getScreenHeight() * 0.67f, 0, 0, 500, 250);
		g.drawImage(frame, Static.getScreenWidth() * 0.3f, Static.getScreenHeight() * 0.7f,
				Static.getScreenWidth() * 0.73f, Static.getScreenHeight() * 0.97f, 0, 0, 500, 250);
		backButton.render(gc, sbg, g);
		cubeButton.render(gc, sbg, g);
		shipButton.render(gc, sbg, g);
		ballButton.render(gc, sbg, g);
		ufoButton.render(gc, sbg, g);
		robotButton.render(gc, sbg, g);
		waveButton.render(gc, sbg, g);
		spiderButton.render(gc, sbg, g);
		swingButton.render(gc, sbg, g);
		rightskinButton.render(gc, sbg, g);
		leftskinButton.render(gc, sbg, g);
		rightcolorButton.render(gc, sbg, g);
		leftcolorButton.render(gc, sbg, g);
		
		pagecolor = Static.getPagecolor();
		ColorOnPage = Math.min(listColorSize - (pagecolor - 1) * 10, 10);
		
		page = Static.getPageskin();
		
		switch (Static.getSkinmod()) {//changement de mod
		case 1:
			Static.setMaxpageskin((int) (tabSkin[0].length / 10) + (tabSkin[0].length % 10 != 0 ? 1 : 0));
			SkinOnPage = Math.min(tabSkin[0].length - (page - 1) * 10, 10);
			chemin = "skins/CubeSkin";
			break;
		case 2:
			Static.setMaxpageskin((int) (tabSkin[1].length / 10) + (tabSkin[1].length % 10 != 0 ? 1 : 0));
			SkinOnPage = Math.min(tabSkin[1].length - (page - 1) * 10, 10);
			chemin = "skins/ShipSkin";
			break;
		case 3:
			Static.setMaxpageskin((int) (tabSkin[2].length / 10) + (tabSkin[2].length % 10 != 0 ? 1 : 0));
			SkinOnPage = Math.min(tabSkin[2].length - (page - 1) * 10, 10);
			chemin = "skins/BallSkin";
			break;

		case 4:
			Static.setMaxpageskin((int) (tabSkin[3].length / 10) + (tabSkin[3].length % 10 != 0 ? 1 : 0));
			SkinOnPage = Math.min(tabSkin[3].length - (page - 1) * 10, 10);
			chemin = "skins/UfoSkin";
			break;
		case 5:
			Static.setMaxpageskin((int) (tabSkin[4].length / 10) + (tabSkin[4].length % 10 != 0 ? 1 : 0));
			SkinOnPage = Math.min(tabSkin[4].length - (page - 1) * 10, 10);
			chemin = "skins/WaveSkin";
			break;
		case 6:
			Static.setMaxpageskin((int) (tabSkin[5].length / 10) + (tabSkin[5].length % 10 != 0 ? 1 : 0));
			SkinOnPage = Math.min(tabSkin[5].length - (page - 1) * 10, 10);
			chemin = "skins/RobotSkin";
			break;
		case 7:
			Static.setMaxpageskin((int) (tabSkin[6].length / 10) + (tabSkin[6].length % 10 != 0 ? 1 : 0));
			SkinOnPage = Math.min(tabSkin[6].length - (page - 1) * 10, 10);
			chemin = "skins/SpiderSkin";
			break;

		case 8:
			Static.setMaxpageskin((int) (tabSkin[7].length / 10) + (tabSkin[7].length % 10 != 0 ? 1 : 0));
			SkinOnPage = Math.min(tabSkin[7].length - (page - 1) * 10, 10);
			chemin = "skins/SwingSkin";
			break;
		}
		
		//###################################################
		if (SkinOnPage > 5) {
			for (int j = 0; j <= 1; j++) {
				for (int i = 1 + j*5; i <= 5 + j * (SkinOnPage-5); i++) {
					//filenames = "images/view.png";
					String filename = new File(chemin, tabSkin[Static.getSkinmod()-1][i - 1 + (page - 1) * 10]).getAbsolutePath();//le chemin + fichier
					 if (new File(filename).exists()) {
			                Image Skin = new Image(filename);
			                if (skinButtons[i - 1] != null) {
			                    double newY = j * 0.12f + 0.43f;
			                    double newX = (i - 1 -(j * 5)) * 0.08f + 0.33f;
			                    skinButtons[i - 1].setImage(Skin);
			                    skinButtons[i - 1].setX((float) newX);
			                    skinButtons[i - 1].setY((float) newY);
			                    skinButtons[i - 1].render(gc, sbg, g);
			                    skinButtons[i - 1].setName(filename);
			                    skinButtons[i - 1].setSize(Skin.getWidth(), Skin.getHeight());
			                    skinButtons[i - 1].setId(i + (page - 1) * 10);
			}
					 }
				}
			}
		} else {
			for (int i = 1; i <= SkinOnPage; i++) {
				//filenames = "images/view.png";
				String filename = new File(chemin, tabSkin[Static.getSkinmod()-1][i - 1 + (page - 1) * 10]).getAbsolutePath();
				 if (new File(filename).exists()) {
		                Image Skin = new Image(filename);
		                if (skinButtons[i - 1] != null) {
		                	double newY = 0.43f;
		                    double newX = (i -1) * 0.08f + 0.33f;
		                    skinButtons[i - 1].setImage(Skin);
		                    skinButtons[i - 1].setX((float) newX);
		                    skinButtons[i - 1].setY((float) newY);
		                    skinButtons[i - 1].render(gc, sbg, g);
		                    skinButtons[i - 1].setName(filename);
		                    skinButtons[i - 1].setSize(Skin.getWidth(), Skin.getHeight());
		                    skinButtons[i - 1].setId(i + (page - 1) * 10);
		}
				 }
			}
		}
		//#############################################################
		//color:
		if (ColorOnPage > 5) {
			for (int j = 0; j <= 1; j++) {
				for (int i = 1 + j*5; i <= 5 + j * (ColorOnPage-5); i++) {
					//filenames = "images/view.png";
					filenames = String.format("skins/Color/%s", ScanFolder.extractLines(colorNames, i + 1 + (pagecolor - 1) * 10));
					Image Color = new Image(filenames);
					if (colorButtons[i - 1] != null) {
						double newY = j * 0.12f + 0.73f;
						double newX = (i - 1 -(j * 5)) * 0.08f + 0.33f;
						colorButtons[i - 1].setImage(Color);
						colorButtons[i - 1].setX((float) newX);
						colorButtons[i - 1].setY((float) newY);
						colorButtons[i - 1].render(gc, sbg, g);
						colorButtons[i - 1].setName(ScanFolder.extractLines(colorNames, i + 1 + (pagecolor - 1) * 10));
						colorButtons[i - 1].setSize(Color.getWidth(), Color.getHeight());
						colorButtons[i - 1].setId(i + (page - 1) * 10);
					}
				}
			}
		} else {
			for (int i = 1; i <= ColorOnPage; i++) {
				//filenames = "images/view.png";
				filenames = String.format("skins/Color/%s", ScanFolder.extractLines(colorNames, i + 1 + (pagecolor - 1) * 10));
				Image Color = new Image(filenames);
				if (colorButtons[i - 1] != null) {
					double newY = 0.73f;
					double newX = (i -1) * 0.08f + 0.33f;
					colorButtons[i - 1].setImage(Color);
					colorButtons[i - 1].setX((float) newX);
					colorButtons[i - 1].setY((float) newY);
					colorButtons[i - 1].render(gc, sbg, g);
					colorButtons[i - 1].setName(ScanFolder.extractLines(colorNames, i + 1 + (pagecolor - 1) * 10));
					colorButtons[i - 1].setSize(Color.getWidth(), Color.getHeight());
					colorButtons[i - 1].setId(i + (page - 1) * 10);
				}
			}
		}
	}
	public static List<String> createAndloadjson(List<String> filenames, String chemin, String savejson, List<String> names, int size, int num){
		filenames = ScanFolder.getFileNamesWithExtension(chemin, ".png");//chercher tout les fichier en .png
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		FileWriter writer = null;
		try {
			writer = new FileWriter(savejson);
		} catch (IOException e) {
			e.printStackTrace();
		}
		gson.toJson(filenames, writer);//tous les ecrire dans un fichier json
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		names = ScanFolder.readJsonFile(savejson);
		size = (names.size() > 2) ? names.size() - 2 : 0;
		if (size > 0) {
			tabSkin[num] = new String[size];
			for (int j = 1; j <= tabSkin[num].length; j++) {
				tabSkin[num][j-1] = ScanFolder.extractLines(names, j+1);//les ajouter a une matrice
			}
		}
		return names;
	}


	public int getID() {
		return 2;
	}


}