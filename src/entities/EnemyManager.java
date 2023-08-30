package entities;

import gamestates.Playing;
import utils.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

public class EnemyManager
{
    private Playing playing;
    private BufferedImage[][] pigAnimations; // NOTICE: pig only currently
    private ArrayList<Pig> pigs = new ArrayList<>();

    public EnemyManager(Playing playing)
    {
        this.playing = playing;
        loadAnimations();
        addEnemies();
    }

    public void update(int[][] levelData, Player player)
    {
        for(Pig p : pigs)
        {
            p.update(levelData, player);
        }
    }

    public void draw(Graphics g, int xLevelOffset)
    {
        drawPigs(g, xLevelOffset);
    }

    private void addEnemies()
    {
        pigs = LoadSave.GetPigs();
        System.out.println("size of pigs: " + pigs.size());
    }

    private void drawPigs(Graphics g, int xLevelOffset)
    {
        for(Pig p : pigs)
        {
            g.drawImage(pigAnimations[p.getEnemyAction()][p.getAniIndex()], (int) p.getHitbox().x - xLevelOffset - PIG_DRAW_OFFSET_X, (int) p.getHitbox().y - PIG_DRAW_OFFSET_Y, PIG_IMAGE_WIDTH, PIG_IMAGE_HEIGHT, null);
            p.drawHitbox(g, xLevelOffset);
        }
    }


    private void loadAnimations()
    {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PIG_ATLAS);

        pigAnimations = new BufferedImage[8][11];

        for (int j = 0; j < pigAnimations.length; j++)
        {
            for (int i = 0; i < pigAnimations[j].length; i++)
            {
                pigAnimations[j][i] = img.getSubimage(i * PIG_IMAGE_DEFAULT_WIDTH, j * PIG_IMAGE_DEFAULT_HEIGHT, PIG_IMAGE_DEFAULT_WIDTH, PIG_IMAGE_DEFAULT_HEIGHT);
            }
        }
    }
}