import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.GameState;
import org.newdawn.slick.state.StateBasedGame;

import Affichage.Menu;
import Gameplay.MainGame;
import Skin.SkinMenu;
import mainMapMaker.MapMaker;
import mainMapMaker.MenuMapMaker;

public class Game extends StateBasedGame {

    public Game(String name) {
        super(name);
    }

    public void initStatesList(GameContainer gc) throws SlickException {
    	addState((GameState) new Menu(0));
        enterState(0);
        addState((GameState) new MapMaker(4));
        addState((GameState) new MenuMapMaker(3));
        addState((GameState) new MainGame(1));
        addState((GameState) new SkinMenu(2));
    }
}