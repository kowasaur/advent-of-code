package utils;

public enum Direction {
    UP, DOWN, LEFT, RIGHT;

    public boolean horizontal() {
        return this == LEFT || this == RIGHT;
    }

    public boolean vertical() {
        return this == UP || this == DOWN;
    }

    public Direction clockwise() {
        if (this == UP) return RIGHT;
        if (this == RIGHT) return DOWN;
        if (this == DOWN) return LEFT;
        return UP;
    }

    public Direction anticlockwise() {
        if (this == RIGHT) return UP;
        if (this == UP) return LEFT;
        if (this == LEFT) return DOWN;
        return RIGHT;
    }
}
