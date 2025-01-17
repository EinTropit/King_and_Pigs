package gamestates;

import entities.EnemyManager;
import entities.Player;
import levels.LevelManager;
import main.Game;
import objects.ObjectManager;
import ui.GameOverOverlay;
import ui.LevelCompletedOverlay;
import ui.PauseOverlay;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

public class Playing extends State implements Statemethods
{
    private Player player;
    private LevelManager levelManager;
    private EnemyManager enemyManager;
    private ObjectManager objectManager;
    private PauseOverlay pauseOverlay;
    private GameOverOverlay gameOverOverlay;
    private LevelCompletedOverlay levelCompletedOverlay;
    private boolean paused = false;

    private int xLevelOffset;
    private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
    private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
    private int maxLevelOffsetX;
    private boolean gameOver = false;
    private boolean levelCompleted = false;
    private boolean playerDying = false;

    public Playing(Game game)
    {
        super(game);
        initGameClasses();

        calculateLevelOffset();
        loadStartLevel();
    }

    public void loadNextLevel()
    {
        resetAll();
        levelManager.loadNextLevel();
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
    }

    private void loadStartLevel()
    {
        enemyManager.loadEnemies(levelManager.getCurrentLevel());
        objectManager.loadObjects(levelManager.getCurrentLevel());
    }

    private void calculateLevelOffset()
    {
        maxLevelOffsetX = levelManager.getCurrentLevel().getLevelOffset();
    }

    private void initGameClasses()
    {
        levelManager = new LevelManager(game);
        enemyManager = new EnemyManager(this);
        objectManager = new ObjectManager(this);
        player = new Player(200, 200, (int) (78 * Game.SCALE), (int) (58 * Game.SCALE), this);
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
        player.setSpawn(levelManager.getCurrentLevel().getPlayerSpawn());
        pauseOverlay = new PauseOverlay(this);
        gameOverOverlay = new GameOverOverlay(this);
        levelCompletedOverlay = new LevelCompletedOverlay(this);
    }

    public void windowFocusLost()
    {
        player.resetDirBooleans();
    }

    public Player getPlayer()
    {
        return player;
    }

    public void resetAll()
    {
        gameOver = false;
        paused = false;
        levelCompleted = false;
        player.resetAll();
        playerDying = false;
        enemyManager.resetAllEnemies();
        objectManager.resetAllObjects();
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }


    public void checkEnemyHit(Rectangle2D.Float attackBox)
    {
        enemyManager.checkEnemyHit(attackBox);
    }

    public void checkObjectHit(Rectangle2D.Float attackBox)
    {
        objectManager.checkObjectHit(attackBox);
    }
    public void checkDiamondTouched(Rectangle2D.Float hitbox)
    {
        objectManager.checkObjectTouched(hitbox);
    }

    public void checkSpikesTouched(Player player)
    {
        objectManager.checkSpikesTouched(player);
    }

    @Override
    public void update()
    {
        if(paused)
        {
            pauseOverlay.update();
        }
        else if (levelCompleted)
        {
            levelCompletedOverlay.update();
        } else if (gameOver)
        {
            gameOverOverlay.update();
        }
        else if (playerDying)
        {
            player.update();
        }
        else
        {
            levelManager.update();
            objectManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            player.update();
            enemyManager.update(levelManager.getCurrentLevel().getLevelData(), player);
            checkCloseToBorder();
        }
    }

    private void checkCloseToBorder()
    {
        int playerX = (int) player.getHitbox().x;
        int diff = playerX - xLevelOffset;

        if (diff > rightBorder)
        {
            xLevelOffset += diff - rightBorder;
        } else if (diff < leftBorder)
        {
            xLevelOffset += diff - leftBorder;
        }

        if (xLevelOffset > maxLevelOffsetX)
        {
            xLevelOffset = maxLevelOffsetX;
        } else if (xLevelOffset < 0)
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
        objectManager.draw(g, xLevelOffset);

        if (paused)
        {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
            pauseOverlay.draw(g);
        } else if (gameOver)
        {
            gameOverOverlay.draw(g);
        } else if (levelCompleted)
        {
            levelCompletedOverlay.draw(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        if (!gameOver)
        {
            if (e.getButton() == MouseEvent.BUTTON1)
            {
                player.setAttacking(true);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        if(!gameOver)
        {
            if (paused)
            {
                pauseOverlay.mousePressed(e);
            } else if (levelCompleted)
            {
                levelCompletedOverlay.mousePressed(e);
            }
        }
        else
        {
            gameOverOverlay.mousePressed(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if(!gameOver)
        {
            if (paused)
            {
                pauseOverlay.mouseReleased(e);
            }
            else if (levelCompleted)
            {
                levelCompletedOverlay.mouseReleased(e);
            }
        }
        else
        {
            gameOverOverlay.mouseReleased(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e)
    {
        if(!gameOver)
        {
            if (paused)
            {
                pauseOverlay.mouseMoved(e);
            }
            else if (levelCompleted)
            {
                levelCompletedOverlay.mouseMoved(e);
            }
        }
        else
        {
            gameOverOverlay.mouseMoved(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(gameOver)
        {
            gameOverOverlay.keyPressed(e);
        }
        else
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
    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(!gameOver)
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
        if(!gameOver)
        {
            if (paused)
            {
                pauseOverlay.mouseDragged(e);
            }
        }
    }

    public EnemyManager getEnemyManager()
    {
        return enemyManager;
    }

    public ObjectManager getObjectManager()
    {
        return objectManager;
    }

    public LevelManager getLevelManager()
    {
        return levelManager;
    }

    public void setMaxLevelOffset(int levelOffset)
    {
        maxLevelOffsetX = levelOffset;
    }

    public void setLevelCompleted(boolean levelCompleted)
    {
        this.levelCompleted = levelCompleted;
    }


    public void setPlayerDying(boolean playerDying)
    {
        this.playerDying = playerDying;
    }
}
