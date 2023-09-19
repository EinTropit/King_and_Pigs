package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.UrmButtons.URM_SIZE;

public class GameOverOverlay
{
    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuButton, playButton;

    public GameOverOverlay(Playing playing)
    {
        this.playing = playing;
        loadBackground();
        createButtons();
    }

    private void createButtons()
    {
        int menuX = (int) (335 * Game.SCALE);
        int playX = (int) (440 * Game.SCALE);
        int buttonY = (int) (195 * Game.SCALE);
        menuButton = new UrmButton(menuX, buttonY, URM_SIZE, URM_SIZE, 2);
        playButton = new UrmButton(playX, buttonY, URM_SIZE, URM_SIZE, 0);
    }

    private void loadBackground()
    {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.DEATH_SCREEN_BG);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (100 * Game.SCALE);
    }

    public void draw(Graphics g)
    {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);

        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);
        menuButton.draw(g);
        playButton.draw(g);

    }

    public void update()
    {
        menuButton.update();
        playButton.update();
    }

    public void keyPressed(KeyEvent e)
    {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            playing.resetAll();
            Gamestate.state = Gamestate.MENU;
        }
    }

    public void mousePressed(MouseEvent e)
    {
        if(isIn(e, menuButton))
        {
            menuButton.setMousePressed(true);
        }
        else if(isIn(e, playButton))
        {
            playButton.setMousePressed(true);
        }
    }

    public void mouseReleased(MouseEvent e)
    {

        if(isIn(e, menuButton))
        {
            if(menuButton.isMousePressed())
            {
                playing.resetAll();
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        }
        else if(isIn(e, playButton))
        {
            if(playButton.isMousePressed())
            {
                playing.resetAll();
            }
        }

        menuButton.resetBools();
        playButton.resetBools();
    }

    public void mouseMoved(MouseEvent e)
    {
        menuButton.setMouseOver(false);
        playButton.setMouseOver(false);

        if(isIn(e, menuButton))
        {
            menuButton.setMouseOver(true);
        }
        else if(isIn(e, playButton))
        {
            playButton.setMouseOver(true);
        }

    }

    private boolean isIn(MouseEvent e, UrmButton b)
    {

        return b.getBounds().contains(e.getX(), e.getY());
    }
}
