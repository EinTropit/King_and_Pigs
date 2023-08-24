package entities;

import main.Game;
import utils.LoadSave;

import java.awt.Graphics;
import java.awt.image.BufferedImage;


import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.CanMoveHere;

public class Player extends Entity
{

    private BufferedImage[][] animations;
    private int aniTick = 0, aniIndex = 0, aniSpeed = 12;
    private int playerAction = IDLE;
    private boolean moving = false, attacking = false;
    private boolean left, right, up, down;
    private float playerSpeed = 2.0f;
    private int[][] levelData;
    private float xDrawOffset = 23 * Game.SCALE;
    private float yDrawOffset = 19 * Game.SCALE;

    public Player(float x, float y, int width, int height)
    {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 19 * Game.SCALE, 26 * Game.SCALE);
    }

    public void update()
    {
        updatePos();
        updateAnimationTick();
        setAnimation();
    }

    public void render(Graphics g)
    {
        g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y-yDrawOffset), width, height, null);
        drawHitbox(g);
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
    }

    private void setAnimation()
    {
        int startAni = playerAction;

        if (moving)
            playerAction = RUN;
        else
            playerAction = IDLE;

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

        if (!left && !right && !up && !down)
            return;

        float xSpeed = 0, ySpeed = 0;

        if (left && !right)
        {
            xSpeed = -playerSpeed;
        } else if (!left && right)
        {
            xSpeed = playerSpeed;
        }

        if (up && !down)
        {
            ySpeed = -playerSpeed;
        } else if (!up && down)
        {
            ySpeed = playerSpeed;
        }

        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y + ySpeed, hitbox.width, hitbox.height, levelData))
        {
            hitbox.x += xSpeed;
            hitbox.y += ySpeed;
            moving = true;
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
