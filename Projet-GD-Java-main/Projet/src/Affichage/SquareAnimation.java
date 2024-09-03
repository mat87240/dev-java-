package Affichage;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import Global.Static;

public class SquareAnimation {
    private int x;
    private int y;
    private int squareSize = Static.getScreenHeight()/16;
    private Color color;
    private long lastTime;
    private boolean isStopping = false;
    private boolean isTurning = false;
    private boolean actionExecuted = false;

    public SquareAnimation() {
        x = -squareSize;
        y = Static.getScreenHeight()/2;
        color = getRandomColor();
        lastTime = System.currentTimeMillis();
    }

    public void update(GameContainer gc, StateBasedGame sbg, int delta) {
        long currentTime = System.currentTimeMillis();
        y = gc.getHeight() / 32 * 21;
        if (currentTime - lastTime > 10) { 
            if (!isStopping && !isTurning) {
                x += 4;
                if (x > gc.getWidth() / 2) {
                    if (Math.random() < 0.001 && !actionExecuted) { 
                        isStopping = true;
                        actionExecuted = true;
                    } else if (Math.random() < 0.001 && !actionExecuted) { 
                        isTurning = true;
                        actionExecuted = true;
                    }
                }
                if (x > gc.getWidth() || (x<0 && actionExecuted)) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    x = -squareSize;
                    color = getRandomColor();
                    actionExecuted = false;
                    isStopping = false;
                    isTurning = false;
                }
            } else {
                if (isStopping) {
                    try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
                    isStopping = false;
                }
                if (isTurning) {
                        x -= 4;
                        if (x<-squareSize && actionExecuted) {
                        	isTurning = false;
                        }
                    }
                }
            }
        }


    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, squareSize, squareSize);

        // Draw mouth
        g.setColor(Color.black);
        g.fillRect(x + squareSize / 4, y + squareSize / 2, squareSize / 2, squareSize / 4);

        // Draw eyes
        g.fillRect(x + squareSize / 4, y + squareSize / 4, squareSize / 6, squareSize / 6);
        g.fillRect(x + squareSize * 2 / 3, y + squareSize / 4, squareSize / 6, squareSize / 6);
    }

    private Color getRandomColor() {
        // Generating random RGB values
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }
}
