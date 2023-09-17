package utils;

import entities.Pig;
import main.Game;
import objects.*;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.PIG;
import static utils.Constants.ObjectConstants.*;

public class HelpMethods
{
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelData)
    {
        if (!IsSolid(x, y, levelData))
        {
            if (!IsSolid(x + width, y + height, levelData))
            {
                if (!IsSolid(x, y + height, levelData))
                {
                    if (!IsSolid(x + width, y, levelData))
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] levelData)
    {
        int maxWidth = levelData[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth)
            return true;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        return IsTileSolid((int) xIndex, (int) yIndex, levelData);
    }

    private static boolean IsTileSolid(int xTile, int yTile, int[][] levelData)
    {
        int value = levelData[yTile][xTile];
        if (value >= 48 || value < 0 || value == 11)
            return false;
        return true;
    }

    public static boolean IsProjectileHittingLevel(Projectile p, int[][] levelData)
    {
        return IsSolid(p.getHitbox().x + p.getHitbox().width / 2, p.getHitbox().y + p.getHitbox().height / 2, levelData);
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed)
    {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0)
        {
            //Right
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        }
        //Left
        return currentTile * Game.TILES_SIZE;
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed)
    {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0)
        {
            //Falling - Touching floor
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        }
        // Jumping
        return currentTile * Game.TILES_SIZE;
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelData)
    {
        //check the pixel below the bottom left and bottom right
        if (!IsSolid(hitbox.x, hitbox.y + hitbox.height + 1, levelData))
        {
            if (!IsSolid(hitbox.x + hitbox.width, hitbox.y + hitbox.height + 1, levelData))
            {
                return false;
            }
        }
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelData)
    {

        if (xSpeed > 0)
            return IsSolid(hitbox.x + hitbox.width + xSpeed, hitbox.y + hitbox.height + 1, levelData);
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height + 1, levelData);
    }


    public static boolean IsSightClear(int[][] levelData, Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2, int yTile)
    {
        int firstXTile = (int) (hitbox1.x / Game.TILES_SIZE);
        int secondXTile = (int) (hitbox2.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesWalkable(secondXTile, firstXTile, yTile, levelData);
        else
            return IsAllTilesWalkable(firstXTile, secondXTile, yTile, levelData);
    }

    public static boolean IsAllTilesClear(int xStart, int xEnd, int y, int[][] levelData)
    {
        for (int i = 0; i < xEnd - xStart; i++)
            if (IsTileSolid(xStart + i, y, levelData))
                return false;
        return true;
    }

    public static boolean IsAllTilesWalkable(int xStart, int xEnd, int y, int[][] levelData)
    {
        if (IsAllTilesClear(xStart, xEnd, y, levelData))
            for (int i = 0; i < xEnd - xStart; i++)
            {
                if (!IsTileSolid(xStart + i, y + 1, levelData))
                    return false;
            }
        return true;
    }

    public static boolean CanCannonSeePlayer(int[][] levelData, Rectangle2D.Float hitbox1, Rectangle2D.Float hitbox2, int yTile)
    {
        int firstXTile = (int) (hitbox1.x / Game.TILES_SIZE);
        int secondXTile = (int) (hitbox2.x / Game.TILES_SIZE);

        if (firstXTile > secondXTile)
            return IsAllTilesClear(secondXTile, firstXTile, yTile, levelData);
        else
            return IsAllTilesClear(firstXTile, secondXTile, yTile, levelData);
    }


    public static int[][] GetLevelData(BufferedImage image)
    {
        int[][] levelData = new int[image.getHeight()][image.getWidth()];

        for (int j = 0; j < image.getHeight(); j++)
        {
            for (int i = 0; i < image.getWidth(); i++)
            {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getRed();
                if (value > 8 * 12)
                    value = 0;
                levelData[j][i] = value;

            }
        }
        return levelData;
    }

    public static ArrayList<Pig> GetPigs(BufferedImage image)
    {
        ArrayList<Pig> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
        {
            for (int i = 0; i < image.getWidth(); i++)
            {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == PIG)
                {
                    list.add(new Pig(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static ArrayList<Box> GetBoxes(BufferedImage image)
    {
        ArrayList<Box> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
        {
            for (int i = 0; i < image.getWidth(); i++)
            {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == BOX)
                {
                    list.add(new Box(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static ArrayList<Diamond> GetDiamonds(BufferedImage image)
    {
        ArrayList<Diamond> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
        {
            for (int i = 0; i < image.getWidth(); i++)
            {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == BIG_DIAMOND)
                {
                    list.add(new Diamond(i * Game.TILES_SIZE, j * Game.TILES_SIZE, BIG_DIAMOND));
                }
            }
        }
        return list;
    }

    public static ArrayList<Spike> GetSpikes(BufferedImage image)
    {
        ArrayList<Spike> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
        {
            for (int i = 0; i < image.getWidth(); i++)
            {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == SPIKE)
                {
                    list.add(new Spike(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static ArrayList<Cannon> GetCannons(BufferedImage image)
    {
        ArrayList<Cannon> list = new ArrayList<>();
        for (int j = 0; j < image.getHeight(); j++)
        {
            for (int i = 0; i < image.getWidth(); i++)
            {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getBlue();
                if (value == CANNON_LEFT || value == CANNON_RIGHT)
                {
                    list.add(new Cannon(i * Game.TILES_SIZE, j * Game.TILES_SIZE, value));
                }
            }
        }
        return list;
    }

    public static Point GetPlayerSpawn(BufferedImage image)
    {
        for (int j = 0; j < image.getHeight(); j++)
        {
            for (int i = 0; i < image.getWidth(); i++)
            {
                Color color = new Color(image.getRGB(i, j));
                int value = color.getGreen();
                if (value == 100)
                {
                    return new Point(i * Game.TILES_SIZE, j * Game.TILES_SIZE);
                }
            }
        }
        return new Point(1 * Game.TILES_SIZE, 1 * Game.TILES_SIZE);
    }
}
