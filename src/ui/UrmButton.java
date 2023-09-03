package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.UrmButtons.*;

public class UrmButton extends PauseButton
{
    private BufferedImage[] urmImg;
    private boolean mouseOver, mousePressed;
    private int rowIndex, index;

    public UrmButton(int xPOS, int yPOS, int width, int height, int rowIndex)
    {
        super(xPOS, yPOS, width, height);
        this.rowIndex = rowIndex;
        loadImages();
    }

    private void loadImages()
    {
        BufferedImage temp = LoadSave.GetSpriteAtlas(LoadSave.URM_BUTTONS);
        urmImg = new BufferedImage[3];
        for (int i = 0; i < urmImg.length; i++)
        {
            urmImg[i] = temp.getSubimage(i * URM_SIZE_DEFAULT, rowIndex * URM_SIZE_DEFAULT, URM_SIZE_DEFAULT, URM_SIZE_DEFAULT);
        }
    }

    public void update()
    {
        index =  0;
        if(mouseOver)
        {
            index = 1;
        }
        if(mousePressed)
        {
            index = 2;
        }
    }

    public  void  resetBools()
    {
        mouseOver = false;
        mousePressed = false;
    }

    public void draw(Graphics g)
    {
        g.drawImage(urmImg[index], xPos, yPos, URM_SIZE, URM_SIZE, null);
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

}
