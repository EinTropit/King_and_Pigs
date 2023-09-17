package objects;

import entities.Pig;
import entities.Player;
import gamestates.Playing;
import levels.Level;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;
import static utils.Constants.ObjectConstants.*;
import static utils.HelpMethods.CanCannonSeePlayer;

public class ObjectManager
{
    private Playing playing;
    private BufferedImage[][] DNHAnimations;
    private BufferedImage[] boxAnimations;
    private BufferedImage spikeImage;
    private BufferedImage[] cannonAnimations;

    private ArrayList<Diamond> diamonds;
    private ArrayList<Box> boxes;
    private ArrayList<Spike> spikes;
    private ArrayList<Cannon> cannons;

    public ObjectManager(Playing playing)
    {
        this.playing = playing;
        loadAnimations();
    }

    public void checkSpikesTouched(Player p)
    {
        for (Spike s : spikes)
        {
            if (p.getHitbox().intersects(s.getHitbox()))
            {
                p.kill();
            }
        }
    }

    public void checkObjectTouched(Rectangle2D.Float hitbox)
    {
        for (Diamond d : diamonds)
        {
            if (d.isActive())
            {
                if (hitbox.intersects(d.getHitbox()))
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
        for (Box b : boxes)
        {
            if (b.isActive() && !b.doAnimation)
            {
                if (b.getHitbox().intersects(attackbox))
                {
                    b.setAnimation(true);
                    diamonds.add(new Diamond((int) (b.getHitbox().x - b.xDrawOffset / 2), (int) (b.getHitbox().y - b.getHitbox().height), BIG_DIAMOND));
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

        BufferedImage boxSprite = LoadSave.GetSpriteAtlas(LoadSave.BOX_ATLAS);
        boxAnimations = new BufferedImage[8];

        for (int j = 0; j < boxAnimations.length; j++)
        {
            boxAnimations[j] = boxSprite.getSubimage(j * BOX_IMAGE_DEFAULT_WIDTH, 0, BOX_IMAGE_DEFAULT_WIDTH, BOX_IMAGE_DEFAULT_HEIGHT);
        }

        spikeImage = LoadSave.GetSpriteAtlas(LoadSave.SPIKE_ATLAS);

        BufferedImage cannonSprite = LoadSave.GetSpriteAtlas(LoadSave.CANON_ATLAS);
        cannonAnimations = new BufferedImage[5];

        for (int j = 0; j < cannonAnimations.length; j++)
        {
            cannonAnimations[j] = cannonSprite.getSubimage(j * CANNON_IMAGE_DEFAULT_WIDTH, 0, CANNON_IMAGE_DEFAULT_WIDTH, CANNON_IMAGE_DEFAULT_HEIGHT);
        }

    }

    public void update(int[][] levelData, Player player)
    {
        for (Diamond d : diamonds)
        {
            if (d.isActive())
            {
                d.update();
            }
        }
        for (Box b : boxes)
        {
            if (b.isActive())
            {
                b.update();
            }
        }

        updateCannons(levelData, player);
    }

    private void updateCannons(int[][] levelData, Player player)
    {
        System.out.println(player.getTileY());
        for (Cannon c : cannons)
        {
            System.out.println(player.getTileY());
            if(!c.doAnimation)
            {
                if (c.getTileY() == player.getTileY())
                {
                    if(isPlayerInRange(c, player))
                    {
                        if(isPlayerInFrontOfCannon(c, player))
                        {
                            if(CanCannonSeePlayer(levelData, player.getHitbox(), c.getHitbox(), c.getTileY()))
                            {
                                shootCannon(c);
                            }
                        }
                    }
                }
            }
            c.update();
        }


    }

    private void shootCannon(Cannon c)
    {
        c.setAnimation(true);
    }

    private boolean isPlayerInFrontOfCannon(Cannon c, Player player)
    {
        if(c.getObjectType() == CANNON_LEFT)
        {
            if(c.getHitbox().x > player.getHitbox().x)
            {
                return true;
            }
        } else if (c.getHitbox().x < player.getHitbox().x)
        {
            return true;
        }
        return false;
    }

    private boolean isPlayerInRange(Cannon c, Player player)
    {
        int absValue = (int) Math.abs(player.getHitbox().x - c.getHitbox().x);
        return absValue <= Game.TILES_SIZE * 5;
    }


    public void draw(Graphics g, int xLevelOffset)
    {
        drawDiamonds(g, xLevelOffset);
        drawBoxes(g, xLevelOffset);
        drawSpikes(g, xLevelOffset);
        drawCannons(g, xLevelOffset);
    }

    private void drawCannons(Graphics g, int xLevelOffset)
    {

        for (Cannon c : cannons)
        {
            g.drawImage(cannonAnimations[c.getAniIndex()],
                    (int) c.getHitbox().x - xLevelOffset - CANNON_DRAW_OFFSET_X + c.flipX(),
                    (int) c.getHitbox().y - CANNON_DRAW_OFFSET_Y, CANNON_IMAGE_WIDTH * c.flipW(),
                    PIG_IMAGE_HEIGHT, null);
            c.drawHitbox(g, xLevelOffset);
        }
    }

    private void drawSpikes(Graphics g, int xLevelOffset)
    {
        for (Spike s : spikes)
        {
            g.drawImage(spikeImage, (int) (s.getHitbox().x - xLevelOffset), (int) (s.getHitbox().y - s.getYDrawOffset()),
                    SPIKE_IMAGE_WIDTH, SPIKE_IMAGE_HEIGHT, null);
            s.drawHitbox(g, xLevelOffset);
        }
    }

    private void drawDiamonds(Graphics g, int xLevelOffset)
    {
        for (Diamond d : diamonds)
        {
            if (d.isActive())
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
        for (Box b : boxes)
        {
            if (b.isActive())
            {
                g.drawImage(boxAnimations[b.getAniIndex()],
                        (int) b.getHitbox().x - xLevelOffset - BOX_DRAW_OFFSET_X,
                        (int) b.getHitbox().y - BOX_DRAW_OFFSET_Y, BOX_IMAGE_WIDTH,
                        BOX_IMAGE_HEIGHT, null);
                b.drawHitbox(g, xLevelOffset);
            }
        }
    }


    public void resetAllObjects()
    {
        loadObjects(playing.getLevelManager().getCurrentLevel());
        for (Diamond d : diamonds)
        {
            d.reset();
        }
        for (Box b : boxes)
        {
            b.reset();
        }
        for (Cannon c : cannons)
        {
            c.reset();
        }
    }

    public void loadObjects(Level newLevel)
    {
        boxes = new ArrayList<>(newLevel.getBoxes());
        diamonds = new ArrayList<>(newLevel.getDiamonds());
        spikes = newLevel.getSpikes();
        cannons = newLevel.getCannons();
    }
}
