package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import ui.PauseOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements Statemethods
{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private PauseOverlay pauseOverlay;
    private boolean paused = false;

    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int lvlTilesWide = LoadSave.GetLevelData()[0].length;
    private int maxTilesOffset = lvlTilesWide - Game.TILES_IN_WIDTH;
    private int maxLevelOffsetX = maxTilesOffset * Game.TILES_SIZE;

    public Playing(Game game)
    {
        super(game);
        initGameClasses();
    }

    private void initGameClasses()
    {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        player = new Player(200, 200, (int) (78 * Game.SCALE), (int) (58 * Game.SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        pauseOverlay = new PauseOverlay(this);
    }

    public void windowFocusLost()
    {
        player.resetDirBooleans();
    }

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public void update()
    {
        if(!paused)
        {
            levelManager.update();
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData());
            checkCloseToBorder();
        }
        else
        {
            pauseOverlay.update();
        }
    }

    private void checkCloseToBorder()
    {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLevelOffset;

        if(diff > rightBorder)
        {
            xLevelOffset += diff - rightBorder;
        }
        else if (diff < leftBorder)
        {
            xLevelOffset += diff - leftBorder;
        }

        if(xLevelOffset > maxLevelOffsetX)
        {
            xLevelOffset = maxLevelOffsetX;
        }
        else if(xLevelOffset < 0)
        {
            xLevelOffset = 0;
        }
    }

    @Override
    public void draw(Graphics g)
    {
        levelManager.draw(g, xLevelOffset);
        player.render(g, xLevelOffset);
        enemyManager.draw(g, xLevelOffset);
        if(paused)
        {
            g.setColor(new Color(0,0,0,150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(e.getButton() == MouseEvent.BUTTON1)
        {
            player.setAttacking(true);
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(paused)
        {
            pauseOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if(paused)
        {
            pauseOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if(paused)
        {
            pauseOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(true);
                break;
            case KeyEvent.VK_ESCAPE:
                paused = !paused;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        switch (e.getKeyCode())
        {
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
            case KeyEvent.VK_SPACE:
                player.setJump(false);
                break;
        }
    }

    public void unpauseGame()
    {
        paused = false;
    }

    public void pauseGame()
    {
        paused = true;
    }

    public void mouseDragged(MouseEvent e)
    {
        if(paused)
        {
            pauseOverlay.mouseDragged(e);
        }
    }
}
