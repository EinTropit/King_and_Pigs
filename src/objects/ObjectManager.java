package objects;

import gamestates.Playing;
import levels.Level;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.ObjectConstants.*;

public class ObjectManager
{
    private Playing playing;
    private BufferedImage[][] DNHAnimations;
    private BufferedImage[] BoxAnimations;
    private ArrayList<Diamond> diamonds;
    private ArrayList<Box> boxes;

    public ObjectManager(Playing playing)
    {
        this.playing = playing;
        loadAnimations();

        diamonds = new ArrayList<>();
        boxes = new ArrayList<>();

        diamonds.add(new Diamond(300,300,BIG_DIAMOND));
        boxes.add(new Box(500, 300));
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox)
    {
        for(Diamond d : diamonds)
        {
            if(d.isActive())
            {
                if(hitbox.intersects(d.getHitbox()))
                {
                    d.setActive(false);
                    applyEffectToPlayer(d);
                }
            }
        }
    }

    public void applyEffectToPlayer(Diamond d)
    {
        playing.getPlayer().changeScore();
    }

    public void checkObjectHit(Rectangle2D.Float attackbox)
    {
        for(Box b : boxes)
        {
            if(b.isActive() && b.getAniIndex() == 0)
            {
                if(b.getHitbox().intersects(attackbox))
                {
                    b.setAnimation(true);
                    diamonds.add(new Diamond((int)(b.getHitbox().x - b.xDrawOffset / 2), (int)(b.getHitbox().y - b.getHitbox().height), BIG_DIAMOND));
                    return;
                }
            }
        }
    }

    private void loadAnimations()
    {
        BufferedImage DNHSprite = LoadSave.GetSpriteAtlas(LoadSave.LIVES_AND_DIAMONDS);
        DNHAnimations = new BufferedImage[7][10];

        for (int j = 0; j < DNHAnimations.length; j++)
        {
            for (int i = 0; i < DNHAnimations[j].length; i++)
            {
                DNHAnimations[j][i] = DNHSprite.getSubimage(i * DNH_IMAGE_DEFAULT_WIDTH, j * DNH_IMAGE_DEFAULT_HEIGHT, DNH_IMAGE_DEFAULT_WIDTH, DNH_IMAGE_DEFAULT_HEIGHT);
            }
        }

        BufferedImage BoxSprite = LoadSave.GetSpriteAtlas(LoadSave.BOX_ATLAS);
        BoxAnimations = new BufferedImage[8];

        for (int j = 0; j < BoxAnimations.length; j++)
        {
            BoxAnimations[j] = BoxSprite.getSubimage(j * BOX_IMAGE_DEFAULT_WIDTH, 0, BOX_IMAGE_DEFAULT_WIDTH, BOX_IMAGE_DEFAULT_HEIGHT);
        }
    }

    public void update()
    {
        for(Diamond d : diamonds)
        {
            if(d.isActive())
            {
                d.update();
            }
        }
        for(Box b : boxes)
    {
        if(b.isActive())
        {
            b.update();
        }
    }
    }


    public void draw(Graphics g, int xLevelOffset)
    {
        drawDiamonds(g, xLevelOffset);
        drawBoxes(g, xLevelOffset);
    }

    private void drawDiamonds(Graphics g, int xLevelOffset)
    {
        for(Diamond d : diamonds)
        {
            if(d.isActive())
            {
                g.drawImage(DNHAnimations[BIG_DIAMOND][d.getAniIndex()],
                        (int) d.getHitbox().x - xLevelOffset - DIAMOND_DRAW_OFFSET_X,
                        (int) d.getHitbox().y - DIAMOND_DRAW_OFFSET_Y, DNH_IMAGE_WIDTH,
                        DNH_IMAGE_HEIGHT, null);
                d.drawHitbox(g, xLevelOffset);
            }
        }
    }

    private void drawBoxes(Graphics g, int xLevelOffset)
    {
        for(Box b : boxes)
        {
            if(b.isActive())
            {
                g.drawImage(BoxAnimations[b.getAniIndex()],
                        (int) b.getHitbox().x - xLevelOffset - BOX_DRAW_OFFSET_X,
                        (int) b.getHitbox().y - BOX_DRAW_OFFSET_Y, BOX_IMAGE_WIDTH,
                        BOX_IMAGE_HEIGHT, null);
                b.drawHitbox(g, xLevelOffset);
            }
        }
    }



    public void resetAllObjects()
    {
        for(Diamond d : diamonds)
        {
            d.reset();
        }
        for(Box b : boxes)
        {
            b.reset();
        }
    }

    public void loadObjects(Level newLevel)
    {
        boxes = newLevel.getBoxes();
        diamonds = newLevel.getDiamonds();
    }
}
