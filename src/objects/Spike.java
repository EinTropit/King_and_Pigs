package objects;

import main.Game;

import static utils.Constants.ObjectConstants.SPIKE;

public class Spike extends GameObject
{

    public Spike(int x, int y)
    {
        super(x, y, SPIKE);
        initHitbox(32, 16);
        xDrawOffset = 0;
        yDrawOffset = (int)(Game.SCALE * 16);
        hitbox.y += yDrawOffset;
    }
}
