package utils;

import entities.Pig;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.PIG;


public class LoadSave
{

    public static final String PLAYER_ATLAS = "King_Human.png";
    public static final String LEVEL_ATLAS = "Terrain.png";
    public static final String MENU_BUTTONS = "button_atlas.png";
    public static final String MENU_BG = "menu_background.png";
    public static final String PAUSE_BG = "pause_menu.png";
    public static final String SOUND_BUTTONS = "sound_button.png";
    public static final String URM_BUTTONS = "urm_buttons.png";
    public static final String VOLUME_BUTTONS = "volume_buttons.png";
    public static final String PIG_ATLAS = "pig.png";
    public static final String STATUS_BAR = "health_power_bar.png";
    public static final String COMPLETED_BG = "completed_menu.png";
    public static final String BOX_ATLAS = "box.png";
    public static final String LIVES_AND_DIAMONDS = "lives_and_diamonds.png";
    public static final String SPIKE_ATLAS = "Spikes.png";
    public static final String CANON_ATLAS = "cannon.png";
    public static final String CANON_BALL_ATLAS = "cannon_ball.png";

    public static BufferedImage GetSpriteAtlas(String fileName)
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

    public static BufferedImage[] GetAllLevels()
    {
        URL url = LoadSave.class.getResource("/levels_res");
        File file = null;

        try
        {
            file = new File(url.toURI());
        } catch (URISyntaxException e)
        {
            e.printStackTrace();
        }

        File[] files = file.listFiles();
        File[] filesSorted = new File[files.length];

        for (int i = 0; i < filesSorted.length; i++)
        {
            for (int j = 0; j < filesSorted.length; j++)
            {
                if (files[j].getName().equals((i + 1) + ".png"))
                {
                    filesSorted[j] = files[i];
                }
            }
        }

        BufferedImage[] images = new BufferedImage[filesSorted.length];

        for (int i = 0; i < images.length; i++)
        {
            try
            {
                images[i] = ImageIO.read(filesSorted[i]);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return images;
    }
}
