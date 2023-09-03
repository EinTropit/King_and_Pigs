package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.UrmButtons.URM_SIZE;

public class LevelCompletedOverlay
{
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuButton, nextButton;

    public LevelCompletedOverlay(Playing playing)
    {
        this.playing = playing;
        loadBackground();
        createButtons();

    }

    private void createButtons()
    {
        int menuX = (int) (330 * Game.SCALE);
        int nextX = (int) (445 * Game.SCALE);
        int buttonY = (int) (195 * Game.SCALE);
        menuButton = new UrmButton(menuX, buttonY, URM_SIZE, URM_SIZE, 2);
        nextButton = new UrmButton(nextX, buttonY, URM_SIZE, URM_SIZE, 0);
    }

    private void loadBackground()
    {
        backgroundImg = LoadSave.getSpriteAtlas(LoadSave.COMPLETED_BG);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (75 * Game.SCALE);
    }

    public void update()
    {
        menuButton.update();
        nextButton.update();
    }

    public void draw(Graphics g)
    {
        //bg
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        //buttons
        menuButton.draw(g);
        nextButton.draw(g);
    }

    public void mousePressed(MouseEvent e)
    {
        if(isIn(e, menuButton))
        {
            menuButton.setMousePressed(true);
        }
        else if(isIn(e, nextButton))
        {
            nextButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e)
    {

        if(isIn(e, menuButton))
        {
            if(menuButton.isMousePressed())
            {
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        }
        else if(isIn(e, nextButton))
        {
            if(nextButton.isMousePressed())
            {
                System.out.println("next");
            }
        }

        menuButton.resetBools();
        nextButton.resetBools();
    }

    public void mouseMoved(MouseEvent e)
    {
        menuButton.setMouseOver(false);
        nextButton.setMouseOver(false);

         if(isIn(e, menuButton))
        {
            menuButton.setMouseOver(true);
        }
        else if(isIn(e, nextButton))
        {
            nextButton.setMouseOver(true);
        }

    }

    private boolean isIn(MouseEvent e, UrmButton b)
    {

        return b.getBounds().contains(e.getX(), e.getY());
    }


}
