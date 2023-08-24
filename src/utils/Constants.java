package utils;

public class Constants {

    public static class Directions {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class PlayerConstants {
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

        public static int GetSpriteAmount(int player_action)
        {
            switch (player_action) {
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
}
