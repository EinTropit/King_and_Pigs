package levels;

import gamestates.Gamestate;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class LevelManager
{
    private Game game;
    private BufferedImage[] levelSprite;
    private ArrayList<Level> levels;
    private int levelIndex = 0;

    public LevelManager(Game game)
    {
        this.game = game;
        importOutSideSprites();
        levels = new ArrayList<>();
        buildAllLevels();
    }

    private void buildAllLevels()
    {
        BufferedImage[] allLevels = LoadSave.GetAllLevels();
        for(BufferedImage image : allLevels)
        {
            levels.add(new Level(image));
        }
    }

    private void importOutSideSprites()
    {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprite = new BufferedImage[8 * 12];
        for (int j = 0; j < 8; j++)
        {
            for (int i = 0; i < 12; i++)
            {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);

            }
        }
    }

    public void draw(Graphics g, int xLevelOffset)
    {
        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
        {
            for (int i = 0; i < levels.get(levelIndex).getLevelData()[0].length; i++)
            {
                int index = levels.get(levelIndex).getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], i * Game.TILES_SIZE - xLevelOffset, j * Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
        }

    }

    public void update()
    {

    }

    public Level getCurrentLevel()
    {
        return levels.get(levelIndex);
    }

    public int getAmountOfLevels()
    {
        return levels.size();
    }

    public void loadNextLevel()
    {
        levelIndex++;
        if(levelIndex >= levels.size())
        {
            levelIndex = 0;
            System.out.println("Game Compelte!");
            Gamestate.state = Gamestate.MENU;
        }

        Level newLevel = levels.get(levelIndex);
        game.getPlaying().getEnemyManager().loadEnemies(newLevel);
        game.getPlaying().getPlayer().loadLevelData(newLevel.getLevelData());
        game.getPlaying().setMaxLevelOffset(newLevel.getLevelOffset());
        game.getPlaying().getObjectManager().loadObjects(newLevel);
    }
}
