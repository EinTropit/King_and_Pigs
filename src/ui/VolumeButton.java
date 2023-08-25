package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.VolumeButtons.*;


public class VolumeButton extends PauseButton
{
    private BufferedImage[] volumeImg;
    private BufferedImage sliderImg;
    private boolean mouseOver, mousePressed;
    private int index;
    private int buttonX, minX, maxX;

    public VolumeButton(int xPOS, int yPOS, int width, int height)
    {
        super(xPOS + width / 2, yPOS, VOLUME_WIDTH, height);
        bounds.x -= VOLUME_WIDTH / 2;
        this.xPos = xPOS;
        this.width = width;
        buttonX = xPos + width / 2;
        minX = xPos + VOLUME_WIDTH / 2;
        maxX = xPos + width - VOLUME_WIDTH / 2;
        loadImages();
    }

    private void loadImages()
    {
        BufferedImage temp = LoadSave.getSpriteAtlas(LoadSave.VOLUME_BUTTONS);
        volumeImg = new BufferedImage[3];

        for (int i = 0; i < volumeImg.length; i++)
        {
            volumeImg[i] = temp.getSubimage(i * VOLUME_WIDTH_DEFAULT, 0, VOLUME_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
        }
        sliderImg = temp.getSubimage(3* VOLUME_WIDTH_DEFAULT, 0, SLIDER_WIDTH_DEFAULT, VOLUME_HEIGHT_DEFAULT);
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
        g.drawImage(sliderImg, xPos, yPos, width, height, null);
        g.drawImage(volumeImg[index], buttonX - VOLUME_WIDTH / 2, yPos, VOLUME_WIDTH, height, null);
    }

    public void changeX(int x)
    {
        if(x < minX)
        {
            buttonX = minX;
        }
        else if(x > maxX)
        {
            buttonX = maxX;
        }
        else
        {
            buttonX = x;
        }

        bounds.x = buttonX;
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
