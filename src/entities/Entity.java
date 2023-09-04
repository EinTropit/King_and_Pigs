package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity
{
    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitbox;
    protected int aniTick = 0, aniIndex = 0;
    protected int action;
    protected float airSpeed;
    protected boolean inAir = false;
    protected int maxHealth;
    protected int currentHealth;
    protected Rectangle2D.Float attackBox;
    protected float walkSpeed;

    public Entity(float x, float y, int width, int height)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void initHitbox(int width, int height)
    {
        hitbox = new Rectangle2D.Float( x, y, width * Game.SCALE, height * Game.SCALE);
    }

    public Rectangle2D.Float getHitbox()
    {
        return hitbox;
    }

    protected void drawHitbox(Graphics g, int xLevelOffset)
    {
        if(!Game.drawHitbox)
        {
            return;
        }
        g.setColor(Color.pink);
        g.drawRect((int)hitbox.x - xLevelOffset, (int)hitbox.y, (int)hitbox.width, (int)hitbox.height);
    }

    public void drawAttackBox(Graphics g, int xLevelOffset)
    {
        if(!Game.drawAttackBox)
        {
            return;
        }
        g.setColor(Color.red);
        g.drawRect((int) attackBox.x - xLevelOffset, (int) attackBox.y, (int) attackBox.width, (int) attackBox.height);
    }

    public int getAniIndex()
    {
        return aniIndex;
    }

    public int getAction()
    {
        return action;
    }
}
