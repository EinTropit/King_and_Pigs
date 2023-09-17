package objects;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Projectiles.*;

public class Projectile
{
    private Rectangle2D.Float hitbox;
    private int dir;
    private boolean active = true;

    public Projectile(int x, int y, int dir)
    {
        int xOffset = 0;
        int yOffset = 2;
        if(dir == 1)
        {
            xOffset = (int) (Game.SCALE * (23 - CANNON_BALL_HITBOX_WIDTH));
        }

        hitbox = new Rectangle2D.Float(x + xOffset, y + yOffset, CANNON_BALL_HITBOX_WIDTH * Game.SCALE, CANNON_BALL_HITBOX_HEIGHT * Game.SCALE);
        this.dir = dir;
    }

    public void updatePos()
    {
        hitbox.x += dir * SPEED;
    }

    public void setPos(int x, int y)
    {
        hitbox.x = x;
        hitbox.y = y;
    }

    public Rectangle2D.Float getHitbox()
    {
        return hitbox;
    }

    public void setActive(boolean active)
    {
        this.active = active;
    }

    public boolean isActive()
    {
        return active;
    }

    //TODO: may need to remove
    public void drawHitbox(Graphics g, int xLevelOffset)
    {
        if (!Game.drawHitbox)
        {
            return;
        }
        g.setColor(Color.pink);
        g.drawRect((int) hitbox.x - xLevelOffset, (int) hitbox.y, (int) hitbox.width, (int) hitbox.height);
    }

}
