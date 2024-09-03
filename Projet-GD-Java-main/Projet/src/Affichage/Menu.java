package Affichage;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Menu extends BasicGameState {
    private menuButton playButton, skinButton, editButton, exitButton;
    private Image play, skin, edit, exit, background;
    private SquareAnimation squareAnimation;

    public Menu(int stateID) {
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

        float gridSize = 150;

        //Image initiation 
     	play = new Image("images/playbutton.png");
    	skin = new Image("images/skinbutton.png");
    	edit = new Image("images/editbutton.png");
    	exit = new Image("images/exitbutton.png");
    	background = new Image("images/background.png");
    	squareAnimation = new SquareAnimation();
    	
    	//Button creation
    	exitButton = new menuButton(exit, gc.getWidth()/2-gc.getWidth()/8, gc.getHeight()/8*7-gc.getHeight()/7,
				  gc.getWidth()/2+gc.getWidth()/8, gc.getHeight()/8*7+gc.getHeight()/7,0, 0, 500, 500,"Exit", 0, 0);
    	
        editButton = new menuButton(edit, gc.getWidth()/5*4-gc.getWidth()/16, gc.getHeight()/2-gc.getWidth()/16,
				  gc.getWidth()/5*4+gc.getWidth()/16, gc.getHeight()/2+gc.getWidth()/16, 0, 0, 500, 500,"Edit", 3, 0);
        
        skinButton = new menuButton(skin, gc.getWidth()/5-gc.getWidth()/16, gc.getHeight()/2-gc.getWidth()/16,
				  gc.getWidth()/5+gc.getWidth()/16, gc.getHeight()/2+gc.getWidth()/16, 0, 0, 500, 500,"Skin", 2, 0);
    	
        playButton = new menuButton(play, gc.getWidth()/2-gc.getWidth()/16, gc.getHeight()/2-gc.getWidth()/16,
        							  gc.getWidth()/2+gc.getWidth()/16, gc.getHeight()/2+gc.getWidth()/16,0, 0, 500, 500,"Play", 1, 0);
        
    }

    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
        g.drawImage(background, 0, 0, gc.getWidth(), gc.getHeight(), 0, 0, 2224, 1080);
        playButton.render(gc, sbg, g);
        skinButton.render(gc, sbg, g);
        editButton.render(gc, sbg, g);
        exitButton.render(gc, sbg, g);
        squareAnimation.render(gc, sbg, g);
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
        playButton.update(gc, sbg, delta);
        skinButton.update(gc, sbg, delta);
        editButton.update(gc, sbg, delta);
        exitButton.update(gc, sbg, delta);
        squareAnimation.update(gc, sbg, delta);
    }

    public int getID() {
        return 0;
    }
}