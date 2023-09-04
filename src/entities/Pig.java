package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.*;
import static utils.Constants.EnemyConstants.*;


public class Pig extends Enemy
{

    private int attackBoxOffsetX;

    public Pig(float x, float y)
    {
        super(x, y, PIG_IMAGE_WIDTH, PIG_IMAGE_HEIGHT, PIG);
        initHitbox(14, 15);
        initAttackBox();
    }

    private void initAttackBox()
    {
        attackBox = new Rectangle2D.Float(x, y, (int) (26 * Game.SCALE), (int) (28 * Game.SCALE));
        attackBoxOffsetX = (int) (12 * Game.SCALE);
    }

    public void update(int[][] levelData, Player player)
    {
        updateBehavior(levelData, player);
        updateAnimationTick();
        updateAttackBox();
    }

    private void updateAttackBox()
    {
        if (walkDir == RIGHT)
        {
            attackBox.x = hitbox.x;
        } else if (walkDir == LEFT)
        {
            attackBox.x = hitbox.x - attackBoxOffsetX;
        }

        attackBox.y = hitbox.y - (13 * Game.SCALE);
    }


    public int flipX()
    {
        if (walkDir == RIGHT)
        {
            return width + 10;
        }
        return 0;
    }

    public int flipW()
    {
        if (walkDir == RIGHT)
        {
            return -1;
        }
        return 1;
    }

    private void updateBehavior(int[][] levelData, Player player)
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
            switch (action)
            {
                case IDLE:
                    newAction(RUN);
                    break;
                case RUN:
                    if (canSeePlayer(levelData, player))
                    {
                        turnTowardsPlayer(player);

                        if (isPlayerCloseToAttack(player))
                        {
                            newAction(ATTACK);
                        }
                    }
                    move(levelData);

                    break;
                case ATTACK:
                    if (aniIndex == 0)
                    {
                        attackChecked = false;
                    }

                    if (aniIndex == 3 && !attackChecked)
                    {
                        checkEnemyHit(attackBox, player);
                    }
                    break;
                case HIT:
                    break;
                default:
                    break;

            }
        }
    }
}
