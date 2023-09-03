package levels;

import entities.Pig;
import main.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelpMethods.*;

public class Level
{
    private BufferedImage levelImage;
    private int[][] levelData;
    private ArrayList<Pig> pigs;

    private int levelTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage levelImage)
    {
        this.levelImage = levelImage;
        createLevelData();
        createEnemies();
        calculateLevelOffsetX();
        calculatePlayerSpawn();
    }

    private void calculatePlayerSpawn()
    {
        playerSpawn = GetPlayerSpawn(levelImage);
    }

    private void createLevelData()
    {
        levelData = GetLevelData(levelImage);
    }

    private void createEnemies()
    {
        pigs = GetPigs(levelImage);
    }

    private void calculateLevelOffsetX()
    {
        levelTilesWide = levelImage.getWidth();
        maxTilesOffset = levelTilesWide - Game.TILES_IN_WIDTH;
        maxLevelOffsetX = maxTilesOffset * Game.TILES_SIZE;

    }

    public int getSpriteIndex(int x, int y)
    {
        return levelData[y][x];
    }

    public int[][] getLevelData()
    {
        return levelData;
    }

    public int getLevelOffset()
    {
        return maxLevelOffsetX;
    }

    public ArrayList<Pig> getPigs()
    {
        return pigs;
    }

    public Point getPlayerSpawn()
    {
        return playerSpawn;
    }
}
