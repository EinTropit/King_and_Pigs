package entities;

import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;


import static utils.Constants.ANIMATION_SPEED;
import static utils.Constants.GRAVITY;
import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

public class Player extends Entity
{

    private BufferedImage[][] animations;
    private boolean moving = false, attacking = false;
    private boolean left, right, jump;
    private int[][] levelData;
    private float xDrawOffset = 23 * Game.SCALE;
    private float yDrawOffset = 19 * Game.SCALE;

    // jump and gravity
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCollision = 0.5f * Game.SCALE;

    // status bar UI
    private BufferedImage statusBarImage;

    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);

    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);

    private int healthWidth = healthBarWidth;

    private int flipX = 0;
    private int flipW = 1;

    private int tileY = 0;

    private boolean attackChecked;
    private Playing playing;

    public Player(float x, float y, int width, int height, Playing playing)
    {
        super(x, y, width, height);
        this.playing = playing;
        this.action = IDLE;
        maxHealth = 100;
        currentHealth = maxHealth;
        walkSpeed = 1.0f * Game.SCALE;
        loadAnimations();
        initHitbox(19, 25);
        initAttackBox();
    }

    public void setSpawn(Point spawn)
    {
        this.x = spawn.x;
        this.y = spawn.y;
        hitbox.x = x;
        hitbox.y = y;
    }

    private void initAttackBox()
    {
        attackBox = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));
    }


    public void update()
    {
        updateHealthBar();

        if(currentHealth <= 0)
        {
            playing.setGameOver(true);
            return;
        }

        updateAttackBox();

        updatePos();
        if(moving)
        {
            checkDiamondTouched();
            checkSpikesTouched();
            tileY = (int)(hitbox.y / Game.TILES_SIZE);
        }
        if(attacking)
        {
            checkAttack();
        }
        updateAnimationTick();
        setAnimation();
    }

    private void checkSpikesTouched()
    {
        playing.checkSpikesTouched(this);
    }

    private void checkDiamondTouched()
    {
        playing.checkDiamondTouched(hitbox);
    }

    private void checkAttack()
    {
        if(attackChecked || aniIndex != 0)
        {
            return;
        }
        attackChecked = true;
        playing.checkEnemyHit(attackBox);
        playing.checkObjectHit(attackBox);

    }

    private void updateAttackBox()
    {
        if (right)
        {
            attackBox.x = hitbox.x + hitbox.width + (int) (15 * Game.SCALE);
        } else if (left)
        {
            attackBox.x = hitbox.x - hitbox.width - (int) (15 * Game.SCALE);
        }

        attackBox.y = hitbox.y + (int) (5 * Game.SCALE);
    }

    private void updateHealthBar()
    {
        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void render(Graphics g, int xLevelOffset)
    {
        g.drawImage(animations[action][aniIndex], (int) (hitbox.x - xDrawOffset) - xLevelOffset + flipX, (int) (hitbox.y - yDrawOffset), width * flipW, height, null);
        drawHitbox(g, xLevelOffset);
        drawAttackBox(g, xLevelOffset);

        drawUI(g);
    }

    private void drawUI(Graphics g)
    {
        g.drawImage(statusBarImage, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.red);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    public void changeHealth(int value)
    {
        currentHealth += value;
        if (currentHealth <= 0)
        {
            currentHealth = 0;
        } else if (currentHealth >= maxHealth)
        {
            currentHealth = maxHealth;
        }
    }

    public void kill()
    {
        currentHealth = 0;
    }

    private void updateAnimationTick()
    {
        aniTick++;
        if (aniTick >= ANIMATION_SPEED)
        {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= GetSpriteAmount(action))
            {
                aniIndex = 0;
                attacking = false;
                attackChecked = false;
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
        int startAni = action;

        if (moving)
            action = RUN;
        else
            action = IDLE;

        if (inAir)
        {
            if (airSpeed > 0)
                action = JUMP;
            else
                action = FALL;
        }

        if (attacking)
            action = ATTACK;

        if (startAni != action)
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

        if (!inAir)
        {
            if ((!left && !right) || (left && right))
            {
                return;
            }
        }

        float xSpeed = 0;

        if (left)
        {
            xSpeed -= walkSpeed;
            flipX = width - 25;
            flipW = -1;
        }
        if (right)
        {
            xSpeed += walkSpeed;
            flipX = 0;
            flipW = 1;
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
                airSpeed += GRAVITY;
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
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[10][11];

        for (int j = 0; j < animations.length; j++)
        {
            for (int i = 0; i < animations[j].length; i++)
            {
                animations[j][i] = img.getSubimage(i * 78, j * 58, 78, 58);
            }
        }

        statusBarImage = LoadSave.GetSpriteAtlas(LoadSave.STATUS_BAR);
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
    }

    public void setAttacking(boolean attacking)
    {
        this.attacking = attacking;
    }

    public void resetAll()
    {
        resetDirBooleans();
        inAir = false;
        attacking = false;
        moving = false;
        action = IDLE;
        currentHealth = maxHealth;

        hitbox.x = x;
        hitbox.y = y;

        if (!IsEntityOnFloor(hitbox, levelData))
        {
            inAir = true;
        }
    }

    public void changeScore()
    {
        System.out.println("added Score!");
    }

    public int getTileY()
    {
        return tileY;
    }

}

