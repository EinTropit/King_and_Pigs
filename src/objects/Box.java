package objects;

import main.Game;

import static utils.Constants.ObjectConstants.*;

public class Box extends GameObject
{
    public Box(int x, int y)
    {
        super(x, y, BOX);
        initHitbox(22,16);
        xDrawOffset = BOX_DRAW_OFFSET_X;
        yDrawOffset = BOX_DRAW_OFFSET_Y;
        hitbox.y += yDrawOffset + (int)(Game.SCALE * 2);
        hitbox.x += xDrawOffset / 2;
    }

    public void  update()
    {
        if(doAnimation)
        {
            updateAnimationTick();
        }
    }
}
