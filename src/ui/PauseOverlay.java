package ui;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.SoundButtons.SOUND_SIZE;
import static utils.Constants.UI.UrmButtons.URM_SIZE;
import static utils.Constants.UI.VolumeButtons.*;

public class PauseOverlay
{

    private Playing playing;
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private AudioOptions audioOptions;
    private UrmButton menuButton, replayButton, unpauseButton;

    public PauseOverlay(Playing playing)
    {
        this.playing = playing;
        loadBackground();
        audioOptions = playing.getGame().getAudioOptions();
        createUrmButton();

    }

    private void createUrmButton()
    {
        int menuX = (int) (313 * Game.SCALE);
        int replayX = (int) (387 * Game.SCALE);
        int pauseX = (int) (462 * Game.SCALE);
        int urmY = (int) (325 * Game.SCALE);
        menuButton = new UrmButton(menuX, urmY, URM_SIZE, URM_SIZE, 2);
        replayButton = new UrmButton(replayX, urmY, URM_SIZE, URM_SIZE, 1);
        unpauseButton = new UrmButton(pauseX, urmY, URM_SIZE, URM_SIZE, 0);
    }


    private void loadBackground()
    {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BG);
        bgW = (int) (backgroundImg.getWidth() * Game.SCALE);
        bgH = (int) (backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int) (25 * Game.SCALE);
    }

    public void update()
    {

        //urm buttons
        menuButton.update();
        replayButton.update();
        unpauseButton.update();

        audioOptions.update();

    }

    public void draw(Graphics g)
    {
        //bg
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        //urm buttons
        menuButton.draw(g);
        replayButton.draw(g);
        unpauseButton.draw(g);

        audioOptions.draw(g);
    }

    public void mouseDragged(MouseEvent e)
    {
        audioOptions.mouseDragged(e);
    }

    public void mousePressed(MouseEvent e)
    {
        if (isIn(e, menuButton))
        {
            menuButton.setMousePressed(true);
        } else if (isIn(e, replayButton))
        {
            replayButton.setMousePressed(true);
        } else if (isIn(e, unpauseButton))
        {
            unpauseButton.setMousePressed(true);
        } else
        {
            audioOptions.mousePressed(e);
        }
    }

    public void mouseReleased(MouseEvent e)
    {
        if (isIn(e, menuButton))
        {
            if (menuButton.isMousePressed())
            {
                Gamestate.state = Gamestate.MENU;
                playing.unpauseGame();
            }
        } else if (isIn(e, replayButton))
        {
            if (replayButton.isMousePressed())
            {
                playing.resetAll();
                playing.unpauseGame();
            }
        } else if (isIn(e, unpauseButton))
        {
            if (unpauseButton.isMousePressed())
            {
                playing.unpauseGame();
            }
        } else
        {
            audioOptions.mouseReleased(e);
        }

        menuButton.resetBools();
        replayButton.resetBools();
        unpauseButton.resetBools();
    }

    public void mouseMoved(MouseEvent e)
    {
        menuButton.setMouseOver(false);
        replayButton.setMouseOver(false);
        unpauseButton.setMouseOver(false);

        if (isIn(e, menuButton))
        {
            menuButton.setMouseOver(true);
        } else if (isIn(e, replayButton))
        {
            replayButton.setMouseOver(true);
        } else if (isIn(e, unpauseButton))
        {
            unpauseButton.setMouseOver(true);
        } else
        {
            audioOptions.mouseMoved(e);
        }
    }

    private boolean isIn(MouseEvent e, PauseButton b)
    {

        return b.getBounds().contains(e.getX(), e.getY());
    }
}
