package ui;

import gamestates.Gamestate;

import java.awt.*;
import java.awt.image.BufferedImage;


public class PauseButton
{
    protected int xPos, yPos, width, height;
    protected Rectangle bounds;

    public PauseButton(int xPOS, int yPOS, int width, int height)
    {
        this.xPos = xPOS;
        this.yPos = yPOS;
        this.width = width;
        this.height = height;
        initBounds();
    }

    private void initBounds()
    {
        bounds = new Rectangle(xPos, yPos, width, height);
    }

    public int getxPos()
    {
        return xPos;
    }

    public void setxPos(int xPos)
    {
        this.xPos = xPos;
    }

    public int getyPos()
    {
        return yPos;
    }

    public void setyPos(int yPos)
    {
        this.yPos = yPos;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }

    public void setBounds(Rectangle bounds)
    {
        this.bounds = bounds;
    }
}
