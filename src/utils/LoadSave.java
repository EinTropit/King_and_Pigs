package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave
{

    public static final String PLAYER_ATLAS = "King_Human.png";
    public static final String LEVEL_ATLAS = "Terrain.png";
    public static final String LEVEL_ONE_DATA = "level_one_data.png";
    //public static final String LEVEL_ONE_DATA = "level_one_data.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BG = "menu_background.png";
    public static final String PAUSE_BG = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";


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

    public static int[][] GetLevelData()
    {
        int[][] levelData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = getSpriteAtlas(LEVEL_ONE_DATA);

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
