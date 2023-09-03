package entities;

import gamestates.Playing;
import levels.Level;
import utils.LoadSave;

import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
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
    }

    public void update(int[][] levelData, Player player)
    {
        boolean isAnyActive = false;
        for(Pig p : pigs)
        {
            if(p.isActive())
            {
                p.update(levelData, player);
                isAnyActive = true;
            }
        }
        if(!isAnyActive)
        {
            playing.setLevelCompleted(true);
        }
    }

    public void draw(Graphics g, int xLevelOffset)
    {
        drawPigs(g, xLevelOffset);
    }

    public void loadEnemies(Level level)
    {
        pigs = level.getPigs();

    }

    private void drawPigs(Graphics g, int xLevelOffset)
    {
        for(Pig p : pigs)
        {
            if(p.isActive())
            {
                g.drawImage(pigAnimations[p.getEnemyAction()][p.getAniIndex()],
                        (int) p.getHitbox().x - xLevelOffset - PIG_DRAW_OFFSET_X + p.flipX(),
                        (int) p.getHitbox().y - PIG_DRAW_OFFSET_Y, PIG_IMAGE_WIDTH * p.flipW(),
                        PIG_IMAGE_HEIGHT, null);
                p.drawHitbox(g, xLevelOffset);
                p.drawAttackBox(g, xLevelOffset);
            }
        }
    }

    public void checkEnemyHit(Rectangle2D.Float attackBox)
    {
        for(Pig p : pigs)
        {
            if(p.isActive())
            {
                if (attackBox.intersects(p.getHitbox()))
                {
                    p.hurt(10);
                    return;
                }
            }
        }
    }


    private void loadAnimations()
    {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PIG_ATLAS);

        pigAnimations = new BufferedImage[8][11];

        for (int j = 0; j < pigAnimations.length; j++)
        {
            for (int i = 0; i < pigAnimations[j].length; i++)
            {
                pigAnimations[j][i] = img.getSubimage(i * PIG_IMAGE_DEFAULT_WIDTH, j * PIG_IMAGE_DEFAULT_HEIGHT, PIG_IMAGE_DEFAULT_WIDTH, PIG_IMAGE_DEFAULT_HEIGHT);
            }
        }
    }

    public void resetAllEnemies()
    {
        for(Pig p : pigs)
        {
            p.resetEnemy();
        }
    }
}
