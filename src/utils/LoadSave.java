package utils;

import entities.Pig;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.PIG;


public class LoadSave
{

    public static final String PLAYER_ATLAS = "King_Human.png";
    public static final String LEVEL_ATLAS = "Terrain.png";
    //public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String LEVEL_ONE_DATA = "level_one_data_long.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BG = "menu_background.png";
    public static final String PAUSE_BG = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String PIG_ATLAS = "pig.png";


    public static BufferedImage getSpriteAtlas(String fileName)
    {
        InputStream is = LoadSave.class.getResourceAsStream("/" + fileName);
        BufferedImage img = null;
        try
        {
            img = ImageIO.read(is);

        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                is.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static ArrayList<Pig> GetPigs()
    {
        BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);
        ArrayList<Pig> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
        {
            for (int i = 0; i < img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == PIG)
                {
                    list.add(new Pig(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }
            }
        }
        return list;
    }

    public static int[][] GetLevelData()
    {
        BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);
        int[][] levelData = new int[img.getHeight()][img.getWidth()];

        for (int j = 0; j < img.getHeight(); j++)
        {
            for (int i = 0; i < img.getWidth(); i++)
            {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value > 8 * 12)
                    value = 0;
                levelData[j][i] = value;

            }
        }
        return levelData;
    }
}
