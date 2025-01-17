package entities;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utils.Constants.ANIMATION_SPEED;
import static utils.Constants.EnemyConstants.*;
import static utils.Constants.Directions.*;
import static utils.Constants.GRAVITY;
import static utils.Constants.PlayerConstants.IDLE;
import static utils.HelpMethods.*;

public class Enemy extends Entity
{
    protected int enemyType;
    protected boolean firstUpdate = true;
    protected int walkDir = LEFT;
    protected int tileY;
    protected float attackDistance = Game.TILES_SIZE;
    protected boolean active = true;
    protected boolean attackChecked;


    public Enemy(float x, float y, int width, int height, int enemyType)
    {
        super(x, y, width, height);
        this.enemyType = enemyType;
        this.action = IDLE;
        walkSpeed = 0.35f * Game.SCALE;
        maxHealth = GetEnemyMaxHealth(enemyType);
        currentHealth = maxHealth;
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
            airSpeed += GRAVITY;
        } else
        {
            inAir = false;
            hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
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
            xSpeed = -walkSpeed;
        } else
        {
            xSpeed = walkSpeed;
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
        this.action = enemyAction;
        aniIndex = 0;
        aniTick = 0;
    }

    public void hurt(int amount)
    {
        currentHealth -= amount;
        if (currentHealth <= 0)
        {
            newAction(DEATH);
        } else
        {
            newAction(HIT);
        }
    }

    protected void checkEnemyHit(Rectangle2D.Float attackBox, Player player)
    {
        if(attackBox.intersects(player.hitbox))
        {
            player.changeHealth(-GetEnemyDamage(enemyType));
        }
        attackChecked = true;
    }

    protected void updateAnimationTick()
    {
        aniTick++;
        if (aniTick >= ANIMATION_SPEED)
        {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(enemyType, action))
            {
                aniIndex = 0;

                switch (action)
                {
                    case ATTACK,HIT -> action = IDLE;
                    case DEATH -> active = false;
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

    public boolean isActive()
    {
        return active;
    }

    public void resetEnemy()
    {
        hitbox.x = x;
        hitbox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        newAction(IDLE);
        active = true;
        airSpeed = 0;
    }
}
