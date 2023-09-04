package utils;

import main.Game;

public class Constants
{

    public static final float GRAVITY = 0.04f * Game.SCALE;
    public static final float ANIMATION_SPEED = 10;

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
