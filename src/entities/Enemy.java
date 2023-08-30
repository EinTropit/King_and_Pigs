package entities;

import main.Game;


import static utils.Constants.EnemyConstants.*;
import static utils.Constants.Directions.*;
import static utils.HelpMethods.*;

public class Enemy extends Entity
{
    private int aniTick = 0, aniIndex = 0, aniSpeed = 10;
    protected int enemyAction, enemyType;

    protected boolean firstUpdate = true;

    // movement, jump and gravity
    protected float airSpeed = 0f;
    protected float gravity = 0.04f * Game.SCALE;
    protected float fallSpeed = 0.5f * Game.SCALE;
    protected boolean inAir = false;
    protected float enemySpeed = 0.35f * Game.SCALE;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;

    public Enemy(float x, float y, int width, int height, int enemyType)
    {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitbox(x, y, width, height);
    }

    protected void firstUpdateCheck(int[][] levelData)
    {
        if (!IsEntityOnFloor(hitbox, levelData))
        {
            inAir = true;
        }
        firstUpdate = false;
    }

    protected void updateInAir(int[][] levelData)
    {
        if (CanMoveHere(hitbox.x, hitbox.y, hitbox.width, hitbox.height, levelData))
        {
            hitbox.y += airSpeed;
            airSpeed += gravity;
        } else
        {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, fallSpeed);
            tileY = (int) (hitbox.y / Game.TILES_SIZE);
        }
    }

    protected void turnTowardsPlayer(Player player)
    {
        if (player.hitbox.x > hitbox.x)
        {
            walkDir = RIGHT;
        } else
        {
            walkDir = LEFT;
        }
    }

    protected boolean canSeePlayer(int[][] levelData, Player player)
    {
        int playerTileY = (int) (player.hitbox.y / Game.TILES_SIZE);
        if (playerTileY == tileY)
        {
            if (isPlayerInRange(player))
            {
                if (IsSightClear(levelData, hitbox, player.hitbox, tileY))
                {
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean isPlayerInRange(Player player)
    {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance * 5;
    }

    protected boolean isPlayerCloseToAttack(Player player)
    {
        int absValue = (int) Math.abs(player.hitbox.x - hitbox.x);
        return absValue <= attackDistance;
    }

    protected void move(int[][] levelData)
    {
        float xSpeed = 0;
        if (walkDir == LEFT)
        {
            xSpeed = -enemySpeed;
        } else
        {
            xSpeed = enemySpeed;
        }

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData))
        {
            if (IsFloor(hitbox, xSpeed, levelData))
            {
                hitbox.x += xSpeed;
                return;
            }
        }

        changeWalkDir();
    }

    protected void newAction(int enemyAction)
    {
        this.enemyAction = enemyAction;
        aniIndex = 0;
        aniTick = 0;
    }


    protected void updateAnimationTick()
    {
        aniTick++;
        if (aniTick >= aniSpeed)
        {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, enemyAction))
            {
                aniIndex = 0;
                if(enemyAction == ATTACK)
                {
                    enemyAction = IDLE;
                }
            }
        }
    }


    protected void changeWalkDir()
    {
        if (walkDir == LEFT)
        {
            walkDir = RIGHT;
        } else
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
