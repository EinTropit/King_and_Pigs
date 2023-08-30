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

    public void update(int[][] levelData, Player player)
    {
        updatePos(levelData, player);
        updateAnimationTick();
    }

    private void updatePos(int[][] levelData, Player player)
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
                    if(canSeePlayer(levelData, player))
                    {
                        turnTowardsPlayer(player);
                    }
                    if(isPlayerCloseToAttack(player))
                    {
                        newAction(ATTACK);
                    }
                   move(levelData);

                    break;
                default:
                    break;

            }
        }
    }
}
