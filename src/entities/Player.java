package entities;

import main.Game;
import utils.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

public class Player extends Entity
{

    private BufferedImage[][] animations;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 10;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, right, up, down, jump;
    private float playerSpeed = 1.0f * Game.SCALE;
    private int[][] levelData;
    private float xDrawOffset = 23 * Game.SCALE;
    private float yDrawOffset = 19 * Game.SCALE;

    // jump and gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height)
    {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, (int) (19 * Game.SCALE), (int) (25 * Game.SCALE));
    }

    public void update()
    {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g, int xLevelOffset)
    {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset) - xLevelOffset, (int) (hitbox.y - yDrawOffset), width, height, null);
        //drawHitbox(g, xLevelOffset);
    }

    private void updateAnimationTick()
    {
        aniTick++;
        if (aniTick >= aniSpeed)
        {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(playerAction))
            {
                aniIndex = 0;
                attacking = false;
            }
        }
    }

    public void loadLevelData(int[][] levelData)
    {
        this.levelData = levelData;
        if (!IsEntityOnFloor(hitbox, levelData))
        {
            inAir = true;
        }
    }

    private void setAnimation()
    {
        int startAni = playerAction;

        if (moving)
            playerAction = RUN;
        else
            playerAction = IDLE;

        if (inAir)
        {
            if (airSpeed > 0)
                playerAction = JUMP;
            else
                playerAction = FALL;
        }

        if (attacking)
            playerAction = ATTACK;

        if (startAni != playerAction)
        {
            resetAniTick();
        }
    }

    private void resetAniTick()
    {
        aniTick = 0;
        aniIndex = 0;
    }

    private void updatePos()
    {
        moving = false;

        if (jump)
        {
            jump();
        }

        if(!inAir)
        {
            if((!left && !right) || (left && right))
            {
                return;
            }
        }

        float xSpeed = 0;

        if (left)
        {
            xSpeed -= playerSpeed;
        }
        if (right)
        {
            xSpeed += playerSpeed;
        }

        if (!inAir)
        {
            if (!IsEntityOnFloor(hitbox, levelData))
            {
                inAir = true;
            }
        }

        if (inAir)
        {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData))
            {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else
            {
                hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                {
                    resetInAir();
                } else airSpeed = fallSpeedAfterCollision;
                updateXPos(xSpeed);
            }
        } else
        {
            updateXPos(xSpeed);
        }

        moving = true;
    }

    private void jump()
    {
        if (inAir) return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir()
    {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed)
    {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData))
        {
            hitbox.x += xSpeed;
        } else
        {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
        }
    }

    private void loadAnimations()
    {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[10][11];

        for (int j = 0; j < animations.length; j++)
        {
            for (int i = 0; i < animations[j].length; i++)
            {
                animations[j][i] = img.getSubimage(i * 78, j * 58, 78, 58);
            }
        }

    }

    public boolean isLeft()
    {
        return left;
    }

    public void setLeft(boolean left)
    {
        this.left = left;
    }

    public boolean isRight()
    {
        return right;
    }

    public void setRight(boolean right)
    {
        this.right = right;
    }

    public boolean isUp()
    {
        return up;
    }

    public void setUp(boolean up)
    {
        this.up = up;
    }

    public boolean isDown()
    {
        return down;
    }

    public void setDown(boolean down)
    {
        this.down = down;
    }

    public boolean isJump()
    {
        return jump;
    }

    public void setJump(boolean jump)
    {
        this.jump = jump;
    }

    public void resetDirBooleans()
    {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public void setAttacking(boolean attacking)
    {
        this.attacking = attacking;
    }
}

