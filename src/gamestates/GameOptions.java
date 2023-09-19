package gamestates;

import main.Game;
import ui.AudioOptions;
import ui.MenuButton;
import ui.PauseButton;
import ui.UrmButton;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.UrmButtons.URM_SIZE;

public class GameOptions extends State implements Statemethods
{
    private BufferedImage optionsBackgroundImg;
    private int optionsX, optionsY, optionsWidth, optionsHeight;
    private UrmButton menuButton;
    private AudioOptions audioOptions;

    public GameOptions(Game game)
    {
        super(game);
        loadBackground();
        loadButtons();
        audioOptions = game.getAudioOptions();

    }

    private void loadBackground()
    {
        optionsBackgroundImg = LoadSave.GetSpriteAtlas(LoadSave.OPTIONS_BG);
        optionsWidth = (int) (optionsBackgroundImg.getWidth() * Game.SCALE);
        optionsHeight = (int) (optionsBackgroundImg.getHeight() * Game.SCALE);
        optionsX = Game.GAME_WIDTH / 2 - optionsWidth / 2;
        optionsY = (int) (33 * Game.SCALE);
    }

    private void loadButtons()
    {
        int menuX = (int) (387 * Game.SCALE);
        int menuY = (int) (325 * Game.SCALE);
        menuButton = new UrmButton(menuX, menuY, URM_SIZE, URM_SIZE, 2);
    }

    @Override
    public void update()
    {
        menuButton.update();
        audioOptions.update();
    }

    @Override
    public void draw(Graphics g)
    {
        g.drawImage(optionsBackgroundImg, optionsX, optionsY, optionsWidth, optionsHeight, null);
        menuButton.draw(g);
        audioOptions.draw(g);
    }

    private boolean isIn(MouseEvent e, PauseButton b)
    {
        return b.getBounds().contains(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e)
    {
        audioOptions.mouseDragged(e);
    }


    @Override
    public void mouseClicked(MouseEvent e)
    {

    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if (isIn(e, menuButton))
        {
            menuButton.setMousePressed(true);
        } else
        {
            audioOptions.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if (isIn(e, menuButton))
        {
            if(menuButton.isMousePressed())
            {
                Gamestate.state = Gamestate.MENU;
            }
        } else
        {
            audioOptions.mouseReleased(e);
        }

        menuButton.resetBools();
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        menuButton.setMouseOver(false);

        if (isIn(e, menuButton))
        {
            menuButton.setMouseOver(true);
        } else
        {
            audioOptions.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            Gamestate.state = Gamestate.MENU;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
