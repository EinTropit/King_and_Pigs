package main;

import entities.Player;
import gamestates.Gamestate;
import gamestates.Playing;
import levels.LevelManager;

import java.awt.*;

public class Game implements Runnable
{
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;

    public static final int TILES_DEFAULT_SIZE = 32;
    public static final float SCALE = 2.0f;
    public static final int TILES_IN_WIDTH = 26;
    public static final int TILES_IN_HEIGHT = 14;
    public static final int TILES_SIZE = (int) (TILES_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILES_IN_WIDTH * TILES_SIZE;
    public static final int GAME_HEIGHT = TILES_IN_HEIGHT * TILES_SIZE;

    private Player player;
    private LevelManager levelManager;

    public Game()
    {
        initGameClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();

        startGameLoop();
    }

    private void initGameClasses()
    {
        levelManager = new LevelManager(this);
        player = new Player(200, 200, (int) (78 * SCALE), (int) (58 * SCALE));
        player.loadLevelData(levelManager.getCurrentLevel().getLevelData());
    }

    public void startGameLoop()
    {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update()
    {
        switch (Gamestate.state)
        {
        case MENU:
            break;
        case PlAYING:
            levelManager.update();
            player.update();
        default:
            break;
        }
    }

    public void render(Graphics g)
    {
        switch (Gamestate.state)
        {
            case MENU:
                break;
            case PlAYING:
                levelManager.draw(g);
                player.render(g);
            default:
                break;
        }

    }

    @Override
    public void run()
    {
        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;
        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true)
        {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1)
            {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1)
            {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }


            if (System.currentTimeMillis() - lastCheck >= 1000)
            {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost()
    {
        player.resetDirBooleans();
    }

    public Player getPlayer()
    {
        return player;
    }
}
