import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import Global.Static;

public class main {
    public static void main(String[] args) throws SlickException {
        Static.setDebugPower(0);

        Game jeu = new Game("Geometry Dash");

        GraphicsDevice myScreen = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int screenWidth = myScreen.getDisplayMode().getWidth();
        int screenHeight = myScreen.getDisplayMode().getHeight();

        Static.setScreenWidth(screenWidth);
        Static.setScreenHeight(screenHeight);

        AppGameContainer app = new AppGameContainer(jeu);
        app.setFullscreen(false);
        app.setDisplayMode(screenWidth, screenHeight, true);

        app.setTargetFrameRate(120);

        app.setShowFPS(true);
        app.start();
    }
}