package entities;

import main.Game;

import static utils.Constants.EnemyConstants.*;

public class Pig extends Enemy
{

    public Pig(float x, float y)
    {
        super(x, y, PIG_IMAGE_WIDTH, PIG_IMAGE_HEIGHT, PIG);
        initHitbox(x, y, (int) (14 * Game.SCALE), (int) (15 * Game.SCALE));
    }
}
