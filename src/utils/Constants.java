package utils;

import main.Game;

public class Constants
{

    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final float ANIMATION_SPEED = 10;

    public static class ObjectConstants
    {
        public static final int BIG_DIAMOND = 0;
        public static final int BIG_DIAMOND_HIT = 1;
        public static final int SMALL_DIAMOND = 7; //TODO: change back with box
        public static final int BIG_HEART = 3;
        public static final int BIG_HEART_HIT = 8; //TODO: change back with spike
        public static final int SMALL_HEART = 9;  //TODO: change back with cannon
        public static final int SMALL_HEART_HIT = 10; //TODO: change back with cannon
        public static final int BOX = 2;
        public static final int SPIKE = 4;
        public static final int CANNON_LEFT = 5;
        public static final int CANNON_RIGHT = 6;

        public static final int DNH_IMAGE_DEFAULT_WIDTH = 18;
        public static final int DNH_IMAGE_DEFAULT_HEIGHT = 14;
        public static final int DNH_IMAGE_WIDTH = (int) (DNH_IMAGE_DEFAULT_WIDTH * Game.SCALE);
        public static final int DNH_IMAGE_HEIGHT = (int) (DNH_IMAGE_DEFAULT_HEIGHT * Game.SCALE);
        public static final int DIAMOND_DRAW_OFFSET_X = (int) (5 * Game.SCALE);
        public static final int DIAMOND_DRAW_OFFSET_Y = (int) (2 * Game.SCALE);

        public static final int BOX_IMAGE_DEFAULT_WIDTH = 40;
        public static final int BOX_IMAGE_DEFAULT_HEIGHT = 30;
        public static final int BOX_IMAGE_WIDTH = (int) (BOX_IMAGE_DEFAULT_WIDTH * Game.SCALE);
        public static final int BOX_IMAGE_HEIGHT = (int) (BOX_IMAGE_DEFAULT_HEIGHT * Game.SCALE);
        public static final int BOX_DRAW_OFFSET_X = (int) (9 * Game.SCALE);
        public static final int BOX_DRAW_OFFSET_Y = (int) (14 * Game.SCALE);

        public static final int SPIKE_IMAGE_DEFAULT_WIDTH = 32;
        public static final int SPIKE_IMAGE_DEFAULT_HEIGHT = 32;
        public static final int SPIKE_IMAGE_WIDTH = (int) (SPIKE_IMAGE_DEFAULT_WIDTH * Game.SCALE);
        public static final int SPIKE_IMAGE_HEIGHT = (int) (SPIKE_IMAGE_DEFAULT_HEIGHT * Game.SCALE);

        public static final int CANNON_IMAGE_DEFAULT_WIDTH = 44;
        public static final int CANNON_IMAGE_DEFAULT_HEIGHT = 28;
        public static final int CANNON_IMAGE_WIDTH = (int) (CANNON_IMAGE_DEFAULT_WIDTH * Game.SCALE);
        public static final int CANNON_IMAGE_HEIGHT = (int) (CANNON_IMAGE_DEFAULT_HEIGHT * Game.SCALE);
        public static final int CANNON_DRAW_OFFSET_X = (int)(Game.SCALE * 17);
        public static final int CANNON_DRAW_OFFSET_Y = (int)(Game.SCALE * 7);

        public static int GetSpriteAmount(int objectType)
        {
            switch (objectType)
            {
                case BIG_DIAMOND:
                    return 10;

                case SMALL_DIAMOND:
                case BIG_HEART:
                case SMALL_HEART:
                case BOX:
                    return 8;
                case BIG_HEART_HIT:
                case BIG_DIAMOND_HIT:
                case SMALL_HEART_HIT:
                    return 2;

                case CANNON_LEFT, CANNON_RIGHT:
                    return 5;
                default:
                    return 1;
            }
        }
    }

    public static class UI
    {
        public static class MenuButtons
        {
            public static final int B_WIDTH_DEFAULT = 140;
            public static final int B_HEIGHT_DEFAULT = 56;
            public static final int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public static final int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);

        }

        public static class SoundButtons
        {
            public static final int SOUND_SIZE_DEFAULT = 42;
            public static final int SOUND_SIZE = (int) (SOUND_SIZE_DEFAULT * Game.SCALE);
        }

        public static class UrmButtons
        {
            public static final int URM_SIZE_DEFAULT = 56;
            public static final int URM_SIZE = (int) (URM_SIZE_DEFAULT * Game.SCALE);
        }

        public static class VolumeButtons
        {
            public static final int VOLUME_HEIGHT_DEFAULT = 44;
            public static final int VOLUME_WIDTH_DEFAULT = 28;
            public static final int SLIDER_WIDTH_DEFAULT = 215;


            public static final int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * Game.SCALE);
            public static final int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * Game.SCALE);
            public static final int SLIDER_WIDTH = (int) (SLIDER_WIDTH_DEFAULT * Game.SCALE);
        }

    }

    public static class Directions
    {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class images
    {
        public static final int PLAYER_IMAGE_DEFAULT_WIDTH = 78;
        public static final int PLAYER_IMAGE_DEFAULT_HEIGHT = 58;
        public static final int PLAYER_HITBOX_DEFAULT_WIDTH = 19;
        public static final int PLAYER_HITBOX_DEFAULT_HEIGHT = 25;
        public static final int PLAYER_HITBOX_DEFAULT_X_OFFSET = 23;
        public static final int PLAYER_HITBOX_DEFAULT_Y_OFFSET = 19;


        public static final int KING_PIG_IMAGE_DEFAULT_WIDTH = 38;
        public static final int KING_PIG_IMAGE_DEFAULT_HEIGHT = 28;
    }

    public static class PlayerConstants
    {
        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int GROUND = 4;
        public static final int ATTACK = 5;
        public static final int DEATH = 6;
        public static final int HIT = 7;
        public static final int DOOR_OUT = 8;
        public static final int DOOR_IN = 9;

        public static int GetSpriteAmount(int playerAction)
        {
            switch (playerAction)
            {
                case IDLE:
                    return 11;
                case JUMP:
                case FALL:
                case GROUND:
                    return 1;
                case ATTACK:
                    return 3;
                case DEATH:
                    return 4;
                case HIT:
                    return 2;
                case RUN:
                case DOOR_IN:
                case DOOR_OUT:
                    return 8;
                default:
                    return 1;
            }
        }
    }

    public static class EnemyConstants
    {
        public static final int PIG = 0;

        public static final int IDLE = 0;
        public static final int RUN = 1;
        public static final int JUMP = 2;
        public static final int FALL = 3;
        public static final int GROUND = 4;
        public static final int ATTACK = 5;
        public static final int HIT = 6;
        public static final int DEATH = 7;

        public static final int PIG_IMAGE_DEFAULT_WIDTH = 34;
        public static final int PIG_IMAGE_DEFAULT_HEIGHT = 28;
        public static final int PIG_IMAGE_WIDTH = (int) (PIG_IMAGE_DEFAULT_WIDTH * Game.SCALE);
        public static final int PIG_IMAGE_HEIGHT = (int) (PIG_IMAGE_DEFAULT_HEIGHT * Game.SCALE);
        public static final int PIG_DRAW_OFFSET_X = (int) (12 * Game.SCALE);
        public static final int PIG_DRAW_OFFSET_Y = (int) (12 * Game.SCALE);

        public static int GetSpriteAmount(int enemyType, int enemyAction)
        {
            switch (enemyType)
            {
            case PIG:
                switch (enemyAction)
                {
                    case IDLE:
                        return 11;
                    case JUMP:
                    case FALL:
                    case GROUND:
                        return 1;
                    case ATTACK:
                        return 5;
                    case DEATH:
                        return 4;
                    case HIT:
                        return 2;
                    case RUN:
                        return 6;
                    default:
                        return 1;
                }
            default:
                return 0;
            }
        }

        public static int GetEnemyMaxHealth(int enemyType)
        {
            switch (enemyType)
            {
                case PIG:
                    return 10;
                default:
                    return 0;
            }
        }

        public static int GetEnemyDamage(int enemyType)
        {
            switch (enemyType)
            {
                case PIG:
                    return 15;
                default:
                    return 0;
            }
        }

    }
}
