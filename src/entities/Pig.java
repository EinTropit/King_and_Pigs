package entities;

import main.Game;

import static utils.Constants.Directions.LEFT;
import static utils.Constants.EnemyConstants.*;
import static utils.HelpMethods.*;

public class Pig extends Enemy
{

    public Pig(float x, float y)
    {
        super(x, y, PIG_IMAGE_WIDTH, PIG_IMAGE_HEIGHT, PIG);
        initHitbox(x, y, (int) (14 * Game.SCALE), (int) (15 * Game.SCALE));
    }

    public void update(int[][] levelData)
    {
        updatePos(levelData);
        updateAnimationTick();
    }

    private void updatePos(int[][] levelData)
    {
        if (firstUpdate)
        {
            firstUpdateCheck(levelData);
        }

        if (inAir)
        {
            updateInAir(levelData);
        } else
        {
            switch (enemyAction)
            {
                case IDLE:
                    newAction(RUN);
                    break;
                case RUN:
                   move(levelData);

                    break;
                default:
                    break;

            }
        }
    }
}
