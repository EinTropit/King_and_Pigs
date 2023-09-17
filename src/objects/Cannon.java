package objects;

import main.Game;

import java.awt.*;

import static utils.Constants.ObjectConstants.*;


public class Cannon extends GameObject
{
    private int tileY;

    public Cannon(int x, int y, int objectType)
    {
        super(x, y, objectType);
        tileY = y / Game.TILES_SIZE;
        initHitbox(23, 18);
        xDrawOffset = CANNON_DRAW_OFFSET_X;
        yDrawOffset = CANNON_DRAW_OFFSET_Y;
        hitbox.y += (int) (Game.SCALE * 14);
        hitbox.x += (int) (Game.SCALE * 4);
    }

    public void update()
    {
        if(doAnimation)
        {
            updateAnimationTick();
        }
    }

    public int getTileY()
    {
        return tileY;
    }

    public int flipX()
    {
        if (objectType == CANNON_RIGHT)
        {
            return (int) hitbox.width + 69;
        }
        return 0;
    }

    public int flipW()
    {
        if (objectType == CANNON_RIGHT)
        {
            return -1;
        }
        return 1;
    }

}
