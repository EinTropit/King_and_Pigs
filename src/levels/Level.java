package levels;

import entities.Pig;
import main.Game;
import objects.Box;
import objects.Diamond;
import utils.HelpMethods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.HelpMethods.*;

public class Level
{
    private BufferedImage levelImage;
    private int[][] levelData;
    private ArrayList<Pig> pigs;
    private ArrayList<Box> boxes;
    private ArrayList<Diamond> diamonds;

    private int levelTilesWide;
    private int maxTilesOffset;
    private int maxLevelOffsetX;
    private Point playerSpawn;

    public Level(BufferedImage levelImage)
    {
        this.levelImage = levelImage;
        createLevelData();
        createEnemies();
        createBoxes();
        createDiamonds();
        calculateLevelOffsetX();
        calculatePlayerSpawn();
    }

    private void createDiamonds()
    {
        diamonds = HelpMethods.GetDiamonds(levelImage);
    }

    private void createBoxes()
    {
        boxes = HelpMethods.GetBoxes(levelImage);
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

    public ArrayList<Box> getBoxes()
    {
        return boxes;
    }

    public ArrayList<Diamond> getDiamonds()
    {
        return diamonds;
    }
}
