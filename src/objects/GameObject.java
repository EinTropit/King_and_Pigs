package objects;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.ANIMATION_SPEED;
import static utils.Constants.ObjectConstants.*;

public class GameObject
{
    protected int x, y, objectType;
    protected Rectangle2D.Float hitbox;
    protected boolean doAnimation, active = true;
    protected int aniTick, aniIndex;
    protected int xDrawOffset, yDrawOffset;

    public GameObject(int x, int y, int objectType)
    {
        this.x = x;
        this.y = y;
        this.objectType = objectType;
    }

    protected void updateAnimationTick()
    {
        aniTick++;
        if (aniTick >= ANIMATION_SPEED)
        {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(objectType))
            {
                aniIndex = 0;
                if (objectType == BOX)
                {
                    doAnimation = false;
                    active = false;
                }
            }
        }
    }

    public void reset()
    {
        aniIndex = 0;
        aniTick = 0;
        active = true;

        if (objectType == BOX)
        {
            doAnimation = false;
        } else
        {
            doAnimation = true;
        }

    }

    protected void initHitbox(int width, int height)
    {
        hitbox = new Rectangle2D.Float(x, y, width * Game.SCALE, height * Game.SCALE);
    }

    public Rectangle2D.Float getHitbox()
    {
        return hitbox;
    }

    public void drawHitbox(Graphics g, int xLevelOffset)
    {
        if (!Game.drawHitbox)
        {
            return;
        }
        g.setColor(Color.pink);
        g.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

    public int getObjectType()
    {
        return objectType;
    }

    public boolean isActive()
    {
        return active;
    }

    public void setActive(boolean value)
    {
        active = value;
    }

    public void setAnimation(boolean value)
    {
        doAnimation = value;
    }

    public int getAniIndex()
    {
        return aniIndex;
    }

    public int getXDrawOffset()
    {
        return xDrawOffset;
    }

    public int getYDrawOffset()
    {
        return yDrawOffset;
    }
}
