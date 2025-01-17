package ui;

import gamestates.Gamestate;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.MenuButtons.*;

public class MenuButton
{
     private int xPos, yPos;
     private int xOffsetCenter = B_WIDTH / 2;
     private int rowIndex, index;
     private Gamestate state;
     private BufferedImage[] imgs;
     private boolean mouseOver, mousePressed;
     private Rectangle bounds;

    public MenuButton(int xPOS, int yPOS, int rowIndex, Gamestate state)
    {
        this.xPos = xPOS;
        this.yPos = yPOS;
        this.rowIndex = rowIndex;
        this.state = state;
        loadImages();
        initBounds();
    }

    private void initBounds()
    {
        bounds = new Rectangle(xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT);
    }

    private void loadImages()
    {
        imgs = new BufferedImage[3];
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.MENU_BUTTONS);
        for(int i = 0; i < imgs.length; i++)
        {
            imgs[i] = temp.getSubimage(i * B_WIDTH_DEFAULT, rowIndex * B_HEIGHT_DEFAULT, B_WIDTH_DEFAULT, B_HEIGHT_DEFAULT);
        }
    }

    public void draw(Graphics g)
    {
        g.drawImage(imgs[index], xPos - xOffsetCenter, yPos, B_WIDTH, B_HEIGHT, null);
    }

    public void update()
    {
        index =  0;
        if(mouseOver)
            index = 1;
        if(mousePressed)
            index = 2;
    }

    public boolean isMouseOver()
    {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver)
    {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed()
    {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed)
    {
        this.mousePressed = mousePressed;
    }

    public void applyGameState()
    {
        Gamestate.state = state;
    }

    public void  resetBools()
    {
        mouseOver = false;
        mousePressed = false;
    }

    public Rectangle getBounds()
    {
        return bounds;
    }
}
