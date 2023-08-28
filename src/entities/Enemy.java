package entities;

import main.Game;


import static utils.Constants.EnemyConstants.*;
import static utils.Constants.Directions.*;
import static utils.HelpMethods.*;

public class Enemy extends Entity
{
    private int aniTick = 0, aniIndex = 0, aniSpeed = 10;
    private int enemyAction, enemyType;

    private boolean firstUpdate = true;

    // movement, jump and gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float fallSpeed = 0.5f * Game.SCALE;
    private boolean inAir = false;
    private float enemySpeed = 0.35f * Game.SCALE;
    private int walkDir = LEFT;

    public Enemy(float x, float y, int width, int height, int enemyType)
    {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    private void updateAnimationTick()
    {
        aniTick++;
        if (aniTick >= aniSpeed)
        {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyAction))
            {
                aniIndex = 0;
                //attacking = false;
            }
        }
    }

    public void update(int[][] levelData)
    {
        updatePos(levelData);
        updateAnimationTick();
        //setAnimation();
    }

    private void updatePos(int[][] levelData)
    {
        if (firstUpdate)
        {
            if (!IsEntityOnFloor(hitbox, levelData))
            {
                inAir = true;
            }
            firstUpdate = false;
        }

        if (inAir)
        {
            if (CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData))
            {
                hitbox.y += airSpeed;
                airSpeed += gravity;
            } else
            {
                inAir = false;
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            }
        } else
        {
            switch (enemyAction)
            {
                case IDLE:
                    enemyAction = RUN;
                    break;
                case RUN:
                    float xSpeed = 0;
                    if(walkDir == LEFT)
                    {
                        xSpeed = -enemySpeed;
                    }
                    else
                    {
                        xSpeed = enemySpeed;
                    }

                    if(CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData))
                    {
                        if(IsFloor(hitbox, xSpeed, levelData))
                        {
                            hitbox.x += xSpeed;
                            return;
                        }
                    }

                    changeWalkDir();

                    break;
                default:
                    break;

            }
        }
    }

    private void changeWalkDir()
    {
        if(walkDir == LEFT)
        {
            walkDir = RIGHT;
        }
        else
            walkDir = LEFT;
    }

    public int getAniIndex()
    {
        return aniIndex;
    }

    public int getEnemyAction()
    {
        return enemyAction;
    }
}
