package levels;

import entities.Pig;
import main.Game;
import objects.Box;
import objects.Cannon;
import objects.Diamond;
import objects.Spike;
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
    private ArrayList<Spike> spikes;
    private ArrayList<Diamond> diamonds;
    private ArrayList<Cannon> cannons;

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
        createSpikes();
        createCannons();
        calculateLevelOffsetX();
        calculatePlayerSpawn();
    }

    private void createCannons()
    {
        cannons = HelpMethods.GetCannons(levelImage);
    }

    private void createSpikes()
    {
        spikes = HelpMethods.GetSpikes(levelImage);
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

    public ArrayList<Spike> getSpikes()
    {
        return spikes;
    }

    public ArrayList<Cannon> getCannons()
    {
        return cannons;
    }
}
