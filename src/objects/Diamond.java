package objects;

import main.Game;

import static utils.Constants.ObjectConstants.*;

public class Diamond extends GameObject
{
    private float hoverOffset;
    private int maxHoverOffset, hoverDir = 1;
    public Diamond(int x, int y, int objectType)
    {
        super(x + (int) (Game.SCALE * 11), y + (int)(Game.SCALE * 13), objectType);
        doAnimation = true;
        initHitbox(12,10);
        xDrawOffset = DIAMOND_DRAW_OFFSET_X;
        yDrawOffset = DIAMOND_DRAW_OFFSET_Y;



        maxHoverOffset = (int) (10 * Game.SCALE);
    }

    public void  update()
    {
        updateAnimationTick();
        updateHover();
    }

    private void updateHover()
    {
        hoverOffset += (0.075f * Game.SCALE * hoverDir);
        if(hoverOffset >= maxHoverOffset)
        {
            hoverDir = -1;
        } else if (hoverOffset < 0)
        {
            hoverDir = 1;
        }
        hitbox.y = y + hoverOffset;
    }
}
